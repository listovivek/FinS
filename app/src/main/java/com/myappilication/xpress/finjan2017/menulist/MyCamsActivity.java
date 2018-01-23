package com.myappilication.xpress.finjan2017.menulist;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.myappilication.xpress.finjan2017.R;
import com.myappilication.xpress.finjan2017.menulist.mycamsinfo.MyCamResponse;
import com.myappilication.xpress.finjan2017.menulist.mycamsschemelist.SchemeListResponse;
import com.myappilication.xpress.finjan2017.models.login.helpers.PermissionString;
import com.myappilication.xpress.finjan2017.models.login.helpers.SharedPrefUtils;
import com.myappilication.xpress.finjan2017.models.login.mycamssetting.MycamSettingResponse;
import com.myappilication.xpress.finjan2017.webservice.RxClient;

import org.apache.http.NameValuePair;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.commons.codec.binary.Hex;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

/**
 * Created by suresh on 19/7/17.
 */
public class MyCamsActivity extends AppCompatActivity {

    LinearLayout linear;

    private ProgressDialog pDialog;

    public static String vJsonStr;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    public static StringBuilder sb = null;

    //https://eiscuat1.camsonline.com/camsapp/myCAMSEKYCCheck.aspx

    public static final String JSON_URL = "https://eiscweb.camsonline.com/CAMSApp_Test/mycamsekyccheck.aspx";

    private static final int PERMISSIONS_REQUEST_READ_PHONE_STATE = 999;
    private TelephonyManager mTelephonyManager;


    private static Cipher cipher;
    private static byte[] key, iv;

    private enum EncryptMode {
        ENCRYPT, DECRYPT;
    }

    private String permission1, permission2;
    private static final int STORAGE_PERMISSION_REQUEST_CODE = 91;

    String isinCode, schemename;

    String clientname, secKey, hmacKey, encryKey, decryKey;



    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scheme_list);

        linear = (LinearLayout) findViewById(R.id.scheme_list);

        sharedpreferences = getSharedPreferences(SharedPrefUtils.MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        //int version = Build.VERSION.SDK_INT;

        permission1 = PermissionString.READ_EXTERNAL_STORAGE;

        ImageButton imageButton = (ImageButton) findViewById(R.id.tb_normal_back);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        callWebService();

        CheckAndRequestPermission();
    }

    private void CheckAndRequestPermission() {

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (checkPermission(permission1)) {
                return;

            }
            requestPermission(permission1);

        }
    }

    private void requestPermission(String Permission) {
        ActivityCompat.requestPermissions(MyCamsActivity.this,
                new String[]{Permission}, STORAGE_PERMISSION_REQUEST_CODE);
    }

    private boolean checkPermission(String permission) {

        int result = ContextCompat.checkSelfPermission(MyCamsActivity.this, permission);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {

            return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case STORAGE_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permissionGranted();

                    Log.d("", "");
                    //Intent2Activity();
                } else {

                    /*Toast.makeText(context, "Kindly, give storage permission to store and" +
                            " access the video and audio", Toast.LENGTH_SHORT).show();*/

                }
                break;

        }
    }


    private void callWebService() {


        RxClient.get(getApplicationContext()).schemeList(new Callback<SchemeListResponse>() {
            @Override
            public void success(final SchemeListResponse schemeListResponse, Response response) {


                if (schemeListResponse.getStatus().equals("200")) {
                    for (int t = 0; t < schemeListResponse.getResult().getInfo().getSchemes().length; t++) {
                        String scName = schemeListResponse.getResult().getInfo().getSchemes()[t].
                                getSchemes_name();


                        final Button btnTag = new Button(MyCamsActivity.this);
                        btnTag.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.
                                LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                        btnTag.setText(schemeListResponse.getResult().getInfo().getSchemes()[t].getSchemes_name());
                        btnTag.setBackgroundResource(R.drawable.list_of_module_btn);

                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

                        params.setMargins(15, 10, 15, 2);
                        btnTag.setTextColor(Color.WHITE);

                        btnTag.setLayoutParams(params);

                        btnTag.setTag(t);
                        linear.addView(btnTag);
                        btnTag.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                               int ii = Integer.valueOf(v.getTag().toString());
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
                            }
                        });

                    }
                }

                callWebService1();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("", "");
            }
        });

        /*RxClient.get(getApplicationContext()).myCamsInfo(new Callback<MyCamResponse>() {
            @Override
            public void success(MyCamResponse myCamResponse, Response response) {

                if(myCamResponse.getStatus().equals("200")){
                    for(int t=0; t<myCamResponse.getResult().getInfo().getCams().length; t++){
                        String key = myCamResponse.getResult().getInfo().getCams()[t].secret_key;

                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });*/


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

                }

            }

            @Override
            public void failure(RetrofitError error) {

                Log.d("", "");

            }
        });
    }


    private class GetData extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MyCamsActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
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

                    encodedString = new String(Hex.encodeHex((sha256_HMAC.doFinal(mValueToEncode.getBytes("ISO-8859-1")))));
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
                Log.d("error", output);

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
            sb.append(String.format("<form id='form1' action='%s' method='%s'>", JSON_URL, "post"));

            for (Map.Entry<String, String> item : postData1)

                try {
                    sb.append(String.format("<input name='mycams_data' type='hidden' value='%s' />",
                            item.getValue()));

                } catch (Exception e) {
                    e.printStackTrace();
                }

            sb.append("</form></body></html>");
            pDialog.dismiss();

            Intent t = new Intent(MyCamsActivity.this, MyCamWebviewAct.class);
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
}
