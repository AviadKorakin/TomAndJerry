package com.example.tomandjerry.Activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import com.example.tomandjerry.R;
import com.example.tomandjerry.Utilities.MySharedPreferences;
import com.example.tomandjerry.Utilities.MyScore;
import com.google.android.material.textview.MaterialTextView;

public class ScoreBoard_Activity extends AppCompatActivity {
    private AppCompatImageButton TomAndJerry_ReturnButton;
    private MediaPlayer backgroundMusic;
    private MaterialTextView TomAndJerry_score0;
    private MaterialTextView TomAndJerry_score1;
    private MaterialTextView TomAndJerry_score2;
    private MaterialTextView TomAndJerry_score3;
    private MaterialTextView TomAndJerry_score4;
    private MaterialTextView TomAndJerry_score5;
    private MaterialTextView TomAndJerry_score6;
    private MaterialTextView TomAndJerry_score7;
    private MaterialTextView TomAndJerry_score8;
    private MaterialTextView TomAndJerry_score9;
    private AppCompatImageButton TomAndJerry_ScoreButton0;
    private AppCompatImageButton TomAndJerry_ScoreButton1;
    private AppCompatImageButton TomAndJerry_ScoreButton2;
    private AppCompatImageButton TomAndJerry_ScoreButton3;
    private AppCompatImageButton TomAndJerry_ScoreButton4;
    private AppCompatImageButton TomAndJerry_ScoreButton5;
    private AppCompatImageButton TomAndJerry_ScoreButton6;
    private AppCompatImageButton TomAndJerry_ScoreButton7;
    private AppCompatImageButton TomAndJerry_ScoreButton8;
    private AppCompatImageButton TomAndJerry_ScoreButton9;
    MySharedPreferences mySharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);
        mySharedPreferences = MySharedPreferences.getInstance();
        setSoundBox();
        findViews();
        updateScore();
        setListener();
    }

    private void setListener() {
        TomAndJerry_ReturnButton.setOnClickListener(v -> returnToMenu());
        TomAndJerry_ScoreButton0.setOnClickListener(v -> goToMap(0));
        TomAndJerry_ScoreButton1.setOnClickListener(v -> goToMap(1));
        TomAndJerry_ScoreButton2.setOnClickListener(v -> goToMap(2));
        TomAndJerry_ScoreButton3.setOnClickListener(v -> goToMap(3));
        TomAndJerry_ScoreButton4.setOnClickListener(v -> goToMap(4));
        TomAndJerry_ScoreButton5.setOnClickListener(v -> goToMap(5));
        TomAndJerry_ScoreButton6.setOnClickListener(v -> goToMap(6));
        TomAndJerry_ScoreButton7.setOnClickListener(v -> goToMap(7));
        TomAndJerry_ScoreButton8.setOnClickListener(v -> goToMap(8));
        TomAndJerry_ScoreButton9.setOnClickListener(v -> goToMap(9));

    }

    private void goToMap(int i) {

        Intent x = new Intent(getApplicationContext(), Maps_Activity.class);
        MyScore loc=new MyScore(mySharedPreferences.readString("SCORE" + i, "0/0/0"));
        Bundle bundle = new Bundle();
        bundle.putDouble("lat", loc.getLat());
        bundle.putDouble("lng", loc.getLng());
        x.putExtras(bundle);
        startActivity(x);
        finish();
    }

    private void returnToMenu() {
        Intent i = new Intent(getApplicationContext(), Lobby_Activity.class);
        startActivity(i);
        finish();
    }

    private void findViews() {
        TomAndJerry_ReturnButton = findViewById(R.id.returnButton);
        TomAndJerry_score0= findViewById(R.id.TomAndJerry_score0);
        TomAndJerry_score1 = findViewById(R.id.TomAndJerry_score1);
        TomAndJerry_score2 = findViewById(R.id.TomAndJerry_score2);
        TomAndJerry_score3 = findViewById(R.id.TomAndJerry_score3);
        TomAndJerry_score4 = findViewById(R.id.TomAndJerry_score4);
        TomAndJerry_score5= findViewById(R.id.TomAndJerry_score5);
        TomAndJerry_score6 = findViewById(R.id.TomAndJerry_score6);
        TomAndJerry_score7 = findViewById(R.id.TomAndJerry_score7);
        TomAndJerry_score8 = findViewById(R.id.TomAndJerry_score8);
        TomAndJerry_score9 = findViewById(R.id.TomAndJerry_score9);
        TomAndJerry_ScoreButton0=findViewById(R.id.TomAndJerry_ScoreButton0);
        TomAndJerry_ScoreButton1=findViewById(R.id.TomAndJerry_ScoreButton1);
        TomAndJerry_ScoreButton2=findViewById(R.id.TomAndJerry_ScoreButton2);
        TomAndJerry_ScoreButton3=findViewById(R.id.TomAndJerry_ScoreButton3);
        TomAndJerry_ScoreButton4=findViewById(R.id.TomAndJerry_ScoreButton4);
        TomAndJerry_ScoreButton5=findViewById(R.id.TomAndJerry_ScoreButton5);
        TomAndJerry_ScoreButton6=findViewById(R.id.TomAndJerry_ScoreButton6);
        TomAndJerry_ScoreButton7=findViewById(R.id.TomAndJerry_ScoreButton7);
        TomAndJerry_ScoreButton8=findViewById(R.id.TomAndJerry_ScoreButton8);
        TomAndJerry_ScoreButton9=findViewById(R.id.TomAndJerry_ScoreButton9);
    }
    private void updateScore()
    {
        MyScore[] bundle=new MyScore[10];
        for(int x=0;x<10;x++) {
            bundle[x] = new MyScore(mySharedPreferences.readString("SCORE" + x, "-1/0/0"));
        }
        if(bundle[0].getScore()==-1)
        {
            TomAndJerry_ScoreButton0.setEnabled(false);
            TomAndJerry_score0.setVisibility(View.INVISIBLE);

        }
        else {
            TomAndJerry_score0.setText(String.valueOf(bundle[0].getScore()));
        }
        if(bundle[1].getScore()==-1)
        {
            TomAndJerry_ScoreButton1.setEnabled(false);
            TomAndJerry_score1.setVisibility(View.INVISIBLE);

        }
        else {
            TomAndJerry_score1.setText(String.valueOf(bundle[1].getScore()));
        }
        if(bundle[2].getScore()==-1)
        {
            TomAndJerry_ScoreButton2.setEnabled(false);
            TomAndJerry_score2.setVisibility(View.INVISIBLE);

        }
        else {
            TomAndJerry_score2.setText(String.valueOf(bundle[2].getScore()));
        }
        if(bundle[3].getScore()==-1)
        {
            TomAndJerry_ScoreButton3.setEnabled(false);
            TomAndJerry_score3.setVisibility(View.INVISIBLE);

        }
        else {
            TomAndJerry_score3.setText(String.valueOf(bundle[3].getScore()));
        }
        if(bundle[4].getScore()==-1)
        {
            TomAndJerry_ScoreButton4.setEnabled(false);
            TomAndJerry_score4.setVisibility(View.INVISIBLE);

        }
        else {
            TomAndJerry_score4.setText(String.valueOf(bundle[4].getScore()));
        }
        if(bundle[5].getScore()==-1)
        {
            TomAndJerry_ScoreButton5.setEnabled(false);
            TomAndJerry_score5.setVisibility(View.INVISIBLE);

        }
        else {
            TomAndJerry_score5.setText(String.valueOf(bundle[5].getScore()));
        }
        if(bundle[6].getScore()==-1)
        {
            TomAndJerry_ScoreButton6.setEnabled(false);
            TomAndJerry_score6.setVisibility(View.INVISIBLE);

        }
        else {
            TomAndJerry_score6.setText(String.valueOf(bundle[6].getScore()));
        }
        if(bundle[7].getScore()==-1)
        {
            TomAndJerry_ScoreButton7.setEnabled(false);
            TomAndJerry_score7.setVisibility(View.INVISIBLE);

        }
        else {
            TomAndJerry_score7.setText(String.valueOf(bundle[7].getScore()));
        }
        if(bundle[8].getScore()==-1)
        {
            TomAndJerry_ScoreButton8.setEnabled(false);
            TomAndJerry_score8.setVisibility(View.INVISIBLE);

        }
        else {
            TomAndJerry_score8.setText(String.valueOf(bundle[8].getScore()));
        }
        if(bundle[9].getScore()==-1)
        {
            TomAndJerry_ScoreButton9.setEnabled(false);
            TomAndJerry_score9.setVisibility(View.INVISIBLE);

        }
        else {
            TomAndJerry_score9.setText(String.valueOf(bundle[9].getScore()));
        }
    }
    private void setSoundBox() {

        backgroundMusic= MediaPlayer.create(this, R.raw.sn_backgroundscore);
        backgroundMusic.setVolume(0.1F,0.1F);
        backgroundMusic.setLooping(true);
    }
    private void stop() {

        stopMedia(backgroundMusic);
    }

    private void start() {
        backgroundMusic.start();
    }
    private void stopMedia(MediaPlayer media)
    {
        media.pause();
        media.seekTo(0);
    }
    @Override
    protected void onResume() {
        super.onResume();
        start();

    }

    @Override
    protected void onPause() {
        super.onPause();
        stop();

    }
}