package com.example.admin.myapplication.activity.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.admin.myapplication.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvPlay;
    private TextView tvAddQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniUI();
    }

    protected void iniUI() {
        tvPlay = findViewById(R.id.tv_play);
        tvAddQuestion = findViewById(R.id.tv_add_question);
        tvPlay.setOnClickListener(this);
        tvAddQuestion.setOnClickListener(this);
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
        }
    }
}
