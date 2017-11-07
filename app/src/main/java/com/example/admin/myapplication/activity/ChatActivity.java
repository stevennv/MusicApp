package com.example.admin.myapplication.activity;

import android.os.Bundle;
import android.renderscript.Double2;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.myapplication.R;
import com.example.admin.myapplication.adapter.ChatAdapter;
import com.example.admin.myapplication.dialog.Chat;
import com.example.admin.myapplication.model.Noti;
import com.example.admin.myapplication.util.Common;
import com.example.admin.myapplication.util.SharedPreferencesUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView rvChat;
    private RecyclerView.LayoutManager layoutManager;
    private Button tvSend;
    private EditText edtChat;
    private String contentChat;
    private DatabaseReference mRoot;
    private String key;
    private SharedPreferencesUtils utils;
    private Noti noti;
    private String room;
    private String value;
    private List<Chat> list = new ArrayList<>();
    private ChatAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        iniUI();
        getData();
    }

    protected void iniUI() {
        rvChat = findViewById(R.id.rv_chat);
        tvSend = findViewById(R.id.tv_send);
        edtChat = findViewById(R.id.edt_chat);
        layoutManager = new LinearLayoutManager(this);
        rvChat.setLayoutManager(layoutManager);
//        tvSend.setOnClickListener(this);

        mRoot = FirebaseDatabase.getInstance().getReference();
        utils = new SharedPreferencesUtils(this);
        if (getIntent() != null) {
            noti = (Noti) getIntent().getSerializableExtra(Common.RIVAL_INFO);
            double idMe = Double.parseDouble(utils.getUserInfo().getId());
            double idRival = Double.parseDouble(noti.getId());
            if (idMe > idRival) {
                room = noti.getId() + utils.getUserInfo().getId();
            } else {
                room = utils.getUserInfo().getId() + noti.getId();
            }
        }
        tvSend.setOnClickListener(this);
        edtChat.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    sendMessage(edtChat.getText().toString());
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_send:
                Toast.makeText(this, "Send", Toast.LENGTH_SHORT).show();
                contentChat = edtChat.getText().toString();
                sendMessage(contentChat);
                break;
        }
    }

    private void sendMessage(String content) {

        value = mRoot.child("Chat").child(room).push().getKey();
        Chat chat = new Chat(content, utils.getUserInfo().getName(), room, utils.getUserInfo().getUrlAvatar());


        mRoot.child("Chat").child(room).child(value).setValue(chat);
        mRoot.child("Home").child(noti.getId()).child("Message_wait").child(utils.getUserInfo().getId()).setValue(chat);
        edtChat.setText("");
    }

    private void getData() {
        mRoot.child("Chat").child(room).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Chat chat = snapshot.getValue(Chat.class);
                    list.add(chat);
                }
                adapter = new ChatAdapter(getApplicationContext(), list);
                adapter.notifyDataSetChanged();
                rvChat.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
