package com.myappilication.xpress.finjan2017;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;


import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.data.DataHolder;
import com.myappilication.xpress.finjan2017.allcalculatorlist.AllCalcListActivity;
import com.myappilication.xpress.finjan2017.feedback.FeedActivity;
import com.myappilication.xpress.finjan2017.feedback.OfflineFeedbackDB;
import com.myappilication.xpress.finjan2017.feedback.UserFeedbackList;
import com.myappilication.xpress.finjan2017.feedpost.FB_Posts;

import com.myappilication.xpress.finjan2017.mcqevalutiontest.McQData;
import com.myappilication.xpress.finjan2017.mcqevalutiontest.QuestionActivity;

import com.myappilication.xpress.finjan2017.menulist.Learning_centre;
import com.myappilication.xpress.finjan2017.menulist.Link_To_Interest;
import com.myappilication.xpress.finjan2017.menulist.MediaActivity;
import com.myappilication.xpress.finjan2017.menulist.Scheme;
import com.myappilication.xpress.finjan2017.models.login.VideoList.MCQ;
import com.myappilication.xpress.finjan2017.models.login.VideoList.VideoListModules;
import com.myappilication.xpress.finjan2017.models.login.VideoList.VideoListReq;
import com.myappilication.xpress.finjan2017.models.login.VideoList.VideoListResponse;
import com.myappilication.xpress.finjan2017.models.login.completemodpushtoserver.CompletemodResponse;
import com.myappilication.xpress.finjan2017.models.login.completemodpushtoserver.Completemodreq;
import com.myappilication.xpress.finjan2017.models.login.evalution.EvalutionModularQues;
import com.myappilication.xpress.finjan2017.models.login.evalution.EvalutionReq;
import com.myappilication.xpress.finjan2017.models.login.evalution.EvalutionResponse;
import com.myappilication.xpress.finjan2017.models.login.faq.faqdata;
import com.myappilication.xpress.finjan2017.models.login.helpers.NetConnectionDetector;
import com.myappilication.xpress.finjan2017.models.login.helpers.SharedPrefUtils;
import com.myappilication.xpress.finjan2017.models.login.helpers.StaticConfig;
import com.myappilication.xpress.finjan2017.models.login.login.loginreq;
import com.myappilication.xpress.finjan2017.models.login.login.loginresp;
import com.myappilication.xpress.finjan2017.models.login.offlineDatabase.OfflineDatabaseHelper;
import com.myappilication.xpress.finjan2017.newfaqcategroylist.FaqCategroyLIstActivity;
import com.myappilication.xpress.finjan2017.newfeedback.NewFeedbackActivity;
import com.myappilication.xpress.finjan2017.progressstyle.ProgressBarStyle;
import com.myappilication.xpress.finjan2017.termscondition.Support;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class DashBoard extends AppCompatActivity {

    //new
    private static String list_modID_position;

    List<VideoListModules> videoList = new ArrayList<>();

    ArrayList<String> Res = new ArrayList<>();
    String temp_module;
    String temp_video_name;
    String temp_enc_url;
    String  temp_video_type;
    String  temp_Calc;
    String temp_video_image;

    public static String incrementmoduleID;

    public static boolean temp;

    int tempCondition;
    Dialog dialog;
    ImageButton Min;

    ProgressBar pb;

    @SuppressWarnings("deprecation")
    @SuppressLint("SetJavaScriptEnabled")
    Button Download, btn_playvideo, bt_next, calc_Button, faq_button;
    //final String vidAddress = "http://183.82.33.232:8094/coursevideos/";
    MessageDigest mdEnc = null;
    private String filename = "MySampleFile.txt";
    private String filepath = "MyFileStorage";
    File myInternalFile;
    ProgressDialog pDialog;
    Intent newinetent;
    public ProgressDialog prgDialog;
    // Progress Dialog type (0 - for Horizontal progress bar)
    public static final int progress_bar_type = 0;
    //String remember_token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjIsImlzcyI6Imh0dHA6XC9cLzE4My44Mi4zMy4yMzI6ODA5NFwvYXBpXC9sb2dpbiIsImlhdCI6MTQ5MDc4Mjg4MiwiZXhwIjoxNDkwNzg2NDgyLCJuYmYiOjE0OTA3ODI4ODIsImp0aSI6IjE2ZGQwYTEzYTE3NDUyN2IzOGY1MjczZmQ5MGJiMTVmIn0.s0WeAohakMPYIBJ5PVWbOGd6d3iaH7ebpVD-X4WcrtM";
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    byte[] decrpt;
    static Context ctx;
    final List<String> videoNames = new ArrayList<String>();
    TextView t3;
    int t5;
    List<EvalutionModularQues> quesList;
    List<EvalutionModularQues> quesListDB;
    int score = 0;
    int qid = 0;
    EvalutionModularQues currentQ;
    DbHelper db = new DbHelper(this);
    NetConnectionDetector NDC;
    Context context;
    String Search_query = "";
    SearchView searchviewfaq;
    ArrayList<String> Module = new ArrayList<>();
    ArrayList<String> s = new ArrayList<>();
    ProgressBar progressBar;
    VideoView vidView;
    String result;
    ArrayList<HashMap<String, Object>> MyArrList = new ArrayList<HashMap<String, Object>>();
    String filename_to_dl;
    int bytesRead, bytesAvailable, bufferSize;
    ArrayList<String> mcq_id = new ArrayList<>();
   // String modular;
    String m, modul;
    int sentBytes = 0;
    long fileSize1;
    TextView tv_finjan_test;
    String Module_id;
    ArrayList<String> mcq_Id = new ArrayList<String>();
    ArrayList<String> mcq_question = new ArrayList<String>();
    ArrayList<String> mcq_answer1 = new ArrayList<String>();
    ArrayList<String> mcq_answer2 = new ArrayList<String>();
    ArrayList<String> mcq_answer3 = new ArrayList<String>();
    ArrayList<String> mcq_answer4 = new ArrayList<String>();
    ArrayList<String> mcq_correct_ans = new ArrayList<>();
    ArrayList<String> modular = new ArrayList<String>();

    boolean visibleGoToNext=true, allmodCompleted=false;

    public static DashBoard mDashBoard;

    public static  boolean mcq_completed, faq_completed;
    public static ArrayList<String> calculator_completed = new ArrayList<>();

    OfflineDatabaseHelper mOfflineDatabaseHelper;


    Toolbar toolbar;
    ImageButton imageButton;
    ImageView imageView;
    Intent intent;
    String str;
    String List_of_moduleID;

    final Context con=this;

    MediaController mediacontroller;
    TextView go_tonext, go_toprevious;

    Bundle bb = new Bundle();

    boolean ccondition=false;

    public static int increaseModIDPos;

    public static Dialog mprProgressDialog;

    ScrollView contentLayout;
    Button FullScreen;
    int mcurrentPosition;

    boolean fullscr = false;

    private GoogleApiClient client;

    boolean fullsrnCon = false;
    File file;

    public static ArrayList<Activity> dashboard_act = new ArrayList<>();
    /**
     * this.email = email;
     * this.mcq_id = mcq_id;
     * this.user_ans = user_ans;
     * this.modular = modular;
     * this.score = score;
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    VideoView full_sc;
    ProgressBar fulprogressBar;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        ScrollView ll = (ScrollView) findViewById(R.id.scrollviewdash);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
            // addContentView(R.layout.rl_dark,);

            ll.setVisibility(View.GONE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            ll.setVisibility(View.VISIBLE);
            //Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
            //  setContentView(R.layout.dashboardnew);
            //   onResume();

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try{

            if(ccondition==true){
              //  super.onCreate(savedInstanceState);
            }else{
                requestWindowFeature(Window.FEATURE_NO_TITLE);// hide statusbar of Android
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
                super.onCreate(savedInstanceState);
            }

        }catch (Exception e){
            Log.d("error", e.toString());
        }

        setContentView(R.layout.dashboardnew);

        bb = savedInstanceState;
        dashboard_act.add(DashBoard.this);

        fulprogressBar = (ProgressBar) findViewById(R.id.full_progress);

        mprProgressDialog = ProgressBarStyle.getInstance().createProgressDialog(this);
       // mprProgressDialog.show();
       /* ActionBar actionBar = getActionBar();
        actionBar.hide();*/

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        sharedpreferences = getSharedPreferences(SharedPrefUtils.MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        setSupportActionBar(toolbar);
        FullScreen=(Button)findViewById(R.id.full_screen_btn);
        mDashBoard = DashBoard.this;
        mOfflineDatabaseHelper = new OfflineDatabaseHelper(DashBoard.this);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        imageButton = (ImageButton) findViewById(R.id.tb_normal_back);

        btn_playvideo = (Button) findViewById(R.id.btn_playvideo);

        go_toprevious = (TextView) findViewById(R.id.previous);
        go_tonext = (TextView) findViewById(R.id.go_to_next);

        contentLayout = (ScrollView)findViewById(R.id.scrollviewdash);
        intent = getIntent();
        Module_id = sharedpreferences.getString("Module_id", "");

        filename_to_dl = sharedpreferences.getString("filename","");
        Log.d("filename",filename_to_dl);

       // Toast.makeText(DashBoard.this, "Filename......"+filename_to_dl, Toast.LENGTH_SHORT).show();
        // Toast.makeText(getApplicationContext(), "Filename----" + filename_to_dlToast.LENGTH_LONG).show();
        progressBar = (ProgressBar) findViewById(R.id.progress);
        final String enc_url = sharedpreferences.getString("enc_url","");
        Log.d("enc_url",enc_url);

        if(ccondition == true){
            List_of_moduleID = incrementmoduleID;
            Module_id = incrementmoduleID;
        }else{
            List_of_moduleID = getIntent().getStringExtra("list_of_module_id");

            if(temp == true){

            }else{
                increaseModIDPos = Integer.valueOf(ListofModuleFinjan.moduleID_position);
            }

            Module_id = sharedpreferences.getString("Module_id", "");
        }

       /* for(int nn=0; nn<ListofModuleFinjan.modulerID_list.size(); nn++){
            if(nn == 0) {

                String listONe = ListofModuleFinjan.modulerID_list.get(nn);
                if(listONe.equalsIgnoreCase(List_of_moduleID)){
                    Log.d("enc_url","equal");
                }
                Log.d("enc_url","");
            }

            if(nn == ListofModuleFinjan.modulerID_list.size() - 1) {
                String listONe = ListofModuleFinjan.modulerID_list.get(ListofModuleFinjan.modulerID_list.size() - 1);
                if(listONe.equalsIgnoreCase(List_of_moduleID)){
                    Log.d("enc_url","equal");
                }
                Log.d("enc_url","");
            }
        }*/



        String firstOne = ListofModuleFinjan.modulerID_list.get(0);
        if(firstOne.equalsIgnoreCase(List_of_moduleID)){
            Log.d("enc_url","equal");
            go_toprevious.setVisibility(View.INVISIBLE);
        }
        Log.d("enc_url","");

        String lastOne = ListofModuleFinjan.modulerID_list.get(ListofModuleFinjan.modulerID_list.size() - 1);
        if(lastOne.equalsIgnoreCase(List_of_moduleID)){
            Log.d("enc_url","equal");
            go_tonext.setVisibility(View.INVISIBLE);
        }
        Log.d("enc_url","");
        //final String video_type=intent.getStringExtra("Video_type");



        final String play_url = sharedpreferences.getString("playvideo","");
        Log.d("playvideo",play_url);

        final String vtype = sharedpreferences.getString("vtype","");
        Log.d("vtype",vtype);


        final String img = sharedpreferences.getString("video_image","");
        Log.d("video_image",img);

        final String str = sharedpreferences.getString("moduleid","");

        Log.d("moduleid",str);
       // final String calc = intent.getStringExtra("calc");

       // Log.d("calc",str);



//        Log.d("Video_im", img);


        // Toast.makeText(DashBoard.this, "Video_type" + vtype, Toast.LENGTH_SHORT).show();

        sharedpreferences = getSharedPreferences(SharedPrefUtils.MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        imageView = (ImageView) findViewById(R.id.imageView1);
        imageView.setVisibility(View.VISIBLE);

        // imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        // Toast.makeText(DashBoard.this, "Video_type" + vtype, Toast.LENGTH_SHORT).show();

        /* editor.putString(SharedUtils.SpModuleId,moduleId);
        editor.commit();*/

        Download = (Button) findViewById(R.id.btn_downloadnew);

      /*  DisplayMetrics metrics = new DisplayMetrics(); getWindowManager().getDefaultDisplay().getMetrics(metrics);
        android.widget.LinearLayout.LayoutParams params = (android.widget.LinearLayout.LayoutParams) vidView.getLayoutParams();
        params.width =  metrics.widthPixels;
        params.height = metrics.heightPixels;
        params.leftMargin = 0;
        vidView.setLayoutParams(params);*/
        //  String s="coursevideos/Encrypt_module8.mp4";
        file = new File(Environment.getExternalStorageDirectory() + filename_to_dl);
        //File fil = new File(Environment.getExternalStorageDirectory() + filename_to_dl+"e");
     /*   long fileSize = file.length();
        fileSize1 = filename_to_dl.length();
*/

        if (file.exists()) {
            Download.setVisibility(View.INVISIBLE);
        }


        if (play_url.equals("http://183.82.33.232:8094/coclusion image")) {
            Download.setVisibility(View.INVISIBLE);
            btn_playvideo.setVisibility(View.GONE);
//            vidView.setVisibility(View.INVISIBLE);
        }

        //Toast.makeText(getApplicationContext(), "result"+result, Toast.LENGTH_SHORT).show();
        /*TextView textView = (TextView) findViewById(R.id.tv_dashboard);
        textView.setText( ModuleFinJan.courseName);*/
        t3 = (TextView) findViewById(R.id.t1);
        tv_finjan_test = (TextView) findViewById(R.id.text_finjan_test);

        if(NDC.isConnected(DashBoard.this)){
            String name = sharedpreferences.getString("Module_name", "");
            t3.setText(" " + name);
            String testfinjan = sharedpreferences.getString("Module_name", "");
            tv_finjan_test.setText(" " + testfinjan);
            Picasso.with(getApplicationContext()).load(img).resize(2400,
                    1300).centerCrop().into(imageView);

        }else{
            Download.setVisibility(View.INVISIBLE);
          //  btn_playvideo.setEnabled(false);
            FullScreen.setVisibility(View.GONE);
            List<VideoListModules> offLineVideolist = mOfflineDatabaseHelper.getdata(List_of_moduleID);

            if(offLineVideolist.size() > 0){
                for(int tt=0; tt<offLineVideolist.size(); tt++){
                    String name = offLineVideolist.get(tt).getCourse_module();
                    t3.setText(" " + name);

                    tv_finjan_test.setText(" " + name);
                    String vidImg = offLineVideolist.get(tt).getVideo_image();

                    Picasso.with(getApplicationContext()).load(StaticConfig.Base + vidImg).
                            resize(2400, 1300).centerCrop().into(imageView);
                }
                // t3.setText(offLineVideolist.get);
            }else{

            }
        }


        dialog = new Dialog(con, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.activity_full_screen);

        // dialog.setTitle("Title...");
       full_sc = (VideoView) dialog.findViewById(R.id.fullscreen);
        pb = (ProgressBar) dialog.findViewById(R.id.full_s_dialog_progress);

        full_sc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "click", Toast.LENGTH_LONG).show();
            }
        });

      /*  String name = sharedpreferences.getString("Module_name", "");
        t3.setText(" " + name);
        String testfinjan = sharedpreferences.getString("Module_name", "");
        tv_finjan_test.setText(" " + testfinjan);*/


        context = getApplicationContext();
        NDC = new NetConnectionDetector();

        ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
        File directory = contextWrapper.getDir(filepath, Context.MODE_PRIVATE);
        myInternalFile = new File(directory, filename);
        vidView = (VideoView) findViewById(R.id.video);


        vidView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                // TODO Auto-generated method stub
                // Once Music is completed playing, enable the button
                Download.setEnabled(true);
                FullScreen.setVisibility(View.GONE);
                btn_playvideo.setVisibility(View.INVISIBLE);
                Log.d("compleate", "video com");

                Toast.makeText(getApplicationContext(), "Video completed", Toast.LENGTH_LONG).show();

                mOfflineDatabaseHelper.setvideoComplete(List_of_moduleID, "true");

                // Toast.makeText(getApplicationContext(), "Music completed playing", Toast.LENGTH_LONG).show();
            }
        });

        bt_next = (Button) findViewById(R.id.take_test);

        calc_Button = (Button) findViewById(R.id.dashboard_calc_btn);
        faq_button = (Button) findViewById(R.id.dashboard_faq_btn);
        faq_button.setVisibility(View.GONE);

        TextView mcqcomplete = (TextView) findViewById(R.id.test_success);
        TextView calcsuccs = (TextView) findViewById(R.id.calc_success);
        TextView faqSuccs = (TextView) findViewById(R.id.faq_success);


                quesList = db.getAllQuestions(Module_id);

        if(quesList.size()==0){
            bt_next.setVisibility(View.GONE);
            mcqcomplete.setVisibility(View.GONE);
            mOfflineDatabaseHelper.setMCQcompleted(Module_id, "true");
        }

        ////////////////////////////////



        final String Cal = sharedpreferences.getString("calc", "");
        String[] calcname = Cal.split(",");
        ArrayList<String> wordList = new ArrayList<String>(Arrays.asList(calcname));
        int s = wordList.size();
        ArrayList<Integer> cc = new ArrayList<>();

        cc.addAll(CalcPPF.calcPPF_validation);
        cc.addAll(CalcSS.calcSS_validation);
        cc.addAll(CalcSip.calcSip_validation);
        cc.addAll(CalcDreams.calcDreams_validation);
        cc.addAll(CalcExpense.calcExpense_validation);

       String mcqComp = mOfflineDatabaseHelper.getMcqComplete(List_of_moduleID);

        if(mcqComp != null){

            /*mcqcomplete.bringToFront();
            mcqcomplete.setVisibility(View.VISIBLE);
            /*Drawable im = getResources().getDrawable( R.drawable.ic_save_profile);
            im.setBounds( 0, 0, 60, 60 );
            bt_next.setCompoundDrawables( im, null, null, null );*/

            if(quesList.size()==0){

            }else{
                mcqcomplete.bringToFront();
                mcqcomplete.setVisibility(View.VISIBLE);
                Drawable im = getResources().getDrawable( R.drawable.ic_save_profile);
                im.setBounds( 0, 0, 60, 60 );
                bt_next.setCompoundDrawables( im, null, null, null );
            }

            calc_Button.setBackgroundResource(R.drawable.dashboard_green_btn);
            faq_button.setBackgroundResource(R.drawable.dashboard_green_btn);
            faq_button.setVisibility(View.GONE);
            calc_Button.setEnabled(true);
            faq_button.setEnabled(true);

        }else{
           // Toast.makeText(DashBoard.this, "MCQ not completed", Toast.LENGTH_SHORT).show();
        }
        int tt = Cal.length();
        String offCalcValue = db.getCalcValue(Module_id);
        if(s == cc.size()){
            mOfflineDatabaseHelper.setCalcCompleted(List_of_moduleID, "true");
           // Toast.makeText(DashBoard.this, "Calc completed", Toast.LENGTH_SHORT).show();

        }else if(offCalcValue == null){
            calc_Button.setVisibility(View.GONE);
            mOfflineDatabaseHelper.setCalcCompleted(List_of_moduleID, "true");
        }else{
          //  Toast.makeText(DashBoard.this, "Calc not completed", Toast.LENGTH_SHORT).show();
        }

       /* int checkC = sharedpreferences.getInt("mFaqdata", 0);
        if(checkC==0){
            faq_button.setVisibility(View.GONE);
            mOfflineDatabaseHelper.setFaqCompleted(getIntent().getStringExtra("list_of_module_id"), "true");
        }*/

        String calcComp = mOfflineDatabaseHelper.getCalcComplete(List_of_moduleID);

        if(calcComp!=null){
            if(offCalcValue != null){
                calcsuccs.bringToFront();
                calcsuccs.setVisibility(View.VISIBLE);
                Drawable im = getResources().getDrawable( R.drawable.ic_save_profile);
                im.setBounds( 0, 0, 60, 60 );
                calc_Button.setCompoundDrawables( im, null, null, null );
            }else{
                calcsuccs.setVisibility(View.INVISIBLE);
            }

        }

        String ch = db.getFaqValue(Module_id);

        if(Integer.valueOf(ch)==0){
            faq_button.setVisibility(View.GONE);
            mOfflineDatabaseHelper.setFaqCompleted(List_of_moduleID, "true");
        }else{
            mOfflineDatabaseHelper.setFaqCompleted(List_of_moduleID, "true");
        }


        String faqComp = mOfflineDatabaseHelper.getFaqComplete(List_of_moduleID);

        if(faqComp != null){
            if(Integer.valueOf(ch)==1){
                //faqSuccs.bringToFront();
               // faqSuccs.setVisibility(View.VISIBLE);
           /* calcsuccs.bringToFront();
            calcsuccs.setVisibility(View.VISIBLE);
            Drawable im = getResources().getDrawable( R.drawable.ic_save_profile);
            im.setBounds( 0, 0, 60, 60 );
            faq_button.setCompoundDrawables( im, null, null, null );*/
              /*  Drawable im = getResources().getDrawable( R.drawable.ic_save_profile);
                im.setBounds( 0, 0, 60, 60 );
                faq_button.setCompoundDrawables( im, null, null, null );*/
                faq_button.setVisibility(View.GONE);
            }else{
                faqSuccs.setVisibility(View.INVISIBLE);
                faq_button.setVisibility(View.GONE);
               // mOfflineDatabaseHelper.setFaqCompleted(getIntent().getStringExtra("list_of_module_id"), "true");
            }

        }



        go_tonext.setVisibility(View.INVISIBLE);
        go_toprevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callWebService(0);
            }
        });

        if(mcqComp!=null && calcComp!= null && faqComp!=null){
            visibleGoToNext=true;
            Download.setVisibility(View.INVISIBLE);

            String firstOne1 = ListofModuleFinjan.modulerID_list.get(0);
            if(firstOne.equalsIgnoreCase(List_of_moduleID)){
                Log.d("enc_url","equal");
                go_toprevious.setVisibility(View.INVISIBLE);
            }else{
                go_toprevious.setVisibility(View.VISIBLE);
            }
            Log.d("enc_url","");

            String lastOne1 = ListofModuleFinjan.modulerID_list.get(ListofModuleFinjan.modulerID_list.size() - 1);
            if(lastOne.equalsIgnoreCase(List_of_moduleID)){
                Log.d("enc_url","equal");
                go_tonext.setVisibility(View.VISIBLE);

                allmodCompleted = true;
                //hgjh

            }else{



                go_tonext.setVisibility(View.VISIBLE);
            }

            completemodPushtoserver();



            //go_tonext.setPaintFlags(go_tonext.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);



           /* int b = increaseModIDPos;

            if(tempCondition==1){
                b++;
            }else{
                b--;
            }
            try{
                String v = ListofModuleFinjan.modulerID_list.get(b);
            }catch (Exception e){
                if(tempCondition==1){
                    go_tonext.setVisibility(View.INVISIBLE);
                }else{
                    go_toprevious.setVisibility(View.INVISIBLE);
                }
            }*/

            go_tonext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                  //  Bundle b = new Bundle();
                   // ccondition=true;
                 //   onCreate(bb);

                   // completemodPushtoserver();

                    String lastOne = ListofModuleFinjan.modulerID_list.
                            get(ListofModuleFinjan.modulerID_list.size() - 1);

                    if(lastOne.equalsIgnoreCase(List_of_moduleID)){
                        if(allmodCompleted==true) {
                            String videoVali = mOfflineDatabaseHelper.getVideoCompleted(List_of_moduleID);

                            if (videoVali != null) {
                                String name = sharedpreferences.getString("coupon_image", "");

                                String reg_c_image = sharedpreferences.getString("reg_d_coupon_image", "");

                                if(name != null&& name.length()>0) {
                                    sheetDialog(name);
                                }else if(reg_c_image != null&& reg_c_image.length()>0){
                                    sheetDialog(reg_c_image);
                                }else{
                                    modFinishedalert();
                                }

                            } else {
                                Toast.makeText(DashBoard.this, "Watch the video completely to proceed",
                                        Toast.LENGTH_LONG).show();
                            }
                        }

                    }else{
                        callWebService(1);
                    }




                   /* if(allmodCompleted==true){
                        String videoVali = mOfflineDatabaseHelper.getVideoCompleted(List_of_moduleID);

                        if(videoVali!=null){
                            modFinishedalert();
                        }else{
                            Toast.makeText(DashBoard.this, "Watch the video completely and then " +
                                    "you can use next", Toast.LENGTH_LONG).show();
                        }

                    }else{
                        callWebService(1);

                    }
*/

                  /*  String videoVali = mOfflineDatabaseHelper.getVideoCompleted(List_of_moduleID);

                    if(videoVali!=null){
                        callWebService(1);
                    }else{
                        Toast.makeText(DashBoard.this, "Watch the video completely and then " +
                                "move to next module", Toast.LENGTH_LONG).show();
                    }*/



                   // if (NDC.isConnected(context)) {
                       /* if(ListofModuleFinjan.listmoduleFinjan != null){
                            ListofModuleFinjan.listmoduleFinjan.finish();
                        }
                        Intent i = new Intent(DashBoard.this, ListofModuleFinjan.class);
                        i.putExtra("moduleID", ListofModuleFinjan.course_ID);
                        startActivity(i);
                        finish();*/
                  /*  }else{
                        Toast.makeText(DashBoard.this, "Kindly check your network connection", Toast.LENGTH_LONG).show();
                    }*/

                }
            });
        }
        //FullScreen.bringToFront();
        try {
            FullScreen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //   setContentView(R.layout.activity_full_screen);


                    //  Toast.makeText(DashBoard.this, "mCurrentposition"+vidView.getCurrentPosition(), Toast.LENGTH_SHORT).show();

                   /* if(dialog.isShowing()){

                        //  File patternDirectory = new File(Environment.getExternalStorageDirectory().getAbsolutePath().toString()+"/sdcard:e.mp4");
                        // patternDirectory.mkdirs();


                    }else
                    {

                    }*/
    /*
                    full_sc.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            // TODO Auto-generated method stub
                            progressBar.setVisibility(View.VISIBLE);

                            mp.start();
                            full_sc.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

                            if (mp.isPlaying()) {
                                full_sc.setBackgroundColor(0);
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

                    full_sc.setOnErrorListener(new MediaPlayer.OnErrorListener() {

                        @Override
                        public boolean onError(MediaPlayer mp, int what, int extra) {
                            imageView.setVisibility(View.VISIBLE);
                            contentLayout.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {

                                @Override
                                public void onScrollChanged() {
                                    mediacontroller.hide();
                                }
                            });
                            new Offlinevideo().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                            return true;
                        }
                    });*/

                  //  pb.setVisibility(View.VISIBLE);
                   // Mini_scr=(ImageButton) findViewById(R.id.mini);

                    Button pyButton = (Button) dialog.findViewById(R.id.play_button);
                    Button stButton = (Button) dialog.findViewById(R.id.stop_button);

                    pyButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            full_sc.start();
                        }
                    });

                    stButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            full_sc.pause();
                        }
                    });

                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    int orientation = getResources().getConfiguration().orientation;

                    // if(orientation == Configuration.ORIENTATION_LANDSCAPE;
                   /* Window window = dialog.getWindow();
                    window.setLayout(WindowManager.LayoutParams.FILL_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT);*/
                    dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                            WindowManager.LayoutParams.WRAP_CONTENT);

                    Min = (ImageButton) dialog.findViewById(R.id.mini);
                    Min.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                            mcurrentPosition = full_sc.getCurrentPosition();

                            vidView.start();
                            vidView.seekTo(mcurrentPosition);
                            full_sc.pause();
                            dialog.dismiss();
                            progressBar.setVisibility(View.GONE);

                        }
                    });

                    dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            // mcurrentPosition = full_sc.getCurrentPosition();
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                            vidView.start();
                            vidView.seekTo(mcurrentPosition);
                            pb.setVisibility(View.GONE);
                        }
                    });

                    DisplayMetrics metrics = new DisplayMetrics();
                    getWindowManager().getDefaultDisplay().getMetrics(metrics);
                    android.widget.FrameLayout.LayoutParams params =
                            (android.widget.FrameLayout.LayoutParams) full_sc.getLayoutParams();
                    params.width = metrics.widthPixels;
                    params.height = metrics.heightPixels;
                    params.leftMargin = 0;
                    full_sc.setLayoutParams(params);

                    File file = new File(Environment.getExternalStorageDirectory() + filename_to_dl);
                    //File fil = new File(Environment.getExternalStorageDirectory() + filename_to_dl+"e");
         /*   long fileSize = file.length();
            fileSize1 = filename_to_dl.length();
    */
                    int curr = 0;


                    if (file.exists()) {
                        Download.setVisibility(View.INVISIBLE);
                        Uri viduri = Uri.parse(Environment.getExternalStorageDirectory() + "/dec.mp4");


                        full_sc.setMediaController(mediacontroller);
                        full_sc.requestFocus();
                        // full_sc.seekTo(mCurrentposition);

                        full_sc.setVideoURI(viduri);
                        mcurrentPosition = full_sc.getCurrentPosition();
                        vidView.pause();
                        full_sc.start();

                        full_sc.seekTo(vidView.getCurrentPosition());
                    } else {
                        //  if (Integer.parseInt(Lang_id)==Integer.parseInt(Module_id)) {
                        WindowManager.LayoutParams a = dialog.getWindow().getAttributes();
                        a.dimAmount = 0;
                        dialog.getWindow().setAttributes(a);

                        imageView.setVisibility(View.INVISIBLE);

                        MediaController mediacontroller = new MediaController(dialog.getContext());
                        getWindow().setFormat(PixelFormat.TRANSLUCENT);
                        mediacontroller.setAnchorView(full_sc);


                        // Get the URL from String VideoURL
                        Uri video = Uri.parse(play_url);

                        full_sc.setMediaController(mediacontroller);
                        full_sc.requestFocus();
                        //mediacontroller.setVisibility(View.VISIBLE);
                        // full_sc.seekTo(mCurrentposition);

                        full_sc.setVideoURI(video);
                        mcurrentPosition = full_sc.getCurrentPosition();

                        vidView.pause();
                        full_sc.start();
                       // mediacontroller.setVisibility(View.VISIBLE);
                        //mediacontroller.setEnabled(true);
                        full_sc.seekTo(vidView.getCurrentPosition());

                    }
                    // Showing Alert Message
    /*
                    alertDialog.show();
    */
                    dialog.show();
                   // pb.setVisibility(View.GONE);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();

        }
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(visibleGoToNext == true){
                   // if (NDC.isConnected(context)) {
                    try{
                        if(ProfileSetting.profile_Act_list.size()>1) {
                            Log.d("profile act", "more 1");
                            for(int t=0; t<ProfileSetting.profile_Act_list.size(); t++){
                                int n = ProfileSetting.profile_Act_list.size();
                                if(n==1){
                                    break;
                                }else{
                                    ProfileSetting.profile_Act_list.remove(t);
                                    ProfileSetting.profile_Act_list.get(t).finish();
                                }
                            }
                        }

                        if(ListofModuleFinjan.list_mod_act.size()>1) {
                            Log.d("profile act", "more 1");
                            for(int t=0; t<ListofModuleFinjan.list_mod_act.size(); t++){
                                int n = ListofModuleFinjan.list_mod_act.size();
                                if(n==1){
                                    break;
                                }else{
                                    ListofModuleFinjan.list_mod_act.remove(t);
                                    ListofModuleFinjan.list_mod_act.get(t).finish();
                                }
                            }
                        }

                        if(FaqCategroyLIstActivity.faq_activity_list.size()>1) {
                            Log.d("profile act", "more 1");
                            for(int t=0; t<FaqCategroyLIstActivity.faq_activity_list.size(); t++){
                                int n = FaqCategroyLIstActivity.faq_activity_list.size();
                                if(n==1){
                                    break;
                                }else{
                                    FaqCategroyLIstActivity.faq_activity_list.remove(t);
                                    FaqCategroyLIstActivity.faq_activity_list.get(t).finish();
                                }
                            }
                        }

                        if(AllCalcListActivity.calc_act_list.size()>1) {
                            Log.d("profile act", "more 1");
                            for(int t=0; t<AllCalcListActivity.calc_act_list.size(); t++){
                                int n = AllCalcListActivity.calc_act_list.size();
                                if(n==1){
                                    break;
                                }else{
                                    AllCalcListActivity.calc_act_list.remove(t);
                                    AllCalcListActivity.calc_act_list.get(t).finish();
                                }
                            }
                        }

                        Intent i = new Intent(DashBoard.this, ListofModuleFinjan.class);
                        i.putExtra("moduleID", ListofModuleFinjan.course_ID);
                        startActivity(i);
                        finish();

                    }catch (Exception e){
                    }



                    /*}else{
                        Toast.makeText(DashBoard.this, "Kindly check your network connection",
                                Toast.LENGTH_LONG).show();
                    }*/
                }else{
                    finish();
                }
            }
        });

////////////////////////

        calc_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String videoVali = mOfflineDatabaseHelper.getVideoCompleted(List_of_moduleID);

                if(videoVali!=null){
                    //callWebService(1);

                    ArrayList<String> worList = new ArrayList<String>();
                    ArrayList<String> Calc = new ArrayList<String>();
                    final String Cal = sharedpreferences.getString("calc", "");
                    Calc.add(Cal);

                    worList.clear();
                    String[] calcname = Cal.split(",");
                    List<String> newList = Arrays.asList(calcname);
                    String nn = newList.get(0);
                    if(nn.equalsIgnoreCase("null")){
                        // Toast.makeText(DashBoard.this,
                        // "No calculator to this module", Toast.LENGTH_SHORT).show();
                    }else{
                        // worList.addAll(newList);
                        Intent i = new Intent(DashBoard.this, CalcModuleActivity.class);
                        i.putExtra("list_of_module_id", List_of_moduleID);
                        startActivity(i);
                    }

                }else{
                    Toast.makeText(DashBoard.this, "Watch the video completely to proceed",
                            Toast.LENGTH_LONG).show();
                }






            }
        });

        faq_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashBoard.this, FaqActivity.class);
                i.putExtra("list_of_module_id", List_of_moduleID);
                startActivity(i);
            }
        });




        vidView.setOnErrorListener(new MediaPlayer.OnErrorListener() {

            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                imageView.setVisibility(View.VISIBLE);
                contentLayout.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {

                    @Override
                    public void onScrollChanged() {
                       // mediacontroller.hide();
                    }
                });
                new Offlinevideo().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                return true;
            }
        });

        vidView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                // TODO Auto-generated method stub
                mp.start();
                vidView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

                if (mp.isPlaying()) {
                    vidView.setBackgroundColor(0);
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


              //  vidView.setMediaController(null);

                int topContainerId = getResources().getIdentifier("mediacontroller_progress", "id", "android");
                SeekBar seekBarVideo = (SeekBar) mediacontroller.findViewById(topContainerId);
                seekBarVideo.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        seekBar.setEnabled(false);
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        seekBar.setEnabled(false);
                    }
                });
            }
        });


        bt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String videoVali = mOfflineDatabaseHelper.getVideoCompleted(List_of_moduleID);

                if(videoVali!=null){

                    Log.d("list of module id", Module_id);

                    quesList = db.getAllQuestions(Module_id);
                    if (NDC.isConnected(context)) {
                        mcq_Id.clear();
                        mcq_question.clear();
                        mcq_answer1.clear();
                        mcq_answer2.clear();
                        mcq_answer3.clear();
                        mcq_answer4.clear();
                        mcq_correct_ans.clear();
                        if(quesList != null) {
                            for (EvalutionModularQues dc : quesList) {

                                mcq_Id.add(dc.getMcq_id());
                                mcq_answer1.add(dc.getMcq_ans1());
                                mcq_answer2.add(dc.getMcq_ans2());
                                mcq_answer3.add(dc.getMcq_ans3());
                                mcq_answer4.add(dc.getMcq_ans4());
                                mcq_question.add(dc.getMcq_qus());
                                mcq_correct_ans.add(dc.getCorrect_ans());
                                // modular.add(dc.getModular());


                                McQData.getInstance().setMCQid(mcq_Id);
                                McQData.getInstance().setMCQQuestion(mcq_question);
                                McQData.getInstance().setMCQanswer1(mcq_answer1);
                                McQData.getInstance().setMCQanswer2(mcq_answer2);
                                McQData.getInstance().setMCQanswer3(mcq_answer3);
                                McQData.getInstance().setMCQanswer4(mcq_answer4);
                                McQData.getInstance().setMCQcorrectans(mcq_correct_ans);
                                // McQData.getInstance().setMCQcorrectans(modular);

                            }

                            newinetent = new Intent(DashBoard.this, QuestionActivity.class);
                            newinetent.putExtra("list_of_mod_id", Module_id);
                            startActivity(newinetent);
                        }else{

                            //  Toast.makeText(DashBoard.this, "No MCQ to this module", Toast.LENGTH_SHORT).show();

                        }

                    }else if (quesList != null) {
                        // btn_playvideo.setEnabled(false);
                        FullScreen.setVisibility(View.GONE);
                        mcq_Id.clear();
                        mcq_question.clear();
                        mcq_answer1.clear();
                        mcq_answer2.clear();
                        mcq_answer3.clear();
                        mcq_answer4.clear();
                        mcq_correct_ans.clear();
                        for (EvalutionModularQues dc : quesList) {


                            mcq_Id.add(dc.getMcq_id());
                            mcq_answer1.add(dc.getMcq_ans1());
                            mcq_answer2.add(dc.getMcq_ans2());
                            mcq_answer3.add(dc.getMcq_ans3());
                            mcq_answer4.add(dc.getMcq_ans4());
                            mcq_question.add(dc.getMcq_qus());
                            mcq_correct_ans.add(dc.getCorrect_ans());
                            // modular.add(dc.getModular());


                            McQData.getInstance().setMCQid(mcq_Id);
                            McQData.getInstance().setMCQQuestion(mcq_question);
                            McQData.getInstance().setMCQanswer1(mcq_answer1);
                            McQData.getInstance().setMCQanswer2(mcq_answer2);
                            McQData.getInstance().setMCQanswer3(mcq_answer3);
                            McQData.getInstance().setMCQanswer4(mcq_answer4);
                            McQData.getInstance().setMCQcorrectans(mcq_correct_ans);
                            // McQData.getInstance().setMCQcorrectans(modular);

                        }

                        newinetent = new Intent(DashBoard.this, QuestionActivity.class);
                        newinetent.putExtra("list_of_mod_id", Module_id);

                        startActivity(newinetent);
                    }else {
                        Toast.makeText(context, "Kindly check your network connection", Toast.LENGTH_LONG).show();
                    }


                }else{
                    Toast.makeText(DashBoard.this, "Watch the video completely to proceed", Toast.LENGTH_LONG).show();
                }







        /*quesList = db.getAllQuestions();
        if (NDC.isConnected(context)) {
            //getSearchData();

        } else if (quesList != null) {


            for (EvalutionModularQues dc : quesList) {
                mcq_Id.add(dc.getMcq_id());
                mcq_answer1.add(dc.getMcq_ans1());
                mcq_answer2.add(dc.getMcq_ans2());
                mcq_answer3.add(dc.getMcq_ans3());
                mcq_answer4.add(dc.getMcq_ans4());
                mcq_question.add(dc.getMcq_qus());
                mcq_correct_ans.add(dc.getCorrect_ans());

                McQData.getInstance().setMCQid(mcq_Id);
                McQData.getInstance().setMCQQuestion(mcq_question);
                McQData.getInstance().setMCQanswer1(mcq_answer1);
                McQData.getInstance().setMCQanswer2(mcq_answer2);
                McQData.getInstance().setMCQanswer3(mcq_answer3);
                McQData.getInstance().setMCQanswer4(mcq_answer4);
                McQData.getInstance().setMCQcorrectans(mcq_correct_ans);
            }

            newinetent = new Intent(DashBoard.this, QuestionActivity.class);
            startActivity(newinetent);

        } else {
            Toast.makeText(context, "Kindly check your network connection", Toast.LENGTH_LONG).show();
        }*/

                ;
            }
        });

        btn_playvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(NDC.isConnected(context)){
                    progressBar.setVisibility(View.VISIBLE);
                    File file = new File(Environment.getExternalStorageDirectory() + filename_to_dl);
                    //File fil = new File(Environment.getExternalStorageDirectory() + filename_to_dl+"e");
     /*   long fileSize = file.length();
        fileSize1 = filename_to_dl.length();
*/

                    if (file.exists()) {
                        Download.setVisibility(View.INVISIBLE);
                        new Offlinevideo().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    } else {
                        //  if (Integer.parseInt(Lang_id)==Integer.parseInt(Module_id)) {

                        imageView.setVisibility(View.INVISIBLE);
                        mediacontroller = new MediaController(
                                DashBoard.this);
                        mediacontroller.setAnchorView(vidView);
                        // Get the URL from String VideoURL
                        Uri video = Uri.parse(play_url);

                        vidView.setMediaController(mediacontroller);
                        vidView.setVideoURI(video);
                        mcurrentPosition = vidView.getCurrentPosition();
                        //  Toast.makeText(DashBoard.this, "mCurrentposition"+mCurrentposition, Toast.LENGTH_SHORT).show();
                        vidView.start();

                        //  t3.setText("Your OutPut"+position);

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
                    }
                }else{
                    Toast.makeText(DashBoard.this, "Offline Unavailable. Sorry, video can't be taken offline.", Toast.LENGTH_LONG).show();                }
            }
        });


        Download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                File file = new File(Environment.getExternalStorageDirectory() + filename_to_dl);
                //File fil = new File(Environment.getExternalStorageDirectory() + filename_to_dl+"e");
                long fileSize = file.length();
                fileSize1 = filename_to_dl.length();


                if (file.exists()) {
                    Download.setVisibility(View.INVISIBLE);
                    new Offlinevideo().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
                // If the Music File doesn't exist in SD card (Not yet downloaded)e} //if(video_type.equals("yes")) {
                else if (vtype.equalsIgnoreCase("yes")) {

                    // Download.setVisibility(View.VISIBLE);

                    Toast.makeText(getApplicationContext(), "File doesn't exist under SD Card, downloading Mp3 from Internet", Toast.LENGTH_LONG).show();
                    // Trigger Async Task (onPreExecute method)
                    new DownloadMusicfromInternet().execute(enc_url);

                } else if(vtype.equalsIgnoreCase("no")) {
                    Download.setVisibility(View.INVISIBLE);
                    imageView.setVisibility(View.INVISIBLE);

                    MediaController mediacontroller = new MediaController(
                            DashBoard.this);
                    mediacontroller.setAnchorView(vidView);
                    // Get the URL from String VideoURL
                    Uri video = Uri.parse(play_url);
                    vidView.setMediaController(mediacontroller);
                    vidView.setVideoURI(video);
                    vidView.start();
                }else{
                   // Toast.makeText(DashBoard.this, "UnHandeled Exception.", Toast.LENGTH_SHORT).show();
                }

            }


            // Async Task Class
            class DownloadMusicfromInternet extends AsyncTask<String, String, String> {

                // Show Progress bar before downloading Music
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    // Shows Progress Bar Dialog and then call doInBackground method
                  /*  showDialog(progress_bar_type);
                    ProgressBar progressBar;*/
                    // progressBar.setVisibility(View.VISIBLE);
                    showDialog(progress_bar_type);
                }

                // Download Music File from Internet
                @Override
                protected String doInBackground(String... f_url) {
                    int count;
                    try {

                        // Log.d("result",result);
                        URL url = new URL(f_url[0]);
                        URLConnection conection = url.openConnection();
                        conection.connect();

                        // Get Music file length
                        int lenghtOfFile = conection.getContentLength();
                        // input stream to read file - with 8k buffer
                        InputStream input = new BufferedInputStream(url.openStream(), 10 * 1024);
                        // Output stream to write file in SD card
                        // String fileName = url.getFile(url.lastIndexOf('/')+1, url.length() )

                        Log.d("FLN", "" + filename_to_dl);

                        // filename_to_dl = "/Encrypt_finjan_chap_2_kannada.mp4";

                        OutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory() + filename_to_dl);

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
                       /* decrpt = decrypt();
                        playMusic(decrpt);*/
                        // Close streams
                        output.close();
                        input.close();
                    } catch (Exception e) {
                    }
                    return null;
                }

                /*  protected void onProgressUpdate(String... progress) {
                      // Set progress percentage
                      prgDialog.setProgress(Integer.parseInt(progress[0]));
                  }*/
                // While Downloading Music File
                protected void onProgressUpdate(String... progress) {
                    // Set progress percentage
                    prgDialog.setProgress(Integer.parseInt(progress[0]));

                }


                // Once Music File is downloaded
                @Override
                protected void onPostExecute(String file_url) {
                    // Dismiss the dialog after the Music file was downloaded
                 /*   dismissDialog(progress_bar_type);*/
                    Download.setVisibility(View.INVISIBLE);

                    dismissDialog(progress_bar_type);

                    Toast.makeText(getApplicationContext(), "Download complete, playing Music", Toast.LENGTH_LONG).show();
                    btn_playvideo.setVisibility(View.VISIBLE);
                    btn_playvideo.setEnabled(false);
                    //  new Offlinevideo().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                }
            }
            class Offlinevideo extends AsyncTask<String, String, String> {
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    // Shows Progress Bar Dialog and then call doInBackground method
                    //  showDialog(progress_bar_type);
                    //   ProgressBar progressBar;
                    progressBar.setVisibility(View.VISIBLE);
            /*        prgDialog = new ProgressDialog(DashBoard.this);
                    prgDialog.setMessage("Decrypt processing ........");
                    prgDialog.setIndeterminate(false);
                    //  prgDialog.setMax(100);
                    prgDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    prgDialog.setCancelable(false);
                    prgDialog.show();*/

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
                    // prgDialog.cancel();
                    playMusic(decrpt);
                    progressBar.setVisibility(View.INVISIBLE);

                }
            }

           /* public void playMusic(byte[] mp3SoundByteArray) {
                // Read Mp3 file present under SD card
                Uri viduri = Uri.parse(Environment.getExternalStorageDirectory() + "/dec.mp4");
                MediaController mediacontroller = new MediaController(
                        DashBoard.this);
                try {
                    mediacontroller.setAnchorView(vidView);
                    imageView.setVisibility(View.INVISIBLE);
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
                            // Toast.makeText(getApplicationContext(), "Music completed playing", Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (IllegalArgumentException e)

                {
                    //  Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
                } catch (
                        SecurityException e
                        )

                {
                    //  Toast.makeText(getApplicationContext(), "URI cannot be accessed, permissed needed", Toast.LENGTH_LONG).show();
                } catch (
                        IllegalStateException e
                        )

                {
                    //    Toast.makeText(getApplicationContext(), "Media Player is not in correct state", Toast.LENGTH_LONG).show();
                }
            }*/
        });

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    private void sheetDialog(String name) {

        final AlertDialog.Builder dialogBuilder = new
                AlertDialog.Builder(DashBoard.this);

        View bView = getLayoutInflater().inflate(R.layout.finstart_bannerdialog, null);
        dialogBuilder.setView(bView);
        dialogBuilder.setCancelable(false);

        ImageView coursesLayout = (ImageView) bView.findViewById(R.id.banner_view);

        if(name!=null){
            Picasso.with(context)
                    .load(StaticConfig.html_Base+name)
                    .into(coursesLayout);
        }


        Button btn_ok = (Button) bView.findViewById(R.id.banner_button);

        final AlertDialog al = dialogBuilder.create();
        al.show();

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reg_c_image = sharedpreferences.getString("reg_d_coupon_image", "");

                String c_img = sharedpreferences.getString("coupon_image", "");

                if(reg_c_image != null&& reg_c_image.length()>0){
                    editor.remove("reg_coupon_image");
                    editor.commit();
                }

                if(c_img != null&& c_img.length()>0){
                    editor.remove("coupon_image");
                    editor.commit();
                }

                al.dismiss();
                modFinishedalert();

            }
        });
    }

    private void modFinishedalert() {

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(DashBoard.this);
        View bView = getLayoutInflater().inflate(R.layout.custom_feedback_alert, null);
        dialogBuilder.setView(bView);
        Button send_btn = (Button) bView.findViewById(R.id.feedback_okbtn);

        TextView t = (TextView) bView.findViewById(R.id.dialog_text);
        t.setText("Thankyou for completing all modules. Please submit your feedback");

        final AlertDialog al = dialogBuilder.create();
        al.show();

        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (NDC.isConnected(context)) {
                    Intent i = new Intent(DashBoard.this, NewFeedbackActivity.class);
                    startActivity(i);
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(), "Kindly check your network connection",
                            Toast.LENGTH_LONG).show();
                }



            }
        });
    }

    private void completemodPushtoserver() {
        RxClient.get(getApplicationContext()).getUserFinishedCourse(sharedpreferences.getString(SharedPrefUtils.
                SpRememberToken, ""), new Completemodreq(sharedpreferences.getString(SharedPrefUtils.SpEmail, "")
        , ListofModuleFinjan.course_ID, List_of_moduleID), new Callback<CompletemodResponse>() {
            @Override
            public void success(CompletemodResponse completemodResponse, Response response) {
                    String status = completemodResponse.getResult();

                Log.d("", "");
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("", "");
            }
        });

    }

    private void callWebService(final int nnn) {

        try{
          // fulprogressBar.setVisibility(View.VISIBLE);
            mprProgressDialog.show();

            temp=true;
            tempCondition = nnn;
            if(nnn == 1){
                increaseModIDPos++;
            }else{
                increaseModIDPos--;
            }

            incrementmoduleID = ListofModuleFinjan.modulerID_list.get(increaseModIDPos);
            final String incrementModuleName = ListofModuleFinjan.modulerName_list.get(increaseModIDPos);

            List<VideoListModules> offLineVideolist = mOfflineDatabaseHelper.getdata(incrementmoduleID);

            if (NDC.isConnected(context)) {

                RxClient.get(getApplicationContext()).VideoList(sharedpreferences.getString(SharedPrefUtils.
                                SpRememberToken, ""),
                        new VideoListReq(incrementmoduleID),
                        new Callback<VideoListResponse>() {
                            @Override
                            public void success(VideoListResponse dashboardResponse, Response response) {

                                mcq_Id.clear();
                                mcq_question.clear();
                                mcq_answer1.clear();
                                mcq_answer2.clear();
                                mcq_answer3.clear();
                                mcq_answer4.clear();
                                mcq_correct_ans.clear();

                                MCQ[] mcqList = dashboardResponse.getResult().getInfo().getMCQ();
                                faqdata[] mFaq = dashboardResponse.getResult().getInfo().getFAQ();

                                if(mcqList.length > 0) {
                                    for (MCQ m : mcqList) {
                                        mcq_Id.add(m.getMcq_id());
                                        mcq_question.add(m.getMcq_qus());
                                        mcq_answer1.add(m.getMcq_ans1());
                                        mcq_answer2.add(m.getMcq_ans2());
                                        mcq_answer3.add(m.getMcq_ans3());
                                        mcq_answer4.add(m.getMcq_ans4());
                                        mcq_correct_ans.add(m.getCorrect_ans());

                                        McQData.getInstance().setMCQid(mcq_Id);
                                        McQData.getInstance().setMCQQuestion(mcq_question);
                                        McQData.getInstance().setMCQanswer1(mcq_answer1);
                                        McQData.getInstance().setMCQanswer2(mcq_answer2);
                                        McQData.getInstance().setMCQanswer3(mcq_answer3);
                                        McQData.getInstance().setMCQanswer4(mcq_answer4);
                                        McQData.getInstance().setMCQcorrectans(mcq_correct_ans);
                                    }
                                    if (mcq_question.size() != 0 && mcq_question.size() > 0) {
                                        db.addQuestion(mcq_question,
                                                mcq_answer1, mcq_answer2, mcq_answer3, mcq_answer4,
                                                mcq_correct_ans, incrementmoduleID);
                                    }
                                }else{
                                  /*  Toast.makeText(DashBoard.this,
                                            "MCQ test not found", Toast.LENGTH_LONG).show();*/
                                }
                                videoList.clear();
                                Res.clear();
                                for (int i = 0; i < dashboardResponse.getResult().getInfo().getModules().length; i++) {
                                    //Toast.makeText(ModuleFinJan.this, "gh" + Module_id.add(dashboardResponse.getResult().getInfo().getCourses()[i].getId()), Toast.LENGTH_SHORT).show();
                                    videoList.add(dashboardResponse.getResult().getInfo().getModules()[i]);

                                    temp_module = dashboardResponse.getResult().getInfo().getModules()[i].getModule_name();
                                    temp_video_name= StaticConfig.Base + dashboardResponse.getResult().getInfo().getModules()[i].getVideo_name();
                                    //Module_id.add(dashboardResponse.getResult().getInfo().getModules()[i].getModule_id());
                                    temp_enc_url= StaticConfig.Base + dashboardResponse.getResult().getInfo().getModules()[i].getVideo_encrypt();
                                    temp_video_type = dashboardResponse.getResult().getInfo().getModules()[i].getVideo_encrypt_type();
                                    temp_video_image= StaticConfig.Base + dashboardResponse.getResult().getInfo().getModules()[i].getVideo_image();
                                    temp_Calc = dashboardResponse.getResult().getInfo().getModules()[i].getCourse_calculator();

                                    db.addCalcValue(temp_Calc, incrementmoduleID);

                                    String s = dashboardResponse.getResult().getInfo().getModules()[i].
                                            getVideo_encrypt();
                                    mOfflineDatabaseHelper.setDashboardValue(videoList, incrementmoduleID);

                                    if(s!=null){
                                        int cs = s.indexOf("/");
                                        result = s.substring(cs, s.length()) + "";
                                        //  Toast.makeText(ModuleFinJan.this, "cs" + result, Toast.LENGTH_SHORT).show();
                                        Log.d("Res........", result);
                                        Res.add(result);
                                        Log.d("Fileres", String.valueOf(Res));
                                    }
                                }

                                if(mFaq.length==0){
                                    int in =0;
                                    editor.putInt("mFaqdata",in);
                                    db.addFaqValue(incrementmoduleID, "0");
                                }else{
                                    int in =1;
                                    editor.putInt("mFaqdata",in);
                                    db.addFaqValue(incrementmoduleID, "1");
                                }

                                editor.putString("playvideo", temp_video_name);
                                editor.putString("moduleid", incrementmoduleID);
                                editor.putString("vtype", temp_video_type);
                                editor.putString("video_image", temp_video_image);
                                editor.putString("filename",result);

                                editor.putString("Module_id", incrementmoduleID);
                                editor.putString("Module_name", incrementModuleName);

                                if(temp_Calc != null){
                                    editor.putString("calc", temp_Calc);
                                }else{
                                    editor.putString("calc", "null");
                                }

                                editor.putString("enc_url", temp_enc_url);
                                editor.commit();

                                CalcPPF.calcPPF_validation.clear();
                                CalcSip.calcSip_validation.clear();
                                CalcSS.calcSS_validation.clear();
                                CalcExpense.calcExpense_validation.clear();
                                CalcDreams.calcDreams_validation.clear();

                                mprProgressDialog.dismiss();

                                ccondition=true;
                                onCreate(bb);
                              // fulprogressBar.setVisibility(View.INVISIBLE);

                            }

                            @Override
                            public void failure(RetrofitError error) {
                               // fulprogressBar.setVisibility(View.INVISIBLE);
                               mprProgressDialog.dismiss();
                            }
                        });

            }else if(offLineVideolist.size() > 0){
               // btn_playvideo.setEnabled(false);
                mprProgressDialog.dismiss();
                FullScreen.setVisibility(View.GONE);
                ccondition=true;
                onCreate(bb);
            }else{
                Toast.makeText(DashBoard.this, "Kindly check your network connection", Toast.LENGTH_LONG).show();
            }





        }catch (Exception e){
            Toast.makeText(DashBoard.this, "Module completed", Toast.LENGTH_SHORT).show();
        }

                }


    private void playMusic(byte[] decrpt) {
        Uri viduri = Uri.parse(Environment.getExternalStorageDirectory() + "/dec.mp4");
        MediaController mediacontroller = new MediaController(
                DashBoard.this);
        try {
            mediacontroller.setAnchorView(vidView);
            imageView.setVisibility(View.INVISIBLE);
            // Get the URL from String VideoURL
            Uri cv = Uri.parse(String.valueOf(viduri));
            vidView.setMediaController(mediacontroller);
            vidView.setVideoURI(cv);
            vidView.start();

        } catch (IllegalArgumentException e)

        {
            //  Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        } catch (
                SecurityException e
                )

        {
            //  Toast.makeText(getApplicationContext(), "URI cannot be accessed, permissed needed", Toast.LENGTH_LONG).show();
        } catch (
                IllegalStateException e
                )

        {
            //    Toast.makeText(getApplicationContext(), "Media Player is not in correct state", Toast.LENGTH_LONG).show();
        }
    }



    void encrypt() throws IOException, NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException {
        // Here you read the cleartext.
        File extStore = Environment.getExternalStorageDirectory();

        FileInputStream fis = new FileInputStream(extStore + filename_to_dl);
        Log.d("FileInputStream", String.valueOf(fis));

        //    Toast.makeText(this,"Encrypt"+fis,Toast.LENGTH_SHORT).show();
        // This stream write the encrypted text. This stream will be wrapped by
        // another stream.
        FileOutputStream fos = new FileOutputStream(extStore + filename_to_dl);
        Log.d("FileOutputStream", String.valueOf(fos));

        // Length is 16 byte
        SecretKeySpec sks = new SecretKeySpec("MyDifficultPassw".getBytes(),
                "AES");
        // Create cipher
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, sks);
        // Wrap the output stream
        CipherOutputStream cos = new CipherOutputStream(fos, cipher);
        // Write bytes
        int b;

        byte[] d = new byte[10240];

        while ((b = fis.read(d)) != -1) {
            cos.write(d, 0, b);
        }
        // Flush and close streams.
        cos.flush();
        cos.close();
        fis.close();


    }

    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case progress_bar_type:
                prgDialog = new ProgressDialog(DashBoard.this);
                prgDialog.setMessage("Downloading  file. Please wait...");
                prgDialog.setIndeterminate(false);
                prgDialog.setMax(100);
                prgDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                prgDialog.setCancelable(true);
                prgDialog.show();
                return prgDialog;
            default:
                return null;
        }
    }


    byte[] decrypt() throws IOException, NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException {

        int VideoI = 1;
        //String s="/Android/data/com.example.balasri.finjonn/data/";
        File extStore = Environment.getExternalStorageDirectory();
        //   Uri u = Uri.parse("android.resource://" + DashBoard.ctx.getPackageName() + "/" + videoNames.get(VideoI));
        FileInputStream fis = new FileInputStream(extStore + filename_to_dl);
        Log.d("DecFileInputStream", String.valueOf(fis));

        FileOutputStream fos = new FileOutputStream(extStore + "/dec.mp4");
        Log.d("DecFileOutputStream", String.valueOf(fos));

        SecretKeySpec sks = new SecretKeySpec("MyDifficultPassw".getBytes(),
                "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, sks);
        CipherInputStream cis = new CipherInputStream(fis, cipher);

        int b;
        byte[] d = new byte[10240];
        long total = 0;
        while ((b = cis.read(d)) != -1) {
            total += b;
            // publishProgress("" + (int) ((total * 100) / fileSize1));

            fos.write(d, 0, b);

        }
        //  Toast.makeText(DashBoard.this, fos.toString(), Toast.LENGTH_LONG).show();
        fos.flush();
        fos.close();


        cis.close();

        // String path = android.os.Environment.getExternalStorageDirectory().getPath()+"/bb.mp4";


        return d;
    }


    public void getSearchData() {
        RxClient.get(DashBoard.this).Evalution(sharedpreferences.getString(SharedPrefUtils.SpRememberToken, ""),
                new EvalutionReq(sharedpreferences.getString(SharedPrefUtils.SpEmail, "")),
                new Callback<EvalutionResponse>() {
                    @Override
                    public void success(EvalutionResponse evalutionResponse, Response response) {

                        if (evalutionResponse.getStatus().equals("200")) {

                            EvalutionModularQues[] resDownloadCourse = evalutionResponse.getResult().getInfo().getModularQus();
                            db.onDelete();


                            for (EvalutionModularQues dc : resDownloadCourse) {
                                if (Module_id.equals(dc.getModular())) {

                                    mcq_Id.add(dc.getMcq_id());
                                    mcq_question.add(dc.getMcq_qus());
                                    mcq_answer1.add(dc.getMcq_ans1());
                                    mcq_answer2.add(dc.getMcq_ans2());
                                    mcq_answer3.add(dc.getMcq_ans3());
                                    mcq_answer4.add(dc.getMcq_ans4());
                                    mcq_correct_ans.add(dc.getCorrect_ans());


                                    McQData.getInstance().setMCQid(mcq_Id);
                                    McQData.getInstance().setMCQQuestion(mcq_question);
                                    McQData.getInstance().setMCQanswer1(mcq_answer1);
                                    McQData.getInstance().setMCQanswer2(mcq_answer2);
                                    McQData.getInstance().setMCQanswer3(mcq_answer3);
                                    McQData.getInstance().setMCQanswer4(mcq_answer4);
                                    McQData.getInstance().setMCQcorrectans(mcq_correct_ans);


                                    // quesList.add(dc);

                                    //db.addQuestion(quesList);

                                    // Toast.makeText(getApplicationContext(), "Result" +quesList, Toast.LENGTH_LONG).show();
                                    // Log.d("Ques", String.valueOf(quesList));
                                    //quesList = db.getAllQuestions();
                                    // currentQ = quesList.get(qid);


                                }
                            }

                            if (mcq_question.size() != 0 && mcq_question.size() > 0) {
                               /* db.addQuestion(mcq_question,
                                        mcq_answer1, mcq_answer2, mcq_answer3, mcq_answer4, mcq_correct_ans);*/

                                //quesList = db.getAllQuestions();
                                newinetent = new Intent(DashBoard.this, QuestionActivity.class);

                                startActivity(newinetent);
                                finish();
                            } else {
                                bt_next.setVisibility(View.INVISIBLE);

                            }


                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        //Toast.makeText(getApplicationContext(), "Result" + error, Toast.LENGTH_LONG).show();

                    }

                });
    }


    protected void onStart() {
       setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        vidView.start();
        vidView.seekTo(mcurrentPosition);
        super.onStart();
       // Toast.makeText(DashBoard.this, "start", Toast.LENGTH_SHORT).show();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        //client.connect();
        //   Toast.makeText(this, "onStart", Toast.LENGTH_SHORT).show();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        /*Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "DashBoard Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
               // "android-app://com.myappilication.xpress.finjan2017/http/host/path"

                Uri.parse("android-app://"+R.string.packge_name+"/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);*/
    }

    @Override
    protected void onResume() {
        super.onResume();

        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

       // Toast.makeText(DashBoard.this, "resume", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //  Toast.makeText(this, "onRestart", Toast.LENGTH_SHORT).show();
    }

   /* @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public boolean onTouch(View v, MotionEvent e) {
        if (e.getAction() == MotionEvent.ACTION_UP) {
            Log.d("MediaPlayer", "Playing the audio.");
            if (vidView.getAudioSessionId() != 0) {
                vidView.start();
                Log.d("MediaPlayer", "Success!");
            }
        }
        return true;
    }
*/
    @Override
    protected void onPause() {
        //Toast.makeText(this, "onPause", Toast.LENGTH_SHORT).show();
        if(fullscr==false) {
            Uri viduri = Uri.parse(Environment.getExternalStorageDirectory() + "/dec.mp4");

            //  File patternDirectory = new File(Environment.getExternalStorageDirectory().getAbsolutePath().toString()+"/sdcard:e.mp4");
            // patternDirectory.mkdirs();
            File auxFile = new File(viduri.toString());

            auxFile.delete();
        }
        vidView.pause();
        full_sc.pause();
        dialog.dismiss();
       /* if(file.exists()){
            FullScreen.setVisibility(View.INVISIBLE);
        }*/
        super.onPause();
        // create temp file that will hold byte array
    }

    @Override
    protected void onDestroy() {
        Log.w("", "App destroyed");

        super.onDestroy();
    }


    @Override
    protected void onStop() {

       // Toast.makeText(this, "onStop", Toast.LENGTH_SHORT).show();
        Uri viduri = Uri.parse(Environment.getExternalStorageDirectory() + "/dec.mp4");
        progressBar.setVisibility(View.GONE);

        //  File patternDirectory = new File(Environment.getExternalStorageDirectory().getAbsolutePath().toString()+"/sdcard:e.mp4");
        // patternDirectory.mkdirs();
        File auxFile = new File(viduri.toString());

        auxFile.delete();
        // Tried reusing instance of media player

        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        /*Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "DashBoard Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.myappilication.xpress.finjan2017/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.disconnect();*/
    }
    // create temp file that will hold byte array

    // but that resulted in system crashes...


    private void mtd_refresh_token() {
       /* Toast.makeText(context, "expired", Toast.LENGTH_SHORT).show();*/
        RxClient.get(DashBoard.this).Login(new loginreq(sharedpreferences.getString(SharedPrefUtils.SpEmail, ""), sharedpreferences.getString(SharedPrefUtils.SpPassword, "")), new Callback<loginresp>() {
            @Override
            public void success(loginresp loginresp, Response response) {


                if (loginresp.getStatus().equals("200")) {


                   // Toast.makeText(getApplicationContext(), "sucesss" , Toast.LENGTH_LONG).show();

                    editor.putString(SharedPrefUtils.SpRememberToken, loginresp.getToken().toString());

                    editor.commit();
                    /*adapter.notifyDataSetChanged();*/

                }
            }


            @Override
            public void failure(RetrofitError error) {

                //Toast.makeText(getApplicationContext(),"Wrong Username And Password",Toast.LENGTH_LONG).show();

            }
        });

    }

    public void onBackPressed() {

        if(visibleGoToNext == true){

            try{
                if(ProfileSetting.profile_Act_list.size()>1) {
                    Log.d("profile act", "more 1");
                    for(int t=0; t<ProfileSetting.profile_Act_list.size(); t++){
                        int n = ProfileSetting.profile_Act_list.size();
                        if(n==1){
                            break;
                        }else{
                            ProfileSetting.profile_Act_list.remove(t);
                            ProfileSetting.profile_Act_list.get(t).finish();
                        }
                    }
                }

                if(ListofModuleFinjan.list_mod_act.size()>1) {
                    Log.d("profile act", "more 1");
                    for(int t=0; t<ListofModuleFinjan.list_mod_act.size(); t++){
                        int n = ListofModuleFinjan.list_mod_act.size();
                        if(n==1){
                            break;
                        }else{
                            ListofModuleFinjan.list_mod_act.remove(t);
                            ListofModuleFinjan.list_mod_act.get(t).finish();
                        }
                    }
                }

                if(FaqCategroyLIstActivity.faq_activity_list.size()>1) {
                    Log.d("profile act", "more 1");
                    for(int t=0; t<FaqCategroyLIstActivity.faq_activity_list.size(); t++){
                        int n = FaqCategroyLIstActivity.faq_activity_list.size();
                        if(n==1){
                            break;
                        }else{
                            FaqCategroyLIstActivity.faq_activity_list.remove(t);
                            FaqCategroyLIstActivity.faq_activity_list.get(t).finish();
                        }
                    }
                }

                if(AllCalcListActivity.calc_act_list.size()>1) {
                    Log.d("profile act", "more 1");
                    for(int t=0; t<AllCalcListActivity.calc_act_list.size(); t++){
                        int n = AllCalcListActivity.calc_act_list.size();
                        if(n==1){
                            break;
                        }else{
                            AllCalcListActivity.calc_act_list.remove(t);
                            AllCalcListActivity.calc_act_list.get(t).finish();
                        }
                    }
                }
                Intent i = new Intent(DashBoard.this, ListofModuleFinjan.class);
                i.putExtra("moduleID", ListofModuleFinjan.course_ID);
                startActivity(i);
                finish();
            }catch (Exception e){
            }

        }else{
            super.onBackPressed();
        }

       /* super.onBackPressed(); // this can go before or after your stuff below
        // do your stuff when the back button is pressed
        filename_to_dl = "";
        finish();
        // super.onBackPressed(); calls finish(); for you

        // clear your SharedPreferences*/

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.finstart_c:
                Intent i = new Intent(getApplicationContext(), ListofModuleFinjan.class);
                i.putExtra("moduleID", "5");
                ModuleFinJan.courseID = "5";
                ModuleFinJan.courseName = "Finstart";
                finish();
                startActivity(i);
                return true;

            case R.id.finpedia:
                startActivity(new Intent(getApplicationContext(), FaqCategroyLIstActivity.class));
                ModuleFinJan.courseID = "5";
                // finish();
                return true;

            case R.id.calc:
                startActivity(new Intent(getApplicationContext(), AllCalcListActivity.class));
                return true;

            case R.id.learning_center:
                startActivity(new Intent(getApplicationContext(), Learning_centre.class));
                // finish();
                return true;

            case R.id.articles:
                startActivity(new Intent(getApplicationContext(), MediaActivity.class));
                // finish();
                return true;

            case R.id.ss_selection:
                startActivity(new Intent(getApplicationContext(), Scheme.class));
                // finish();
                return true;

            /*case R.id.li_invest:
                startActivity(new Intent(getApplicationContext(), Link_To_Interest.class));
                // finish();
                return true;*/

            /*case R.id.fb_post:
                startActivity(new Intent(getApplicationContext(), FB_Posts.class));
                return true;*/

            case R.id.profile_menu:
                startActivity(new Intent(getApplicationContext(), ProfileSetting.class));
                return true;
            /*case R.id.finstaffcources:
                startActivity(new Intent(getApplicationContext(), ModuleFinJan.class));
                return true;*/
            case R.id.changepassword:
                startActivity(new Intent(getApplicationContext(), ChangePassword.class));
                return true;

            case R.id.feedback:
                if (NDC.isConnected(context)) {
                    startActivity(new Intent(getApplicationContext(), NewFeedbackActivity.class));
                    return true;
                }else{
                    Toast.makeText(DashBoard.this, "Kindly check your network connection",
                            Toast.LENGTH_LONG).show();
                    return false;
                }

            case R.id.fin_support:
                startActivity(new Intent(getApplicationContext(), Support.class));
                return true;

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

                mOfflineDatabaseHelper.deleteAll();

                finish();
                return true;
        }
        return false;
    }


    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_for_all, menu);

        return true;
    }


    class Offlinevideo extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Shows Progress Bar Dialog and then call doInBackground method
            //  showDialog(progress_bar_type);
            //   ProgressBar progressBar;
            progressBar.setVisibility(View.VISIBLE);
            /*        prgDialog = new ProgressDialog(DashBoard.this);
                    prgDialog.setMessage("Decrypt processing ........");
                    prgDialog.setIndeterminate(false);
                    //  prgDialog.setMax(100);
                    prgDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    prgDialog.setCancelable(false);
                    prgDialog.show();*/

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
            // prgDialog.cancel();
            playMusic(decrpt);
            progressBar.setVisibility(View.INVISIBLE);

        }

    }

           /* public void playMusic(byte[] mp3SoundByteArray) {
                // Read Mp3 file present under SD card
                Uri viduri = Uri.parse(Environment.getExternalStorageDirectory() + "/dec.mp4");
                MediaController mediacontroller = new MediaController(
                        DashBoard.this);
                try {
                    mediacontroller.setAnchorView(vidView);
                    imageView.setVisibility(View.INVISIBLE);
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
                            // Toast.makeText(getApplicationContext(), "Music completed playing", Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (IllegalArgumentException e)

                {
                    //  Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
                } catch (
                        SecurityException e
                        )

                {
                    //  Toast.makeText(getApplicationContext(), "URI cannot be accessed, permissed needed", Toast.LENGTH_LONG).show();
                } catch (
                        IllegalStateException e
                        )

                {
                    //    Toast.makeText(getApplicationContext(), "Media Player is not in correct state", Toast.LENGTH_LONG).show();
                }
            }*/
}
