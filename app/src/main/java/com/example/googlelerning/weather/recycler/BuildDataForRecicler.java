package com.example.googlelerning.weather.recycler;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.googlelerning.weather.R;
import com.example.googlelerning.weather.database.WeatherDataTable;
import com.example.googlelerning.weather.rest.entities.WeatherRequestRestModel;
import com.example.googlelerning.weather.rest.entities.WeatherUpdateModel;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class BuildDataForRecicler {
    private  ArrayList<DataClass> list;
    private Context mContext;
    private WeatherRequestRestModel mWeatherRequestRestModel;
    private SQLiteDatabase database;
    private ArrayList<ContentValues> listDataForDB;

    public BuildDataForRecicler(WeatherRequestRestModel weatherRequestRestModel, Context context,SQLiteDatabase database) {
        mWeatherRequestRestModel = weatherRequestRestModel;
        list = new ArrayList<>();
        this.database=database;
        this.mContext=context;
        listDataForDB=new ArrayList<>();
    }

    private void name_cityFieldDB(ContentValues cv, WeatherRequestRestModel weatherRequestRestModel){
      String city= weatherRequestRestModel.mCityInfoModel.getName();
      cv.put(WeatherDataTable.COLUMN_NAME_CITY,city);
    }

    private void name_countryFieldDB(ContentValues cv, WeatherRequestRestModel weatherRequestRestModel){
        String country= weatherRequestRestModel.mCityInfoModel.getCountry();
        cv.put(WeatherDataTable.COLUMN_NAME_COUNTRY,country);
    }

    private void name_temperatureFieldDB(ContentValues cv, WeatherUpdateModel wum){
        float temp = wum.getWeatherMain().getTemp();
        cv.put(WeatherDataTable.COLUMN_TEMPERATURE,temp);
    }

    private void name_humidityFieldDB(ContentValues cv, WeatherUpdateModel wum){
        int hum = wum.getWeatherMain().getHumidity();
        cv.put(WeatherDataTable.COLUMN_HUMIDITY,hum);
    }

    private void name_pressureFieldDB(ContentValues cv, WeatherUpdateModel wum){
        int press = wum.getWeatherMain().getPressure();
        cv.put(WeatherDataTable.COLUMN_PRESSURE,press);
    }

    private void name_date_updateFieldDB(ContentValues cv, WeatherUpdateModel wum){
        String[] arrTmp=new String[2];
        arrTmp = wum.getDt_txt().split(" ");
        String date = arrTmp[0];
        cv.put(WeatherDataTable.COLUMN_DATE_UPDATE,date);
    }
    private void name_time_updateFieldDB(ContentValues cv, WeatherUpdateModel wum){
        String[] arrTmp=new String[2];
        arrTmp = wum.getDt_txt().split(" ");
        String time = arrTmp[1];
        cv.put(WeatherDataTable.COLUMN_TIME_UPDATE,time);
    }

    private void cod_iconFieldDB(ContentValues cv, WeatherUpdateModel wum){
        int id= wum.getWeatherModel()[0].getId();
        cv.put(WeatherDataTable.COLUMN_COD_WEATHER_ICON,id);
    }

    private void clouds_descriptFieldDB(ContentValues cv, WeatherUpdateModel wum){
        String cloudsDesc=wum.getWeatherModel()[0].getDescription();
        cv.put(WeatherDataTable.COLUMN_CLOUDS,cloudsDesc);
    }
    private void insertNotesDb(ArrayList<ContentValues> listDataForDB)
    { final ArrayList<ContentValues> listDataForDBTmp=listDataForDB;
        new  Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0; i<listDataForDBTmp.size(); i++) {
                    WeatherDataTable.addNotes(listDataForDBTmp.get(i),database);
                }
            }
        }).start();

    }
    private String getDetails(WeatherUpdateModel wum) {
        return new StringBuilder().append(wum.getWeatherModel()[0].getDescription().toUpperCase()).append("\n").
                append("Humidity: ").append(wum.getWeatherMain().getHumidity()).append("%").append("\n").
                append("Pressure: ").append(wum.getWeatherMain().getPressure()).append("hPa").append("\n").toString();

    }
    private String getCurrentTemp(WeatherUpdateModel wum)  {
        return String.format(Locale.getDefault(), "%.1f", wum.getWeatherMain().getTemp()) + "\u2103";
    }

    private String getUpdatedText(WeatherUpdateModel wum){
        return "Update: "+wum.getDt_txt();
    }
    private String getWeatherIcon(WeatherUpdateModel wum) {
        int actualId= wum.getWeatherModel()[0].getId();
        long sunrise=mWeatherRequestRestModel.mCityInfoModel.getSunrise();
        long sunset=mWeatherRequestRestModel.mCityInfoModel.getSunset();
        int id = actualId / 100;
        String icon = "";

        if (actualId == 800) {
            long currentTime = new Date().getTime();
            if (currentTime >= sunrise && currentTime < sunset) {
                icon = "\u2600";
            } else {
                icon = mContext.getApplicationContext().getResources().getString(R.string.weather_clear_night);
            }
        } else {
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
        }
        return  icon;
    }
    public ArrayList <DataClass> createListData() {
        for (int i=0; i<mWeatherRequestRestModel.mWeatherUpdateModels.length;i++){
            DataClass dc=new DataClass();
            ContentValues cv=new ContentValues();
            WeatherUpdateModel wum=mWeatherRequestRestModel.mWeatherUpdateModels[i];
            //заполнение ContentValues для базы данных
            name_cityFieldDB(cv,mWeatherRequestRestModel);
            name_countryFieldDB(cv,mWeatherRequestRestModel);
            name_temperatureFieldDB(cv,wum);
            name_humidityFieldDB(cv,wum);
            name_pressureFieldDB(cv,wum);
            name_date_updateFieldDB(cv,wum);
            name_time_updateFieldDB(cv,wum);
            clouds_descriptFieldDB(cv,wum);
            cod_iconFieldDB(cv,wum);

            String details=getDetails(wum);
            dc.setDetailsField(details);
            String currentTemp=getCurrentTemp(wum);
            dc.setCurrentTemperatureField(currentTemp);
            String updateTime=getUpdatedText(wum);
            dc.setUpdatedField(updateTime);
            String icon =getWeatherIcon(wum);
            dc.setWeatherIcon(icon);
            list.add(dc);
            listDataForDB.add(cv);
        }
        insertNotesDb(listDataForDB);
        return list;
    }
}
