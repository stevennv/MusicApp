package com.example.admin.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.admin.myapplication.R;
import com.example.admin.myapplication.dialog.Chat;
import com.example.admin.myapplication.util.SharedPreferencesUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Admin on 11/6/2017.
 */

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Chat> list;
    private SharedPreferencesUtils utils;

    public ChatAdapter(Context context, List<Chat> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_chat_me, parent, false);
        viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        Chat chat = list.get(position);
        utils = new SharedPreferencesUtils(context);
        if (chat.getSender().equals(utils.getUserInfo().getName())) {
            myViewHolder.tvMessageMe.setVisibility(View.VISIBLE);
            myViewHolder.tvMessageMe.setText(chat.getContent());
        } else {
            myViewHolder.llChatRival.setVisibility(View.VISIBLE);
            Glide.with(context).load(chat.getAvatar()).into(myViewHolder.civAvatar);
            myViewHolder.tvMessageRival.setText(chat.getContent());
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvMessageMe;
        public TextView tvMessageRival;
        public LinearLayout llChatRival;
        public CircleImageView civAvatar;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvMessageMe = itemView.findViewById(R.id.tv_messsage_me);
            tvMessageRival = itemView.findViewById(R.id.tv_messsage_rival);
            llChatRival = itemView.findViewById(R.id.ll_chat_rival);
            civAvatar = itemView.findViewById(R.id.civ_avatar);
        }
    }
}
