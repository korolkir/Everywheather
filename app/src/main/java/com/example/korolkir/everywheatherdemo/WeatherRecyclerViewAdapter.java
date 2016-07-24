package com.example.korolkir.everywheatherdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by korolkir on 23.07.16.
 */
public class WeatherRecyclerViewAdapter extends RecyclerView.Adapter<WeatherRecyclerViewAdapter.WeatherViewHolder> {

    private List<Weather> mWeatherList;
    private Context mContext;
    private WeatherImageSelector mImageSelector;
    private WeatherColorSelector mColorSelector;


    public WeatherRecyclerViewAdapter(Context context, List<Weather> weatherList) {
        mWeatherList = weatherList;
        mContext = context;
        mImageSelector = new WeatherImageSelector();
        mColorSelector = new WeatherColorSelector(context);
    }

    @Override
    public WeatherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_list_item, parent, false);
        WeatherViewHolder wvh = new WeatherViewHolder(v);
        return  wvh;
    }

    @Override
    public void onBindViewHolder(WeatherViewHolder holder, int position) {
        Weather weather = mWeatherList.get(position);
        holder.dayOfTheWeek.setText(weather.getDayOfWeek());
        holder.description.setText(weather.getDescription());
        holder.temperatureRange.setText(weather.getTempMin() +"º"+ " - "  + weather.getTempMax() + "º");
        String typeOfWeather = weather.getTypeOfWeather();
        Picasso.with(mContext).load(mImageSelector.getImageIdAccordingTypeOfWeather(typeOfWeather)).
                into(holder.weatherImage);
        holder.itemLayout.setBackgroundColor(mColorSelector.getColorAccordingTypeOfWeather(typeOfWeather));


    }

    @Override
    public int getItemCount() {
        return mWeatherList.size();
    }

    public static class WeatherViewHolder extends RecyclerView.ViewHolder {

        LinearLayout itemLayout;
        ImageView weatherImage;
        TextView description;
        TextView dayOfTheWeek;
        TextView temperatureRange;

        WeatherViewHolder(View itemView) {
            super(itemView);
            itemLayout = ButterKnife.findById(itemView, R.id.list_item_linear_layout);
            weatherImage = ButterKnife.findById(itemView, R.id.weather_image);
            description = ButterKnife.findById(itemView, R.id.description);
            temperatureRange = ButterKnife.findById(itemView, R.id.temperature_range);
            dayOfTheWeek = ButterKnife.findById(itemView, R.id.day_of_the_week);
        }
    }
}
