package com.myappilication.xpress.finjan2017.feedpost;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

import com.myappilication.xpress.finjan2017.R;

/**
 * Created by suresh on 28/7/17.
 */
public class WebAssertAct extends Activity {

    WebView webView;
    final int SELECT_PHOTO = 1;

    final Handler myHandler = new Handler();

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mycam_webview);


        final MyJavascriptInterface myJavaScriptInterface
                = new MyJavascriptInterface(this);

        webView = (WebView) findViewById(R.id.webview);
       /* webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setLightTouchEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);*/
        // webView.addJavascriptInterface(new MyJavascriptInterface(this), "Android");
        webView.getSettings().setLightTouchEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAllowFileAccessFromFileURLs(true);
        webView.getSettings().setAllowUniversalAccessFromFileURLs(true);

        webView.addJavascriptInterface(myJavaScriptInterface, "Android");

        webView.loadUrl("file:///android_asset/sam.html");

    }

    class MyJavascriptInterface{

        Context context;

        MyJavascriptInterface(Context c){
            context = c;
        }

        @JavascriptInterface
        public String choosePhoto()
        {
            // TODO Auto-generated method stub
            String file = "test";
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image");
            startActivityForResult(photoPickerIntent, SELECT_PHOTO);
            return file;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode)
        {
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK)
                {
                    Uri selectedImage = intent.getData();
                    webView.loadUrl("javascript:setFileUri('" + selectedImage.toString() + "')");
                    String path = getRealPathFromURI(this, selectedImage);
                    webView.loadUrl("javascript:setFilePath('" + path + "')");
                }
        }
    }

    public String getRealPathFromURI(Context context, Uri contentUri)
    {
        Cursor cursor = null;
        try
        {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        finally
        {
            if (cursor != null)
            {
                cursor.close();
            }
        }
    }

    public class MyHandler {
        Context mContext;

        MyHandler(Context c) {
            mContext = c;
        }

        @JavascriptInterface
        public void setResult(String webMessage){
            final String msgeToast = webMessage;
            myHandler.post(new Runnable() {
                @Override
                public void run() {
                    // This gets executed on the UI thread so it can safely modify Views
                    Toast.makeText(mContext, msgeToast, Toast.LENGTH_LONG).show();

                    finish();
                }
            });

            Toast.makeText(mContext, webMessage, Toast.LENGTH_LONG).show();
        }
    }
}
