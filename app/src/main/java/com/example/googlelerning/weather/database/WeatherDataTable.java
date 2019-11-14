package com.example.googlelerning.weather.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class WeatherDataTable {
     final static String TABLE_NAME = "WeatherData";
     final static String COLUMN_ID = "_id";
    public final static String COLUMN_NAME_CITY = "name_city";
    public final static String COLUMN_NAME_COUNTRY = "name_country";
    public final static String COLUMN_DATE_UPDATE = "date_update";
    public final static String COLUMN_TIME_UPDATE = "time_update";
    public final static String COLUMN_HUMIDITY = "humidity";
    public final static String COLUMN_PRESSURE = "pressure";
    public final static String COLUMN_TEMPERATURE = "temperature";
    public final static String COLUMN_CLOUDS = "clouds_descript";
    public final static String COLUMN_COD_WEATHER_ICON = "cod_icon";
    public final static String COLUMN_WIND_SPEED = "wind_speed";

    static void createTable(SQLiteDatabase database) {
        database.execSQL("CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME_CITY +" TEXT,"
                + COLUMN_NAME_COUNTRY +" TEXT,"
                + COLUMN_DATE_UPDATE+" TEXT,"
                + COLUMN_TIME_UPDATE+" TEXT,"
                + COLUMN_HUMIDITY+" INTEGER,"
                + COLUMN_PRESSURE+" INTEGER,"
                + COLUMN_TEMPERATURE+" REAL,"
                + COLUMN_CLOUDS+" TEXT,"
                + COLUMN_COD_WEATHER_ICON+" INTEGER);");

    }

    static void onUpgrade(SQLiteDatabase database) {
        database.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + COLUMN_WIND_SPEED+" REAL ");
    }

    public static void addNotes(ContentValues values , SQLiteDatabase database) {
        database.insert(TABLE_NAME, null, values);
    }

    public static Cursor getAllNotes(SQLiteDatabase database, String city){
        String[] columnName=new String[]{
                COLUMN_NAME_CITY,
                COLUMN_NAME_COUNTRY,
                COLUMN_DATE_UPDATE,
                COLUMN_TIME_UPDATE,
                COLUMN_HUMIDITY,
                COLUMN_PRESSURE,
                COLUMN_TEMPERATURE,
                COLUMN_CLOUDS,
                COLUMN_COD_WEATHER_ICON
        };
        String[] selectionArgs=new String[]{city};
         Cursor cursor = database.query(TABLE_NAME,
                 columnName,
                 "COLUMN_NAME_CITY=?",
                 selectionArgs,
                null, null, null);
        return cursor;


    }
}
