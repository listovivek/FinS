package com.myappilication.xpress.finjan2017.feedback;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myappilication.xpress.finjan2017.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by suresh on 7/6/17.
 */
public class FeedbackCustomAdapter extends RecyclerView.Adapter<FeedbackCustomAdapter.MyViewHolder> {


    private ArrayList<String> mList;


    public FeedbackCustomAdapter(ArrayList<String> list) {
            this.mList = list;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtName, time;

        public MyViewHolder(View itemView) {
            super(itemView);

            txtName = (TextView) itemView.findViewById(R.id.t);
            time = (TextView) itemView.findViewById(R.id.date_time);
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feedback_recycler_item, parent, false);

        view.setOnClickListener(UserFeedbackList.myOnClickListener);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        TextView textViewName = holder.txtName;
        TextView txt_time = holder.time;
        textViewName.setText(OfflineFeedbackDB.nameLi.get(position));
        txt_time.setText(OfflineFeedbackDB.dateList.get(position));
    }

    @Override
    public int getItemCount() {
        return OfflineFeedbackDB.nameLi.size();
    }


}
