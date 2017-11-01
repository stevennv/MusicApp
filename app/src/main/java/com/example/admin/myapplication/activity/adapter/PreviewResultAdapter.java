package com.example.admin.myapplication.activity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.myapplication.R;
import com.example.admin.myapplication.activity.model.PreviewResult;

import java.util.List;

/**
 * Created by Admin on 11/1/2017.
 */

public class PreviewResultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<PreviewResult> list;

    public PreviewResultAdapter(Context context, List<PreviewResult> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_preview_result, parent, false);
        viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        PreviewResult preview = list.get(position);
        myViewHolder.tvNumberQuestion.setText(preview.getNumberQuestion() + "");
        if (preview.isResult()) {
            myViewHolder.tvResult.setText("Đúng");
        } else {
            myViewHolder.tvResult.setText("Sai");
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNumberQuestion;
        public TextView tvResult;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvNumberQuestion = itemView.findViewById(R.id.tv_number_question);
            tvResult = itemView.findViewById(R.id.tv_result);
        }
    }
}
