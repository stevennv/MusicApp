package com.example.admin.myapplication.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.myapplication.R;
import com.example.admin.myapplication.adapter.NotiMessageAdapter;
import com.example.admin.myapplication.adapter.NotificationAdapter;
import com.example.admin.myapplication.model.Noti;
import com.example.admin.myapplication.model.NotiMessage;
import com.example.admin.myapplication.util.SharedPreferencesUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 11/6/2017.
 */

public class MessageFragment extends Fragment {
    private RecyclerView rvMessage;
    private RecyclerView.LayoutManager layoutManager;
    private NotiMessageAdapter adapter;
    private List<NotiMessage> list = new ArrayList<>();
    private DatabaseReference mRoot;
    private SharedPreferencesUtils utils;
    private ValueEventListener listener;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        rvMessage = view.findViewById(R.id.rv_message);
        layoutManager = new LinearLayoutManager(getContext());
        rvMessage.setLayoutManager(layoutManager);
        mRoot = FirebaseDatabase.getInstance().getReference();
        utils = new SharedPreferencesUtils(getContext());
        getData();
        return view;
    }

    private void getData() {
        listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    NotiMessage noti = snapshot.getValue(NotiMessage.class);
                    list.add(noti);
                }
                adapter = new NotiMessageAdapter(getContext(), list);
                adapter.notifyDataSetChanged();
                rvMessage.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mRoot.child("Home").child(utils.getUserInfo().getId()).child("Message_wait").addValueEventListener(listener);
    }
}
