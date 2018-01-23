package com.myappilication.xpress.finjan2017.menulist;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.myappilication.xpress.finjan2017.R;
import com.myappilication.xpress.finjan2017.models.login.helpers.SharedPrefUtils;
import com.myappilication.xpress.finjan2017.models.login.mycamstransactiondetails.Transactionreq;
import com.myappilication.xpress.finjan2017.models.login.mycamstransactiondetails.Transactionresponse;
import com.myappilication.xpress.finjan2017.webservice.RxClient;

import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by suresh on 19/7/17.
 */
public class MyCamWebviewAct extends AppCompatActivity {

    private WebView view;

    final Handler myHandler = new Handler();
    final int SELECT_PHOTO = 1;

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;


    /*private static final int FILECHOOSER_RESULTCODE   = 2888;
    private ValueCallback<Uri> mUploadMessage;
    private Uri mCapturedImageURI = null;*/


    private static final int FILECHOOSER_RESULTCODE = 1;
    private ValueCallback<Uri> mUploadMessage;
    private Uri mCapturedImageURI = null;

    // the same for Android 5.0 methods only
    private ValueCallback<Uri[]> mFilePathCallback;
    private String mCameraPhotoPath;

    String sName, isinc;

    ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mycam_webview);

        ImageButton imageButton = (ImageButton) findViewById(R.id.tb_normal_back);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MyCamWebviewAct.this);
                View bView = getLayoutInflater().inflate(R.layout.custom_dialog1, null);
                dialogBuilder.setView(bView);
                dialogBuilder.setCancelable(false);

                TextView txt1 = (TextView) bView.findViewById(R.id.dialog_text1);
                TextView txtCancel = (TextView) bView.findViewById(R.id.cancel_btn1);
                TextView txtOk = (TextView) bView.findViewById(R.id.ok_btn1);

                txt1.setText("Do you want to close this process?");

                final AlertDialog al = dialogBuilder.create();

                txtCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        al.dismiss();
                    }
                });

                txtOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });


                al.show();


            }
        });

        view = (WebView) findViewById(R.id.webview);

        progressBar = (ProgressBar) findViewById(R.id.progressBar_mycams);
        progressBar.setVisibility(View.VISIBLE);

        sharedpreferences = getSharedPreferences(SharedPrefUtils.MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        final MyJavascriptInterface myJavaScriptInterface = new MyJavascriptInterface(this);

        view.getSettings().setJavaScriptEnabled(true);
        view.getSettings().setDomStorageEnabled(true);

        view.getSettings().setAllowFileAccessFromFileURLs(true);
        view.getSettings().setAllowUniversalAccessFromFileURLs(true);

        view.getSettings().setPluginState(WebSettings.PluginState.ON);
        view.getSettings().setAllowFileAccess(true);

        sName = getIntent().getStringExtra("schemename");
        isinc = getIntent().getStringExtra("isincode");

        /*String vv = "SUCCESS|Your transaction is successfully completed and " +
                "Reference No.369056|369056|J1625640074532|06/09/2017";

        StringTokenizer tokens = new StringTokenizer(vv, "|");
        cWebservice(tokens);*/

        /*view.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        view.setScrollbarFadingEnabled(false);
        view.getSettings().setBuiltInZoomControls(true);
        view.getSettings().setPluginState(WebSettings.PluginState.ON);
        view.getSettings().setAllowFileAccess(true);
        view.getSettings().setSupportZoom(true);*/

        view.addJavascriptInterface(myJavaScriptInterface, "MyHandler");

        try{
            view.setWebViewClient(new myWebClient());
            view.loadData(Scheme.sb.toString(), "text/html", "UTF-8");

            view.setWebChromeClient(new WebChromeClient() {

                                        public boolean onShowFileChooser(
                                                WebView webView, ValueCallback<Uri[]> filePathCallback,
                                                WebChromeClient.FileChooserParams fileChooserParams) {
                                            if (mFilePathCallback != null) {
                                                mFilePathCallback.onReceiveValue(null);
                                            }
                                            mFilePathCallback = filePathCallback;

                                            /*Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

                                                // create the file where the photo should go
                                                File photoFile = null;
                                                try {
                                                    photoFile = createImageFile();
                                                    takePictureIntent.putExtra("PhotoPath", mCameraPhotoPath);
                                                } catch (IOException ex) {
                                                    // Error occurred while creating the File
                                                    Log.e("", "Unable to create Image File", ex);
                                                }

                                                // continue only if the file was successfully created
                                                if (photoFile != null) {
                                                    mCameraPhotoPath = "file:" + photoFile.getAbsolutePath();
                                                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                                                            Uri.fromFile(photoFile));
                                                } else {
                                                    takePictureIntent = null;
                                                }
                                            }*/

                                            Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
                                            contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
                                            contentSelectionIntent.setType("image/*");

                                            /*Intent[] intentArray;
                                            if (takePictureIntent != null) {
                                                intentArray = new Intent[]{takePictureIntent};
                                            } else {
                                                intentArray = new Intent[0];
                                            }*/

                                            Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
                                            chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
                                            chooserIntent.putExtra(Intent.EXTRA_TITLE, "File Chooser");

                                            //chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);

                                            startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE);

                                            return true;
                                        }

                                        // creating image files (Lollipop only)
                                        private File createImageFile() throws IOException {

                                            File imageStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "DirectoryNameHere");

                                            if (!imageStorageDir.exists()) {
                                                imageStorageDir.mkdirs();
                                            }

                                            // create an image file name
                                            imageStorageDir  = new File(imageStorageDir + File.separator + "IMG_" + String.valueOf(System.currentTimeMillis()) + ".jpg");
                                            return imageStorageDir;
                                        }
                // openFileChooser for Android 3.0+
                public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
                    mUploadMessage = uploadMsg;

                    try {
                        File imageStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "DirectoryNameHere");

                        if (!imageStorageDir.exists()) {
                            imageStorageDir.mkdirs();
                        }

                        File file = new File(imageStorageDir + File.separator + "IMG_" + String.valueOf(System.currentTimeMillis()) + ".jpg");

                        mCapturedImageURI = Uri.fromFile(file); // save to the private variable

                        final Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
                        // captureIntent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                        i.addCategory(Intent.CATEGORY_OPENABLE);
                        i.setType("image/*");

                        Intent chooserIntent = Intent.createChooser(i, "File Chooser");
                        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Parcelable[]{captureIntent});

                        startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE);
                    } catch (Exception e) {
                        Toast.makeText(getBaseContext(), "Camera Exception:" + e, Toast.LENGTH_LONG).show();
                    }

                }

                // openFileChooser for Android < 3.0
                public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                    openFileChooser(uploadMsg, "");
                }

                // openFileChooser for other Android versions
            /* may not work on KitKat due to lack of implementation of openFileChooser() or onShowFileChooser()
               https://code.google.com/p/android/issues/detail?id=62220
               however newer versions of KitKat fixed it on some devices */
                public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                    openFileChooser(uploadMsg, acceptType);
                }

            });




            /*view.setWebChromeClient(new WebChromeClient() {

                public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
                    mUploadMessage = uploadMsg;

                    try{
                        // Create AndroidExampleFolder at sdcard

                        File imageStorageDir = new File(
                                Environment.getExternalStoragePublicDirectory(
                                        Environment.DIRECTORY_PICTURES)
                                , "AndroidExampleFolder");

                        if (!imageStorageDir.exists()) {

                            // Create AndroidExampleFolder at sdcard
                            imageStorageDir.mkdirs();
                        }

                        // Create camera captured image file path and name
                        File file = new File(imageStorageDir + File.separator + "IMG_"
                                        + String.valueOf(System.currentTimeMillis())
                                        + ".jpg");

                        mCapturedImageURI = Uri.fromFile(file);

                        // Camera capture image intent
                        final Intent captureIntent = new Intent(
                                android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

                        captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);

                        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                        i.addCategory(Intent.CATEGORY_OPENABLE);
                        i.setType("image*//*");

                        // Create file chooser intent
                        Intent chooserIntent = Intent.createChooser(i, "Image Chooser");

                        // Set camera intent to file chooser
                        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS
                                , new Parcelable[] { captureIntent });

                        // On select image call onActivityResult method of activity
                        startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE);

                    }

                    catch(Exception e){
                        Toast.makeText(getBaseContext(), "Exception:"+e,
                                Toast.LENGTH_LONG).show();
                    }
                }

                public void openFileChooser(ValueCallback<Uri> uploadMsg){
                    openFileChooser(uploadMsg, "");
                }

                //openFileChooser for other Android versions
                public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {

                    openFileChooser(uploadMsg, acceptType);
                }



                // The webPage has 2 filechoosers and will send a
                // console message informing what action to perform,
                // taking a photo or updating the file

                public boolean onConsoleMessage(ConsoleMessage cm) {

                    onConsoleMessage(cm.message(), cm.lineNumber(), cm.sourceId());
                    return true;
                }

                public void onConsoleMessage(String message, int lineNumber, String sourceID) {
                    //Log.d("androidruntime", "Show console messages, Used for debugging: " + message);

                }
            });*/



        }catch (Exception e){
            Log.d("", "");
        }
    }

    private void startWebView() {
    }

    public class MyJavascriptInterface {
        Context mContext;
        MyJavascriptInterface(Context c) {
            mContext = c;
        }

        @JavascriptInterface
        public void setResult(String webMessage) {

            final String msgeToast = webMessage;
            final String[] st = webMessage.split("#\\$#");



           /* String vv = "SUCCESS|Your transaction is successfully completed and " +
                    "Reference No.369056|369056|J1625640074532|06/09/2017";*/


            try{
                //Toast.makeText(mContext, st[0], Toast.LENGTH_LONG).show();
                StringTokenizer tokens = new StringTokenizer(st[0], "|");
                String first = tokens.nextToken();// this will contain "Fruit"
                String second = tokens.nextToken();

                finalresponse(second);
                cWebservice(tokens);
            }catch (Exception e){
                Log.d("", "");
            }

        }

       /* @JavascriptInterface
        public String choosefile()
        {
            // TODO Auto-generated method stub
            String file = "test";
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image*//*");
            startActivityForResult(photoPickerIntent, SELECT_PHOTO);
            return file;
        }*/


    }

    private void cWebservice(StringTokenizer tokens) {
        String one = tokens.nextToken();
        String two = tokens.nextToken();
        String th = tokens.nextToken();
        String four = tokens.nextToken();
        String five = tokens.nextToken();

        RxClient.get(MyCamWebviewAct.this).transactiondetails(sharedpreferences.getString(SharedPrefUtils.SpRememberToken, "")
        ,new Transactionreq(sName, one, two, th, four, five, isinc),
                new Callback<Transactionresponse>() {
                    @Override
                    public void success(Transactionresponse transactionresponse, Response response) {

                        Log.d("Transaction response", "successfully updated");
                    }

                    @Override
                    public void failure(RetrofitError error) {

                        Log.d("Transaction response", "failure");

                        Toast.makeText(getBaseContext(), error.toString(), Toast.LENGTH_LONG).show();

                    }
                });

    }


    private void finalresponse(String s) {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MyCamWebviewAct.this);
        View bView = getLayoutInflater().inflate(R.layout.custom_feedback_alert, null);
        dialogBuilder.setView(bView);
        dialogBuilder.setCancelable(false);
        Button send_btn = (Button) bView.findViewById(R.id.feedback_okbtn);

        TextView t = (TextView) bView.findViewById(R.id.dialog_text);
        t.setText(s);

        final AlertDialog al = dialogBuilder.create();
        al.show();

        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                al.dismiss();
                finish();

            }
        });

    }


    public class myWebClient extends WebViewClient {
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

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            // TODO Auto-generated method stub
            Toast.makeText(MyCamWebviewAct.this, "Loading! " +
                    description, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
          //  progressBar.setVisibility(View.GONE);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }


    /*@Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {

        if(requestCode==FILECHOOSER_RESULTCODE)
        {

            if (null == this.mUploadMessage) {
                return;
            }

            Uri result=null;

            try{
                if (resultCode != RESULT_OK) {

                    result = null;

                } else {

                    // retrieve from the private variable if the intent is null
                    result = intent == null ? mCapturedImageURI : intent.getData();
                }
            }
            catch(Exception e)
            {
                Toast.makeText(getApplicationContext(), "activity :"+e, Toast.LENGTH_LONG).show();
            }

            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;

        }

    }*/

    @Override
    public void onActivityResult (int requestCode, int resultCode, Intent data) {

        // code for all versions except of Lollipop
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {

            if(requestCode==FILECHOOSER_RESULTCODE) {
                if (null == this.mUploadMessage) {
                    return;
                }

                Uri result=null;

                try{
                    if (resultCode != RESULT_OK) {
                        result = null;
                    } else {
                        // retrieve from the private variable if the intent is null
                        result = data == null ? mCapturedImageURI : data.getData();
                    }
                }
                catch(Exception e) {
                    Toast.makeText(getApplicationContext(), "activity :"+e, Toast.LENGTH_LONG).show();
                }

                mUploadMessage.onReceiveValue(result);
                mUploadMessage = null;
            }

        } // end of code for all versions except of Lollipop

        // start of code for Lollipop only
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            if (requestCode != FILECHOOSER_RESULTCODE || mFilePathCallback == null) {
                super.onActivityResult(requestCode, resultCode, data);
                return;
            }

            Uri[] results = null;

            // check that the response is a good one
            if (resultCode == Activity.RESULT_OK) {
                if (data == null || data.getData() == null) {
                    // if there is not data, then we may have taken a photo
                    if (mCameraPhotoPath != null) {
                        results = new Uri[]{Uri.parse(mCameraPhotoPath)};
                    }
                } else {
                    String dataString = data.getDataString();
                    if (dataString != null) {
                        results = new Uri[]{Uri.parse(dataString)};
                    }
                }
            }

            mFilePathCallback.onReceiveValue(results);
            mFilePathCallback = null;

        } // end of code for Lollipop only

    }

    @Override
    public void onBackPressed() {

    }
}
