package com.example.korolkir.everywheatherdemo.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by korolkir on 25.07.16.
 */
public class Temperature {


    @SerializedName("day")
    @Expose
    private double day;
    @SerializedName("min")
    @Expose
    private double min;
    @SerializedName("max")
    @Expose
    private double max;
    @SerializedName("night")
    @Expose
    private double night;
    @SerializedName("eve")
    @Expose
    private double eve;
    @SerializedName("morn")
    @Expose
    private double morn;

    /**
     *
     * @return
     * The day
     */
    public int getDay() {
        return convertTemperatureInCelsius(day);
    }

    /**
     *
     * @param day
     * The day
     */
    public void setDay(double day) {
        this.day = day;
    }

    /**
     *
     * @return
     * The min
     */
    public int getMin() {
        return convertTemperatureInCelsius(min);
    }

    /**
     *
     * @param min
     * The min
     */
    public void setMin(double min) {
        this.min = min;
    }

    /**
     *
     * @return
     * The max
     */
    public int getMax() {
        return convertTemperatureInCelsius(max);
    }

    /**
     *
     * @param max
     * The max
     */
    public void setMax(double max) {
        this.max = max;
    }

    /**
     *
     * @return
     * The night
     */
    public int getNight() {
        return convertTemperatureInCelsius(night) ;
    }

    /**
     *
     * @param night
     * The night
     */
    public void setNight(double night) {
        this.night = night;
    }

    /**
     *
     * @return
     * The eve
     */
    public int getEve() {
        return convertTemperatureInCelsius(eve);
    }

    /**
     *
     * @param eve
     * The eve
     */
    public void setEve(double eve) {
        this.eve = eve;
    }

    /**
     *
     * @return
     * The morn
     */
    public int getMorn() {
        return convertTemperatureInCelsius(morn);
    }

    /**
     *
     * @param morn
     * The morn
     */
    public void setMorn(double morn) {
        this.morn = morn;
    }

    private int convertTemperatureInCelsius(double temp ) {
        int celc = (int) temp - 273;
        return celc;
    }

}


