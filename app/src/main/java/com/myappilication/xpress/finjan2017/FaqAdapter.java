package com.myappilication.xpress.finjan2017;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myappilication.xpress.finjan2017.models.login.faq.Faqlistdatas;

import java.util.List;

/**
 * Created by sureshmano on 3/17/2017.
 */

public  class FaqAdapter extends RecyclerView.Adapter<FaqAdapter.MyViewHolder> {

    private List<Faqlistdatas> Listdata;
    boolean condition=false;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_question, tv_answer ;

        public MyViewHolder(View view) {
            super(view);
            tv_question = (TextView) view.findViewById(R.id.textfaq_question);
            tv_answer = (TextView) view.findViewById(R.id.textfaq_answer);

        }
    }


    public FaqAdapter(List<Faqlistdatas> Listdata) {
        this.Listdata = Listdata;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.faq_cardview, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
       /* ArrayList<String> fq = new ArrayList<String>();
        fq.add("faq_qus");*/

        Faqlistdatas faqlistdatas = Listdata.get(position);


       /* faqresp faqlist = Listdata.get(position);*/
        holder.tv_question.setText(faqlistdatas.getFaq_qus());
        holder.tv_answer.setText(Html.fromHtml(faqlistdatas.getFaq_ans()));
        holder.tv_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(holder.tv_answer.getVisibility() == View.VISIBLE) {
                    condition=true;
                }else{
                    condition=false;
                }

                if(condition == false){
                    holder.tv_answer.setVisibility(View.VISIBLE);
                    condition=true;
                }else{
                    condition=false;
                    holder.tv_answer.setVisibility(View.GONE);
                }



            }
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





