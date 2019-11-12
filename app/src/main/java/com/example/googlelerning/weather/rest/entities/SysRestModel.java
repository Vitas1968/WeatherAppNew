package com.example.googlelerning.weather.rest.entities;

import com.google.gson.annotations.SerializedName;

public class SysRestModel {
    @SerializedName("pod") private String pod;

    public String getPod() {
        return pod;
    }
}
