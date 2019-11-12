package com.example.googlelerning.weather.rest.entities;

import com.google.gson.annotations.SerializedName;

public class CloudsRestModel {
    @SerializedName("all") private int all;

    public int getAll() {
        return all;
    }
}
