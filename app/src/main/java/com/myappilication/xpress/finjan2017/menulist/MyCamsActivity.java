package com.myappilication.xpress.finjan2017.menulist;

import android.Manifest;
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
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.myappilication.xpress.finjan2017.R;
import com.myappilication.xpress.finjan2017.menulist.mycamsinfo.MyCamResponse;
import com.myappilication.xpress.finjan2017.menulist.mycamsschemelist.SchemeListResponse;
import com.myappilication.xpress.finjan2017.models.login.helpers.SharedPrefUtils;
import com.myappilication.xpress.finjan2017.webservice.RxClient;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.commons.codec.binary.Hex;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.crypto.Mac;
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

    public static final String JSON_URL = "https://eiscuat1.camsonline.com/camsapp/myCAMSEKYCCheck.aspx";

    private static final int PERMISSIONS_REQUEST_READ_PHONE_STATE = 999;
    private TelephonyManager mTelephonyManager;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.scheme_list);

        linear = (LinearLayout) findViewById(R.id.scheme_list);

        sharedpreferences = getSharedPreferences(SharedPrefUtils.MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        //int version = Build.VERSION.SDK_INT;

        ImageButton imageButton = (ImageButton) findViewById(R.id.tb_normal_back);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

       /* if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},
                    PERMISSIONS_REQUEST_READ_PHONE_STATE);
        } else {
            mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            String deviceid = mTelephonyManager.getDeviceId();
            Log.d("msg", "DeviceImei " + deviceid);
        }*/


        callWebService();
    }


    /*@Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_PHONE_STATE
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            String deviceid = mTelephonyManager.getDeviceId();
            Log.d("msg", "DeviceImei " + deviceid);
        }


    }*/

    private void callWebService() {


        RxClient.get(getApplicationContext()).schemeList(new Callback<SchemeListResponse>() {
            @Override
            public void success(final SchemeListResponse schemeListResponse, Response response) {

                if(schemeListResponse.getStatus().equals("200")){
                    for(int t=0; t<schemeListResponse.getResult().getInfo().getSchemes().length; t++){
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
                                String key = schemeListResponse.getResult().
                                        getInfo().getSchemes()[Integer.valueOf(v.getTag().toString())]
                                        .getIsin_code();

                                String scheme = schemeListResponse.getResult().getInfo().getSchemes()
                                        [Integer.valueOf(v.getTag().toString())].getSchemes_name();
                                String amc_code = schemeListResponse.getResult().getInfo().getSchemes()
                                        [Integer.valueOf(v.getTag().toString())].getAmc_code();

                                Log.d("isin code", key);
                                String[] arr = new String[]{scheme, key, amc_code};
                                new GetData().execute(arr);
                            }
                        });

                    }
                }
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




            DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            Date date = new Date();
            String datee = dateFormat.format(date);

            String syntax = "FINSAFE::d38nr0WoVJhGH64oZdHSrF5k5poaoOOWRloXMqQbDbE=::"+datee;

            //String syntax = "clientid::v/xUgHpPuXXiqbx5mLDsp4bO613IwH/zD7LqwIZFUFA=::20170403115100

            MessageDigest md = null;
            String b=null;
            try {
                md = MessageDigest.getInstance("SHA-256");
                md.update(syntax.getBytes());
                byte[] digest = md.digest();
                b = Base64.encodeToString(digest, Base64.DEFAULT);
                Log.d("", "");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }



            String f = null;
            try {
                final Charset asciiCs = Charset.forName("US-ASCII");
                final Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
				/*final SecretKeySpec secret_key = new javax.crypto.spec.SecretKeySpec(asciiCs.encode(syntax).array(),
						"HmacSHA256");*/

                SecretKeySpec secret_key = new SecretKeySpec(b.getBytes("UTF-8"), "HmacSHA256");
                sha256_HMAC.init(secret_key);

                f =new String(Hex.encodeHex(sha256_HMAC.doFinal("Finsafe".getBytes("UTF-8"))));
                Log.d("", " ");

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }



            String myCams = em+"|"+"7338746633"+"|"+params[2]+"|"+"DIRECT"+"|"+"GROWTH"+"|"+"|"+"|"+"|"+params[1]+"|"+"|"+"|"+"|"+
                    "|"+"|"+"|"+"|"+"|"+"|"+"|"+"|"+"|"+"|"+"|"+"|"+
                    android_id+"|"+versionRelease
                    +"|"+"ANDROID"+"|"+ip+"|"+"FINSAFE"+"|"+datee+"|"+
                    f;


            // Making a request to url and getting response
            List<NameValuePair> vParams = new ArrayList<NameValuePair>();
            //vParams.add(new BasicNameValuePair("userid","Finsafe"));

            vParams.add(new BasicNameValuePair("mycams_data", myCams));
            /*vParams.add(new BasicNameValuePair("mobile","7338746633"));
            vParams.add(new BasicNameValuePair("Amc", params[2]));
            vParams.add(new BasicNameValuePair("plantype", "DIRECT"));

            vParams.add(new BasicNameValuePair("schemeplan", "GROWTH"));
            vParams.add(new BasicNameValuePair("ISINcode", params[1]));
            vParams.add(new BasicNameValuePair("Deviceid", android_id));
            vParams.add(new BasicNameValuePair("Osid",versionRelease));

            vParams.add(new BasicNameValuePair("devicetype","ANDROID"));
            vParams.add(new BasicNameValuePair("ipaddress", ip));
            vParams.add(new BasicNameValuePair("Clientid","FINSAFE"));
            vParams.add(new BasicNameValuePair("datetimestamp", datee));

            vParams.add(new BasicNameValuePair("Signature", f));*/

            //vParams.add(new BasicNameValuePair("pan","AHAPL3302K"));
            //vParams.add(new BasicNameValuePair("mobile","9043175749"));

            /*email=gold1@gmail.com, amc=P, plantype=p, schemeplan=GROWTH, ISINcode=INF109K016L0,
                    deviceid=36a0c7a71cf10956, osid=4.4.2, devicetype=Android, ipaddress=192.168.1.234,

                    Clientid=FINSAFE, datetimestamp=20170721140043,
                    Signature=f9086ebf86ef5eb6aae546c45733937e0f5f1da292d48c481d62a7a4c013b744*/

            vJsonStr = sh.makeServiceCall(JSON_URL, ServiceHandler.POST, vParams,
                    datee, f);

            Intent i = new Intent(MyCamsActivity.this, MyCamWebviewAct.class);
            startActivity(i);
            pDialog.dismiss();
            return null;
        }

        /*List<NameValuePair> vParams = new ArrayList<NameValuePair>();
//vParams.add(new BasicNameValuePair("userid","Finsafe"));


        vParams.add(new BasicNameValuePair("email", "google@gmail.com"));
        vParams.add(new BasicNameValuePair("mobile","7338746633"));
        vParams.add(new BasicNameValuePair("Amc", "P"));
        vParams.add(new BasicNameValuePair("plantype", "DIRECT"));

        vParams.add(new BasicNameValuePair("schemeplan", "GROWTH"));
        vParams.add(new BasicNameValuePair("ISINcode", "INF109K016L0"));
        vParams.add(new BasicNameValuePair("Deviceid", "36a0c7a71cf10956"));
        vParams.add(new BasicNameValuePair("Osid", "4.4.2"));

        vParams.add(new BasicNameValuePair("devicetype","ANDROID"));
        vParams.add(new BasicNameValuePair("ipaddress", "192.168.1.234"));
        vParams.add(new BasicNameValuePair("Clientid","FINSAFE"));
        vParams.add(new BasicNameValuePair("datetimestamp", "20170727193211"));

        vParams.add(new BasicNameValuePair("Signature", "54155930f00239c8d67290c63ebddbc51116dc9a842c9a4ca4affedb0e4b01af"));



        Header:

        HttpPost httpPost = new HttpPost(url);

        httpPost.setEntity(new UrlEncodedFormEntity(params));

        String finall="FINSAFE, "+date+", "+signs;
        httpPost.setHeader("Content-type", "application/json");
        httpPost.setHeader("Authorization", finall);*/

    }
}
