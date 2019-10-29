package com.example.googlelerning.weather.weather_processing;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class WeatherLoaderService extends Service {
    public WeatherLoaderService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
