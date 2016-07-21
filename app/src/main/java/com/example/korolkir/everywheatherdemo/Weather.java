package com.example.korolkir.everywheatherdemo;

/**
 * Created by korolkir on 21.07.16.
 */
public class Weather {
    private int temperature;
    private int tempMin;
    private int tempMax;
    private int speed;
    private int color;
    private String dayOfWeek;
    private String description;
    private String city;
    private String typeOfWeather;

    public Weather(String city, String description, int tempMax, int tempMin, int speed, String dayOfWeek, String typeOfWeather) {

        this.city = city;
        this.description = description;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
        this.speed = speed;
        this.dayOfWeek = dayOfWeek;
        this.typeOfWeather = typeOfWeather;


    }

    public Weather() {
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getTempMin() {
        return tempMin;
    }

    public void setTempMin(int tempMin) {
        this.tempMin = tempMin;
    }

    public int getTempMax() {
        return tempMax;
    }

    public void setTempMax(int tempMax) {
        this.tempMax = tempMax;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getColor(){return color;}

    public void setColor(int color){ this.color = color;}

    public  void setTypeOfWeather(String typeOfWeather){this.typeOfWeather = typeOfWeather;}

    public String getTypeOfWeather(){return  typeOfWeather;}
}