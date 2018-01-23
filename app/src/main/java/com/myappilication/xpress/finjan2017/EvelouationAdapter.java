package com.myappilication.xpress.finjan2017;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myappilication.xpress.finjan2017.mcqevalutiontest.McQData;
import com.myappilication.xpress.finjan2017.models.login.evalution.Evaluationdatas;
import com.myappilication.xpress.finjan2017.models.login.faq.Faqlistdatas;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sureshmano on 3/17/2017.
 */

public  class EvelouationAdapter extends RecyclerView.Adapter<EvelouationAdapter.MyViewHolder> {

    private List<Evaluationdatas> Listdata;
    public static int score=0;
    int count =1;
    Context con;
    TextView tt;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_mcqques, tv_correctanswer, tv_sno, tv_res_cor_ans ;

        public MyViewHolder(View view) {
            super(view);
            tv_mcqques = (TextView) view.findViewById(R.id.result_ques);
            tv_correctanswer = (TextView) view.findViewById(R.id.result_ans);
            tv_sno = (TextView) view.findViewById(R.id.s_no);
            tv_res_cor_ans = (TextView) view.findViewById(R.id.result_cor_ans);
        }
    }


    public EvelouationAdapter(ActivityEvaluation c, TextView t) {
            con = c;
        tt = t;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.evaluvation_cardview, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        holder.tv_mcqques.setText(McQData.getInstance().getMCQQuestion().get(position));
        holder.tv_correctanswer.setText(McQData.getInstance().getUserSelectedData().get(position));
        int sno = position;
        sno++;
        holder.tv_sno.setText(String.valueOf(sno)+",");
        holder.tv_res_cor_ans.setText("Ans : "+McQData.getInstance().getMCQcorrectans().get(position));

       /* if(McQData.getInstance().getMCQcorrectans().get(position).
                equalsIgnoreCase(McQData.getInstance().getMCQcorrectans().get(QuestionActivity.selected[position]))){
            Log.d("final", "correct ans");
            holder.tv_correctanswer.setTextColor(Color.GREEN);
        }else{
            Log.d("final", "correct ans");
            holder.tv_correctanswer.setTextColor(Color.RED);
        }*/

            if(McQData.getInstance().getUserSelectedData().get(position).
                    equalsIgnoreCase(McQData.getInstance().getMCQcorrectans().get(position))) {
                holder.tv_correctanswer.setTextColor(Color.WHITE);
                holder.tv_correctanswer.setBackgroundColor(con.getResources().getColor(R.color.result_green));
                score = count++;
               // tt.setText("Score " +score+"/"+ McQData.getInstance().getMCQQuestion().size());

            }else{

                holder.tv_correctanswer.setTextColor(Color.WHITE);
                holder.tv_correctanswer.setBackgroundColor(con.getResources().getColor(R.color.result_red));
                //holder.tv_res_cor_ans.setVisibility(View.VISIBLE);
                /*holder.tv_res_cor_ans.setBackgroundColor(Color.GREEN);
                holder.tv_res_cor_ans.setTextColor(Color.WHITE);*/

        }

    }

    @Override
    public int getItemCount() {
        return McQData.getInstance().getMCQQuestion().size();
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





