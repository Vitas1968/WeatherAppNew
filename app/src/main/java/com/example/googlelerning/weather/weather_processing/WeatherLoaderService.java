package com.example.googlelerning.weather.weather_processing;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.example.googlelerning.weather.fragments.ShowWeatherFragment;

import org.json.JSONObject;

public class WeatherLoaderService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String city=intent.getStringExtra(ShowWeatherFragment.PARAM_CITY);
        getJsonWeather(city);
        return super.onStartCommand(intent, flags, startId);
    }

    private void getJsonWeather(String city) {
        final String fCity=city;
        new Thread() {
            @Override
            public void run() {
              JSONObject  jsonObject = WeatherDataLoader.getJSONData(fCity);
              if (jsonObject!=null) {
                  String jsonString =jsonObject.toString();
                  Intent intent = new Intent(ShowWeatherFragment.BROADCAST_ACTION);
                  intent.putExtra("jsonObject",jsonString );
                  sendBroadcast(intent);
              }

            }
        }.start();
    }
}
