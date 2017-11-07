package com.example.admin.myapplication.dialog;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.astuetz.PagerSlidingTabStrip;
import com.example.admin.myapplication.R;
import com.example.admin.myapplication.adapter.ListPagerAdapter;
import com.example.admin.myapplication.adapter.NotificationAdapter;
import com.example.admin.myapplication.fragment.MessageFragment;
import com.example.admin.myapplication.fragment.NotiFragment;
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

public class NotificationDialog extends FragmentActivity {
    private ImageView imgDisk;
    private ImageView imgNote1;
    private ImageView imgNote2;
    private ImageView imgNote3;
    private RecyclerView rvNoti;
    private RecyclerView.LayoutManager layoutManager;
    private NotificationAdapter adapter;
    private DatabaseReference mRoot;
    private SharedPreferencesUtils utils;
    private List<Noti> list = new ArrayList<>();
    private ValueEventListener listener;
    private PagerSlidingTabStrip tabStrip;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_noti);
        iniUI();

//        getData();
    }

    protected void iniUI() {
        imgDisk = findViewById(R.id.img_disk);
        imgNote1 = findViewById(R.id.img_music_1);
        imgNote2 = findViewById(R.id.img_music_2);
        imgNote3 = findViewById(R.id.img_music_3);
        tabStrip = findViewById(R.id.tabs);
        tabStrip.setShouldExpand(true);
        tabStrip.setIndicatorColor(0xffffffff);
        tabStrip.setIndicatorHeight(10);
        tabStrip.setBackgroundColor(0xff1abc0e);
        tabStrip.setTextColor(0xffffffff);
        ListPagerAdapter pagerAdapter = new ListPagerAdapter(getSupportFragmentManager());

        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(pagerAdapter);
        tabStrip.setViewPager(viewPager);
        mRoot = FirebaseDatabase.getInstance().getReference();
        utils = new SharedPreferencesUtils(this);
        AnimationDrawable animation = (AnimationDrawable) imgDisk.getBackground();
        animation.start();
        AnimationDrawable animation1 = (AnimationDrawable) imgNote1.getBackground();
        animation1.start();
        AnimationDrawable animation2 = (AnimationDrawable) imgNote2.getBackground();
        animation2.start();
        AnimationDrawable animation3 = (AnimationDrawable) imgNote3.getBackground();
        animation3.start();
    }

//    private void getData() {
//        listener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                list.clear();
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    Noti noti = snapshot.getValue(Noti.class);
//                    list.add(noti);
//                }
//                adapter = new NotificationAdapter(NotificationDialog.this, list, new NotificationAdapter.clickReject() {
//                    @Override
//                    public void reject(String key) {
//                        mRoot.child("Home").child(utils.getUserInfo().getId()).child("listInvite").child(key).removeValue();
//                    }
//                });
//                adapter.notifyDataSetChanged();
//                rvNoti.setAdapter(adapter);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        };
//        mRoot.child("Home").child(utils.getUserInfo().getId()).child("listInvite").addValueEventListener(listener);
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        mRoot.child("Home").child(utils.getUserInfo().getId()).child("listInvite").removeEventListener(listener);
    }

}
