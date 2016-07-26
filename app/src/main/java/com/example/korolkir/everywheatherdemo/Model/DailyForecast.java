package com.example.korolkir.everywheatherdemo.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by korolkir on 25.07.16.
 */
public class DailyForecast {

    @SerializedName("temp")
    @Expose
    private Temperature temp;
    @SerializedName("pressure")
    @Expose
    private double pressure;
    @SerializedName("humidity")
    @Expose
    private int humidity;
    @SerializedName("weather")
    @Expose
    private java.util.List<Weather> weather = new ArrayList<Weather>();
    @SerializedName("speed")
    @Expose
    private double speed;



    /**
     *
     * @return
     * The temp
     */
    public Temperature getTemp() {
        return temp;
    }

    /**
     *
     * @param temp
     * The temp
     */
    public void setTemp(Temperature temp) {
        this.temp = temp;
    }

    /**
     *
     * @return
     * The pressure
     */
    public double getPressure() {
        return pressure;
    }

    /**
     *
     * @param pressure
     * The pressure
     */
    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    /**
     *
     * @return
     * The humidity
     */
    public int getHumidity() {
        return humidity;
    }

    /**
     *
     * @param humidity
     * The humidity
     */
    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    /**
     *
     * @return
     * The weather
     */
    public java.util.List<Weather> getWeather() {
        return weather;
    }

    /**
     *
     * @param weather
     * The weather
     */
    public void setWeather(java.util.List<Weather> weather) {
        this.weather = weather;
    }

    /**
     *
     * @return
     * The speed
     */
    public double getSpeed() {
        return speed;
    }

    /**
     *
     * @param speed
     * The speed
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
