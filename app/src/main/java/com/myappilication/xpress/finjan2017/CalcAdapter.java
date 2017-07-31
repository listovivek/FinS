package com.myappilication.xpress.finjan2017;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.myappilication.xpress.finjan2017.models.login.faq.Faqlistdatas;
import com.myappilication.xpress.finjan2017.models.login.helpers.SharedPrefUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.myappilication.xpress.finjan2017.models.login.helpers.SharedPrefUtils.SpCalcPPF;

/**
 * Created by sureshmano on 3/17/2017.
 */

public  class CalcAdapter extends RecyclerView.Adapter<CalcAdapter.MyViewHolder> {

    private List<String> Listdata;
    private Context mcon;
    String word;
    List<String> splitword;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    String calcppf,calcss14,calcss21,calccsi;
    public static int CaltoProcess= 0;
    public static int CurrentProcess;

    String module_id;






    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_clac_modules ;



        public MyViewHolder(View view) {
            super(view);
            tv_clac_modules = (TextView) view.findViewById(R.id.textcalc_modules);

            sharedpreferences = mcon.getSharedPreferences(SharedPrefUtils.MyPREFERENCES, Context.MODE_PRIVATE);
            editor = sharedpreferences.edit();
            word = sharedpreferences.getString("wordList", "");
            calcppf = sharedpreferences.getString(SharedPrefUtils.SpCalcPPF,"");
            calcss14 = sharedpreferences.getString(SharedPrefUtils.SpCalcSS14,"");
            calcss21 = sharedpreferences.getString(SharedPrefUtils.SpCalcSS21,"");
            calccsi = sharedpreferences.getString(SharedPrefUtils.SpCalcSicalcmaturity, "");
            /*editor.putString("calcppf",calcppf.toString());
            editor.commit();*/










        }
    }


    public CalcAdapter(List<String> Listdata, Context mcon,
                       String list_of_module_id) {
        this.Listdata = Listdata;
        this.mcon = mcon;
        module_id = list_of_module_id;
        this.splitword = splitword;
        //this.word =word;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.calc_cardview, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.tv_clac_modules.setText(Listdata.get(position));
        holder.tv_clac_modules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             String[] wordnew = word.split(",");
               splitword = new ArrayList<String>(Arrays.asList(wordnew));
                String splitnew = splitword.get(position);


           /*    if(CaltoProcess > position){

                    CaltoProcess = position;
                    //CurrentProcess = CaltoProcess;
                }*/

                if (splitnew.contains("Dreams Calculator") && position<=CaltoProcess /*&& CurrentProcess <= CaltoProcess*/ ){

                    Intent intent = new Intent(mcon,CalcDreams.class);
                    intent.putExtra("list_of_module_id", module_id);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mcon.startActivity(intent);



                }else if(splitnew.contains("Sukanya Samriddhi Calculator")  && position<=CaltoProcess){

                    Intent intent = new Intent(mcon,CalcSS.class);
                    intent.putExtra("list_of_module_id", module_id);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mcon.startActivity(intent);


                } else if (splitnew.contains("SIP Calculator")&& position<=CaltoProcess){

                    Intent intent = new Intent(mcon,CalcSip.class);
                    intent.putExtra("list_of_module_id", module_id);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mcon.startActivity(intent);

                }else if (splitnew.contains("Expense Calculator")&& position<=CaltoProcess){

                    Intent intent = new Intent(mcon,CalcExpense.class);
                    intent.putExtra("list_of_module_id", module_id);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mcon.startActivity(intent);

                }

                else if (splitnew.contains("Financial Calculator")&& position<=CaltoProcess) {
                    Intent intent = new Intent(mcon, CalcPPF.class);
                    intent.putExtra("list_of_module_id", module_id);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mcon.startActivity(intent);

                }
                else if (splitnew.contains("Sukanya Samriddhi Calculator")&& position<=CaltoProcess){

                    Intent intent = new Intent(mcon,CalcSS.class);
                    intent.putExtra("list_of_module_id", module_id);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mcon.startActivity(intent);

                }
                else if (splitnew.contains("PPF Calculator")&& position<=CaltoProcess) {
                    Intent intent = new Intent(mcon, CalcPPF.class);
                    intent.putExtra("list_of_module_id", module_id);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mcon.startActivity(intent);

                }else {

                    Toast.makeText(mcon, "Finish the Previous Calculation", Toast.LENGTH_SHORT).show();
                }


              /*  if (splitnew.contains("Dreams Calculator")){

                    Intent intent = new Intent(mcon,CalcPPF.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mcon.startActivity(intent);

                }else if(splitnew.contains("Sukanya Samriddhi")){

                        Intent intent = new Intent(mcon,CalcSS.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mcon.startActivity(intent);
                } else if (splitnew.contains("Expense Calculator")){

                    if (calcppf.length()== 0){
                        Toast.makeText(mcon, "First Finish Dreams Calculator ", Toast.LENGTH_LONG).show();
                    }
                    else {
                    Intent intent = new Intent(mcon,CalcSS.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mcon.startActivity(intent);}
                }

                else if (splitnew.contains("Financial Calculator")) {
                    Intent intent = new Intent(mcon, CalcPPF.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mcon.startActivity(intent);
                }
                else if (splitnew.contains("Sukanya Samriddhi")){
                    if (calcppf.length()== 0){
                        Toast.makeText(mcon, "First Finish Financial Calculator ", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Intent intent = new Intent(mcon,CalcSS.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mcon.startActivity(intent);}

                }
                 else{
                    if (calcss14.length()== 0){
                        Toast.makeText(mcon, "First Finish Sukanya Samriddhi Calc", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Intent intent = new Intent(mcon, CalcPPF.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mcon.startActivity(intent);
                    }*/




            }
            /*    if(wordList.equals("Sukanya Samriddhi Calculator")){
                    startActivity(new Intent(getApplicationContext(), CalcSS.class));

                }else if(wordList.equals("PPF Calculator")){
                    startActivity(new Intent(getApplicationContext(), CalcPPF.class));

                }else if(wordList.equals("Dreams Calculator")){
                    startActivity(new Intent(getApplicationContext(), CalcSip.class));

                }
            }*/
        });



    }

    @Override
    public int getItemCount() {
        return Listdata.size();
    }

    public interface OnRecyclerListener {
        void onItemClicked(int position);


        void showAnswer(String id, String type);

    }


/*    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NotificationStreamModelList notificationStreamModelList = ListData.get(position);
        holder.title.setText(notificationStreamModelList.getTitle());
        holder.user_name.setText(notificationStreamModelList.getSendername());
        String DateTime = notificationStreamModelList.getCreatedDate();
        String outTime="",OutDate = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm aa");
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("HH:mm");
        SimpleDateFormat dateFormat3 = new SimpleDateFormat("MMM dd, yyyy");
        try {
            Date date = dateFormat.parse(DateTime);

            outTime = dateFormat2.format(date);
            OutDate = dateFormat3.format(date);
        } catch (ParseException e) {
        }
        holder.date.setText(outTime);
        holder.time.setText(OutDate);

        String TBPath, tb = notificationStreamModelList.getTBPath();

        //  Toast.makeText(context, ""+out, Toast.LENGTH_SHORT).show();

        if (tb.contains("/root/cpanel3-skel/public_html/Xpress")) {
            TBPath = StaticConfig.ROOT_URL + tb.replace("/root/cpanel3-skel/public_html/Xpress", "");
        } else {
            TBPath = StaticConfig.ROOT_URL + "/" + tb;
        }
        Glide.with(context).load(TBPath).placeholder(R.drawable.ic_user_icon).fitCenter().into(holder.iv_thumb);

    }

    @Override
    public int getItemCount() {
        return ListData.size();
    }

}*/


}





