package com.example.admin.myapplication.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.example.admin.myapplication.R;
import com.example.admin.myapplication.activity.MainActivity;
import com.example.admin.myapplication.adapter.ListSongAdapter;
import com.example.admin.myapplication.model.Song;
import com.example.admin.myapplication.util.Utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 11/4/2017.
 */

public class SearchDialog extends AppCompatActivity {
    private EditText edtSearch;
    private RecyclerView rvResultSearch;
    private RecyclerView.LayoutManager layoutManager;
    private List<Song> list = new ArrayList<>();
    private ListSongAdapter adapter;
    private String urlImage1;
    private String songName;
    private String contentSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_search);
        iniUI();
    }

    private void iniUI() {
        edtSearch = findViewById(R.id.edt_search);
        rvResultSearch = findViewById(R.id.rv_result_search);
        layoutManager = new LinearLayoutManager(this);
        rvResultSearch.setLayoutManager(layoutManager);
        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
//                    getData();
                    new readHTML().execute(Utils.convertSearchUrl(edtSearch.getText().toString()));
                    getData();
                    return true;
                }
                return false;
            }
        });
    }

    private void getData() {

    }

    private class readHTML extends AsyncTask<String, Void, List<Song>> {
        @Override
        protected List<Song> doInBackground(String... strings) {
            Document document = null;
            try {
                document = (Document) Jsoup.connect(strings[0]).get();
                if (document != null) {
                    Elements elements = document.select("div.item-song");
                    if (elements != null && elements.size() > 0) {
                        for (Element element : elements) {
                            Element urlImage = element.getElementsByTag("img").first();
                            Element info = element.getElementsByTag("fl ml-20").first();
                            if (urlImage != null) {
                                urlImage1 = urlImage.attr("src");
                                songName = urlImage.attr("alt");
                                Log.d("doInBackground", "doInBackground: " + urlImage1);
                                Song song = new Song("123", songName, "123", urlImage1, "123", "123");
                                list.add(song);
                            }
                        }
                    }

                }
                list.size();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Song> songs) {
            super.onPostExecute(songs);
            adapter = new ListSongAdapter(SearchDialog.this, list);
            adapter.notifyDataSetChanged();
            rvResultSearch.setAdapter(adapter);
        }
    }
}
