package com.example.googlelerning.weather;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;


import java.util.ArrayList;
import java.util.List;

import static android.content.Context.SENSOR_SERVICE;

public class Sensors {

    private Context context;
    private SensorManager sensorManager;
    private Sensor sensorTemperature;
    private Sensor sensorHumidity;
    private ArrayList<Sensor> sensorList;

    public Sensors(Context context){
        this.context=context;
        sensorList=new ArrayList<>();
    }


    public ArrayList<Sensor> getSensors() {
        sensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);
        assert sensorManager != null;
        sensorTemperature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        sensorHumidity = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        sensorList.add(sensorTemperature);
        sensorList.add(sensorHumidity);
        return sensorList;
    }


}
