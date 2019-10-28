package com.example.googlelerning.weather.recicler;
import android.content.Context;
import com.example.googlelerning.weather.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class BuildDataForRecicler {

    private JSONObject mainJSONObject;
    private JSONArray listJSONArray;
    private JSONObject cityJSONObject;
    private static ArrayList<DataClass> list;
    private Context mContext;

    public BuildDataForRecicler(JSONObject JSONObject, Context context) {
        this.mainJSONObject = JSONObject;
        list = new ArrayList<>();
        try {
            cityJSONObject = mainJSONObject.getJSONObject("city");
            listJSONArray = mainJSONObject.getJSONArray("list");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.mContext=context;
    }

    private String getPlaceName() throws JSONException {
        return cityJSONObject.getString("name").toUpperCase() + ", "
                + cityJSONObject.getString("country").toUpperCase();
    }

    private String getDetails(JSONObject jsonObject) throws JSONException {
        return new StringBuilder().append(jsonObject.getJSONArray("weather").
                getJSONObject(0).getString("description").toUpperCase()).append("\n")
                .append("Humidity: ").append(jsonObject.getJSONObject("main").getString("humidity")).append("%").append("\n")
                .append("Pressure: ").append(jsonObject.getJSONObject("main").getString("pressure")).append("hPa").toString();
    }

    private String getCurrentTemp(JSONObject jsonObject) throws JSONException {
        return String.format(Locale.getDefault(), "%.1f", jsonObject.getJSONObject("main").getDouble("temp")) + "\u2103";
    }

    private String getUpdatedText(JSONObject jsonObject) throws JSONException {
        return "Update: "+ jsonObject.getString("dt_txt");
    }

    private String getWeatherIcon(JSONObject jsonObject) throws JSONException{
        int actualId=jsonObject.getJSONArray("weather").getJSONObject(0).getInt("id");
        long sunrise=cityJSONObject.getLong("sunrise");
        long sunset=cityJSONObject.getLong("sunset");



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

    public ArrayList <DataClass> createListData() throws JSONException {
        for (int i=0; i<listJSONArray.length();i++){
            DataClass dc=new DataClass();
            JSONObject jsonObject=listJSONArray.getJSONObject(i);
            String details=getDetails(jsonObject);
            dc.setDetailsField(details);
            String cityField = getPlaceName();
            dc.setCityField(cityField);
            String currentTemp=getCurrentTemp(jsonObject);
            dc.setCurrentTemperatureField(currentTemp);
            String updateTime=getUpdatedText(jsonObject);
            dc.setUpdatedField(updateTime);
            String icon =getWeatherIcon(jsonObject);
            dc.setWeatherIcon(icon);
            list.add(dc);

        }
        return list;
    }
}
