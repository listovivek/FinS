package com.myappilication.xpress.finjan2017;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.myappilication.xpress.finjan2017.models.login.DownloadFinjan.DownloadFinjanCourse;
import com.myappilication.xpress.finjan2017.models.login.DownloadFinjan.DownloadFinjanReq;
import com.myappilication.xpress.finjan2017.models.login.DownloadFinjan.DownloadFinjanResponse;
import com.myappilication.xpress.finjan2017.models.login.FinjanVideo.FinjanReq;
import com.myappilication.xpress.finjan2017.models.login.FinjanVideo.FinjanResponse;
import com.myappilication.xpress.finjan2017.models.login.FinjanVideo.FinjanVideos;
import com.myappilication.xpress.finjan2017.models.login.helpers.FinalVideoDB;
import com.myappilication.xpress.finjan2017.models.login.helpers.NetConnectionDetector;
import com.myappilication.xpress.finjan2017.models.login.helpers.SharedPrefUtils;
import com.myappilication.xpress.finjan2017.models.login.helpers.StaticConfig;
import com.myappilication.xpress.finjan2017.models.login.login.loginreq;
import com.myappilication.xpress.finjan2017.models.login.login.loginresp;
import com.myappilication.xpress.finjan2017.webservice.RxClient;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class FinjanActivity extends AppCompatActivity {


    @SuppressWarnings("deprecation")
    @SuppressLint("SetJavaScriptEnabled")
    Button Download, btn_playvideo, bt_next,btn_calculator_module;
    // final String vidAddress = "https://archive.org/download/ksnn_compilation_master_the_internet/ksnn_compilation_master_the_internet_512kb.mp4";
    MessageDigest mdEnc = null;
    private String filename = "MySampleFile.txt";
    private String filepath = "MyFileStorage";
    File myInternalFile;
    ProgressDialog pDialog;
    Intent newinetent;
    // String remember_token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjUsImlzcyI6Imh0dHA6XC9cLzE4My44Mi4zMy4yMzI6ODA5NFwvYXBpXC9sb2dpbiIsImlhdCI6MTQ5MzAxMzAyMCwiZXhwIjoxNDkzMDE2NjIwLCJuYmYiOjE0OTMwMTMwMjAsImp0aSI6IjM1ZTRlZWJlODE1NjRhZmYzNzEwNDAzMmRlNmY2MDhhIn0.KGzCHFO9_VZYWKO3MQXPKG3uvc3M3GGg7W3EQuuLtos";
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    Intent intent;
    byte[] decrpt;
    public static final int progress_bar_type = 0;
    private ProgressDialog prgDialog;
    String result;
    ArrayList<String> Res = new ArrayList<>();
    TextView t3;
    List<DownloadFinjanCourse> queslist1 = new ArrayList<>();
    int score = 0;
    int qid = 0;
    DownloadFinjanCourse currentQ;
    //DbHelper db = new DbHelper(this);

    Context context;
    ArrayList<String> video_name = new ArrayList<>();
    ArrayList<String> Module_id = new ArrayList<>();
    ArrayList<String> Module = new ArrayList<>();
    ArrayList<String> enc_url = new ArrayList<>();
    ArrayList<String> Lang_id = new ArrayList<>();
    ArrayList<String> Res1 = new ArrayList<>();
    ArrayList<String> video_type = new ArrayList<>();
    NetConnectionDetector NDC;
    ProgressBar progressBar;

    VideoView vidView;
    String name;
    //String result;
    FinalVideoDB db1;
    String str = "";
    TextView tv_finjan_test;
    Toolbar toolbar;
    ImageButton imageButton,btn_nb_nav;
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finjan_hindi);

        t3 = (TextView) findViewById(R.id.tv_dashboard);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        imageButton = (ImageButton) findViewById(R.id.tb_normal_back);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent i = new Intent(FinjanActivity.this, ModuleFinJan.class);
                startActivity(i);
            }
        });
        //  String Module_id = intent.getStringExtra("Module_id");
        // Toast.makeText(FinjanActivity.this, "Module_id"+Module_id, Toast.LENGTH_SHORT).show();
        ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
        File directory = contextWrapper.getDir(filepath, Context.MODE_PRIVATE);
        myInternalFile = new File(directory, filename);

        db1 = new FinalVideoDB(FinjanActivity.this);

        context = getApplicationContext();
        progressBar = (ProgressBar) findViewById(R.id.progress);

        queslist1 = (ArrayList<DownloadFinjanCourse>) db1.getFinjan_video();
        sharedpreferences = getSharedPreferences(SharedPrefUtils.MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        btn_playvideo = (Button) findViewById(R.id.btn_playvideo);
        btn_calculator_module = (Button) findViewById(R.id.btn_calculator_module);


        String module_name = sharedpreferences.getString("Module_name", "");
     //   Toast.makeText(FinjanActivity.this, "Module_name....." + module_name, Toast.LENGTH_SHORT).show();
        t3.setText(" " + module_name);
        final String img = sharedpreferences.getString("Video_image", "");

        Log.d("Video_im", img);


        imageView = (ImageView) findViewById(R.id.imageView1);

        // imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        Picasso.with(getApplicationContext()).load(img).resize(2600, 1700).centerCrop().into(imageView);

      /*  tv_finjan_test = (TextView) findViewById(R.id.text_finjan_test);
        String testfinjan = sharedpreferences.getString("Module_name", "");

        tv_finjan_test.setText(" " + testfinjan);*/

       name = sharedpreferences.getString("name", "");
      //  Toast.makeText(FinjanActivity.this, "Module_id....." + name, Toast.LENGTH_SHORT).show();
        bt_next = (Button) findViewById(R.id.bt_next);


        vidView = (VideoView) findViewById(R.id.video);
        //Uri video1 = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video);
        //vidView.setVideoURI(video1);
        /*ProgressBack PB= new ProgressBack();
        PB.execute("");*/
       // btn_playvideo = (Button) findViewById(R.id.btn_playvideo);

        btn_playvideo = (Button) findViewById(R.id.btn_playvideo);

        vidView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                // TODO Auto-generated method stub
                progressBar.setVisibility(View.VISIBLE);

                mp.start();
                if (mp.isPlaying()) {
                    btn_playvideo.setVisibility(View.INVISIBLE);

                    progressBar.setVisibility(View.GONE);
                }
                mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int arg1,
                                                   int arg2) {
                        // TODO Auto-generated method stub


                    }
                });
            }
        });

        bt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newinetent = new Intent(FinjanActivity.this, FaqActivity.class);
                startActivity(newinetent);

            }
        });

        btn_calculator_module.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intclac = new Intent(FinjanActivity.this,FinjanCalcModule.class);
                startActivity(intclac);
            }
        });





        btn_playvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxClient.get(getApplicationContext()).Finjan(sharedpreferences.getString(SharedPrefUtils.SpRememberToken, ""), new FinjanReq(sharedpreferences.getString(SharedPrefUtils.SpEmail, "")), new Callback<FinjanResponse>() {


                    @Override
                    public void success(FinjanResponse finjanResponse, Response response) {
                        //   String link = "http://183.82.33.232:8094/coursevideos/14889723941488626430737-NS-Video-5-sport.mp4";
                        FinjanVideos[] finjan = finjanResponse.getResult().getInfo().getFinjanvideo();

                        for (FinjanVideos dc : finjan) {
                            if(Integer.parseInt(name)==Integer.parseInt(dc.getId())) {
                                String url = StaticConfig.Base + dc.getVideo_name();
                           //     Toast.makeText(getApplicationContext(), "Result" + url, Toast.LENGTH_LONG).show();
                                //int position = intent.getIntExtra("position", 2);
                                //Toast.makeText(getApplicationContext(), "position" + position, Toast.LENGTH_LONG).show();
                                //if (position == Integer.parseInt(dc.getId())) {
                                imageView.setVisibility(View.INVISIBLE);
                                MediaController mediacontroller = new MediaController(
                                        FinjanActivity.this);
                                mediacontroller.setAnchorView(vidView);
                                // Get the URL from String VideoURL
                                Uri video = Uri.parse(url);
                                vidView.setMediaController(mediacontroller);
                                vidView.setVideoURI(video);
                                vidView.start();
                            }
                        }
                    }
                    //  String url="http://183.82.33.232:8094/api/videochecking/?id=1";

                    //  }

                    @Override
                    public void failure(RetrofitError error) {

                    }

                    ;
                });
            }

            FileOutputStream fos = null;

            {
                try {
                    fos = new FileOutputStream(myInternalFile);
                    fos.close();

                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
        });
               /* final WebView webview = new WebView(FinjanActivity.this);


                MediaController vidControl = new MediaController(FinjanActivity.this);
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
                settings.setDomStorageEnabled(true);*/
        Download = (Button) findViewById(R.id.btn_downloadnew);
        Download.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                if (NDC.isConnected(context)) {
                    Finjan_video();
                } else {
                    queslist1 = db1.getFinjan_video();
                    for (DownloadFinjanCourse dc : queslist1) {


                        result = dc.getFile_Name();
                        Toast.makeText(FinjanActivity.this, "cs" + result, Toast.LENGTH_SHORT).show();

                        File file = new File(Environment.getExternalStorageDirectory() + result);

                        if (file.exists()) {
                            Download.setVisibility(View.INVISIBLE);

                            Toast.makeText(getApplicationContext(), "File already exist under SD card, playing Music", Toast.LENGTH_LONG).show();
                            // Play Music
                            new Offlinevideo().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                        }
                    }


                }

            }


        });

    }

    private void Finjan_video() {

        RxClient.get(getApplicationContext()).DownloadFinjan(sharedpreferences.getString(SharedPrefUtils.SpRememberToken, ""), new DownloadFinjanReq(sharedpreferences.getString(SharedPrefUtils.SpModuleId, "")), new Callback<DownloadFinjanResponse>() {

            public void success(DownloadFinjanResponse downloadFinjanResponse, Response response) {
                //JsonObject jsonObject = downloadResponse.getResult().getInfo().getCourses();

               // Toast.makeText(getApplicationContext(), " playing Music" + downloadFinjanResponse, Toast.LENGTH_LONG).show();


                DownloadFinjanCourse[] resDownloadCourse = downloadFinjanResponse.getResult().getInfo().getCourses();
                queslist1 = new ArrayList<DownloadFinjanCourse>();

                for (DownloadFinjanCourse dc : resDownloadCourse) {
                    if(Integer.parseInt(name)==Integer.parseInt(dc.getId())) {
                        db1.onDelete();
                        queslist1.add(dc);


                        db1.Finjan_video(queslist1);
                        Log.d("Ques", String.valueOf(queslist1));

                        queslist1 = (ArrayList<DownloadFinjanCourse>) db1.getFinjan_video();
                        currentQ = queslist1.get(qid);
                        String url = StaticConfig.Base + dc.getVideo_encrypt();
                        String s = dc.getVideo_encrypt();

                        int cs = s.indexOf("/");
                        result = s.substring(cs, s.length()) + "";
                       // Toast.makeText(FinjanActivity.this, "cs" + result, Toast.LENGTH_SHORT).show();
                        Log.d("Res........", result);
                        Res.add(result);
                        Log.d("Fileres", String.valueOf(Res));

                          /*  int position = intent.getIntExtra("position", 2);
                            Toast.makeText(getApplicationContext(), "position" + position, Toast.LENGTH_LONG).show();
                            if (position == Integer.parseInt(dc.getId())) {*/
                        File file = new File(Environment.getExternalStorageDirectory() + result);
                        // Check if the Music file already exists
                        if (file.exists()) {
                            Download.setVisibility(View.INVISIBLE);

                            Toast.makeText(getApplicationContext(), "File already exist under SD card, playing Music", Toast.LENGTH_LONG).show();
                            // Play Music
                            new Offlinevideo().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                            // If the Music File doesn't exist in SD card (Not yet downloaded)
                        } else {
                            Toast.makeText(getApplicationContext(), "File doesn't exist under SD Card, downloading Mp3 from Internet", Toast.LENGTH_LONG).show();
                            // Trigger Async Task (onPreExecute method)
                            new DownloadMusicfromInternet().execute(url);
                        }

                        ;
                    }
                }
            }

            // }

            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(), "Fail" + error, Toast.LENGTH_SHORT).show();

            }


        });

    }


    // Async Task Class
    class DownloadMusicfromInternet extends AsyncTask<String, String, String> {

        // Show Progress bar before downloading Music
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Shows Progress Bar Dialog and then call doInBackground method
            showDialog(progress_bar_type);
        }

        // Download Music File from Internet
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection conection = url.openConnection();
                conection.connect();
                // Get Music file length
                int lenghtOfFile = conection.getContentLength();
                // input stream to read file - with 8k buffer
                InputStream input = new BufferedInputStream(url.openStream(), 10 * 1024);
                // Output stream to write file in SD card
                OutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory() + result);
                byte data[] = new byte[1024];
                long total = 0;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    // Publish the progress which triggers onProgressUpdate method
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                    // Write data to file
                    output.write(data, 0, count);
                }
                // Flush output
                output.flush();
                // Close streams
                output.close();
                input.close();
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
            return null;
        }


        protected void onProgressUpdate(String... progress) {
            // Set progress percentage
            prgDialog.setProgress(Integer.parseInt(progress[0]));

        }


        // Once Music File is downloaded
        @Override
        protected void onPostExecute(String file_url) {
            // Dismiss the dialog after the Music file was downloaded
            dismissDialog(progress_bar_type);
            new Offlinevideo().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    }

    class Offlinevideo extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Shows Progress Bar Dialog and then call doInBackground method
            //  showDialog(progress_bar_type);
            //   ProgressBar progressBar;
            prgDialog = new ProgressDialog(FinjanActivity.this);
            prgDialog.setMessage("Decrypt processing ........");
            prgDialog.setIndeterminate(false);
            //  prgDialog.setMax(100);
            prgDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            prgDialog.setCancelable(false);
            prgDialog.show();

        }

        // Download Music File from Internet
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                decrpt = decrypt();

            } catch (IOException e) {
                e.printStackTrace();
                Log.d("Error", String.valueOf(e));

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
                Log.d("Error", String.valueOf(e));
            }
            return null;

        }

        protected void onPostExecute(String file_url) {
            // Dismiss the dialog after the Music file was downloaded
            //dismissDialog(progress_bar_type);
            //Toast.makeText(getApplicationContext(), "Download complete, playing Music", Toast.LENGTH_LONG).show();
            // Play the music
            prgDialog.cancel();
            playMusic(decrpt);

        }

    }



    byte[] decrypt() throws IOException, NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException {

        int VideoI = 1;
        //String s="/Android/data/com.example.balasri.finjonn/data/";
        File extStore = Environment.getExternalStorageDirectory();
        //   Uri u = Uri.parse("android.resource://" + DashBoard.ctx.getPackageName() + "/" + videoNames.get(VideoI));
        FileInputStream fis = new FileInputStream(extStore + result);

        FileOutputStream fos = new FileOutputStream(extStore + "/dec.mp4");

        SecretKeySpec sks = new SecretKeySpec("MyDifficultPassw".getBytes(),
                "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, sks);
        CipherInputStream cis = new CipherInputStream(fis, cipher);

        int b;
        byte[] d = new byte[16384];
        while ((b = cis.read(d)) != -1) {
            fos.write(d, 0, b);

        }
      //  Toast.makeText(FinjanActivity.this, fos.toString(), Toast.LENGTH_LONG).show();
        fos.flush();
        fos.close();
        cis.close();

        // String path = android.os.Environment.getExternalStorageDirectory().getPath()+"/bb.mp4";

        return d;
    }


    protected void playMusic(byte[] mp3SoundByteArray) {
        // Read Mp3 file present under SD card
        Uri viduri = Uri.parse(Environment.getExternalStorageDirectory() + "/dec.mp4");
        MediaController mediacontroller = new MediaController(
                FinjanActivity.this);
        try {
            imageView.setVisibility(View.INVISIBLE);
            mediacontroller.setAnchorView(vidView);
            // Get the URL from String VideoURL
            Uri cv = Uri.parse(String.valueOf(viduri));
            vidView.setMediaController(mediacontroller);
            vidView.setVideoURI(cv);
            vidView.start();
            vidView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    // TODO Auto-generated method stub
                    // Once Music is completed playing, enable the button
                    Download.setEnabled(true);
                //    Toast.makeText(getApplicationContext(), "Music completed playing", Toast.LENGTH_LONG).show();
                }
            });
        } catch (IllegalArgumentException e)

        {
            Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        } catch (
                SecurityException e
                )

        {
            Toast.makeText(getApplicationContext(), "URI cannot be accessed, permissed needed", Toast.LENGTH_LONG).show();
        } catch (
                IllegalStateException e
                )

        {
            Toast.makeText(getApplicationContext(), "Media Player is not in correct state", Toast.LENGTH_LONG).show();
        }
    }



    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case progress_bar_type:
                prgDialog = new ProgressDialog(FinjanActivity.this);
                prgDialog.setMessage("Downloading  file. Please wait...");
                prgDialog.setIndeterminate(false);
                prgDialog.setMax(100);
                prgDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                prgDialog.setCancelable(false);
                prgDialog.show();
                prgDialog.cancel();
                return prgDialog;
            default:
                return null;
        }
    }
    protected void onStart() {
        super.onStart();
     //   Toast.makeText(this, "onStart", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
      //  Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
      //  Toast.makeText(this, "onRestart", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
      //  Toast.makeText(this, "onPause", Toast.LENGTH_SHORT).show();
        Uri viduri = Uri.parse(Environment.getExternalStorageDirectory() + "/dec.mp4");

        //  File patternDirectory = new File(Environment.getExternalStorageDirectory().getAbsolutePath().toString()+"/sdcard:e.mp4");
        // patternDirectory.mkdirs();
        File auxFile = new File(viduri.toString());

        auxFile.delete();

        super.onPause();
        // create temp file that will hold byte array


    }


    @Override
    protected void onStop() {
       // Toast.makeText(this, "onStop", Toast.LENGTH_SHORT).show();
        Uri viduri = Uri.parse(Environment.getExternalStorageDirectory() + "/dec.mp4");

        //  File patternDirectory = new File(Environment.getExternalStorageDirectory().getAbsolutePath().toString()+"/sdcard:e.mp4");
        // patternDirectory.mkdirs();
        File auxFile = new File(viduri.toString());

        auxFile.delete();
        // Tried reusing instance of media player

        super.onStop();

        // create temp file that will hold byte array

        // but that resulted in system crashes...


    }
    public void onDestroy() {
        super.onDestroy();
        if (prgDialog != null) {
            prgDialog.dismiss();
            prgDialog = null;
        }
    }
    private void mtd_refresh_token() {
       /* Toast.makeText(context, "expired", Toast.LENGTH_SHORT).show();*/
        RxClient.get(FinjanActivity.this).Login(new loginreq(sharedpreferences.getString(SharedPrefUtils.SpEmail, ""),sharedpreferences.getString(SharedPrefUtils.SpPassword, "")), new Callback<loginresp>() {
            @Override
            public void success(loginresp loginresp, Response response) {




                if (loginresp.getStatus().equals("200")){


                    Toast.makeText(getApplicationContext(),"sucesss"+loginresp.getToken().toString(),Toast.LENGTH_LONG).show();

                    editor.putString(SharedPrefUtils.SpRememberToken,loginresp.getToken().toString());

                    editor.commit();
                    /*adapter.notifyDataSetChanged();*/

                }
            }





            @Override
            public void failure(RetrofitError error) {

                Toast.makeText(getApplicationContext(),"Wrong Username And Password",Toast.LENGTH_LONG).show();

            }
        });

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
            case R.id.profile_menu:
                startActivity(new Intent(getApplicationContext(), ProfileSetting.class));
                return true;
            /*case R.id.finstaffcources:
                startActivity(new Intent(getApplicationContext(), ModuleFinJan.class));
                return true;*/

            case R.id.changepassword:
                startActivity(new Intent(getApplicationContext(), ChangePassword.class));
                return true;
           /* case R.id.dashboard_menu:
                startActivity(new Intent(getApplicationContext(), ModuleFinJan.class));
                return true;*/

        }
        return false;
    }


    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_for_all, menu);

        return true;
    }

}


//  *
//   * The webview client receives notifications about appView


/*
            *
                 * File name from URL
                 *
                 * @param url
                 * @return*/
