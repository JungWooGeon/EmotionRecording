package com.example.softwareengineering.statistics;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.RadarData;

import java.util.List;

public class StatisticsActivityViewModel extends ViewModel {

    private StatisticsModel model;

    private final MutableLiveData<List<Score>> scoreList = new MutableLiveData<>();
    private final MutableLiveData<RadarData> radarData = new MutableLiveData<>();
    private final MutableLiveData<PieData> pieData = new MutableLiveData<>();
    private final MutableLiveData<BarData> barData = new MutableLiveData<>();

    public void init(Context context) {
        model = new StatisticsModel();
        model.calculateScore(context);

        scoreList.setValue(model.getScoreList());
        radarData.setValue(model.getRadarData());
        pieData.setValue(model.getPieData());
        barData.setValue(model.getBarData());
    }

    public MutableLiveData<List<Score>> getScore() { return scoreList; }
    public MutableLiveData<RadarData> getRadarData() { return radarData; }
    public MutableLiveData<PieData> getPieData() { return pieData; }
    public MutableLiveData<BarData> getBarData() { return barData; }

    public String[] getLabels() { return model.getLabels(); }
}
