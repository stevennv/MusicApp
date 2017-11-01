package com.example.admin.myapplication.activity.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.example.admin.myapplication.R;
import com.example.admin.myapplication.activity.activity.GameActivity;
import com.example.admin.myapplication.activity.model.PreviewResult;

import java.util.List;

/**
 * Created by Admin on 11/1/2017.
 */

public class FinishGameDialog extends Dialog implements View.OnClickListener {
    private ImageView imgDisk;
    private ImageView imgMusic1;
    private ImageView imgMusic2;
    private ImageView imgMusic3;
    private Button btnBack;
    private Button btnPreview;
    private List<PreviewResult> list;

    public FinishGameDialog(@NonNull Context context, List<PreviewResult> list) {
        super(context);
        this.list = list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_finish_game);
        iniUI();
    }

    protected void iniUI() {
        imgDisk = findViewById(R.id.img_disk);
        imgMusic1 = findViewById(R.id.img_music_1);
        imgMusic2 = findViewById(R.id.img_music_2);
        imgMusic3 = findViewById(R.id.img_music_3);
        btnBack = findViewById(R.id.btn_back);
        btnPreview = findViewById(R.id.btn_preview_result);

        AnimationDrawable animation = (AnimationDrawable) imgDisk.getBackground();
        animation.start();
        AnimationDrawable animation1 = (AnimationDrawable) imgMusic1.getBackground();
        animation1.start();
        AnimationDrawable animation2 = (AnimationDrawable) imgMusic2.getBackground();
        animation2.start();
        AnimationDrawable animation3 = (AnimationDrawable) imgMusic3.getBackground();
        animation3.start();
        btnBack.setOnClickListener(this);
        btnPreview.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                dismiss();
                ((Activity) getContext()).finish();
                break;
            case R.id.btn_preview_result:
                dismiss();
                PreviewResultDialog dialog = new PreviewResultDialog(getContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen, list);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//                dialog.setCancelable(false);
                dialog.show();
                break;
        }
    }
}
