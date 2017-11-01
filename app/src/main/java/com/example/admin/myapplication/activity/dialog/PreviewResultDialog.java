package com.example.admin.myapplication.activity.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.widget.TextView;

import com.example.admin.myapplication.R;
import com.example.admin.myapplication.activity.adapter.PreviewResultAdapter;
import com.example.admin.myapplication.activity.customview.DividerItemDecoration;
import com.example.admin.myapplication.activity.model.PreviewResult;

import java.util.List;

/**
 * Created by Admin on 11/1/2017.
 */

public class PreviewResultDialog extends Dialog {
    private List<PreviewResult> list;
    private PreviewResultAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView rvPreview;
    private TextView tvResult;

    public PreviewResultDialog(@NonNull Context context, @StyleRes int themeResId, List<PreviewResult> list) {
        super(context, themeResId);
        this.list = list;
    }

//    public PreviewResultDialog(@NonNull Context context, List<PreviewResult> list) {
//        super(context);
//        this.list = list;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_preview_result);
        iniUI();
    }

    protected void iniUI() {
        rvPreview = findViewById(R.id.rv_preview_result);
        tvResult = findViewById(R.id.tv_result);
        tvResult.setText("Hello");
        layoutManager = new LinearLayoutManager(getContext(), 1, false);
        rvPreview.setLayoutManager(layoutManager);
        rvPreview.addItemDecoration(new DividerItemDecoration(getContext()));
        adapter = new PreviewResultAdapter(getContext(), list);
        adapter.notifyDataSetChanged();
        rvPreview.setAdapter(adapter);
    }
}
