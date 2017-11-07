package com.example.admin.myapplication.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.myapplication.R;
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
 * Created by Admin on 11/6/2017.
 */

public class SearchMusicDialog extends Dialog {
    private EditText edtSearch;
    private RecyclerView rvSearch;
    private GridLayoutManager layoutManager;
    private List<Song> list = new ArrayList<>();
    private ListSongAdapter adapter;
    private String contentSearch;

    public SearchMusicDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_search);
        Toast.makeText(getContext(), "DIALOG", Toast.LENGTH_SHORT).show();
        iniUI();
    }

    protected void iniUI() {
        edtSearch = findViewById(R.id.edt_search);
        rvSearch = findViewById(R.id.rv_result_search);
        layoutManager = new GridLayoutManager(getContext(), 1);
        rvSearch.setLayoutManager(layoutManager);
        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    contentSearch = edtSearch.getText().toString();
                    new readHTML().execute(Utils.convertSearchUrl(contentSearch));
                }
                return false;
            }
        });
    }

    private class readHTML extends AsyncTask<String, Void, List<Song>> {
        @Override
        protected List<Song> doInBackground(String... strings) {
            try {
                Document document = Jsoup.connect(strings[0]).get();
                Elements elements = document.select("div.item_content");
                if (elements != null && elements.size() > 0) {
                    for (Element element : elements) {
                        Elements eName = element.select("a");
                        if (eName != null) {
                            String name = eName.text();
                            String url = eName.attr("href");
                            Log.d("doInBackground:", "doInBackground: " + url);
                            Song song = new Song("123", name,
                                    url, "http://avatar.nct.nixcdn.com/singer/avatar/2017/03/17/c/4/6/d/1489725818099_600.jpg",
                                    "Singer", "123");
                            list.add(song);
                        }
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Song> songs) {
            super.onPostExecute(songs);
            adapter = new ListSongAdapter(getContext(), list);
            adapter.notifyDataSetChanged();
            rvSearch.setAdapter(adapter);
        }
    }
}
