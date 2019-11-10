package com.example.googlelerning.weather.rest.entities;

import com.google.gson.annotations.SerializedName;

public class WeatherRequestRestModel {
    @SerializedName("cod") public String codSuccessfully;
    @SerializedName("message") public int message;
    @SerializedName("cnt") public int cnt;
    @SerializedName("list") public WeatherUpdateModel[] mWeatherUpdateModels;
    @SerializedName("city") public CityInfoModel mCityInfoModel;
}
