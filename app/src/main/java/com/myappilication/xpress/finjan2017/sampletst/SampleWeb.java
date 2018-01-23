package com.myappilication.xpress.finjan2017.sampletst;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Toast;

import com.myappilication.xpress.finjan2017.R;

/**
 * Created by suresh on 21/9/17.
 */
public class SampleWeb extends Activity {

    WebView web;

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheme);

        web = (WebView) findViewById(R.id.commentsView);

        final MyJavaScriptInterface myJavaScriptInterface
                = new MyJavaScriptInterface(SampleWeb.this);
        web.addJavascriptInterface(myJavaScriptInterface, "AndroidFunction");

        web.getSettings().setJavaScriptEnabled(true);
        web.loadUrl("file:///android_asset/test.html");
    }

    public class MyJavaScriptInterface {
        Context mContext;

        MyJavaScriptInterface(Context c) {
            mContext = c;
        }

        public void showToast(String toast){
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
        }

        public void openAndroidDialog(){
            AlertDialog.Builder myDialog
                    = new AlertDialog.Builder(SampleWeb.this);
            myDialog.setTitle("DANGER!");
            myDialog.setMessage("You can do what you want!");
            myDialog.setPositiveButton("ON", null);
            myDialog.show();
        }

    }
}
