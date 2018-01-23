package com.myappilication.xpress.finjan2017.feedpost;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.RippleDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Xml;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.myappilication.xpress.finjan2017.ChangePassword;
import com.myappilication.xpress.finjan2017.ListofModuleFinjan;
import com.myappilication.xpress.finjan2017.MainActivity;
import com.myappilication.xpress.finjan2017.ProfileSetting;
import com.myappilication.xpress.finjan2017.R;
import com.myappilication.xpress.finjan2017.models.login.CourseList.CourseListReq;
import com.myappilication.xpress.finjan2017.models.login.CourseList.CourseListResponse;
import com.myappilication.xpress.finjan2017.models.login.availablecourses.AvailableCoursesReq;
import com.myappilication.xpress.finjan2017.models.login.availablecourses.AvailableCoursesResponse;
import com.myappilication.xpress.finjan2017.models.login.couponbasedcourses.CouponBSReq;
import com.myappilication.xpress.finjan2017.models.login.couponbasedcourses.CouponBSResponse;
import com.myappilication.xpress.finjan2017.models.login.dashboard.DashboardCourses;
import com.myappilication.xpress.finjan2017.models.login.helpers.DataBase;
import com.myappilication.xpress.finjan2017.models.login.helpers.NetConnectionDetector;
import com.myappilication.xpress.finjan2017.models.login.helpers.SharedPrefUtils;
import com.myappilication.xpress.finjan2017.models.login.login.loginreq;
import com.myappilication.xpress.finjan2017.models.login.login.loginresp;
import com.myappilication.xpress.finjan2017.models.login.modulelist.ListOfModuleReq;
import com.myappilication.xpress.finjan2017.models.login.modulelist.ListOfModuleResponse;
import com.myappilication.xpress.finjan2017.models.login.offlineDatabase.OfflineDatabaseHelper;
import com.myappilication.xpress.finjan2017.models.login.offlineDatabase.DatabaseModules;
import com.myappilication.xpress.finjan2017.models.login.userreg.UserRegResponse;
import com.myappilication.xpress.finjan2017.webservice.RxClient;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Bala on 3/2/2017.
 */
public class ModuleFinJan extends AppCompatActivity {
    String remember_token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjMsImlzcyI6Imh0dHA6XC9cLzE4My44Mi4zMy4yMzI6ODA5NFwvYXBpXC9sb2dpbiIsImlhdCI6MTQ5MDY5NzcwMiwiZXhwIjoxNDkwNzAxMzAyLCJuYmYiOjE0OTA2OTc3MDIsImp0aSI6Ijg3NTIzNGQxOTlmMjkyY2E1NjMzNzY2YjZjZDU2ZWFkIn0.RtR5jF_vhPynlnvwC_odi5klUfcsCqY_Eg_zF-cmSlU";
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    TextView Tamil;
    TextView kannda;
    TextView hindi;
    TextView modularTamil;
    TextView modularHindi;
    TextView modularKannada;
    Intent k;
    ListView listView;
    List<DashboardCourses> queslist = new ArrayList<>();
    int score = 0;
    int qid = 0;
    DashboardCourses currentQ;
    //DbHelper db = new DbHelper(this);
    Boolean isSearchtoakenExpired = false;
    Context context;
    ArrayList<String> video_name = new ArrayList<>();
    ArrayList<String> module_id = new ArrayList<>();
    ArrayList<String> Module = new ArrayList<>();
    ArrayList<String> enc_url = new ArrayList<>();
    ArrayList<String> Lang_id = new ArrayList<>();
    ArrayList<String> Res = new ArrayList<>();
    ArrayList<String> video_type = new ArrayList<>();
    ArrayList<String> video_image = new ArrayList<>();
    String img;

    String result;
    DataBase database;
    String str = "";

    OfflineDatabaseHelper offlineDB;

    ArrayAdapter<String> adapter;

    NetConnectionDetector NDC;

    public static ModuleFinJan modFinjan;




    Toolbar toolbar;
    ImageButton imageButton;

    LinearLayout linear;
    public static String courseName;

    Button btnTag, available_Courses;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finjan_module);

        modFinjan = ModuleFinJan.this;
        sharedpreferences = getSharedPreferences(SharedPrefUtils.MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        context = getApplicationContext();

        linear = (LinearLayout) findViewById(R.id.linear);


        available_Courses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NDC.isConnected(context)) {
                    dAlart();
                }else{
                    Toast.makeText(ModuleFinJan.this, "Kindly check your network connection",
                            Toast.LENGTH_LONG).show();
                }

            }
        });

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

       /* if (NDC.isConnected(context)) {
            String token = sharedpreferences.getString(SharedPrefUtils.SpRememberToken, "");
            if (token == null) {
                mtd_refresh_token();
            }
        }*/

        imageButton = (ImageButton) findViewById(R.id.tb_normal_back);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveTaskToBack(true);
                ModuleFinJan.this.finish();
            }
        });

      //  listView = (ListView) findViewById(R.id.list);
        database = new DataBase(ModuleFinJan.this);

        offlineDB = new OfflineDatabaseHelper(ModuleFinJan.this);





        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        //queslist = (ArrayList<DashboardCourses>) database.getdata();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


      /* adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, Module);*/

        offline();

        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) {
                int itemPosition = position;

                if(module_id.size() != 0){
                    Intent i = new Intent(ModuleFinJan.this, ListofModuleFinjan.class);
                    i.putExtra("moduleID", module_id.get(position));

                    startActivity(i);
                }
            }

        });*/
    }

    private void dAlart() {

        callWebService();





    }

    private void callWebService() {
        String email = sharedpreferences.getString(SharedPrefUtils.SpEmail,"");

        RxClient.get(getApplicationContext()).availableCourseslist(sharedpreferences.getString
                (SharedPrefUtils.SpRememberToken, ""), new AvailableCoursesReq(email), new Callback<AvailableCoursesResponse>() {

            @Override
            public void success(AvailableCoursesResponse aCR, Response response) {

                ArrayList<String> avCourseList = new ArrayList<String>();

                if(aCR.getStatus().equalsIgnoreCase("200")){
                    for(int t=0; t<aCR.getResult().getAvailableCoursesInFo().getaC().length; t++){
                        avCourseList.add(aCR.getResult().getAvailableCoursesInFo().getaC()[t].getModule_name());
                    }
                    dialogSheet(avCourseList);
                }



                Log.d("", "");
            }

            @Override
            public void failure(RetrofitError error) {

                Log.d("", "");
            }
        });
    }

    private void dialogSheet(final ArrayList<String> avCourseList) {

        final Dialog mBottomSheetDialog = new Dialog(ModuleFinJan.this,
                R.style.MaterialDialogSheet);
        View Bottom_view = getLayoutInflater().inflate(R.layout.courses_cus_dialog, null);
        mBottomSheetDialog.setContentView(Bottom_view);

        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.CENTER);



        LinearLayout coursesLayout = (LinearLayout) Bottom_view.findViewById(R.id.courses_layout);

        for(int t=0; t<avCourseList.size(); t++){
            Button btnTag = new Button(ModuleFinJan.this);
            btnTag.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.
                    LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            btnTag.setText(avCourseList.get(t));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);

            params.setMargins(15, 5, 15, 2);
            btnTag.setPadding(15, 25, 0, 0);
            btnTag.setTextColor(Color.WHITE);
            btnTag.setGravity(Gravity.LEFT);
          //  btnTag.setTextSize(22);

            btnTag.setLayoutParams(params);

            btnTag.setBackgroundResource(R.drawable.courses_list_btn);

            btnTag.setTag(t);
            // btnTag.setEnabled(false);
            coursesLayout.addView(btnTag);

            btnTag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String pp = v.getTag().toString();

                    final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ModuleFinJan.this);
                    View bView = getLayoutInflater().inflate(R.layout.custom_couponcode, null);
                    dialogBuilder.setView(bView);

                    TextView title = (TextView) bView.findViewById(R.id.coupon_title);
                    title.setText(avCourseList.get(Integer.valueOf(pp)));

                    Button discard_btn = (Button) bView.findViewById(R.id.discard_button);
                    Button send_btn = (Button) bView.findViewById(R.id.send_button);

                    final EditText coupon = (EditText) bView.findViewById(R.id.edit1);



                    final AlertDialog al = dialogBuilder.create();
                    al.show();

                    discard_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                         al.dismiss();
                        }
                    });

                    send_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if(coupon.getText().toString().length()>0){
                                pushCoupon(coupon.getText().toString(), al, mBottomSheetDialog);
                            }else{
                                Toast.makeText(ModuleFinJan.this, "fill your coupon code", Toast.LENGTH_LONG).show();
                            }

                        }
                    });




                }
            });
        }

        mBottomSheetDialog.show();
    }

    private void pushCoupon(String s, final AlertDialog al, final Dialog mBottomSheetDialog) {
        String email = sharedpreferences.getString(SharedPrefUtils.SpEmail, "");

        RxClient.get(getApplicationContext()).couponbasedCourse(sharedpreferences.
                getString(SharedPrefUtils.SpRememberToken, ""), new CouponBSReq(email, s),
                new Callback<CouponBSResponse>() {
            @Override
            public void success(CouponBSResponse couponBSResponse, Response response) {

                if(couponBSResponse.getStatus().equalsIgnoreCase("200")){
                    mtd_module_list();
                  //  String message = couponBSResponse.getResult();
                   // Toast.makeText(ModuleFinJan.this, message, Toast.LENGTH_LONG).show();
                    al.dismiss();
                    mBottomSheetDialog.dismiss();

                }
            }

            @Override
            public void failure(RetrofitError error) {
               // CouponBSResponse usere = (CouponBSResponse) error.getBodyAs(CouponBSResponse.class);
               // Toast.makeText(ModuleFinJan.this, usere.getResult(), Toast.LENGTH_LONG).show();
                al.dismiss();
                mBottomSheetDialog.dismiss();
            }
        });

    }

    private void offline() {
        List<DatabaseModules> finjan_courses = offlineDB.get_courses();

        if (NDC.isConnected(context)) {

            mtd_module_list();

        }else if(finjan_courses.size() > 0){
            for(DatabaseModules v : finjan_courses){
                Module.add(v.getFinjan_courses());
                module_id.add(v.getFinjan_coursesid());
            }
            if(Module.size()>0){
                /*adapter = new ArrayAdapter(ModuleFinJan.this,
                        android.R.layout.simple_list_item_1, android.R.id.text1, Module);
                listView.setAdapter(adapter);*/

                for(int t=0; t<Module.size(); t++){
                    Button btnTag = new Button(ModuleFinJan.this);
                    btnTag.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.
                            LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                    btnTag.setText(Module.get(t));

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT);

                    params.setMargins(15, 10, 15, 2);
                    btnTag.setTextColor(Color.WHITE);

                    btnTag.setLayoutParams(params);

                    btnTag.setBackgroundResource(R.drawable.courses_list_btn);

                    List<String> cou = offlineDB.getCourseValue();

                    try{
                        if(module_id.get(t).equalsIgnoreCase(cou.get(t))){
                          //  btnTag.setBackgroundResource(R.drawable.dashboard_green_btn);
                            btnTag.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_save_profile, 0);
                        }
                    }catch (Exception e){
                        Log.d("mod error", e.toString());
                    }

                    btnTag.setTag(t);
                    // btnTag.setEnabled(false);
                    linear.addView(btnTag);

                    btnTag.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String position = v.getTag().toString();
                            Log.d("position", position);
                            if(module_id.size() != 0){
                                courseName = Module.get(Integer.valueOf(position));
                                Intent i = new Intent(ModuleFinJan.this, ListofModuleFinjan.class);
                                i.putExtra("moduleID", module_id.get(Integer.valueOf(position)));
                                i.putExtra("courses", Module.get(Integer.valueOf(position)));
                                startActivity(i);


                            }
                        }
                    });
                }
            }
        }else{
            Toast.makeText(ModuleFinJan.this, "Kindly check your network connection",
                    Toast.LENGTH_LONG).show();
            finish();
        }
    }

    /*for(int t=0; t<cModule.size(); t++){
        btnTag = new Button(ListofModuleFinjan.this);
        btnTag.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.
                LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        btnTag.setText(cModule.get(t));
        btnTag.setTag(t);
        linear.addView(btnTag);
    }*/


    private void mtd_module_list() {

        String token = sharedpreferences.getString(SharedPrefUtils.SpRememberToken, "");
        String spemail = sharedpreferences.getString(SharedPrefUtils.SpEmail, "");

        RxClient.get(getApplicationContext()).CourseList(sharedpreferences.getString(SharedPrefUtils.SpRememberToken, ""),
                new CourseListReq(sharedpreferences.getString(SharedPrefUtils.SpEmail, "")),
                new Callback<CourseListResponse>() {
                    @Override
                    public void success(CourseListResponse dashboardResponse, Response response) {

                       // queslist = new ArrayList<DashboardCourses>();
                        offlineDB.onDeleteCourseMod();
                        Module.clear();
                        module_id.clear();


                        for (int i = 0; i < dashboardResponse.getResult().getInfo().getCourses().length; i++) {

                            Module.add(dashboardResponse.getResult().getInfo().getCourses()[i].getModule_name());
                            module_id.add(dashboardResponse.getResult().getInfo().getCourses()[i].getModule_id());


                        }

                        offlineDB.set_courses(Module, module_id);

                        //database.finjan_Courses(Module, module_id);
                        if(((LinearLayout) linear).getChildCount() > 0)
                            linear.removeAllViews();

                        for(int t=0; t<Module.size(); t++){
                            btnTag = new Button(ModuleFinJan.this);
                            btnTag.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.
                                    LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                            btnTag.setText(Module.get(t));

                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.MATCH_PARENT);
                            params.setMargins(15, 10, 15, 2);
                            btnTag.setTextColor(Color.WHITE);

                            btnTag.setLayoutParams(params);

                            btnTag.setBackgroundResource(R.drawable.courses_list_btn);

                            List<String> cou = offlineDB.getCourseValue();

                            try{
                                String vv = cou.get(t);
                               // String v = module_id.get(Integer.valueOf());

                                if(module_id.get(t).equalsIgnoreCase(cou.get(t))){
                                   // btnTag.setBackgroundResource(R.drawable.dashboard_green_btn);
                                    btnTag.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_save_profile,0);
                                }
                            }catch (Exception e){

                            }



                            btnTag.setTag(t);
                            // btnTag.setEnabled(false);
                            linear.addView(btnTag);

                            btnTag.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String position = v.getTag().toString();
                                    Log.d("position", position);
                                    if(module_id.size() != 0){
                                        courseName = Module.get(Integer.valueOf(position));
                                        Intent i = new Intent(ModuleFinJan.this, ListofModuleFinjan.class);
                                        i.putExtra("moduleID", module_id.get(Integer.valueOf(position)));
                                        i.putExtra("courses", Module.get(Integer.valueOf(position)));
                                        startActivity(i);
                                    }
                                }
                            });
                        }

                       /* adapter = new ArrayAdapter(ModuleFinJan.this,
                                android.R.layout.simple_list_item_1, android.R.id.text1, Module);
                        listView.setAdapter(adapter);*/

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d("result","");
                        CourseListResponse usere = (CourseListResponse)
                                error.getBodyAs(CourseListResponse.class);
                        String ee = usere.getStatus();
                        Toast.makeText(ModuleFinJan.this, ee, Toast.LENGTH_LONG).show();
                       // finish();

                        isSearchtoakenExpired = false;

                        mtd_refresh_token();
                    }
                });
    }


    @Override
    protected void onResume() {

        super.onResume();
        if(module_id.size()>0){
          //  ArrayList<String> cou = offlineDB.getCourseValue();
            try{

                if (NDC.isConnected(context)) {
                    linear.removeAllViews();
                    mtd_module_list();
                }else{
                   // offline();
                }
            }catch (Exception e){

            }
            Log.d("result",module_id.get(0));
        }
    }

    @Override
    public void onBackPressed() {
        finish();
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
               *//* startActivity(new Intent(getApplicationContext(), ModuleFinJan.class));
                finish();*//*
                return true;*/

           /* case R.id.faq:
                startActivity(new Intent(getApplicationContext(), FaqActivity.class));
                return true;
            case R.id.calculator:
                startActivity(new Intent(getApplicationContext(), FinjanCalcModule.class));
                return true;*/
            case R.id.changepassword:
                startActivity(new Intent(getApplicationContext(), ChangePassword.class));
                return true;
           /* case R.id.dashboard_menu:
                startActivity(new Intent(getApplicationContext(), ModuleFinJan.class));
                return true;*/
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

                finish();
                return true;



        }
        return false;
    }


    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_for_all, menu);

        return true;
    }


    private void mtd_refresh_token() {
       /* Toast.makeText(context, "expired", Toast.LENGTH_SHORT).show();*/
        RxClient.get(context).Login(new loginreq(sharedpreferences.getString(SharedPrefUtils.SpEmail, ""),sharedpreferences.getString(SharedPrefUtils.SpPassword, "")), new Callback<loginresp>() {
            @Override
            public void success(loginresp loginresp, Response response) {




                if (loginresp.getStatus().equals("200")){


                    //Toast.makeText(getApplicationContext(),"sucesss"+loginresp.getToken().toString(),Toast.LENGTH_LONG).show();

                    editor.putString(SharedPrefUtils.SpRememberToken,loginresp.getToken().toString());

                    editor.commit();


                }







            }

            @Override
            public void failure(RetrofitError error) {

                Toast.makeText(getApplicationContext(),"Wrong Username And Password",Toast.LENGTH_LONG).show();

            }
        });

    }


}




