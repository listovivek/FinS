package com.myappilication.xpress.finjan2017.menulist;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;

import com.myappilication.xpress.finjan2017.R;

/**
 * Created by suresh on 19/7/17.
 */
public class MyCamWebviewAct extends AppCompatActivity {

    private WebView view;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mycam_webview);


        view = (WebView) findViewById(R.id.webview);

        WebSettings myWebViewSettings = view.getSettings();

        ImageButton imageButton = (ImageButton) findViewById(R.id.tb_normal_back);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        myWebViewSettings.setJavaScriptEnabled(true);
        myWebViewSettings.setDomStorageEnabled(true);
        myWebViewSettings.setAllowFileAccessFromFileURLs(true);
        myWebViewSettings.setAllowUniversalAccessFromFileURLs(true);


        try{

           // view.loadUrl("http://www.google.com");
            view.loadDataWithBaseURL("https://eiscuat1.camsonline.com/camsapp/", MyCamsActivity.vJsonStr, "text/html", "UTF-8", null);
            view.setWebViewClient(new myWebClient());
        }catch (Exception e){
            Log.d("", "");
        }


        /*view.post(new Runnable() {
            @Override
            public void run() {
            }
        });*/
    }

    public class myWebClient extends WebViewClient
    {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub

            view.loadUrl(url);
            return true;

        }

    }


}
