package com.example.korolkir.everywheatherdemo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by korolkir on 21.07.16.
 */
public class JsonParser {

    public Forecast parseForecast(String jsonString) throws JSONException {
        Forecast forecast = new Forecast();
        JSONObject dataJsonObj = new JSONObject(jsonString);

        JSONObject cityJson = dataJsonObj.getJSONObject("city");
        String city = cityJson.getString("name");
        List<Weather> weatherList = new ArrayList<>();
        forecast.setCityName(city);
        forecast.setWeatherList(weatherList);
        JSONArray list = dataJsonObj.getJSONArray("list");
        int currentDayOfTheWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        for (int i = 0, size = list.length(); i < size; i++) {
            Weather weather = parseWeather(list.getJSONObject(i));
            weather.setCity(city);
            weather.setDayOfWeek(getDayOfTheWeek(currentDayOfTheWeek + i));
            weatherList.add(weather);
        }
        return forecast;
    }

    private  static Weather parseWeather(JSONObject weatherJson) throws JSONException {
        Weather weather = new Weather();
        JSONObject temp = weatherJson.getJSONObject("temp");
        weather.setTemperature(temp.getInt("day") - 273);
        weather.setTempMax(temp.getInt("max") - 273);
        weather.setTempMin(temp.getInt("min") - 273);
        weather.setSpeed(weatherJson.getInt("speed"));
        JSONArray weatherDetails = weatherJson.getJSONArray("weather");
        JSONObject descr = weatherDetails.getJSONObject(0);
        weather.setTypeOfWeather(descr.getString("main"));
        weather.setDescription(descr.getString("description"));
        return weather;
    }

    private String getDayOfTheWeek(int i) {
        if (i > 7) i -= 7;
        switch (i) {
            case Calendar.MONDAY: return "Monday";
            case Calendar.TUESDAY: return "Tuesday";
            case Calendar.WEDNESDAY: return "Wednesday";
            case Calendar.THURSDAY: return "Thursday";
            case Calendar.FRIDAY: return "Friday";
            case Calendar.SATURDAY: return "Saturday";
            case Calendar.SUNDAY: return "Sunday";
        }
        return null;
    }

}

