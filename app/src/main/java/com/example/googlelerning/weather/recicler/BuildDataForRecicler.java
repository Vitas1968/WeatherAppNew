package com.example.googlelerning.weather.recicler;
import android.content.Context;
import com.example.googlelerning.weather.R;
import com.example.googlelerning.weather.rest.entities.WeatherRequestRestModel;
import com.example.googlelerning.weather.rest.entities.WeatherUpdateModel;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class BuildDataForRecicler {
    private static ArrayList<DataClass> list;
    private Context mContext;
    private WeatherRequestRestModel mWeatherRequestRestModel;

    public BuildDataForRecicler(WeatherRequestRestModel weatherRequestRestModel, Context context) {
        mWeatherRequestRestModel = weatherRequestRestModel;
        list = new ArrayList<>();
        this.mContext=context;
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
            WeatherUpdateModel wum=mWeatherRequestRestModel.mWeatherUpdateModels[i];
            String details=getDetails(wum);
            dc.setDetailsField(details);
            String currentTemp=getCurrentTemp(wum);
            dc.setCurrentTemperatureField(currentTemp);
            String updateTime=getUpdatedText(wum);
            dc.setUpdatedField(updateTime);
            String icon =getWeatherIcon(wum);
            dc.setWeatherIcon(icon);
            list.add(dc);

        }
        return list;
    }
}
