package com.example.admin.myapplication.activity.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.example.admin.myapplication.R;
import com.example.admin.myapplication.activity.dialog.FinishGameDialog;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvPlay;
    private TextView tvAddQuestion;
    private TextView tvFbExample;
    public static String FACEBOOK_URL = "https://www.facebook.com/VietnamPizzaHut/";
    public static String FACEBOOK_PAGE_ID = "179850822266";

    //method to get the right URL to use in the intent

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniUI();
    }

    protected void iniUI() {
        tvPlay = findViewById(R.id.tv_play);
        tvAddQuestion = findViewById(R.id.tv_add_question);
        tvFbExample = findViewById(R.id.tv_fb_examle);
        tvPlay.setOnClickListener(this);
        tvAddQuestion.setOnClickListener(this);
        tvFbExample.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_play:
                Intent intent = new Intent(this, GameActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_add_question:
                Intent intent1 = new Intent(this, AddQuestionActivity.class);
                startActivity(intent1);
                break;
            case R.id.tv_fb_examle:
//                openFanPageFb();
                Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
                String facebookUrl = getFacebookPageURL();
                facebookIntent.setData(Uri.parse(facebookUrl));
                startActivity(facebookIntent);
                break;
        }
    }

    private Intent openFanPageFb() {

        try {
            getPackageManager().getPackageInfo("com.facebook.katana", 0);
            return new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/179850822266"));
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/179850822266"));
        }
    }

    public String getFacebookPageURL() {
        PackageManager packageManager = getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                return "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            } else { //older versions of fb app
                return "fb://page/" + FACEBOOK_PAGE_ID;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return FACEBOOK_URL; //normal web url
        }
    }
}
