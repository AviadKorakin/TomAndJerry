package com.example.tomandjerry.Activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.example.tomandjerry.R;
import com.example.tomandjerry.Fragments.ScoreFragment;
import com.example.tomandjerry.databinding.ActivityMapsAltBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsAlt_Activity extends FragmentActivity implements OnMapReadyCallback, ScoreFragment.OnScoreSelectedListener {

    private GoogleMap mMap;
    private ActivityMapsAltBinding binding;
    private AppCompatImageButton TomAndJerry_ReturnButton;
    private MediaPlayer backgroundMusic;
    private LatLng defaultLatLng = new LatLng(31.0461, 34.8516); // Israel coordinates
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsAltBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Add ScoreFragment dynamically
        ScoreFragment scoreFragment = new ScoreFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.score, scoreFragment)
                .commit();

        // Add MapFragment dynamically
        SupportMapFragment mapFragment = new SupportMapFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.map, mapFragment)
                .commit();
        TomAndJerry_ReturnButton = findViewById(R.id.returnButton);
        TomAndJerry_ReturnButton.setOnClickListener(v -> returnToMenu());
        setSoundBox();
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLatLng, 6f));
    }

    @Override
    public void onScoreSelected(double lat, double lng) {
        if (mMap != null) {
            LatLng location = new LatLng(lat, lng);
            mMap.clear();
            mMap.addMarker(new MarkerOptions().position(location).title("You played here!"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f));
        }
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
    private void setSoundBox() {

        backgroundMusic= MediaPlayer.create(this, R.raw.sn_map);
        backgroundMusic.setVolume(0.1F,0.1F);
        backgroundMusic.setLooping(true);
    }
    private void returnToMenu() {
        Intent i = new Intent(getApplicationContext(), Lobby_Activity.class);
        startActivity(i);
        finish();
    }
}