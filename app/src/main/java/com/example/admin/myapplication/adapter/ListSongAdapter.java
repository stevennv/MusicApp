package com.example.admin.myapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.admin.myapplication.R;
import com.example.admin.myapplication.activity.MusicActivity;
import com.example.admin.myapplication.model.Song;
import com.example.admin.myapplication.util.Common;

import java.util.List;

/**
 * Created by Admin on 11/4/2017.
 */

public class ListSongAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Song> list;

    public ListSongAdapter(Context context, List<Song> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_song, parent, false);
        viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        final Song song = list.get(position);
        Glide.with(context).load(song.getUrlImage()).into(myViewHolder.imgSinger);
        myViewHolder.tvSongName.setText(song.getName());
        myViewHolder.tvLenght.setText(song.getLength());
        myViewHolder.llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MusicActivity.class);
                intent.putExtra(Common.SONG_NAME, song.getName());
                intent.putExtra(Common.SONG_URL, song.getUrl());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgSinger;
        private TextView tvSongName;
        private TextView tvLenght;
        private LinearLayout llItem;

        public MyViewHolder(View itemView) {
            super(itemView);
            imgSinger = itemView.findViewById(R.id.img_singer);
            tvLenght = itemView.findViewById(R.id.tv_length);
            tvSongName = itemView.findViewById(R.id.tv_name);
            llItem = itemView.findViewById(R.id.ll_item);
        }
    }
}
