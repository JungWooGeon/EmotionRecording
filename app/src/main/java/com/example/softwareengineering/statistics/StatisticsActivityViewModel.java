package com.example.softwareengineering.statistics;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.github.mikephil.charting.data.RadarData;

import java.util.List;

public class StatisticsActivityViewModel extends ViewModel {

    private StatisticsModel model;

    private final MutableLiveData<RadarData> radarData = new MutableLiveData<>();
    private final MutableLiveData<List<Score>> scoreList = new MutableLiveData<>();

    public void init(Context context) {
        model = new StatisticsModel();
        model.calculateScore(context);

        radarData.setValue(model.getRadarData());
        scoreList.setValue(model.getScoreList());
    }

    public MutableLiveData<RadarData> getRadarData() { return radarData; }
    public MutableLiveData<List<Score>> getScore() { return scoreList; }

    public String[] getLabels() { return model.getLabels(); }
}
