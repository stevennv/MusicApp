package com.example.admin.myapplication.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.admin.myapplication.R;
import com.example.admin.myapplication.activity.ChatActivity;
import com.example.admin.myapplication.model.Noti;
import com.example.admin.myapplication.util.Common;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Admin on 11/6/2017.
 */

public class NotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Noti> list;
    private DatabaseReference mRoot;
    private clickReject clickReject;

    public NotificationAdapter(Context context, List<Noti> list, clickReject clickReject) {
        this.context = context;
        this.list = list;
        this.clickReject = clickReject;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_noti, parent, false);
        viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        final Noti noti = list.get(position);
//        Glide.get(context).clearDiskCache();
//        Glide.get(context).clearMemory();
        Glide.with(context).load(noti.getAvatar()).into(myViewHolder.civAvatar);

        mRoot = FirebaseDatabase.getInstance().getReference();
        final String content = "<font color='blue'>" + noti.getName() + "</font> " + context.getString(R.string.invite_friend);
        myViewHolder.tvContent.setText(Html.fromHtml(content), TextView.BufferType.SPANNABLE);
        myViewHolder.llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog dialog = new AlertDialog.Builder(context)
                        .setMessage(noti.getName() + context.getString(R.string.invite_friend))
                        .setNegativeButton("Chơi", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(context, "Chơi", Toast.LENGTH_SHORT).show();
                                clickReject.reject(noti.getKey());
                                dialogInterface.dismiss();
                            }
                        })
                        .setNeutralButton("Từ chối", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(context, "Từ chối", Toast.LENGTH_SHORT).show();

                                dialogInterface.dismiss();
                            }
                        })
                        .setPositiveButton("Chat", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(context, "Chat", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(context, ChatActivity.class);
                                intent.putExtra(Common.RIVAL_INFO, noti);
                                context.startActivity(intent);
                                dialogInterface.dismiss();
                            }
                        }).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout llItem;
        public CircleImageView civAvatar;
        public TextView tvContent;

        public MyViewHolder(View itemView) {
            super(itemView);
            llItem = itemView.findViewById(R.id.ll_item);
            civAvatar = itemView.findViewById(R.id.civ_avatar);
            tvContent = itemView.findViewById(R.id.tv_content);
        }
    }

    public interface clickReject {
        void reject(String key);
    }
}
