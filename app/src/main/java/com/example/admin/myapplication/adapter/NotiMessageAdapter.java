package com.example.admin.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.admin.myapplication.R;
import com.example.admin.myapplication.activity.ChatActivity;
import com.example.admin.myapplication.model.NotiMessage;
import com.example.admin.myapplication.util.Common;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Admin on 11/7/2017.
 */

public class NotiMessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<NotiMessage> list;

    public NotiMessageAdapter(Context context, List<NotiMessage> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_noti_message, parent, false);
        viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        final NotiMessage notiMessage = list.get(position);
        Glide.with(context).load(notiMessage.getAvatar()).into(myViewHolder.civAvata);
        myViewHolder.tvContent.setText(notiMessage.getSender());
        myViewHolder.tvLastMessage.setText(notiMessage.getContent());
        myViewHolder.llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra(Common.KEY_CHAT, notiMessage.getKey());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout llItem;
        public CircleImageView civAvata;
        public TextView tvContent;
        public TextView tvLastMessage;

        public MyViewHolder(View itemView) {
            super(itemView);
            llItem = itemView.findViewById(R.id.ll_item);
            civAvata = itemView.findViewById(R.id.civ_avatar);
            tvContent = itemView.findViewById(R.id.tv_content);
            tvLastMessage = itemView.findViewById(R.id.tv_last_message);
        }
    }
}
