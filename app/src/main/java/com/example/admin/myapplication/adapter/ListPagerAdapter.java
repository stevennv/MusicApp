package com.example.admin.myapplication.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.admin.myapplication.fragment.MessageFragment;
import com.example.admin.myapplication.fragment.NotiFragment;

/**
 * Created by Admin on 11/7/2017.
 */

public class ListPagerAdapter extends FragmentPagerAdapter {

    private final String[] TITLES = {"Mời chơi", "Tin nhắn",};

    public ListPagerAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }

    @Override
    public Fragment getItem(int position) {
        Fragment listSongFragment = null;

        if (position == 0) {
            listSongFragment = new NotiFragment();
        } else  {
            listSongFragment = new MessageFragment();
        }
        return listSongFragment;
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }
}
