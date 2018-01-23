package com.myappilication.xpress.finjan2017.menulist;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.format.Formatter;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.myappilication.xpress.finjan2017.ChangePassword;
import com.myappilication.xpress.finjan2017.ListofModuleFinjan;
import com.myappilication.xpress.finjan2017.MainActivity;
import com.myappilication.xpress.finjan2017.ModuleFinJan;
import com.myappilication.xpress.finjan2017.ProfileSetting;
import com.myappilication.xpress.finjan2017.R;
import com.myappilication.xpress.finjan2017.TryFinStart;
import com.myappilication.xpress.finjan2017.allcalculatorlist.AllCalcListActivity;
import com.myappilication.xpress.finjan2017.feedback.FeedActivity;
import com.myappilication.xpress.finjan2017.menulist.mycamsinfo.MyCamResponse;
import com.myappilication.xpress.finjan2017.menulist.mycamsschemelist.SchemeListResponse;
import com.myappilication.xpress.finjan2017.models.login.helpers.NetConnectionDetector;
import com.myappilication.xpress.finjan2017.models.login.helpers.SharedPrefUtils;
import com.myappilication.xpress.finjan2017.models.login.helpers.StaticConfig;
import com.myappilication.xpress.finjan2017.models.login.login.loginreq;
import com.myappilication.xpress.finjan2017.models.login.login.loginresp;
import com.myappilication.xpress.finjan2017.models.login.modulelist.ListOfModuleResponse;
import com.myappilication.xpress.finjan2017.models.login.mycamssetting.MycamSettingResponse;
import com.myappilication.xpress.finjan2017.models.login.offlineDatabase.OfflineDatabaseHelper;
import com.myappilication.xpress.finjan2017.newfaqcategroylist.FaqCategroyLIstActivity;
import com.myappilication.xpress.finjan2017.newfeedback.NewFeedbackActivity;
import com.myappilication.xpress.finjan2017.progressstyle.ProgressBarStyle;
import com.myappilication.xpress.finjan2017.termscondition.Support;
import com.myappilication.xpress.finjan2017.webservice.RxClient;

import org.apache.commons.codec.binary.Hex;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Scheme extends AppCompatActivity {
    WebView web;
    ProgressBar progressBar;

    Toolbar toolbar;

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    NetConnectionDetector NDC;
    Context context;

    public static ArrayList<Activity> scheme_act = new ArrayList<>();

    String clientname, secKey, hmacKey, encryKey, decryKey;

    String isinCode, schemename, amc_code;
    static SchemeListResponse schemeResponse;

    private static Cipher cipher;
    private static byte[] key, iv;
    public static StringBuilder sb = null;

    private ProgressDialog pDialog;

    public static Dialog mprProgressDialog;

    OfflineDatabaseHelper offlineDB;

    private enum EncryptMode {
        ENCRYPT, DECRYPT;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheme);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = Scheme.this;
        scheme_act.add(Scheme.this);

        sb = null;

        sharedpreferences = getSharedPreferences(SharedPrefUtils.MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        progressBar = (ProgressBar) findViewById(R.id.scheme_progressBar);
        //progressBar.setVisibility(View.VISIBLE);

        mprProgressDialog = ProgressBarStyle.getInstance().createProgressDialog(this);

        if (NDC.isConnected(context)) {
            callWebService();
        }else{
            Toast.makeText(context, "Kindly check your network connection", Toast.LENGTH_LONG).show();
        }

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        offlineDB = new OfflineDatabaseHelper(Scheme.this);

        ImageButton imageButton = (ImageButton) findViewById(R.id.tb_normal_back);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        web = (WebView) findViewById(R.id.commentsView);

        web.setWebViewClient(new myWebClient());
        web.getSettings().setJavaScriptEnabled(true);

        web.loadUrl(StaticConfig.html_Base+"/schemes.html");

        web.addJavascriptInterface(new JavaScriptInterface(this), "Android");
    }

    public class JavaScriptInterface {
        Context mContext;

        /** Instantiate the interface and set the context */
        JavaScriptInterface(Context c) {
            mContext = c;
        }


        @JavascriptInterface
        public void showToast(String toast) {
           // Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
          //  startActivity(new Intent(WebViewDemo.this, WebViewDemo.class));
                if(schemeResponse != null){
                    for (int t = 0; t < schemeResponse.getResult().getInfo().getSchemes().length; t++) {
                        String scID = schemeResponse.getResult().getInfo().getSchemes()[t].
                                getId();
                        if(toast.equalsIgnoreCase(scID)){
                            String finalid = schemeResponse.getResult().getInfo().getSchemes()[t].
                                    getId();
                            schemename = schemeResponse.getResult().getInfo().getSchemes()[t].
                                    getSchemes_name();
                            isinCode = schemeResponse.getResult().
                                    getInfo().getSchemes()[t]
                                    .getIsin_code();
                            amc_code = schemeResponse.getResult().getInfo().getSchemes()
                                    [t].getAmc_code();

                            Log.d("isin code", isinCode);

                            String[] arr = new String[]{schemename, isinCode, amc_code};
                            new GetData().execute(arr);
                            break;
                        }
                    }
                }
        }
    }



    public class myWebClient extends WebViewClient
    {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
            //progressBar.setVisibility(View.GONE);
            //progressBar.setVisibility(View.VISIBLE);
            mprProgressDialog.show();

        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub

            view.loadUrl(url);
            return true;

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
            mprProgressDialog.dismiss();

        }
    }

    // To handle "Back" key press event for WebView to go back to previous screen.
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && web.canGoBack()) {
            web.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.fin_support:
                startActivity(new Intent(getApplicationContext(), Support.class));
                return true;

            case R.id.finstart_c:
                String isusrgetModid = sharedpreferences.getString("isusergetmoduleid", "");
                //  String isusrgetModid = sharedpreferences.getString("isusergetmoduleid", "");

                if(isusrgetModid.equalsIgnoreCase("5")){
                    Intent i = new Intent(getApplicationContext(), ListofModuleFinjan.class);
                    i.putExtra("moduleID", "5");
                    finish();

                    ModuleFinJan.courseID = "5";
                    ModuleFinJan.courseName = "Finstart";
                    startActivity(i);
                }else{
                    Intent i = new Intent(getApplicationContext(), TryFinStart.class);
                    finish();
                    startActivity(i);

                }
                return true;

            case R.id.finpedia:
                startActivity(new Intent(getApplicationContext(), FaqCategroyLIstActivity.class));
                finish();
                ModuleFinJan.courseID = "5";
                // finish();
                return true;

            case R.id.calc:
                startActivity(new Intent(getApplicationContext(), AllCalcListActivity.class));
                finish();
                return true;

            case R.id.learning_center:
                startActivity(new Intent(getApplicationContext(), Learning_centre.class));
                finish();
                // finish();
                return true;

            case R.id.articles:
                startActivity(new Intent(getApplicationContext(), MediaActivity.class));
                finish();
                return true;

            case R.id.ss_selection:
                /*startActivity(new Intent(getApplicationContext(), Scheme.class));
                finish();*/
                return true;

            /*case R.id.li_invest:
                startActivity(new Intent(getApplicationContext(), Link_To_Interest.class));
                finish();
                return true;*/

            /*case R.id.fb_post:
                startActivity(new Intent(getApplicationContext(), FB_Posts.class));
                return true;*/

            case R.id.profile_menu:
                startActivity(new Intent(getApplicationContext(), ProfileSetting.class));
                finish();
                return true;
            /*case R.id.finstaffcources:
                startActivity(new Intent(getApplicationContext(), ModuleFinJan.class));
                return true;*/
            case R.id.changepassword:
                startActivity(new Intent(getApplicationContext(), ChangePassword.class));
                finish();
                return true;

            case R.id.feedback:
                if (NDC.isConnected(context)) {
                    startActivity(new Intent(getApplicationContext(), NewFeedbackActivity.class));
                    finish();
                    return true;
                }else{
                    Toast.makeText(getApplicationContext(), "Kindly check your network connection",
                            Toast.LENGTH_LONG).show();
                    return false;
                }

            /*case R.id.feedback_list:
                OfflineFeedbackDB feedbackDB = new OfflineFeedbackDB(this);
                String em = sharedpreferences.getString(SharedPrefUtils.SpEmail, "");
                ArrayList<String> list = feedbackDB.getdata(em);
                if(list.size() > 0){
                    startActivity(new Intent(getApplicationContext(), UserFeedbackList.class));
                    return true;
                }else{
                    Toast.makeText(DashBoard.this, "No records", Toast.LENGTH_SHORT).show();
                    return false;
                }*/

            case R.id.logout:
                sharedpreferences = getSharedPreferences(SharedPrefUtils.MyPREFERENCES, Context.MODE_PRIVATE);
                editor = sharedpreferences.edit();

                editor.putString(SharedPrefUtils.SpEmail, "");
                editor.putString(SharedPrefUtils.SpPassword, "");
                editor.putBoolean(SharedPrefUtils.SpIsNewUser, true);
                editor.commit();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("EXIT", true);
                startActivity(intent);


                editor.remove("couponbaseModuleid");
                editor.remove("isusergetmoduleid");
                editor.remove("isusergetexpdate");
                editor.apply();

                offlineDB.deleteAll();

                finish();
                return true;


        }
        return false;
    }


    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_for_all, menu);

        return true;
    }


    private void callWebService() {

        mprProgressDialog.show();

        RxClient.get(getApplicationContext()).schemeList(new Callback<SchemeListResponse>() {
            @Override
            public void success(final SchemeListResponse schemeListResponse, Response response) {
                //INF209K01S38

                if (schemeListResponse.getStatus().equals("200")) {

                    schemeResponse = schemeListResponse;



                   /*  for (int t = 0; t < schemeListResponse.getResult().getInfo().getSchemes().length; t++) {
                        String scName = schemeListResponse.getResult().getInfo().getSchemes()[t].
                                getSchemes_name();
                        
                       isinCode = schemeListResponse.getResult().
                                getInfo().getSchemes()[Integer.valueOf(v.getTag().toString())]
                                .getIsin_code();

                        schemename = schemeListResponse.getResult().getInfo().getSchemes()
                                [Integer.valueOf(v.getTag().toString())].getSchemes_name();
                        String amc_code = schemeListResponse.getResult().getInfo().getSchemes()
                                [Integer.valueOf(v.getTag().toString())].getAmc_code();

                        Log.d("isin code", isinCode);

                        Log.d("scheme name", schemename);
                        String[] arr = new String[]{schemename, isinCode, amc_code};
                        new GetData().execute(arr);

                       

                    }*/
                }

                callWebService1();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("", "");
                Toast.makeText(Scheme.this, error.toString(),
                        Toast.LENGTH_LONG).show();
                finish();
            }
        });

    }

    private void callWebService1() {

        RxClient.get(getApplicationContext()).mycamssetting(sharedpreferences.getString(SharedPrefUtils.
                        SpRememberToken, ""),
                new Callback<MycamSettingResponse>() {
                    @Override
                    public void success(MycamSettingResponse r, Response response) {

                        if(r.getStatus().equals("200")){
                            for(int t=0; t<r.getResult().getInfo().getCams().length; t++){
                                clientname = r.getResult().getInfo().getCams()[t].client_id;
                                secKey = r.getResult().getInfo().getCams()[t].secret_key;
                                hmacKey = r.getResult().getInfo().getCams()[t].hmac_key;
                                encryKey = r.getResult().getInfo().getCams()[t].encryption_key;
                                decryKey = r.getResult().getInfo().getCams()[t].decryption_key;
                            }
                            //progressBar.setVisibility(View.GONE);
                        //    mprProgressDialog.dismiss();

                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d("", "");

                        try{
                            MycamSettingResponse usere = (MycamSettingResponse)
                                    error.getBodyAs(MycamSettingResponse.class);

                            if(usere.getStatus().equalsIgnoreCase("402")){
                                mtd_refresh_token();
                            }else{
                                mprProgressDialog.dismiss();
                                Toast.makeText(Scheme.this, error.toString(),
                                        Toast.LENGTH_LONG).show();
                            }

                        }catch (Exception e){
                            Toast.makeText(Scheme.this, e.toString(),
                                    Toast.LENGTH_LONG).show();
                            finish();
                        }

                    }
                });

    }


    private void mtd_refresh_token() {
       /* Toast.makeText(context, "expired", Toast.LENGTH_SHORT).show();*/
        RxClient.get(Scheme.this).Login(new loginreq(sharedpreferences.
                getString(SharedPrefUtils.SpEmail, ""),
                sharedpreferences.getString(SharedPrefUtils.SpPassword, "")), new Callback<loginresp>() {
            @Override
            public void success(loginresp loginresp, Response response) {

                if (loginresp.getStatus().equals("200")){

                    editor.putString(SharedPrefUtils.SpRememberToken,loginresp.getToken().toString());
                    editor.commit();

                    final Handler handler = new Handler();
                    final Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            callWebService1();
                            //progressBar.setVisibility(View.INVISIBLE);
                        }
                    };
                    handler.postDelayed(runnable, 500);

                }
            }

            @Override
            public void failure(RetrofitError error) {
             //   progressBar.setVisibility(View.INVISIBLE);
                mprProgressDialog.dismiss();
                Log.d("refresh token", "refresh token error");
                Toast.makeText(Scheme.this, "Service not response",
                        Toast.LENGTH_LONG).show();
                finish();
            }
        });

    }



    private class GetData extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(Scheme.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
          //  mprProgressDialog.show();
        }


        @Override
        protected Void doInBackground(String... params) {

            ServiceHandler sh = new ServiceHandler();
            WifiManager wm = (WifiManager) getSystemService(WIFI_SERVICE);

            String em = sharedpreferences.getString(SharedPrefUtils.SpEmail, "");
            //192.168.0.1
            //FPP

            String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
            String versionRelease = Build.VERSION.RELEASE;
            Log.d("ip address", ip);


            String android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            Log.d("device id", android_id);



            SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMddHHmmss");
            Date now = new Date();
            String strDate = sdfDate.format(now);
            Log.e("DATE", strDate);
            String encodedString = null;
            try{

                Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
                if(hmacKey.length()>0){
                    SecretKeySpec secret_key = new SecretKeySpec(hmacKey.getBytes("UTF-8"), "HmacSHA256");
                    sha256_HMAC.init(secret_key);
                    String mValueToEncode = clientname+"::"+secKey+"::"+strDate;

                    //FINSAFE::d38nr0WoVJhGH64oZdHSrF5k5poaoOOWRloXMqQbDbE=::" + strDate

                    // clientname+"::"+secKey+"::"+strDate

                    encodedString = new String(Hex.encodeHex((sha256_HMAC.
                            doFinal(mValueToEncode.getBytes("ISO-8859-1")))));
                }

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();

            } catch (InvalidKeyException e) {
                e.printStackTrace();
            }

           /* String postData3 = "arnainar@gmail.com|9952980099|P|DIRECT|GROWTH||INF109KA1XQ0||||||" +
                    "B5C661A5-7028- 49C5-83A6-A188EB816526" +
                    "|10.1|ANDROID|192.168.95.65|FINSAFE|20170720100957|" +
                    "dbb402feed78d3c0cc4723ca92774e7d3a47e7e160b4cb23a5577af1034f350a"*/


           /* String postData3 = "dinesh@gmail.com|9840964767|P|DIRECT|GROWTH||INF109KA1XQ0||||||" +
                    "B5C661A5-7028-49C5-83A6-A188EB816526|10.1|ANDROID|192.168.95.65|FINSAFE|"+""+"|" +
                    "d106f9002ef23415e916fb7c648be7a7e5b8fadaec69d7413f065b2caf451341";*/

            /*redmi@gmail.com|7338746633|D|DIRECT|GROWTH|INF740K01OK1||||||36a0c7a71cf10956|4.4.2|
                    ANDROID|192.168.1.234|FINSAFE||
                    5d84724513591425848f4660366b4c953f1bdecc00ea6693ae671c64cd746ab5*/

           /* String postData3 = em + "|" + "7338746633" + "|" + params[2] +
                    "|" + "DIRECT" + "|" + "GROWTH" + "|" + params[1] + "||||||" +
                    android_id
                    + "|" + versionRelease + "|" + "ANDROID" + "|" + ip + "|" +
                    "FINSAFE" + "|" + "" + "|" + encodedString;*/


            //current

            ///////////////////////////////////////////
            /*String postData3 = em+"|9840964767|"+params[2]+"|DIRECT|"+"GROWTH||"+params[1]+"||||||" +
                    android_id+"|"+versionRelease+"|"+"ANDROID|"+ip+"|FINSAFE|"+strDate+"|" +
                    encodedString;*/

           /* String postData3 = em+"|"+"9444145558"+"|"+params[2]+"|DIRECT|"+"GROWTH||"+params[1]+"||||||" +
                    android_id+"|"+versionRelease+"|"+"ANDROID|"+ip+"|"+clientname+"|"+strDate+"|" +
                    encodedString;*/

            /////////////////////////////////////////////

            String mnum =  sharedpreferences.getString("mobilenum", "");

            String postData3 = em+"|"+"9444145558"+"|"+params[2]+"|DIRECT|"+"GROWTH||"+params[1]+"||||||" +
                    android_id+"|"+versionRelease+"|"+"ANDROID|"+ip+"|"+clientname+"|"+strDate+"|" +
                    encodedString;

         /*   output
            google@gmail.com|7338746633|B|DIRECT|GROWTH|INF209K01XX1|bbbd5b1ae6636ed9
                    |6.0.1|ANDROID|192.168.1.228|FINSAFE
                    20170803135503|1504c7fd577ae29fa631d7a2cab2748c3237ea0f9b1e73e4c2429ee85adfc5da*/

            String output = null;
            try {

                output = EnCrypt(postData3, decryKey);
                Log.d("encrypt output", output);

            } catch (Exception e) {
                e.printStackTrace();
            }

            String KEY_AES = "c7v1s0c2m";
            String key_ivs = "globalaesvectors";



            try {
                    /*byte[] b = new byte[16];

                    SecretKey secret = getSecretKey(KEY_AES.toCharArray(), b);
                   *//* Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

                    cipher.init(Cipher.ENCRYPT_MODE, secret, new IvParameterSpec(new byte[cipher.getBlockSize()]));
                    String  ciphertext = Base64.encodeToString(cipher.doFinal(postData3.getBytes("UTF-8")),
                            Base64.DEFAULT);*//*



                    byte[] key = KEY_AES.getBytes("UTF-8");
                    byte[] ivs = key_ivs.getBytes("UTF-8");
                    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
                    SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");

                    AlgorithmParameterSpec paramSpec = new IvParameterSpec(ivs);
                    cipher.init(Cipher.ENCRYPT_MODE, secret, paramSpec);
                    output = Base64.encodeToString(cipher.doFinal(postData3.getBytes("UTF-8")), Base64.DEFAULT);

                    Log.d("error", output);*/

            } catch (Exception e) {
                Log.d("error", e.toString());
            }

            Map<String, String> mapParams = new HashMap<String, String>();
            mapParams.put("value", output);

            Collection<Map.Entry<String, String>> postData1 = mapParams.entrySet();

            sb = new StringBuilder();
            sb.append("<html><head></head>");
            sb.append("<body onload='form1.submit()'>");
            sb.append(String.format("<form id='form1' action='%s' method='%s'>",
                    StaticConfig.JSON_URL, "post"));

            for (Map.Entry<String, String> item : postData1)

                try {
                    sb.append(String.format("<input name='mycams_data' type='hidden' value='%s' />",
                            item.getValue()));

                } catch (Exception e) {
                    e.printStackTrace();
                }

            sb.append("</form></body></html>");
          pDialog.dismiss();

           // mprProgressDialog.dismiss();

            Log.d("mycams value", sb.toString());

            Intent t = new Intent(Scheme.this, MyCamWebviewAct.class);
            t.putExtra("schemename", schemename);
            t.putExtra("isincode", isinCode);
            startActivity(t);

            return null;
        }


      /*  Authentication

        ==============

        Client ID       : FINSAFE

        Secret Key    : d38nr0WoVJhGH64oZdHSrF5k5poaoOOWRloXMqQbDbE=

        HMAC Key    : RklOU0FGRWhtYWM=

        Input validation

        ================

        Encryption Key    : c7v1s0c2m

        Decryption Key    : m1c7s2v0c  */



    }

    @SuppressLint("TrulyRandom")
    public static String EnCrypt(String strInput, String de) throws Exception {
        String tempStr = "";
        try {
            //  byte[] b = new byte[32];
            //  byte[] ivs = new byte[16];
            key = new byte[32];
            //SecretKey secret = getSecretKey(KEY_AES.toCharArray(), b);
            iv = new byte[16];
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            String output= "";
            String plainText=strInput;
            String key = SHA256("c7v1s0c2m", 32); //32 bytes = 256 bit
            output= encryptDecrypt(plainText, key, EncryptMode.ENCRYPT, "globalaesvectors");
            Log.e("encrypted text=",output);
            tempStr=output.replace("+","-").replace("/","_");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return tempStr;
    }


    private static String encryptDecrypt(String inputText, String encryptionKey,
                                         EncryptMode mode, String initVector) throws UnsupportedEncodingException,
            InvalidKeyException, InvalidAlgorithmParameterException,
            IllegalBlockSizeException, BadPaddingException {
        String out = "";
        int len = encryptionKey.getBytes("UTF-8").length;
        if (encryptionKey.getBytes("UTF-8").length > key.length)
            len = key.length;
        int ivlen = initVector.getBytes("UTF-8").length;
        if (initVector.getBytes("UTF-8").length > iv.length)
            ivlen = iv.length;
        System.arraycopy(encryptionKey.getBytes("UTF-8"), 0, key, 0, len);
        System.arraycopy(initVector.getBytes("UTF-8"), 0, iv, 0, ivlen);
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        if (mode.equals(EncryptMode.ENCRYPT)) {
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
            byte[] results = cipher.doFinal(inputText.getBytes("UTF-8"));
            out = Base64.encodeToString(results, Base64.DEFAULT);
        }
        System.out.println(out);
        return out;
    }




    public static String SHA256(String text, int length) throws
            NoSuchAlgorithmException, UnsupportedEncodingException {
        String resultStr;
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(text.getBytes("UTF-8"));
        byte[] digest = md.digest();
        StringBuffer result = new StringBuffer();
        for (byte b : digest) {
            result.append(String.format("%02x", b)); //convert to hex
        }
        if (length > result.toString().length()) {
            resultStr = result.toString();
        } else {
            resultStr = result.toString().substring(0, length);
        }
        return resultStr;

    }






    private SecretKey getSecretKey(char[] chars, byte[] salt) {
        SecretKeyFactory factory = null;
        try {
            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

            KeySpec spec = new PBEKeySpec(chars, salt, 1024, 128);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");
            return(secret);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        // NOTE: last argument is the key length, and it is 128
        return null;
    }

    @Override
    protected void onStart() {
        super.onStart();



    }

}
