package com.example.googlelerning.weather.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.googlelerning.weather.R;
import com.example.googlelerning.weather.recycler.DataClass;

import java.util.ArrayList;
import java.util.Locale;

public class ConvertDataFromDB {

    private SQLiteDatabase database;
    private Context mContext;
    private  ArrayList<DataClass> listDataFromDB;
    private Cursor cursorDB;

    public ConvertDataFromDB(SQLiteDatabase database, Context context) {
        this.database = database;
        mContext = context;
        listDataFromDB=new ArrayList<>();
    }

    private String getCity(Cursor cursor){
        String cityRes= cursor.getString(cursor.getColumnIndex(WeatherDataTable.COLUMN_NAME_CITY));
        String country= cursor.getString(cursor.getColumnIndex(WeatherDataTable.COLUMN_NAME_COUNTRY));
        return cityRes+" , "+country;
    }
    private String getUpdate(Cursor cursor){
        String time= cursor.getString(cursor.getColumnIndex(WeatherDataTable.COLUMN_TIME_UPDATE));
        String date= cursor.getString(cursor.getColumnIndex(WeatherDataTable.COLUMN_DATE_UPDATE));
        return date+" "+time;
    }
    private String getTemperature(Cursor cursor){

        return String.format(Locale.getDefault(), "%.1f",
                cursor.getFloat(cursor.getColumnIndex(WeatherDataTable.COLUMN_TIME_UPDATE)))
                + "\u2103";
    }
    private String getDetails(Cursor cursor){
        String description= cursor.getString(cursor.getColumnIndex(WeatherDataTable.COLUMN_CLOUDS));
        String humidity= Integer.toString(cursor.getInt(cursor.getColumnIndex(WeatherDataTable.COLUMN_HUMIDITY)));
        String pressure= Integer.toString(cursor.getInt(cursor.getColumnIndex(WeatherDataTable.COLUMN_DATE_UPDATE)));

        return new StringBuilder().append(description.toUpperCase()).append("\n").
                append("Humidity: ").append(humidity).append("%%").append("\n").
                append("Pressure: ").append(pressure).append("hPa").append("\n").toString();

    }
    private String getWeatherIcon(Cursor cursor){
        int actualId= cursor.getInt(cursor.getColumnIndex(WeatherDataTable.COLUMN_COD_WEATHER_ICON));
        int id = actualId / 100;
        String icon = "";
        switch (id) {
                case 2: {
                    icon = mContext.getResources().getString(R.string.weather_thunder);
                    break;
                }
                case 3: {
                    icon = mContext.getResources().getString(R.string.weather_drizzle);
                    break;
                }
                case 5: {
                    icon = mContext.getResources().getString(R.string.weather_rainy);
                    break;
                }
                case 6: {
                    icon = mContext.getApplicationContext().getResources().getString(R.string.weather_snowy);
                    break;
                }
                case 7: {
                    icon = mContext.getApplicationContext().getResources().getString(R.string.weather_foggy);
                    break;
                }
                case 8: {
                    icon = "\u2601";
                    break;
                }
            }
        return  icon;
    }

    public ArrayList <DataClass> createListDataFromDB(String city) {

       final String cityTmp=city;
       new Thread(new Runnable() {
           @Override
           public void run() {
               cursorDB = WeatherDataTable.getAllNotes(database,cityTmp);
           }
       }).start();
        if (cursorDB!=null) {
            while (cursorDB.moveToNext()) {
                DataClass dc=new DataClass();
                String cityRes=getCity(cursorDB);
                dc.setCityField(cityRes);
                String update = getUpdate(cursorDB);
                dc.setUpdatedField(update);
                String temp=getTemperature(cursorDB);
                dc.setCurrentTemperatureField(temp);
                String details=getDetails(cursorDB);
                dc.setDetailsField(details);
                String weatherIcon=getWeatherIcon(cursorDB);
                dc.setWeatherIcon(weatherIcon);
                listDataFromDB.add(dc);
            }
            cursorDB.close();
            return  listDataFromDB;

        }
        return  null;
    }
}
