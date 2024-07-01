package com.example.tomandjerry.Activities;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import com.example.tomandjerry.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.tomandjerry.databinding.MapsActivityBinding;

public class Maps_Activity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private MapsActivityBinding binding;
    private double lat;
    private double lng;
    private AppCompatImageButton TomAndJerry_ReturnButton;
    private MediaPlayer backgroundMusic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = MapsActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent prev = getIntent();
        Bundle bundle=prev.getExtras();
        lat= bundle != null ? bundle.getDouble("lat") : 0;
        lng= bundle != null ? bundle.getDouble("lng") : 0;
        TomAndJerry_ReturnButton = findViewById(R.id.returnButton);
        TomAndJerry_ReturnButton.setOnClickListener(v -> returnToMenu());
        setSoundBox();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng pos = new LatLng(lat, lng);
        mMap.addMarker(new MarkerOptions().position(pos).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(pos));
    }
    private void returnToMenu() {
        Intent i = new Intent(getApplicationContext(), ScoreBoard_Activity.class);
        startActivity(i);
        finish();
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
}