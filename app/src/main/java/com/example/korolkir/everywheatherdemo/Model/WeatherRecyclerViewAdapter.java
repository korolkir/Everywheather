package com.example.korolkir.everywheatherdemo.model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.korolkir.everywheatherdemo.R;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by korolkir on 23.07.16.
 */
public class WeatherRecyclerViewAdapter extends RecyclerView.Adapter<WeatherRecyclerViewAdapter.WeatherViewHolder> {

    private List<DailyForecast> weatherList;
    private Context context;
    private WeatherImageSelector imageSelector;
    private WeatherColorSelector colorSelector;

    public WeatherRecyclerViewAdapter(Context context, List<DailyForecast> weatherList) {
        this.weatherList = weatherList;
        this.context = context;
        imageSelector = new WeatherImageSelector();
        colorSelector = new WeatherColorSelector(context);


    }

    @Override
    public WeatherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_list_item, parent, false);
        WeatherViewHolder wvh = new WeatherViewHolder(v);
        return  wvh;
    }

    @Override
    public void onBindViewHolder(WeatherViewHolder holder, int position) {
        DailyForecast forecast = weatherList.get(position);
        holder.dayOfTheWeek.setText(getDayOfTheWeek(position+1));
        holder.description.setText(forecast.getWeather().get(0).getDescription());
        holder.temperatureRange.setText(String.format("%dº - %dº", forecast.getTemp().getMin(), forecast.getTemp().getMax()));
        String typeOfWeather = forecast.getWeather().get(0).getMain();
        Picasso.with(context).load(imageSelector.getImageIdAccordingTypeOfWeather(typeOfWeather)).
                into(holder.weatherImage);
        holder.itemLayout.setBackgroundColor(colorSelector.getColorAccordingTypeOfWeather(typeOfWeather));
    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }

    private String getDayOfTheWeek(int i) {
        int dayOfTheWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        dayOfTheWeek +=i;
        if (dayOfTheWeek > 7) dayOfTheWeek -= 7;
        switch (dayOfTheWeek) {
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

    public static class WeatherViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.list_item_linear_layout) LinearLayout itemLayout;
        @BindView(R.id.weather_image) ImageView weatherImage;
        @BindView(R.id.description) TextView description;
        @BindView(R.id.day_of_the_week) TextView dayOfTheWeek;
        @BindView(R.id.temperature_range) TextView temperatureRange;

        WeatherViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
