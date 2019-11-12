package com.example.googlelerning.weather.rest;

import com.example.googlelerning.weather.rest.entities.WeatherRequestRestModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IOpenWeather {
    @GET("data/2.5/forecast")
    Call<WeatherRequestRestModel> loadWeather(@Query("q") String city,
                                              @Query("appid") String keyApi,
                                              @Query("mode") String jsonMode,
                                              @Query("units") String units);
}
