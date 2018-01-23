package com.myappilication.xpress.finjan2017;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.myappilication.xpress.finjan2017.allcalculatorlist.AllCalcListActivity;
import com.myappilication.xpress.finjan2017.feedback.FeedActivity;
import com.myappilication.xpress.finjan2017.feedback.OfflineFeedbackDB;
import com.myappilication.xpress.finjan2017.feedback.UserFeedbackList;
import com.myappilication.xpress.finjan2017.mcqevalutiontest.McQData;
import com.myappilication.xpress.finjan2017.menulist.Learning_centre;
import com.myappilication.xpress.finjan2017.menulist.Link_To_Interest;
import com.myappilication.xpress.finjan2017.menulist.MediaActivity;
import com.myappilication.xpress.finjan2017.menulist.Scheme;
import com.myappilication.xpress.finjan2017.models.login.VideoList.MCQ;
import com.myappilication.xpress.finjan2017.models.login.VideoList.VideoListModules;
import com.myappilication.xpress.finjan2017.models.login.VideoList.VideoListReq;
import com.myappilication.xpress.finjan2017.models.login.VideoList.VideoListResponse;
import com.myappilication.xpress.finjan2017.models.login.couponbasedcourses.CouponBSResponse;
import com.myappilication.xpress.finjan2017.models.login.evalution.EvalutionModularQues;
import com.myappilication.xpress.finjan2017.models.login.faq.Faqlistdatas;
import com.myappilication.xpress.finjan2017.models.login.faq.faqdata;
import com.myappilication.xpress.finjan2017.models.login.helpers.DataBase;
import com.myappilication.xpress.finjan2017.models.login.helpers.NetConnectionDetector;
import com.myappilication.xpress.finjan2017.models.login.helpers.SharedPrefUtils;
import com.myappilication.xpress.finjan2017.models.login.helpers.StaticConfig;
import com.myappilication.xpress.finjan2017.models.login.login.loginreq;
import com.myappilication.xpress.finjan2017.models.login.login.loginresp;
import com.myappilication.xpress.finjan2017.models.login.modulelist.LOCourse;
import com.myappilication.xpress.finjan2017.models.login.modulelist.ListOfModuleReq;
import com.myappilication.xpress.finjan2017.models.login.modulelist.ListOfModuleResponse;
import com.myappilication.xpress.finjan2017.models.login.newfaqcategorylist.NewFaqCategoryResponse;
import com.myappilication.xpress.finjan2017.models.login.newfaqmoduleweb.NewFaqRequest;
import com.myappilication.xpress.finjan2017.models.login.newfaqmoduleweb.NewFaqResponse;
import com.myappilication.xpress.finjan2017.models.login.offlineDatabase.DatabaseModules;
import com.myappilication.xpress.finjan2017.models.login.offlineDatabase.OfflineDatabaseHelper;
import com.myappilication.xpress.finjan2017.newfaqcategroylist.FaqCategroyLIstActivity;
import com.myappilication.xpress.finjan2017.newfeedback.NewFeedbackActivity;
import com.myappilication.xpress.finjan2017.progressstyle.ProgressBarStyle;
import com.myappilication.xpress.finjan2017.termscondition.Support;
import com.myappilication.xpress.finjan2017.webservice.RxClient;
import com.squareup.picasso.Picasso;

import java.sql.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by suresh on 15/5/17.
 */
public class ListofModuleFinjan extends AppCompatActivity {

  //  ListView listView;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    Toolbar toolbar;
    ArrayList<String> cModule = new ArrayList<>();
    ArrayList<String> cmoduleID = new ArrayList<>();
    Context context;
    //ArrayList<String> video_name = new ArrayList<>();
    ArrayList<String> Module_id = new ArrayList<>();
    ArrayList<String> Module = new ArrayList<>();
    ArrayList<String> Res = new ArrayList<>();
  /*  ArrayList<String> enc_url = new ArrayList<>();
    ArrayList<String> Lang_id = new ArrayList<>();
    ArrayList<String> Res = new ArrayList<>();
    ArrayList<String> video_type = new ArrayList<>();
    ArrayList<String> video_image = new ArrayList<>();
    ArrayList<String> Calc = new ArrayList<>();*/
    String img;
    String video_name;
    String module_id;
    String module;
    String enc_url;
    String Lang_id;
    String video_type;
    String video_image;
    String Calc;
    String result;
    NetConnectionDetector NDC;
    DataBase database = new DataBase(ListofModuleFinjan.this);
    ListView listView;
    List<VideoListModules> videoList = new ArrayList<>();
    List<LOCourse> CourseList = new ArrayList<>();

    int score = 0;
    int qid = 0;
    VideoListModules currentQ;
    String moduleid;

    LOCourse curr;
    String play_url;
    String vtype;
    String filename;


    ArrayList<String> mcq_question = new ArrayList<String>();
    ArrayList<String> mcq_Id = new ArrayList<String>();
    ArrayList<String> mcq_answer1 = new ArrayList<String>();
    ArrayList<String> mcq_answer2 = new ArrayList<String>();

    ArrayList<String> mcq_answer3 = new ArrayList<String>();
    ArrayList<String> mcq_answer4 = new ArrayList<String>();
    ArrayList<String> mcq_correct_ans = new ArrayList<>();

    DbHelper db = new DbHelper(this);
    List<EvalutionModularQues> quesList;

    OfflineDatabaseHelper offlineDB;

    String strMCQ_question, strMCQ_id, strMCQ_answer1, strMCQ_answer2, strMCQ_answer3, strMCQ_answer4,
    strMCQCorrect_ans;
    public static String course_ID;
    public static String course_Name;
    LinearLayout linear;



    public static ArrayList<String> modulerID_list = new ArrayList<>();
    public static ArrayList<String> modulerName_list = new ArrayList<>();


    public static String moduleID_position;

    public static String coupon_img;


    TextView t3;
    ImageButton imageButton;
    Button faq_button;

    public static ArrayList<Activity> list_mod_act = new ArrayList<>();

    ProgressBar progressBar;

    public static Dialog mprProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.finjan_module_course);

        sharedpreferences = getSharedPreferences(SharedPrefUtils.MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        course_ID = getIntent().getStringExtra("moduleID");

        progressBar = (ProgressBar) findViewById(R.id.progressBar_cyclic);


        if(list_mod_act.size()==0){
            list_mod_act.add(ListofModuleFinjan.this);
        }

        context = getApplicationContext();
        offlineDB = new OfflineDatabaseHelper(ListofModuleFinjan.this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        mprProgressDialog = ProgressBarStyle.getInstance().createProgressDialog(this);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

       String c_image = getIntent().getStringExtra("coupon_image");

        String reg_c_image = sharedpreferences.getString("reg_m_coupon_image", "");

        if(c_image!=null && c_image.length()>0){
            sheetDialog(c_image);
        }else if(reg_c_image != null&& reg_c_image.length()>0){
            sheetDialog(reg_c_image);
        }

        imageButton = (ImageButton) findViewById(R.id.tb_normal_back);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // moveTaskToBack(true);
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

                    if(list_mod_act.size()>1) {
                        Log.d("profile act", "more 1");
                        try{
                            for(int t=0; t<=list_mod_act.size(); t++){
                                int n = list_mod_act.size();
                                if(n==1){
                                    break;
                                }else{
                                    list_mod_act.remove(t);
                                    list_mod_act.get(t).finish();
                                }
                            }
                        }catch (Exception e){

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

                }catch (Exception e){
                }
                finish();
      // FinstartHomeActivity.homeActivity.finish();
            }
        });

        //listView = (ListView) findViewById(R.id.list);
        linear = (LinearLayout) findViewById(R.id.linear);

        faq_button = (Button) findViewById(R.id.listofmod_faq_btn);

        t3 = (TextView) findViewById(R.id.tv_dashboard);
        t3.setText(" " + ModuleFinJan.courseName);

        faq_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

       /* editor.remove("couponvalidation");
        editor.commit();*/

        List<DatabaseModules> getDB_listMod = offlineDB.getListOfCourseMod(course_ID);


                if (NDC.isConnected(context)) {

                    callWebService(course_ID);

                }else if(getDB_listMod.size()>0){

                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date();
                    String datee = dateFormat.format(date);

                    DateFormat dateF = android.text.format.DateFormat.getDateFormat(getApplicationContext());


                    Date d=new Date();
                    SimpleDateFormat sdf=new SimpleDateFormat("hh:mm:ss");
                    String currentDateTimeString = sdf.format(d);


                    String em = sharedpreferences.getString(SharedPrefUtils.SpEmail, "");
                    String expvalidation = offlineDB.getexpValidation(em);



                     Log.d("database exp", expvalidation);
                    Log.d("Android current date", datee);

                    //  if(expvalidation!= null && expvalidation.length()>0){
           /* DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            Log.d(" act", expvalidation + dateFormat.format(date));*/
                    //   2017-07-05 18:15:00

                    boolean offBCondition=false;
                    try {

                        if(expvalidation != null){
                            if(dateFormat.parse(expvalidation).before(dateFormat.parse(datee)))
                            {
                                offBCondition = true;//If start date is before end date
                                Log.d("database exp", "true");
                            }
                            else if(dateFormat.parse(expvalidation).equals(dateFormat.parse(datee)))
                            {
                                offBCondition = true;//If two dates are equal
                                Log.d("database exp", "true");
                            }
                            else
                            {
                                offBCondition = false; //If start date is after the end date
                                Log.d("database exp", "false");
                            }
                        }


                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }



                    if(offBCondition==true){
                        // callWebService(course_ID);

                        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ListofModuleFinjan.this);
                        View bView = getLayoutInflater().inflate(R.layout.custom_feedback_alert, null);
                        dialogBuilder.setView(bView);
                        dialogBuilder.setCancelable(false);
                        Button send_btn = (Button) bView.findViewById(R.id.feedback_okbtn);

                        TextView t = (TextView) bView.findViewById(R.id.dialog_text);
                        t.setText("Your subscription for finstart has expired kindly subscribe again " +
                                "if you want to use finstart");

                        final AlertDialog al = dialogBuilder.create();
                        al.show();

                        send_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                editor.remove("couponbaseModuleid");
                                editor.remove("isusergetmoduleid");
                                editor.remove("isusergetexpdate");


                                editor.commit();

                                offlineDB.deleteSingleEmail(sharedpreferences.getString(SharedPrefUtils.SpEmail, ""));
                                al.dismiss();

                                try{
                                    for(Activity a : list_mod_act){
                                        a.finish();
                                    }

                                    for(Activity a : AllCalcListActivity.calc_act_list){
                                        a.finish();
                                    }

                                    for(Activity a: NewFeedbackActivity.feed_act) {
                                        a.finish();
                                    }

                                    for(Activity a : ProfileSetting.profile_Act_list){
                                        a.finish();
                                    }

                                    for(Activity a : FaqCategroyLIstActivity.faq_activity_list){
                                        a.finish();
                                    }

                                    for(Activity a : DashBoard.dashboard_act){
                                        a.finish();
                                    }

                                    for(Activity a : ActivityEvaluation.actEval_act){
                                        a.finish();
                                    }

                                    for(Activity a :Learning_centre.learning_cen_act){
                                        a.finish();
                                    }
                                    for(Activity a :Link_To_Interest.link_inter_act){
                                        a.finish();
                                    }
                                    for(Activity a :Scheme.scheme_act){
                                        a.finish();
                                    }
                                    for(Activity a :Link_To_Interest.link_inter_act){
                                        a.finish();
                                    }
                                    for(Activity a :ChangePassword.changep_act){
                                        a.finish();
                                    }

                                }catch (Exception e){
                                }

                                Intent i = new Intent(ListofModuleFinjan.this, TryFinStart.class);
                                startActivity(i);
                                finish();

                            }
                        });
                        // }
                    }else{
                        modulerName_list.clear();
                        modulerID_list.clear();
                        cModule.clear();
                        cmoduleID.clear();
                        for(DatabaseModules v : getDB_listMod){
                            cModule.add(v.get_listof_CourseName());
                            cmoduleID.add(v.get_listof_modID());
                            modulerName_list.add(v.get_listof_CourseName());
                            modulerID_list.add(v.get_listof_modID());

                        }

                        if(cModule.size()>0) {

                            List<DatabaseModules> complete_prossess = offlineDB.getAllMCQComplete(true);
                            List<DatabaseModules> CALCcomplete_prossess = offlineDB.getAllCALCComplete(true);
                            List<DatabaseModules> FAQcomplete_prossess = offlineDB.getAllFAQComplete(true);

                            boolean tempCon = true;
                            ArrayList<String> mcqtempList = new ArrayList<String>();
                            ArrayList<String> calctempList = new ArrayList<String>();
                            ArrayList<String> faqtempList = new ArrayList<String>();

                            for(DatabaseModules mm : complete_prossess){
                                mcqtempList.add(mm.getMcq_complete_all_courseid());
                            }

                            for(DatabaseModules mm : CALCcomplete_prossess){
                                calctempList.add(mm.getCalc_complete_all_courseid());
                            }

                            for(DatabaseModules mm : FAQcomplete_prossess){
                                faqtempList.add(mm.getFaq_complete_all_courseid());
                            }

                            for(int t=0; t<cModule.size(); t++){
                                final Button btnTag = new Button(ListofModuleFinjan.this);
                                btnTag.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.
                                        LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                                btnTag.setText(cModule.get(t));
                                btnTag.setBackgroundResource(R.drawable.list_of_module_btn);

                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

                                params.setMargins(15, 10, 15, 2);
                                btnTag.setTextColor(Color.WHITE);

                                btnTag.setLayoutParams(params);

                                if(t==0){
                                    btnTag.setEnabled(true);
                                }
                                // btnTag.setEnabled(false);

                                try {
                               /* String mcq = complete_prossess.get(t).getMcq_complete_all_courseid();
                                String calc = complete_prossess.get(t).getCalc_complete_all_courseid();
                                String faq = complete_prossess.get(t).getFaq_complete_all_courseid();*/

                                    String mcq = mcqtempList.get(t);
                                    String calc = calctempList.get(t);
                                    String faq = faqtempList.get(t);

                                    // if(tempCon==true){
                                    if(mcq != null && calc != null && faq != null){
                                        btnTag.setEnabled(true);
                                        /*Drawable im = getResources().getDrawable( R.drawable.ic_save_profile);
                                        im.setBounds( 0, 0, 60, 60 );
                                        btnTag.setCompoundDrawables( im, null, null, null );*/
                                        if(cModule.size() == t){
                                            Log.d("comp", "compl");
                                        }else{
                                        }

                                        btnTag.setBackgroundResource(R.drawable.dashboard_green_btn);
                                        btnTag.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_save_profile,0);

                                        Log.d("value", mcq);

                                    }else{
                                        tempCon=false;
                                        Log.d("value", mcq);
                                    }
                                    //  }
                                }catch (Exception e){
                                    if(tempCon==true){

                                        tempCon=false;
                                        btnTag.setBackgroundResource(R.drawable.dashboard_green_btn);
                                        btnTag.setEnabled(true);

                                    }else{

                                        Log.d("error", e.toString());
                                        btnTag.setBackgroundResource(R.drawable.list_of_module_btn);
                                        btnTag.setEnabled(false);
                                    }
                                }

                                btnTag.setTag(t);
                                // btnTag.setEnabled(false);
                                linear.addView(btnTag);
                                if(btnTag.getTag().toString().equalsIgnoreCase("0")) {
                                    btnTag.setEnabled(true);
                                }else{
                                }

                                btnTag.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Log.d("value", v.getTag().toString());

                                        moduleid = cmoduleID.get(Integer.valueOf(v.getTag().toString()));
                                        editor.putString("Module_id", cmoduleID.get(Integer.valueOf(v.getTag().toString())));
                                        editor.putString("Module_name", cModule.get(Integer.valueOf(v.getTag().toString())));

                                        //editor.putString("filename", Res.get(position));
                                        Log.d("cmid",moduleid);

                                        editor.commit();
                                        //quesList = db.getAllQuestions(moduleid);
                                        List<VideoListModules> offLineVideolist = offlineDB.getdata(moduleid);
                                        if (NDC.isConnected(context)) {
                                            mtd_module_list(v.getTag().toString());

                                        }else if(offLineVideolist.size() > 0) {

                                            CalcPPF.calcPPF_validation.clear();
                                            CalcSip.calcSip_validation.clear();
                                            CalcSS.calcSS_validation.clear();

                                            moduleID_position = v.getTag().toString();
                                            DashBoard.temp = false;

                                            Intent i = new Intent(ListofModuleFinjan.this, DashBoard.class);
                                            i.putExtra("list_of_module_id", moduleid);
                                            startActivity(i);
                                            finish();

                                        }else{
                                            Toast.makeText(ListofModuleFinjan.this,
                                                    "Kindly check your network connection", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }


               /* ArrayAdapter adapter = new ArrayAdapter(ListofModuleFinjan.this,
                        android.R.layout.simple_list_item_1, android.R.id.text1,cModule);
                listView.setAdapter(adapter);
                for(int t=0; t<listView.getChildCount(); t++){
                    if(t==0){
                        listView.getChildAt(t).setEnabled(true);
                    }else{
                        listView.getChildAt(t).setEnabled(false);
                    }
                }*/
                        }
                    }





                }else{
                    Toast.makeText(ListofModuleFinjan.this, "Kindly check your network connection",
                            Toast.LENGTH_LONG).show();
                    finish();
                }
            }

    private void sheetDialog(String coupon_img) {

        final AlertDialog.Builder dialogBuilder = new
                AlertDialog.Builder(ListofModuleFinjan.this);

        View bView = getLayoutInflater().inflate(R.layout.finstart_bannerdialog, null);
        dialogBuilder.setView(bView);
       // bView.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialogBuilder.setCancelable(false);

        ImageView coursesLayout = (ImageView) bView.findViewById(R.id.banner_view);

        Picasso.with(context)
                .load(StaticConfig.html_Base+coupon_img)
                .into(coursesLayout);

        Button btn_Cancel = (Button) bView.findViewById(R.id.banner_button);

        final AlertDialog al = dialogBuilder.create();

        try{
            final Handler handler = new Handler();
            final Runnable r = new Runnable(){
                public void run(){
                    al.show();
                }
            };
            handler.postDelayed(r, 300);
        }catch (Exception e){
            al.show();
        }


        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reg_c_image = sharedpreferences.getString("reg_m_coupon_image", "");
                if(reg_c_image != null&& reg_c_image.length()>0) {
                    editor.remove("reg_m_coupon_image");
                    editor.commit();
                }
                al.dismiss();
            }
        });
    }


    //  Toast.makeText(ModuleFinJan.this, "cs" + Res, Toast.LENGTH_SHORT).show();




           /* listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                    moduleid = cmoduleID.get(position);
                    editor.putString("Module_id", cmoduleID.get(position));
                    editor.putString("Module_name", cModule.get(position));

                    //editor.putString("filename", Res.get(position));
                    Log.d("cmid",moduleid);

                    editor.commit();
                    quesList = db.getAllQuestions(moduleid);
                    if (NDC.isConnected(context)) {
                        mtd_module_list();
                    }else if(quesList.size() > 0){
                        Intent i = new Intent(ListofModuleFinjan.this, DashBoard.class);
                        startActivity(i);
                    }else{
                        Toast.makeText(ListofModuleFinjan.this, "Kindly check your network connection",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });*/


    private void callWebService(String c_id) {
        progressBar.setVisibility(View.VISIBLE);
        RxClient.get(getApplicationContext()).finListOfModule(sharedpreferences.getString
                        (SharedPrefUtils.SpRememberToken, ""), new ListOfModuleReq(c_id),
                new Callback<ListOfModuleResponse>() {
                    @Override
                    public void success(ListOfModuleResponse listOfModuleResponse, Response response) {
                        Log.d("value", "");
                        CourseList = new ArrayList<LOCourse>();
                        modulerID_list.clear();
                        modulerName_list.clear();
                        cModule.clear();
                        cmoduleID.clear();


                        if(listOfModuleResponse.getLomResult() != null){
                            String expirydate = null;
                            String crt = null;
                            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            Date date = new Date();
                            String datee = dateFormat.format(date);

                            try{
                                for (int t = 0; t < listOfModuleResponse.getLomResult().
                                        getModuleInFo().getModulesList().length; t++) {
                                    expirydate = listOfModuleResponse.getLomResult().getModuleInFo().
                                            getModulesList()[t].getExpiry_day();

                                    crt = listOfModuleResponse.getLomResult().getModuleInFo().
                                            getModulesList()[t].getCreated_at();
                                    Log.d("exp datee", expirydate);

                                }

                                Log.d("database exp date", expirydate);
                                Log.d("Android current date", datee);
                            }catch (Exception e){

                            }

                            boolean expCondition=false;
                            try {

                                if(expirydate != null){
                                    if(dateFormat.parse(expirydate).before(dateFormat.parse(datee)))
                                    {
                                        expCondition = true;//If start date is before end date
                                        //expiryAlert(expCondition);
                                        Log.d("database exp", "true");
                                    }
                                    else if(dateFormat.parse(expirydate).equals(dateFormat.parse(datee)))
                                    {
                                        expCondition = true;//If two dates are equal
                                        // expiryAlert(expCondition);
                                        Log.d("database exp", "true");
                                    }
                                    else
                                    {
                                        expCondition = false; //If start date is after the end date
                                        Log.d("database exp", "false");
                                    }
                                }


                            } catch (ParseException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                            if (expCondition == false) {
                                try{
                                    for (int t = 0; t < listOfModuleResponse.getLomResult().
                                            getModuleInFo().getCourseList().length; t++) {

                                        cModule.add(listOfModuleResponse.getLomResult().getModuleInFo().
                                                getCourseList()[t].getCourseModule());
                                        cmoduleID.add(listOfModuleResponse.getLomResult().getModuleInFo().
                                                getCourseList()[t].getCmID());

                                        modulerName_list.add(listOfModuleResponse.getLomResult().getModuleInFo().getCourseList()[t].getCourseModule());
                                        modulerID_list.add(listOfModuleResponse.getLomResult().
                                                getModuleInFo().getCourseList()[t].getCmID());
                                    }
                                }catch (Exception e){
                                }

                                List<DatabaseModules> complete_prossess = offlineDB.getAllMCQComplete(true);
                                List<DatabaseModules> CALCcomplete_prossess = offlineDB.getAllCALCComplete(true);
                                List<DatabaseModules> FAQcomplete_prossess = offlineDB.getAllFAQComplete(true);

                                List<DatabaseModules> videocomplete_prossess = offlineDB.getAllVideoComplete(true);


                                boolean tempCon = true;
                                ArrayList<String> mcqtempList = new ArrayList<String>();
                                ArrayList<String> calctempList = new ArrayList<String>();
                                ArrayList<String> faqtempList = new ArrayList<String>();

                                ArrayList<String> videotempList = new ArrayList<String>();

                                for(DatabaseModules mm : complete_prossess){
                                    mcqtempList.add(mm.getMcq_complete_all_courseid());
                                }

                                for(DatabaseModules mm : CALCcomplete_prossess){
                                    calctempList.add(mm.getCalc_complete_all_courseid());
                                }

                                for(DatabaseModules mm : FAQcomplete_prossess){
                                    faqtempList.add(mm.getFaq_complete_all_courseid());
                                }

                                for(DatabaseModules mm : videocomplete_prossess){
                                    videotempList.add(mm.getVideo_complete_all_courseid());
                                }

                                if(((LinearLayout) linear).getChildCount() > 0)
                                    linear.removeAllViews();

                                for(int t=0; t<cModule.size(); t++){
                                    final Button btnTag = new Button(ListofModuleFinjan.this);
                                    btnTag.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.
                                            LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                                    btnTag.setText(cModule.get(t));
                                    btnTag.setBackgroundResource(R.drawable.list_of_module_btn);

                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.
                                            LayoutParams.MATCH_PARENT);

                                    params.setMargins(15, 10, 15, 2);
                                    btnTag.setTextColor(Color.WHITE);

                                    btnTag.setLayoutParams(params);

                                    if(t==0){
                                        btnTag.setEnabled(true);
                                    }
                                    // btnTag.setEnabled(false);

                                    try {
                               /* String mcq = complete_prossess.get(t).getMcq_complete_all_courseid();
                                String calc = complete_prossess.get(t).getCalc_complete_all_courseid();
                                String faq = complete_prossess.get(t).getFaq_complete_all_courseid();*/

                                        String mcq = mcqtempList.get(t);
                                        String calc = calctempList.get(t);
                                        String faq = faqtempList.get(t);
                                        String video = videotempList.get(t);

                                        // if(tempCon==true){
                                        if(mcq != null && calc != null && faq != null
                                                && video != null){
                                            btnTag.setEnabled(true);

                                            int b=t;
                                            b++;
                                            if(cModule.size() == b){
                                                Log.d("comp", "compl");
                                                offlineDB.setFinishedCourse(course_ID, "1");
                                            }else{
                                            }

                                        /*Drawable im = getResources().getDrawable( R.drawable.ic_save_profile);
                                        im.setBounds( 0, 0, 60, 60 );
                                        btnTag.setCompoundDrawables( im, null, null, null );*/

                                            btnTag.setBackgroundResource(R.drawable.dashboard_green_btn);
                                            btnTag.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_save_profile,0);
                                            Log.d("value", mcq);
                                        }else{
                                            tempCon=false;
                                            Log.d("value", mcq);
                                        }
                                        //  }
                                    }catch (Exception e){
                                        if(tempCon==true){
                                            tempCon=false;
                                            btnTag.setBackgroundResource(R.drawable.dashboard_green_btn);
                                            btnTag.setEnabled(true);
                                            //btnTag.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_save_profile,0);
                                        }else{

                                            Log.d("error", e.toString());
                                            btnTag.setBackgroundResource(R.drawable.list_of_module_btn);
                                            btnTag.setTextColor(Color.parseColor("#80FFFFFF"));
                                            btnTag.setEnabled(false);
                                        }
                                    }

                                    btnTag.setTag(t);
                                    // btnTag.setEnabled(false);
                                    linear.addView(btnTag);
                                    if(btnTag.getTag().toString().equalsIgnoreCase("0")){
                                        btnTag.setEnabled(true);
                                    }else{
                                    }

                                    btnTag.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Log.d("value", v.getTag().toString());

                                            if(mprProgressDialog.isShowing()){
                                                mprProgressDialog.dismiss();
                                            }
                                            mprProgressDialog.show();

                                            moduleid = cmoduleID.get(Integer.valueOf(v.getTag().toString()));
                                            editor.putString("Module_id", cmoduleID.get(Integer.valueOf(v.getTag().toString())));
                                            editor.putString("Module_name", cModule.get(Integer.valueOf(v.getTag().toString())));

                                            //editor.putString("filename", Res.get(position));
                                            Log.d("cmid",moduleid);

                                            editor.commit();
                                            // quesList = db.getAllQuestions(moduleid);
                                            List<VideoListModules> offLineVideolist = offlineDB.getdata(moduleid);
                                            if (NDC.isConnected(context)) {
                                                mtd_module_list(v.getTag().toString());
                                            }else if(offLineVideolist.size() > 0) {

                                                CalcPPF.calcPPF_validation.clear();
                                                CalcSip.calcSip_validation.clear();
                                                CalcSS.calcSS_validation.clear();

                                                moduleID_position = v.getTag().toString();
                                                DashBoard.temp = false;

                                                Intent i = new Intent(ListofModuleFinjan.this, DashBoard.class);
                                                i.putExtra("list_of_module_id", moduleid);
                                                startActivity(i);
                                                finish();
                                                mprProgressDialog.dismiss();
                                            }else{
                                                Toast.makeText(ListofModuleFinjan.this, "Kindly check your network connection",
                                                        Toast.LENGTH_SHORT).show();
                                                mprProgressDialog.dismiss();
                                            }
                                        }
                                    });
                                }
                            }else{
                                expiryAlert(datee, expirydate);
                            }


                            offlineDB.set_listOFmodules(cModule, cmoduleID, course_ID,
                                    expirydate, crt, sharedpreferences.getString(SharedPrefUtils.SpEmail, ""));

                            //  newFaqModuleWBService(course_ID);




                      /*  offlineDB.set_listOFmodules(cModule, cmoduleID, id);

                        ArrayAdapter adapter = new ArrayAdapter(ListofModuleFinjan.this,
                                android.R.layout.simple_list_item_1, android.R.id.text1,cModule);
                        listView.setAdapter(adapter);
                        *//*for(int t=0; t<listView.getChildCount(); t++){

                            if(t==0){
                                listView.getChildAt(t).setEnabled(true);
                            }else{
                                listView.getChildAt(t).setEnabled(false);
                            }
                        }*/
                            progressBar.setVisibility(View.INVISIBLE);
                        }else{
                            Toast.makeText(ListofModuleFinjan.this, "No modules allocated", Toast.LENGTH_LONG).show();
                            finish();
                        }


                    }

                    @Override
                    public void failure(RetrofitError error) {

                        /*try{
                            ListOfModuleResponse usere = (ListOfModuleResponse) error.
                                    getBodyAs(ListOfModuleResponse.class);

                            Toast.makeText(ListofModuleFinjan.this, usere.getStatus(), Toast.LENGTH_LONG).show();
                            finish();
                        }catch (Exception e){
                            finish();
                        }*/
                        try{
                            ListOfModuleResponse usere = (ListOfModuleResponse)
                                    error.getBodyAs(ListOfModuleResponse.class);

                            if(usere.getStatus().equalsIgnoreCase("402")){
                                mtd_refresh_token();
                            }
                        }catch (Exception e){

                        }

                    }
                });
    }

    private void mtd_refresh_token() {
       /* Toast.makeText(context, "expired", Toast.LENGTH_SHORT).show();*/
        RxClient.get(ListofModuleFinjan.this).Login(new loginreq(sharedpreferences.
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
                            callWebService(course_ID);
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    };
                    handler.postDelayed(runnable, 500);

                }
            }

            @Override
            public void failure(RetrofitError error) {
                progressBar.setVisibility(View.INVISIBLE);
                Log.d("refresh token", "refresh token error");
                Toast.makeText(ListofModuleFinjan.this, "Service not response",
                        Toast.LENGTH_LONG).show();
                finish();
            }
        });

    }

    private void expiryAlert(String cDate, String expDate) {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ListofModuleFinjan.this);
        View bView = getLayoutInflater().inflate(R.layout.custom_feedback_alert, null);
        dialogBuilder.setView(bView);
        dialogBuilder.setCancelable(false);
        Button send_btn = (Button) bView.findViewById(R.id.feedback_okbtn);

        TextView t = (TextView) bView.findViewById(R.id.dialog_text);
        t.setText("Your subscription for finstart has expired kindly subscribe again " +
                "if you want to use finstart");

        final AlertDialog al = dialogBuilder.create();
        al.show();

        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editor.remove("couponbaseModuleid");
                editor.remove("isusergetmoduleid");
                editor.remove("isusergetexpdate");
                editor.apply();


                offlineDB.deleteSingleEmail(sharedpreferences.getString(SharedPrefUtils.SpEmail, ""));
                al.dismiss();

                try{
                    for(Activity a : list_mod_act){
                        a.finish();
                    }

                    for(Activity a : AllCalcListActivity.calc_act_list){
                        a.finish();
                    }

                    for(Activity a: NewFeedbackActivity.feed_act){
                        a.finish();
                    }

                    for(Activity a : ProfileSetting.profile_Act_list){
                        a.finish();
                    }

                    for(Activity a : FaqCategroyLIstActivity.faq_activity_list){
                        a.finish();
                    }

                    for(Activity a : DashBoard.dashboard_act){
                        a.finish();
                    }

                    for(Activity a : ActivityEvaluation.actEval_act){
                        a.finish();
                    }

                    for(Activity a :Learning_centre.learning_cen_act){
                        a.finish();
                    }
                    for(Activity a :Link_To_Interest.link_inter_act){
                        a.finish();
                    }
                    for(Activity a :Scheme.scheme_act){
                        a.finish();
                    }
                    for(Activity a :Link_To_Interest.link_inter_act){
                        a.finish();
                    }
                    for(Activity a :ChangePassword.changep_act){
                        a.finish();
                    }

                }catch (Exception e){
                }

                Intent i = new Intent(ListofModuleFinjan.this, TryFinStart.class);
                startActivity(i);
                finish();

            }
        });

    }

    public void mtd_module_list(final String position) {
       Log.d("module id", moduleid);

        RxClient.get(getApplicationContext()).VideoList(sharedpreferences.getString(SharedPrefUtils.SpRememberToken, ""),
                new VideoListReq(moduleid),
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


                       // db.onDelete();
                        if(mcqList.length > 0){
                            for(MCQ m : mcqList){
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
                                        mcq_correct_ans, moduleid);
                            }


                        }else{
                           // Toast.makeText(ListofModuleFinjan.this, "MCQ test not found", Toast.LENGTH_LONG).show();
                        }
                        videoList.clear();
                        Module_id.clear();
                        for (int i = 0; i < dashboardResponse.getResult().getInfo().getModules().length; i++) {
                            //Toast.makeText(ModuleFinJan.this, "gh" + Module_id.add(dashboardResponse.getResult().getInfo().getCourses()[i].getId()), Toast.LENGTH_SHORT).show();
                            videoList.add(dashboardResponse.getResult().getInfo().getModules()[i]);

                            module=dashboardResponse.getResult().getInfo().getModules()[i].getModule_name();
                            video_name= StaticConfig.Base + dashboardResponse.getResult().getInfo().getModules()[i].getVideo_name();
                            Module_id.add(dashboardResponse.getResult().getInfo().getModules()[i].getModule_id());
                            enc_url= StaticConfig.Base + dashboardResponse.getResult().getInfo().getModules()[i].getVideo_encrypt();
                            video_type=dashboardResponse.getResult().getInfo().getModules()[i].getVideo_encrypt_type();
                            video_image= StaticConfig.Base + dashboardResponse.getResult().getInfo().getModules()[i].getVideo_image();
                            Calc=dashboardResponse.getResult().getInfo().getModules()[i].getCourse_calculator();

                          db.addCalcValue(Calc, moduleid);

                            String s = dashboardResponse.getResult().getInfo().getModules()[i].
                                    getVideo_encrypt();
                            offlineDB.setDashboardValue(videoList, moduleid);

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
                            db.addFaqValue(moduleid, "0");
                        }else{
                            int in =1;
                            editor.putInt("mFaqdata",in);
                            db.addFaqValue(moduleid, "1");
                        }
                        editor.putString("playvideo",video_name);
                        editor.putString("moduleid", moduleid);
                        editor.putString("vtype",video_type);
                        editor.putString("video_image",video_image);
                        editor.putString("filename",result);
                        if(Calc != null){
                            editor.putString("calc", Calc);
                        }else{
                            editor.putString("calc", "null");
                        }

                        editor.putString("enc_url",enc_url);
                        editor.commit();

                        CalcPPF.calcPPF_validation.clear();
                        CalcSip.calcSip_validation.clear();
                        CalcSS.calcSS_validation.clear();

                        moduleID_position = position;
                        DashBoard.temp = false;

                        Intent i = new Intent(ListofModuleFinjan.this, DashBoard.class);
                        i.putExtra("list_of_module_id", moduleid);
                       // i.putExtra("moduleid_position", position);.
                        finish();
                        startActivity(i);

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(ListofModuleFinjan.this, "Service not response", Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();



       /* if(ModuleFinJan.modFinjan !=null){
            ModuleFinJan.modFinjan.finish();
            Intent i = new Intent(ListofModuleFinjan.this, ModuleFinJan.class);
            startActivity(i);
        }*/
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

            case R.id.finpedia:
                startActivity(new Intent(getApplicationContext(), FaqCategroyLIstActivity.class));
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
                // finish();
                return true;

            case R.id.ss_selection:
                startActivity(new Intent(getApplicationContext(), Scheme.class));
                finish();
                // finish();
                return true;

           /* case R.id.li_invest:
                startActivity(new Intent(getApplicationContext(), Link_To_Interest.class));
                // finish();
                return true;*/

            case R.id.calc:
                startActivity(new Intent(getApplicationContext(), AllCalcListActivity.class));
                finish();
                return true;

            case R.id.finstart_c:
                if(!course_ID.equalsIgnoreCase("5")){
                    course_ID = "5";
                    ModuleFinJan.courseID= "5";
                    t3.setText("Finstart");
                    callWebService(course_ID);
                }

                return true;

            case R.id.profile_menu:
                startActivity(new Intent(getApplicationContext(), ProfileSetting.class));
                finish();
                return true;
           /* case R.id.finstaffcources:
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
                    Toast.makeText(ListofModuleFinjan.this, "Kindly check your network connection",
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
                    Toast.makeText(ListofModuleFinjan.this, "No records", Toast.LENGTH_SHORT).show();
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

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

       /* if(course_ID.equalsIgnoreCase("5")){
            MenuItem item1=menu.findItem(R.id.finstart_c);
            item1.setVisible(false);
        }*/

       /* MenuItem item=menu.findItem(R.id.finpedia);
        MenuItem item1=menu.findItem(R.id.finstart_c);
        item.setVisible(false);
        item1.setVisible(false);*/

        return super.onPrepareOptionsMenu(menu);
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_for_all, menu);

        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
       /*boolean con =  dbHd.isFaqFinised(course_ID);

        if(con == true){
            faq_button.setBackgroundResource(R.drawable.dashboard_green_btn);
            faq_button.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_save_profile,0);
        }*/
    }

    @Override
    protected void onStop() {
        super.onStop();

    }


}


