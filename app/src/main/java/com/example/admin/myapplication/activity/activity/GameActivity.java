package com.example.admin.myapplication.activity.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.admin.myapplication.R;
import com.example.admin.myapplication.activity.dialog.FinishGameDialog;
import com.example.admin.myapplication.activity.model.Answer;
import com.example.admin.myapplication.activity.model.PreviewResult;
import com.example.admin.myapplication.activity.util.CoutTime;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = GameActivity.class.getSimpleName();
    private ImageView imgSpeaker;
    private MediaPlayer player;
    private Button btnAnswerA;
    private Button btnAnswerB;
    private Button btnAnswerC;
    private Button btnAnswerD;
    private int myAnswer;
    private StorageReference mStorageRef;
    private DatabaseReference myRef;
    private List<Answer> list = new ArrayList<>();
    private CoutTime coutTime;
    private int numberQuestion = 0;
    private List<PreviewResult> listResult = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        iniUI();
    }

    protected void iniUI() {
        myRef = FirebaseDatabase.getInstance().getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        imgSpeaker = (ImageView) findViewById(R.id.img_speaker);
        btnAnswerA = (Button) findViewById(R.id.btn_anser_1);
        btnAnswerB = (Button) findViewById(R.id.btn_anser_2);
        btnAnswerC = (Button) findViewById(R.id.btn_anser_3);
        btnAnswerD = (Button) findViewById(R.id.btn_anser_4);
        btnAnswerA.setOnClickListener(this);
        btnAnswerB.setOnClickListener(this);
        btnAnswerC.setOnClickListener(this);
        btnAnswerD.setOnClickListener(this);
        AnimationDrawable animation = (AnimationDrawable) imgSpeaker.getBackground();
        animation.start();
        setUpQuestion();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_anser_1:
                myAnswer = 1;
                checkAnswer(myAnswer);
                break;
            case R.id.btn_anser_2:
                myAnswer = 2;
                checkAnswer(myAnswer);
                break;
            case R.id.btn_anser_3:
                myAnswer = 3;
                checkAnswer(myAnswer);
                break;
            case R.id.btn_anser_4:
                myAnswer = 4;
                checkAnswer(myAnswer);
                break;
        }
    }

    private class DownloadFile extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... url) {
            int count;
            try {
                URL url2 = new URL(url[0]);
                URLConnection conexion = url2.openConnection();
                conexion.connect();
                // this will be useful so that you can show a tipical 0-100% progress bar
                int lenghtOfFile = conexion.getContentLength();

                // downlod the file
                InputStream input = new BufferedInputStream(url2.openStream());
                OutputStream output = new FileOutputStream("/sdcard/somewhere/nameofthefile.mp3");

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    publishProgress((int) (total * 100 / lenghtOfFile));
                    output.write(data, 0, count);
                }

                output.flush();
                output.close();
                input.close();
            } catch (Exception e) {
            }
            return null;
        }
    }

    private void runMedia(String url) {
        try {
            player = new MediaPlayer();
            player.setDataSource(url);
//            player.seekTo();
            player.prepare();
            player.start();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "runMedia: " + e.getMessage());
        }

    }


    private void setUpQuestion() {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Vui lòng chờ ...");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        myRef.child("Music").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Answer answer = snapshot.getValue(Answer.class);
                    Log.d(TAG, "onDataChange: " + answer.getUrl_play());
                    list.add(answer);

                }
                setTime();
                enableButton();
                runMedia(list.get(numberQuestion).getUrl_play());
                btnAnswerA.setText("A. " + list.get(numberQuestion).getAnswerA());
                btnAnswerB.setText("B. " + list.get(numberQuestion).getAnswerB());
                btnAnswerC.setText("C. " + list.get(numberQuestion).getAnswerC());
                btnAnswerD.setText("D. " + list.get(numberQuestion).getAnswerD());
                dialog.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                dialog.dismiss();
            }
        });

//        list.get(0);
    }

    private void checkAnswer(int myAnswer) {
        disableButton();
        coutTime.cancel();
        if (myAnswer == list.get(1).getCorrect()) {
            Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
            PreviewResult result = new PreviewResult(numberQuestion, true);
            listResult.add(result);
        } else {
            Toast.makeText(this, "Failure", Toast.LENGTH_SHORT).show();
            PreviewResult result = new PreviewResult(numberQuestion, false);
            listResult.add(result);
        }
        if (numberQuestion == 1) {
            player.stop();
            FinishGameDialog dialog = new FinishGameDialog(GameActivity.this, listResult);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.setCancelable(false);
            dialog.show();
        } else {
            numberQuestion++;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        player.stop();
    }

    private void setTime() {
        coutTime = new CoutTime(10000, 1000, new CoutTime.checkFinish() {
            @Override
            public void onFinish() {
                AlertDialog dialog = new AlertDialog.Builder(GameActivity.this)
                        .setMessage("Hết giờ")
                        .setNegativeButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).show();
            }
        });
        coutTime.start();
    }

    private void disableButton() {
        btnAnswerA.setEnabled(false);
        btnAnswerB.setEnabled(false);
        btnAnswerC.setEnabled(false);
        btnAnswerD.setEnabled(false);
    }

    private void enableButton() {
        btnAnswerA.setEnabled(true);
        btnAnswerB.setEnabled(true);
        btnAnswerC.setEnabled(true);
        btnAnswerD.setEnabled(true);
    }
}
