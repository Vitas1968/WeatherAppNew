package com.example.googlelerning.weather.rest.entities;

import com.google.gson.annotations.SerializedName;

public class WindRestModel {
    @SerializedName("speed") private float speed;
    @SerializedName("deg") private int deg;

    public float getSpeed() {
        return speed;
    }

    public int getDeg() {
        return deg;
    }
}
