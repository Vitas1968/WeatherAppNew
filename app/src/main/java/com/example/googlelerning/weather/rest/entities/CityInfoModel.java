package com.example.googlelerning.weather.rest.entities;

import com.google.gson.annotations.SerializedName;

public class CityInfoModel {

    @SerializedName("id") private int id;
    @SerializedName("name") private String name;
    @SerializedName("coord") private CoordRestModel coordinate;
    @SerializedName("country") private String country;
    @SerializedName("population") private int population;
    @SerializedName("timezone") private int timezone;
    @SerializedName("sunrise") private long sunrise;
    @SerializedName("sunset") private long sunset;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public CoordRestModel getCoordinate() {
        return coordinate;
    }

    public String getCountry() {
        return country;
    }

    public int getPopulation() {
        return population;
    }

    public int getTimezone() {
        return timezone;
    }

    public long getSunrise() {
        return sunrise;
    }

    public long getSunset() {
        return sunset;
    }
}
