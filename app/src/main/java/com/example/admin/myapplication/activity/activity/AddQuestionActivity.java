package com.example.admin.myapplication.activity.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.myapplication.R;
import com.example.admin.myapplication.activity.model.Answer;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import java.io.IOException;
import java.util.Random;

public class AddQuestionActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = AddQuestionActivity.class.getSimpleName();
    private ImageView imgUpload;
    private RelativeLayout rlUpload;
    private TextView tvFileName;
    private EditText edtA;
    private EditText edtB;
    private EditText edtC;
    private EditText edtD;
    private int numberCorrect;
    private static final int REQ_CODE_PICK_SOUNDFILE = 1;
    private StorageReference storageReference;
    private Uri audioFileUri;
    private String nameFile;
    private RangeSeekBar seekBar;
    private ImageView imgPlay;
    private ImageView imgPause;
    private DatabaseReference mRoot;
    private MediaPlayer player;
    private String path;
    private int duration;
    private int seekStart;
    private int seekStop;
    private String answerA;
    private String answerB;
    private String answerC;
    private String answerD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);
        iniUI();
        randomCorrectAnswer();
    }

    protected void iniUI() {
        mRoot = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
        imgUpload = findViewById(R.id.img_upload);
        tvFileName = findViewById(R.id.tv_file_name);
        rlUpload = findViewById(R.id.rl_upload);
        edtA = findViewById(R.id.edt_a);
        edtB = findViewById(R.id.edt_b);
        edtC = findViewById(R.id.edt_c);
        edtD = findViewById(R.id.edt_d);
        seekBar = findViewById(R.id.seek_bar);
        imgPlay = findViewById(R.id.img_play);
        imgPause = findViewById(R.id.img_pause);
        tvFileName.setText(getString(R.string.pls_choose_one_file_mp3));
        imgUpload.setOnClickListener(this);
        rlUpload.setOnClickListener(this);
        imgPlay.setOnClickListener(this);
        imgPause.setOnClickListener(this);
        player = new MediaPlayer();
        seekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {
                double min = Integer.parseInt(String.valueOf(minValue));
                double percent = ((100.00 - min) / 100) * duration;
                seekStart = (int) (percent);
                player.seekTo(seekStart);
                seekStop = seekStart + 30;
                Log.d(TAG, "onRangeSeekBarValuesChanged: " + percent);
//                player.seekTo(seekStart);
            }
        });
    }

    private void playMusic() {

        try {
            player.setDataSource(this, audioFileUri);
            player.prepare();
            player.start();
            duration = player.getDuration();
            Log.d(TAG, "playMusic: " + duration);
        } catch (IllegalArgumentException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();

        } catch (SecurityException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();

        } catch (IllegalStateException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    private void pauseMusic() {
        player.stop();
    }

    private void randomCorrectAnswer() {
        Random random = new Random();
        numberCorrect = random.nextInt(3) + 1;
        if (numberCorrect == 1) {
            edtA.setBackgroundResource(R.drawable.bg_btn_answer_selected);
            edtA.setHint(getString(R.string.enter_correct_answer));
        } else if (numberCorrect == 2) {
            edtB.setBackgroundResource(R.drawable.bg_btn_answer_selected);
            edtB.setHint(getString(R.string.enter_correct_answer));
        } else if (numberCorrect == 3) {
            edtC.setBackgroundResource(R.drawable.bg_btn_answer_selected);
            edtC.setHint(getString(R.string.enter_correct_answer));
        } else {
            edtD.setBackgroundResource(R.drawable.bg_btn_answer_selected);
            edtD.setHint(getString(R.string.enter_correct_answer));
        }
    }

    private void chooseFile() {
        Intent intent;
        intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("audio/*");
        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_audio_file_title)), REQ_CODE_PICK_SOUNDFILE);
    }

    private void uploadFile() {
        if (audioFileUri != null) {
            final ProgressDialog dialog = new ProgressDialog(this);
            StorageReference uploadRef = storageReference.child("Mp3/" + nameFile + ".mp3");
            uploadRef.putFile(audioFileUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Uri urlDownload = taskSnapshot.getDownloadUrl();
                            Answer answer = new Answer(answerA, answerB, answerC, answerD, numberCorrect, String.valueOf(urlDownload), seekStart, seekStop);
                            String child = mRoot.child("Music").push().getKey();
                            mRoot.child("Music").child(child).setValue(answer);
                            Log.d(TAG, "onSuccess: " + urlDownload);

                            Toast.makeText(AddQuestionActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddQuestionActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double percent = (100.00 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            dialog.setMessage((int) percent + "% uploaded!!");
                            dialog.setCancelable(false);
                            dialog.setCanceledOnTouchOutside(false);
                            dialog.show();

                        }
                    });
        } else {
            Toast.makeText(this, "Uri null", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_upload:
                chooseFile();
                break;
            case R.id.rl_upload:
                checkEmpty();
                break;
            case R.id.img_play:
                if (!player.isPlaying()) {
                    playMusic();
                }
                break;
            case R.id.img_pause:
                if (player.isPlaying()) {
                    pauseMusic();
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE_PICK_SOUNDFILE && resultCode == Activity.RESULT_OK) {
            if ((data != null) && (data.getData() != null)) {
                audioFileUri = data.getData();
                path = audioFileUri.getPath();

                String filename1 = audioFileUri.getLastPathSegment();
                nameFile = filename1.substring(filename1.lastIndexOf("/") + 1);
                tvFileName.setText(nameFile);
                seekBar.setVisibility(View.VISIBLE);
                imgPause.setVisibility(View.VISIBLE);
                imgPlay.setVisibility(View.VISIBLE);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setMessage(getString(R.string.guide))
                        .setNegativeButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).show();
//                playMusic();
                // Now you can use that Uri to get the file path, or upload it, ...
            }
        }
    }

    private void checkEmpty() {
        answerA = edtA.getText().toString();
        answerB = edtB.getText().toString();
        answerC = edtC.getText().toString();
        answerD = edtD.getText().toString();
        if (answerA.equals("") || answerB.equals("") || answerC.equals("") || answerD.equals("") || audioFileUri == null) {
            Toast.makeText(this, getString(R.string.not_null), Toast.LENGTH_SHORT).show();
        } else {
            uploadFile();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        player.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.stop();
    }
}
