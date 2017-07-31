package com.myappilication.xpress.finjan2017;

import android.annotation.SuppressLint;
import java.net.URL;
import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;

public class VideoDownload extends AppCompatActivity {



    @SuppressWarnings("deprecation")
    @SuppressLint("SetJavaScriptEnabled")
    Button Download,btn_playvideo,bt_next;
    final String vidAddress = "https://archive.org/download/ksnn_compilation_master_the_internet/ksnn_compilation_master_the_internet_512kb.mp4";
    MessageDigest mdEnc = null;
    private String filename = "MySampleFile.txt";
    private String filepath = "MyFileStorage";
    File myInternalFile;
    ProgressDialog pDialog;

    Intent newinetent;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboardnew);
        getSupportActionBar().setDisplayOptions(android.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.customlayout);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4a285b")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
        File directory = contextWrapper.getDir(filepath, Context.MODE_PRIVATE);
        myInternalFile = new File(directory, filename);



        btn_playvideo = (Button) findViewById(R.id.btn_playvideo);

        bt_next = (Button) findViewById(R.id.bt_next);


        final VideoView vidView = (VideoView) findViewById(R.id.video);
        //Uri video1 = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video);
        //vidView.setVideoURI(video1);
        /*ProgressBack PB= new ProgressBack();
        PB.execute("");*/

        bt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newinetent = new Intent(VideoDownload.this,FinjanActivity.class);
                startActivity(newinetent);

            }
        });


        btn_playvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                vidView.start();
            }
        });


        FileOutputStream fos = null;
        try {
            Toast.makeText(getApplicationContext(), "Value" + myInternalFile, Toast.LENGTH_SHORT).show();
            fos = new FileOutputStream(myInternalFile);

            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        final WebView webview = new WebView(this);

        Uri vidUri = Uri.parse(vidAddress);
        pDialog = new ProgressDialog(VideoDownload.this);
        // Set progressbar title

        // Set progressbar message
        pDialog.setMessage("Buffering...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        // Show progressbar
        pDialog.show();
        try {
            // Start the MediaController
            MediaController mediacontroller = new MediaController(
                    VideoDownload.this);
            mediacontroller.setAnchorView(vidView);
            // Get the URL from String VideoURL
            Uri video = Uri.parse(vidAddress);
            vidView.setMediaController(mediacontroller);
            vidView.setVideoURI(video);

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        vidView.requestFocus();
        vidView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            // Close the progress bar and play the video
            public void onPrepared(MediaPlayer mp) {
                pDialog.dismiss();
                vidView.start();
            }
        });


        MediaController vidControl = new MediaController(this);
        vidControl.setAnchorView(vidView);
        vidView.setMediaController(vidControl);
        webview.setWebChromeClient(new WebChromeClient());
        WebViewClient client = new ChildBrowserClient();
        webview.setWebViewClient(client);
        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
        webview.setInitialScale(1);
        webview.getSettings().setUseWideViewPort(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(false);
        settings.setBuiltInZoomControls(true);
        settings.setPluginState(WebSettings.PluginState.ON);
        settings.setDomStorageEnabled(true);
        Download = (Button) findViewById(R.id.btn_downloadnew);
        Download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                webview.loadUrl(vidAddress);
                webview.setId(5);

            }

        });
    }




    /**
     * The webview client receives notifications about appView
     */
    public class ChildBrowserClient extends WebViewClient {
        @SuppressLint("InlinedApi")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            boolean value = true;
            String extension = MimeTypeMap.getFileExtensionFromUrl(url);
            if (extension != null) {
                MimeTypeMap mime = MimeTypeMap.getSingleton();
                String mimeType = mime.getMimeTypeFromExtension(extension);
                if (mimeType != null) {
                    if (mimeType.toLowerCase().contains("video")
                            || extension.toLowerCase().contains("mov")
                            || extension.toLowerCase().contains("mp4")) {
                        DownloadManager mdDownloadManager = (DownloadManager) VideoDownload.this
                                .getSystemService(Context.DOWNLOAD_SERVICE);
                        DownloadManager.Request request = new DownloadManager.Request(
                                Uri.parse(url));
                        File destinationFile = new File(
                                Environment.getExternalStorageDirectory(),
                                getFileName(url));
                        request.setDescription("Downloading via Your app name..");
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                        request.setDestinationUri(Uri.fromFile(destinationFile));
                        mdDownloadManager.enqueue(request);
                        value = false;
                    }
                }
                if (value) {
                    view.loadUrl(url);
                }
            }
            return value;
        }


        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        /**
         * Notify the host application that a page has started loading.
         *
         * @param view The webview initiating the callback.
         * @param url  The url of the page.
         */
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }
    }


    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return false;
    }
    /**
     * File name from URL
     *
     * @param url
     * @return
     */
    public String getFileName(String url) {
        String filenameWithoutExtension = "";
        filenameWithoutExtension = String.valueOf(System.currentTimeMillis()
                + ".mp4");
        return filenameWithoutExtension;

    }
}
