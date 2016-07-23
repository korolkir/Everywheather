package com.example.korolkir.everywheatherdemo;

import java.util.Calendar;

/**
 * Created by korolkir on 24.07.16.
 */
public class WeatherImageSelector  {

    public int getImageIdAccordingTypeOfWeather(String typeOfWeather){
        int id = 0;
        switch (typeOfWeather) {
            case "Rain":
                if(getTime() == 0) id = R.drawable.rain_day;
                if(getTime() == 1) id = R.drawable.rain_night;
                break;
            case "Clear":
                if(getTime() == 0) id = R.drawable.clear_day;
                if(getTime() == 1) id = R.drawable.clear_night;
                break;
            case "Clouds":
                if(getTime() == 0) id = R.drawable.clouds_day;
                if(getTime() == 1) id = R.drawable.clouds_night;
                break;
            case "Snow":
                if(getTime() == 0) id = R.drawable.snow_day;
                if(getTime() == 1) id = R.drawable.snow_night;
                break;
            case "Thunderstorm":
                if(getTime() == 0) id = R.drawable.thunder_day;
                if(getTime() == 1) id = R.drawable.thunder_night;
                break;
            case "Drizzle":
                if(getTime() == 0) id = R.drawable.drizzle_day;
                if(getTime() == 1) id = R.drawable.drizzle_night;
                break;
            case "Atmosphere":
                if(getTime() == 0) id = R.drawable.atmosphere_day;
                if(getTime() == 1) id = R.drawable.atmosphere_night;

                break;

        }
        return id;
    }

    private int getTime(){
        Calendar time = Calendar.getInstance();
        int num;
        int result = 100;
        num = time.get(Calendar.HOUR_OF_DAY);
        if(num < 6 || num >= 22) result = 1;
        if(num >= 6 && num < 22) result = 0;
        return result;
    }

}
