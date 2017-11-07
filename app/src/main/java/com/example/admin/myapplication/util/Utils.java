package com.example.admin.myapplication.util;

import android.util.Log;

/**
 * Created by Admin on 11/4/2017.
 */

public class Utils {
    public static final String MAIN_SEARCH = "http://www.nhaccuatui.com/tim-kiem/bai-hat?q=";

    public static String convertSearchUrl(String content) {
        String result = MAIN_SEARCH + content.replace(' ', '+');
        return result;
    }
}
