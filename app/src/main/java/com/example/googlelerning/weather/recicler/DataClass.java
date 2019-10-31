package com.example.googlelerning.weather.recicler;



public class DataClass {
    private  String cityField;
    private String updatedField;
    private String weatherIcon;
    private String currentTemperatureField;
    private String detailsField;

    DataClass() {}

    public  String getCityField() {
        return cityField;
    }

    void setCityField(String cityField) {
        this.cityField = cityField;
    }

    String getUpdatedField() {
        return updatedField;
    }

    void setUpdatedField(String updatedField) {
        this.updatedField = updatedField;
    }

    String getWeatherIcon() {
        return weatherIcon;
    }

    void setWeatherIcon(String weatherIcon) {
        this.weatherIcon = weatherIcon;
    }

    String getCurrentTemperatureField() {
        return currentTemperatureField;
    }

    void setCurrentTemperatureField(String currentTemperatureField) {
        this.currentTemperatureField = currentTemperatureField;
    }

    String getDetailsField() {
        return detailsField;
    }

    void setDetailsField(String detailsField) {
        this.detailsField = detailsField;
    }
}
