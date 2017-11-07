package com.example.admin.myapplication.activity;

import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.admin.myapplication.R;
import com.example.admin.myapplication.util.Common;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class MusicActivity extends AppCompatActivity implements View.OnClickListener {
    private String url;
    private String songName;
    private TextView tvName;
    private CircleImageView civImage;
    private MediaPlayer player;
    private EditText edtAnswerA;
    private EditText edtAnswerB;
    private EditText edtAnswerC;
    private EditText edtAnswerD;
    private RelativeLayout rlUpload;
    private String lyric;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        iniUI();
    }

    protected void iniUI() {
        tvName = findViewById(R.id.tv_song_name);
        civImage = findViewById(R.id.civ_image);
        player = new MediaPlayer();
        edtAnswerA = findViewById(R.id.edt_a);
        edtAnswerB = findViewById(R.id.edt_b);
        edtAnswerC = findViewById(R.id.edt_c);
        edtAnswerD = findViewById(R.id.edt_d);
        rlUpload = findViewById(R.id.rl_upload);
        if (getIntent() != null) {
            songName = getIntent().getStringExtra(Common.SONG_NAME);
            url = getIntent().getStringExtra(Common.SONG_URL);
//            playMedia(url);
//            new readHTML().execute("https://mp3.zing.vn/bai-hat/Cham-Khe-Tim-Anh-Mot-Chut-Thoi-Noo-Phuoc-Thinh/ZW8IFZUI.html");
            new readHTML().execute("https://www.nhaccuatui.com/bai-hat/hom-nay-toi-co-don-qua-toc-tien-ft-rhymastic.5zzcvtTW0OvN.html");
        }

        rlUpload.setOnClickListener(this);
    }

    private void playMedia(String url) {
        try {
            player.setDataSource(url);
            player.prepare();
            player.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_upload:
                break;
        }
    }

    private class readHTML extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                Document document = Jsoup.connect(strings[0]).get();
                Element element = document.getElementsByTag("div.flash_playing nctPlayer").first();
                lyric = element.select("audio").attr("src");
//                element.select("#mp3flashPlayer").attr("src")
//                if (element != null) {
//                    Element ele = element.getElementById("mp3flashPlayer");
//                    if (ele != null) {
//                        lyric = ele.attr("src");
                Log.d("doInBackground", "doInBackground123123: " + lyric);
//
//                    }
//                }
            } catch (IOException e) {
                Log.d("doInBackground", "doInBackground123123: " + e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            tvName.setText(lyric);
        }
    }
}
