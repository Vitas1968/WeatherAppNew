package com.example.googlelerning.weather.recicler;



public class DataClass {
    private  String cityField;
    private String updatedField;
    private String weatherIcon;
    private String currentTemperatureField;
    private String detailsField;

    public DataClass() {}

    public  String getCityField() {
        return cityField;
    }

    public void setCityField(String cityField) {
        this.cityField = cityField;
    }

    public String getUpdatedField() {
        return updatedField;
    }

    public void setUpdatedField(String updatedField) {
        this.updatedField = updatedField;
    }

    public String getWeatherIcon() {
        return weatherIcon;
    }

    public void setWeatherIcon(String weatherIcon) {
        this.weatherIcon = weatherIcon;
    }

    public String getCurrentTemperatureField() {
        return currentTemperatureField;
    }

    public void setCurrentTemperatureField(String currentTemperatureField) {
        this.currentTemperatureField = currentTemperatureField;
    }

    public String getDetailsField() {
        return detailsField;
    }

    public void setDetailsField(String detailsField) {
        this.detailsField = detailsField;
    }
}
