package com.example.googlelerning.weather.rest.entities;

import com.google.gson.annotations.SerializedName;

public class CoordRestModel {
    @SerializedName("lon") public float lon;
    @SerializedName("lat") public float lat;

    public float getLon() {
        return lon;
    }

    public float getLat() {
        return lat;
    }
}
