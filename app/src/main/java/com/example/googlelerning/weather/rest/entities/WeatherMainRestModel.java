package com.example.googlelerning.weather.rest.entities;

import com.google.gson.annotations.SerializedName;

public class WeatherMainRestModel {

    @SerializedName("temp") private float temp;
    @SerializedName("temp_min") private float temp_min;
    @SerializedName("temp_max") private float temp_max;
    @SerializedName("pressure") private int pressure;
    @SerializedName("sea_level") private int sea_level;
    @SerializedName("grnd_level") private int grnd_level;
    @SerializedName("humidity") private byte humidity;
    @SerializedName("temp_kf") private float temp_kf;

    public float getTemp() {
        return temp;
    }

    public float getTemp_min() {
        return temp_min;
    }

    public float getTemp_max() {
        return temp_max;
    }

    public int getPressure() {
        return pressure;
    }

    public int getSea_level() {
        return sea_level;
    }

    public int getGrnd_level() {
        return grnd_level;
    }

    public byte getHumidity() {
        return humidity;
    }

    public float getTemp_kf() {
        return temp_kf;
    }
}
