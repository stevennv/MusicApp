package com.example.admin.myapplication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.myapplication.R;
import com.example.admin.myapplication.adapter.NotificationAdapter;
import com.example.admin.myapplication.dialog.NotificationDialog;
import com.example.admin.myapplication.model.Noti;
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

public class NotiFragment extends Fragment {
    private RecyclerView rvNoti;
    private RecyclerView.LayoutManager layoutManager;
    private NotificationAdapter adapter;
    private DatabaseReference mRoot;
    private SharedPreferencesUtils utils;
    private ValueEventListener listener;
    private List<Noti> list = new ArrayList<>();

    public NotiFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_noti, container, false);
        rvNoti = v.findViewById(R.id.rv_noti);
        layoutManager = new LinearLayoutManager(getContext());
        rvNoti.setLayoutManager(layoutManager);
        mRoot = FirebaseDatabase.getInstance().getReference();
        utils = new SharedPreferencesUtils(getContext());
        getData();
        return v;

    }

    private void getData() {
        listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Noti noti = snapshot.getValue(Noti.class);
                    list.add(noti);
                }
                adapter = new NotificationAdapter(getContext(), list, new NotificationAdapter.clickReject() {
                    @Override
                    public void reject(String key) {
                        mRoot.child("Home").child(utils.getUserInfo().getId()).child("listInvite").child(key).removeValue();
                    }
                });
                adapter.notifyDataSetChanged();
                rvNoti.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mRoot.child("Home").child(utils.getUserInfo().getId()).child("listInvite").addValueEventListener(listener);
    }
}
