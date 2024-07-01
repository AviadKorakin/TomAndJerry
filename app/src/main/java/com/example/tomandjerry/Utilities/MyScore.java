package com.example.tomandjerry.Utilities;

import androidx.annotation.NonNull;

public class MyScore {
    private int score;
    private double lat;
    private double lng;
    private final String delimiter = "/";

    public MyScore(int score, double lat, double lng) {
        this.score = score;
        this.lat = lat;
        this.lng = lng;
    }

    public MyScore(String composedLocation) {
        String[] composed = composedLocation.split(delimiter);
        this.score = Integer.parseInt(composed[0]);
        this.lat = Double.parseDouble(composed[1]);
        this.lng = Double.parseDouble(composed[2]);

    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    @NonNull
    @Override
    public String toString() {
        return score+delimiter+lat+delimiter+lng;
    }
}
