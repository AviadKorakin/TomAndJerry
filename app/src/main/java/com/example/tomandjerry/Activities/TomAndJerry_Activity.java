package com.example.tomandjerry.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;
import android.Manifest;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityCompat;
import com.example.tomandjerry.GameUtilities.GameManager;
import com.example.tomandjerry.GameUtilities.MainCharacter;
import com.example.tomandjerry.R;
import com.example.tomandjerry.Utilities.MoveDetector;
import com.example.tomandjerry.Utilities.MyLocationManager;
import com.example.tomandjerry.interfaces.MoveCallback;
import com.google.android.material.textview.MaterialTextView;

public class TomAndJerry_Activity extends AppCompatActivity {
    private static final int DEF_DELAY = 1000;
    private static boolean isFast = false;
    private static boolean isSensor = false;
    private static int delay = 1000;
    private static final int defaultLife = 3;
    private MaterialTextView TomAndJerry_score;
    private MaterialTextView TomAndJerry_meters;
    private AppCompatImageButton TomAndJerry_LeftButton;
    private AppCompatImageButton TomAndJerry_RightButton;
    private AppCompatImageButton TomAndJerry_ReturnButton;
    private AppCompatImageView[][] TomAndJerry_Grid;
    private AppCompatImageView[] TomAndJerry_cheeseLifeScale;
    private GameManager gameManager;
    private MediaPlayer[] soundBox, stepBox;
    private MediaPlayer background;
    private int rows;
    private int cols;
    private int stepBoxCounter = 1;
    private Vibrator vib;
    private MoveDetector moveDetector;
    private MyLocationManager myLocationManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.tomandjerry_main);
        findViews();
        initViews();
        setListeners();

        myLocationManager = new MyLocationManager(this);
        // @Deprecated startLocationUpdates();
        setSoundBox();
        this.rows = TomAndJerry_Grid.length;
        this.cols = TomAndJerry_Grid[0].length;
        gameManager = new GameManager(defaultLife, defaultLife, rows, cols
                , (new MainCharacter(R.drawable.ic_jerry, cols / 2, cols)));
        generateEmptyGridAndCenterMainCharacterUI();

    }

    private void returnToMenu() {
        gameManager.updateScoreBoard(myLocationManager.getLatitude(), myLocationManager.getLongitude());
        Intent i = new Intent(getApplicationContext(), Lobby_Activity.class);
        startActivity(i);
        finish();
    }

    private void initViews() {
        vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        initMoveDetector();
        Intent prev = getIntent();
        Bundle bundle = prev.getExtras();
        if (bundle != null && bundle.getInt("FAST") == 1) {
            delay = DEF_DELAY / 2;
            isFast = true;
        }
        if (bundle != null && bundle.getInt("SENSOR") == 1) {
            isSensor = true;
            TomAndJerry_LeftButton.setEnabled(false);
            TomAndJerry_RightButton.setEnabled(false);
            TomAndJerry_LeftButton.setVisibility(View.INVISIBLE);
            TomAndJerry_RightButton.setVisibility(View.INVISIBLE);
            moveDetector.start();

        } else {
            TomAndJerry_LeftButton.setOnClickListener(v -> moveCharRight(false));
            TomAndJerry_RightButton.setOnClickListener(v -> moveCharRight(true));
        }
    }

    private void setListeners() {
        TomAndJerry_ReturnButton.setOnClickListener(v -> returnToMenu());
    }

    private void setSoundBox() {
        background = MediaPlayer.create(this, R.raw.sn_background);
        background.setVolume(0.1F, 0.1F);
        background.setLooping(true);
        soundBox = new MediaPlayer[]{
                MediaPlayer.create(this, R.raw.sn_tick),
                MediaPlayer.create(this, R.raw.sn_hit),
                MediaPlayer.create(this, R.raw.sn_eat),
                MediaPlayer.create(this, R.raw.sn_kiss)


        };
        soundBox[0].setVolume(0.8F, 0.8F);
        soundBox[1].setVolume(0.5F, 0.5F);

        stepBox = new MediaPlayer[]{
                MediaPlayer.create(this, R.raw.sn_originalstepssound1),
                MediaPlayer.create(this, R.raw.sn_originalstepssound2),
                MediaPlayer.create(this, R.raw.sn_originalstepssound3)

        };


    }

    private void findViews() {
        TomAndJerry_score = findViewById(R.id.TomAndJerry_scoreNumber);
        TomAndJerry_meters = findViewById(R.id.TomAndJerry_OdometerNumber);
        TomAndJerry_LeftButton = findViewById(R.id.TomAndJerry_LeftButton);
        TomAndJerry_RightButton = findViewById(R.id.TomAndJerry_RightButton);
        TomAndJerry_ReturnButton = findViewById(R.id.returnButton);
        TomAndJerry_cheeseLifeScale = new AppCompatImageView[]{
                findViewById(R.id.TomAndJerry_cheeseLifeScale_cheese3),
                findViewById(R.id.TomAndJerry_cheeseLifeScale_cheese2),
                findViewById(R.id.TomAndJerry_cheeseLifeScale_cheese1)
        };
        TomAndJerry_Grid = new AppCompatImageView[][]{
                {
                        findViewById(R.id.TomAndJerry_Grid_col0_row0),
                        findViewById(R.id.TomAndJerry_Grid_col1_row0),
                        findViewById(R.id.TomAndJerry_Grid_col2_row0),
                        findViewById(R.id.TomAndJerry_Grid_col3_row0),
                        findViewById(R.id.TomAndJerry_Grid_col4_row0)

                },
                {
                        findViewById(R.id.TomAndJerry_Grid_col0_row1),
                        findViewById(R.id.TomAndJerry_Grid_col1_row1),
                        findViewById(R.id.TomAndJerry_Grid_col2_row1),
                        findViewById(R.id.TomAndJerry_Grid_col3_row1),
                        findViewById(R.id.TomAndJerry_Grid_col4_row1)
                },
                {
                        findViewById(R.id.TomAndJerry_Grid_col0_row2),
                        findViewById(R.id.TomAndJerry_Grid_col1_row2),
                        findViewById(R.id.TomAndJerry_Grid_col2_row2),
                        findViewById(R.id.TomAndJerry_Grid_col3_row2),
                        findViewById(R.id.TomAndJerry_Grid_col4_row2)

                },
                {
                        findViewById(R.id.TomAndJerry_Grid_col0_row3),
                        findViewById(R.id.TomAndJerry_Grid_col1_row3),
                        findViewById(R.id.TomAndJerry_Grid_col2_row3),
                        findViewById(R.id.TomAndJerry_Grid_col3_row3),
                        findViewById(R.id.TomAndJerry_Grid_col4_row3)
                },
                {
                        findViewById(R.id.TomAndJerry_Grid_col0_row4),
                        findViewById(R.id.TomAndJerry_Grid_col1_row4),
                        findViewById(R.id.TomAndJerry_Grid_col2_row4),
                        findViewById(R.id.TomAndJerry_Grid_col3_row4),
                        findViewById(R.id.TomAndJerry_Grid_col4_row4)
                },
                {
                        findViewById(R.id.TomAndJerry_Grid_col0_row5),
                        findViewById(R.id.TomAndJerry_Grid_col1_row5),
                        findViewById(R.id.TomAndJerry_Grid_col2_row5),
                        findViewById(R.id.TomAndJerry_Grid_col3_row5),
                        findViewById(R.id.TomAndJerry_Grid_col4_row5)
                },
                {
                        findViewById(R.id.TomAndJerry_Grid_col0_row6),
                        findViewById(R.id.TomAndJerry_Grid_col1_row6),
                        findViewById(R.id.TomAndJerry_Grid_col2_row6),
                        findViewById(R.id.TomAndJerry_Grid_col3_row6),
                        findViewById(R.id.TomAndJerry_Grid_col4_row6)
                }
        };
    }

    private void initMoveDetector() {
        moveDetector = new MoveDetector(this,
                new MoveCallback() {

                    @Override
                    public void moveLeft() {
                        moveCharRight(false);
                    }

                    @Override
                    public void moveRight() {
                        moveCharRight(true);
                    }

                    @Override
                    public void moveUp() {
                        if (isFast)
                            delay = DEF_DELAY / 4;
                        else delay = DEF_DELAY / 2;
                    }

                    @Override
                    public void moveDown() {
                        if (isFast)
                            delay = DEF_DELAY / 2;
                        else delay = DEF_DELAY;
                    }
                });

    }

    private void moveCharRight(boolean direction) {
        if (stepBox.length == stepBoxCounter) {
            stepBoxCounter = 0;
        }
        if (direction) {
            stepBox[stepBoxCounter].setVolume(1.0F, 0.0F);
        } else {
            stepBox[stepBoxCounter].setVolume(0.0F, 1.0F);
        }

        stepBox[stepBoxCounter++].start();

        updateMainCharacterLocationUI(gameManager.mainCharacterMoveRight(direction));

    }

    private void pauseSoundBox(MediaPlayer[] soundBox) {
        for (MediaPlayer box : soundBox) {
            stopMedia(box);
        }
    }

    private void stopMedia(MediaPlayer media) {
        media.pause();
        media.seekTo(0);
    }

    private void generateEmptyGridAndCenterMainCharacterUI() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                TomAndJerry_Grid[row][col].setImageResource(0);
            }
        }
        TomAndJerry_Grid[rows - 1][gameManager.getMainCharacter().getLocation()].setImageResource(
                gameManager.getMainCharacter().getImage());
    }

    private void updateMainCharacterLocationUI(int[] updateLocations) {

        TomAndJerry_Grid[rows - 1][updateLocations[0]].setImageResource(0);
        TomAndJerry_Grid[rows - 1][updateLocations[1]].setImageResource(gameManager.getMainCharacter().getImage());
    }

    private final Handler handler = new Handler();
    private final Runnable runnable = new Runnable() {
        public void run() {
            onTick();
            handler.postDelayed(runnable, delay);
        }
    };

    private void onTick() {
        int res = gameManager.isHit();
        if (res == 0) {
            stopMedia(stepBox[stepBoxCounter - 1]);
            soundBox[1].seekTo(0);
            soundBox[1].start();
        } else if (res == 1) {
            stopMedia(stepBox[stepBoxCounter - 1]);
            soundBox[2].seekTo(0);
            soundBox[2].start();
        } else if (res == 2) {
            stopMedia(stepBox[stepBoxCounter - 1]);
            soundBox[3].seekTo(0);
            soundBox[3].start();

        }
        soundBox[0].start();
        updateLifeScoreAndMetersUI(gameManager.getLives(), gameManager.getScore(), gameManager.getMeters());
        updateGameUI(gameManager.UpdateGame());
    }

    private void updateGameUI(int[][] mat) {
        for (int row = 0; row < rows - 1; row++) {
            for (int col = 0; col < cols; col++) {
                TomAndJerry_Grid[row][col].setImageResource(mat[row][col]);
            }
        }
    }

    private void updateLifeScoreAndMetersUI(int lives, int score, int meters) {
        if (lives == 0) {
            gameManager.updateScoreBoard(myLocationManager.getLatitude(), myLocationManager.getLongitude());
            restartGame();
        }
        for (int x = 0; x < lives; x++)
            TomAndJerry_cheeseLifeScale[x].setVisibility(View.VISIBLE);
        for (int y = lives; y < TomAndJerry_cheeseLifeScale.length; y++)
            TomAndJerry_cheeseLifeScale[y].setVisibility(View.INVISIBLE);
        TomAndJerry_score.setText(String.valueOf(score));
        TomAndJerry_meters.setText(String.valueOf(meters));

    }

    private void restartGame() {
        Toast.makeText(this, gameManager.getScore() + " points, lets try again", Toast.LENGTH_LONG).show();
        gameManager.restGame(defaultLife);
        vib.vibrate(500);
    }

    private void stop() {
        handler.removeCallbacks(runnable);
        stopMedia(background);
        pauseSoundBox(soundBox);
        pauseSoundBox(stepBox);
        if (isSensor)
            moveDetector.stop();
        myLocationManager.detachLocationListener();
    }

    private void start() {
        handler.postDelayed(runnable, delay);
        background.start();
        if (isSensor)
            moveDetector.start();
        myLocationManager.attachLocationListener();

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
@Deprecated// using location manager - old way, using high level api FusedLocationProvider
    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                1000,
                1,
                location -> {
                   // gameManager.setLat(location.getLatitude());
                    //gameManager.setLng(location.getLongitude());
                });
    }
}