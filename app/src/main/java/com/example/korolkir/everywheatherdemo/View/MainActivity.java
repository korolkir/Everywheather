package com.example.korolkir.everywheatherdemo.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.provider.Settings;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.SearchSuggestionsAdapter;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.example.korolkir.everywheatherdemo.R;
import com.example.korolkir.everywheatherdemo.model.DailyForecast;
import com.example.korolkir.everywheatherdemo.model.WeatherRecyclerViewAdapter;

import com.example.korolkir.everywheatherdemo.presenter.ForecastPresenter;
import com.example.korolkir.everywheatherdemo.presenter.EveryWeatherForecastPresenter;
import com.example.korolkir.everywheatherdemo.model.suggestions.CitySuggestion;

import com.facebook.CallbackManager;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.squareup.picasso.Picasso;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ShowingView, FloatingSearchView.OnQueryChangeListener,
        SearchSuggestionsAdapter.OnBindSuggestionCallback, SwipeRefreshLayout.OnRefreshListener {

    private static final int GPS_SETTINGS_REQUEST_CODE = 1234 ;
    private static final int FACEBOOK_LOGIN_REQUEST_CODE = 64206;
    private static final int FACEBOOK_SHARE_REQUEST_CODE = 64207;

    private ForecastPresenter presenter;
    private List<DailyForecast> dailyForecastList;
    private WeatherRecyclerViewAdapter adapter;
    private NavigationDrawerMenuAdapter navigationDrawerAdapter;
    private CallbackManager facebookCallbackManager;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.current_day_description)
    TextView currentDayDescription;
    @BindView(R.id.current_day_temperature)
    TextView currentDayTemp;
    @BindView(R.id.current_day_speed)
    TextView currentDaySpeed;
    @BindView(R.id.current_day_temperature_range)
    TextView currentDayTemperatureRange;
    @BindView(R.id.current_day_image)
    ImageView image;
    @BindView(R.id.current_day_linear_layout)
    LinearLayout currentDayLinear;
    @BindView(R.id.search_view)
    FloatingSearchView searchView;
    @BindView(R.id.city_name)
    TextView cityName;
    @BindView(R.id.main_layout)
    RelativeLayout mainLayout;
    @BindView(R.id.adView)
    AdView adView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.drawer_recycler_view)
    RecyclerView drawerRecyclerView;
    @BindView(R.id.progress_view)
    CircularProgressView progressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initViews();
        initCallbacks();
        presenter = new EveryWeatherForecastPresenter(this);
        presenter.onActivityCreate();
    }



    private void initCallbacks() {
        facebookCallbackManager = CallbackManager.Factory.create();
        ProfileTracker profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                presenter.currentFacebookProfileChaned();
                if(currentProfile == null) {
                    navigationDrawerAdapter.notifyDataSetChanged();
                    Log.i("Profile", "tracking!");
                }
            }
        };
        profileTracker.startTracking();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showCurrentDayTemperature(int temperature) {
        currentDayTemp.setText(String.format("%dº", temperature));
    }

    @Override
    public void showCurrentDayDescription(String description) {
        currentDayDescription.setText(description);
    }

    @Override
    public void showCurrentDayWindSpeed(int speed) {
        currentDaySpeed.setText(String.format("%d mph", speed));
    }

    @Override
    public void showCurrentDayTemperatureRange(int minTemperature, int maxTemperature) {
        currentDayTemperatureRange.setText(String.format("%dº - %dº", minTemperature, maxTemperature));
    }

    @Override
    public void showCurrentDayImage(int imageId) {
        Picasso.with(this).load(imageId).
                into(image);
    }

    @Override
    public void setCurrentDayColor(int color) {
        currentDayLinear.setBackgroundColor(color);
        searchView.setBackgroundColor(color);
        mainLayout.setBackgroundColor(color);
    }

    @Override
    public void showWeatherList(List<DailyForecast> weatherList) {
        dailyForecastList.clear();
        dailyForecastList.addAll(weatherList);
        adapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onSearchTextChanged(String oldQuery, String newQuery) {
        presenter.onSearchTextChanged(newQuery);

    }

    @Override
    public void setCityName(String city) {
        cityName.setText(city);
    }

    @Override
    public void clearSearchFocus() {
        searchView.clearSearchFocus();
    }

    @Override
    public void setSearchText(String city) {
        searchView.setSearchText(city);
    }

    @Override
    public void setRefreshing(boolean b) {
        swipeRefreshLayout.setRefreshing(b);
    }

    @Override
    public void onBindSuggestion(final View suggestionView, ImageView leftIcon, final TextView textView,
                                 final SearchSuggestion item, int itemPosition) {

        leftIcon.setColorFilter(getResources().getColor(R.color.colorTextWhite));
        textView.setTextColor(getResources().getColor(R.color.colorTextWhite));
        suggestionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onSuggestionClick(item.getBody());
            }
        });
    }

    @Override
    public void onRefresh() {
        presenter.onRefresh();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case FACEBOOK_LOGIN_REQUEST_CODE:
                facebookCallbackManager.onActivityResult(requestCode, resultCode, data);
                break;
            case FACEBOOK_SHARE_REQUEST_CODE:
                presenter.facebookShareResult(resultCode);
                break;
            case GPS_SETTINGS_REQUEST_CODE:
                presenter.gpsResult();
                break;
        }
    }



    private void initViews() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        dailyForecastList = new ArrayList<>();
        adapter = new WeatherRecyclerViewAdapter(this, dailyForecastList);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(this)
                        .color(Color.WHITE)
                        .size(1)
                        .build());

        searchView.setOnQueryChangeListener(this);
        searchView.setOnBindSuggestionCallback(this);
        searchView.setCloseSearchOnKeyboardDismiss(true);
        searchView.setOnLeftMenuClickListener(new FloatingSearchView.OnLeftMenuClickListener() {
            @Override
            public void onMenuOpened() {
                drawerLayout.openDrawer(Gravity.LEFT);
            }

            @Override
            public void onMenuClosed() {

            }
        });

        AdRequest adRequest = new AdRequest.Builder()
                .build();
        adView.loadAd(adRequest);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        drawerRecyclerView.setLayoutManager(llm);
        navigationDrawerAdapter = new NavigationDrawerMenuAdapter();
        drawerRecyclerView.setAdapter(navigationDrawerAdapter);
        swipeRefreshLayout.setOnRefreshListener(this);
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                searchView.setLeftMenuOpen(false);
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    @Override
    public void askToEnableGpsProvider() {
        AlertDialog.Builder alertdialog = new AlertDialog.Builder(this);
        alertdialog.setTitle(getResources().getString(R.string.gps_enable_asking_dialog_title));
        alertdialog.setMessage(getResources().getString(R.string.gps_enable_asking_dialog_message));
        alertdialog.setPositiveButton(getResources().getString(R.string.gps_enable_asking_dialog_positive_button),
                new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                presenter.userAcceptToTurnOnGps();
            }
        });
        alertdialog.setNegativeButton(getResources().getString(R.string.gps_enable_asking_dialog_negative_button),
                new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                presenter.userDeclineToTurnOnGps();
                dialog.cancel();
            }
        });
        alertdialog.show();
    }

    @Override
    public void showToastMessagge(String string) {
        Toast.makeText(this, string, Toast.LENGTH_LONG);
    }

    @Override
    public void deleteAdvertisment() {
        adView.destroy();
    }

    @Override
    public void showSuggestions(List<CitySuggestion> suggestions) {
        searchView.swapSuggestions(suggestions);
    }

    @Override
    public void goToGpsSettings() {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivityForResult(intent, GPS_SETTINGS_REQUEST_CODE);
    }

    @Override
    public void refreshCurrentFacebookProfileName() {
        navigationDrawerAdapter.notifyDataSetChanged();
    }

    @Override
    public void setVisibleMainLayouts() {
        currentDayLinear.setVisibility(View.VISIBLE);
        searchView.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setVisibility(View.VISIBLE);
        adView.setVisibility(View.VISIBLE);
    }

    @Override
    public void setProgressBarEnable(boolean b) {
        if(b) {
            progressView.startAnimation();
        } else {
            progressView.stopAnimation();
        }
    }

    @Override
    public void deleteProgressBar() {
        mainLayout.removeView(progressView);
    }
}
