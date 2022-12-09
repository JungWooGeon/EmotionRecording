package com.example.softwareengineering.weather;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class WeatherActivityViewModel extends ViewModel {

    private WeatherModel model;

    private final MutableLiveData<String> weatherEmotion = new MutableLiveData<>();

    public void init(Context context) {
        model = new WeatherModel();
        model.calculateScore(context);
        weatherEmotion.setValue(model.getWeatherEmotion());
    }

    public MutableLiveData<String> getWeatherEmotion() { return weatherEmotion; }
}
