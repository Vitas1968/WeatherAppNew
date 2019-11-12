package com.example.googlelerning.weather.rest.entities;

import com.google.gson.annotations.SerializedName;

public class WeatherUpdateModel {

    @SerializedName("dt") private long dt;
    @SerializedName("main") private WeatherMainRestModel weatherMain;
    @SerializedName("weather") private WeatherModel [] weatherModel;
    @SerializedName("clouds") private CloudsRestModel clouds;
    @SerializedName("wind") private WindRestModel wind;
    @SerializedName("sys") private SysRestModel sys;
    @SerializedName("dt_txt") private String dt_txt;

    public long getDt() {
        return dt;
    }

    public WeatherMainRestModel getWeatherMain() {
        return weatherMain;
    }

    public WeatherModel[] getWeatherModel() {
        return weatherModel;
    }

    public CloudsRestModel getClouds() {
        return clouds;
    }

    public WindRestModel getWind() {
        return wind;
    }

    public SysRestModel getSys() {
        return sys;
    }

    public String getDt_txt() {
        return dt_txt;
    }
}
