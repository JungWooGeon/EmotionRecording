package com.example.softwareengineering.statistics;

import android.content.Context;
import android.graphics.Color;

import com.example.softwareengineering.R;
import com.example.softwareengineering.emotion_database.EmotionDataBase;
import com.example.softwareengineering.emotion_database.EmotionInfo;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StatisticsModel {

    private final List<Score> scoreList = new ArrayList<>();
    private String[] labels;
    private RadarData radarData;
    private PieData pieData;
    private BarData barData;

    public List<Score> getScoreList() { return scoreList; }
    public String[] getLabels() { return labels; }
    public RadarData getRadarData() { return radarData; }
    public PieData getPieData() { return pieData; }
    public BarData getBarData() { return barData; }

    public void calculateScore(Context context) {
        final String PLEASURE = "기쁨";
        final String SADNESS = "슬픔";
        final String AGGRO = "화남";
        final String FLUTTER = "설렘";
        final String EXCITED = "신남";
        final String ANNOYANCE = "짜증";

        Score pleasureScore = new Score(PLEASURE, 0);
        Score sadnessScore = new Score(SADNESS, 0);
        Score aggroScore = new Score(AGGRO, 0);
        Score flutterScore = new Score(FLUTTER, 0);
        Score excitedScore = new Score(EXCITED, 0);
        Score annoyanceScore = new Score(ANNOYANCE, 0);

        // DB에서 탐색
        List<EmotionInfo> infos = EmotionDataBase.getInstance(context).emotionInfoDao().getAll();
        for (int i = 0; i < infos.size(); i++) {
            if (infos.get(i).getEmotionType().contentEquals(context.getText(R.string.default_emotion))) {
                switch (infos.get(i).getEmotionName()) {
                    case PLEASURE:
                        pleasureScore.addScore();
                        break;
                    case SADNESS:
                        sadnessScore.addScore();
                        break;
                    case AGGRO:
                        aggroScore.addScore();
                        break;
                    case FLUTTER:
                        flutterScore.addScore();
                        break;
                    case EXCITED:
                        excitedScore.addScore();
                        break;
                    case ANNOYANCE:
                        annoyanceScore.addScore();
                        break;
                    default:
                        break;
                }
            }
        }

        scoreList.clear();
        scoreList.add(pleasureScore);
        scoreList.add(sadnessScore);
        scoreList.add(aggroScore);
        scoreList.add(flutterScore);
        scoreList.add(excitedScore);
        scoreList.add(annoyanceScore);

        Collections.sort(scoreList);

        updateRadarSet(context);
        updatePieSet(context);
        updateBarSet(context);
    }

    private void updateRadarSet(Context context) {
        ArrayList<RadarEntry> emotions = new ArrayList<>();

        labels = new String[scoreList.size()];
        for (int i = 0; i < scoreList.size(); i++) {
            emotions.add(new RadarEntry(scoreList.get(i).getScore()));
            labels[i] = scoreList.get(i).getName();
        }

        RadarDataSet radarDataSet = new RadarDataSet(emotions, context.getText(R.string.emotions).toString());
        radarDataSet.setColor(Color.RED);
        radarDataSet.setLineWidth(2f);
        radarDataSet.setValueTextColor(Color.RED);
        radarDataSet.setValueTextSize(14f);
        radarDataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return Integer.toString((int) value);
            }
        });

        radarData = new RadarData();
        radarData.addDataSet(radarDataSet);
    }

    private void updatePieSet(Context context) {
        ArrayList<PieEntry> emotions = new ArrayList<>();

        int total = 0;
        for (int i = 0; i < scoreList.size(); i++) {
            if (scoreList.get(i).getScore() == 0)
                continue;
            total += scoreList.get(i).getScore();
            emotions.add(new PieEntry(scoreList.get(i).getScore(), scoreList.get(i).getName()));
        }

        PieDataSet pieDataSet = new PieDataSet(emotions, context.getText(R.string.emotions).toString());
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);

        int finalTotal = total;
        pieDataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                if (finalTotal == 0) {
                    return Integer.toString(0) + context.getText(R.string.percent);
                } else {
                    return Integer.toString((int)((value / finalTotal) * 100)) + context.getText(R.string.percent);
                }

            }
        });

        pieData = new PieData(pieDataSet);
    }

    private void updateBarSet(Context context) {
        ArrayList<BarEntry> emotions = new ArrayList<>();

        for (int i = 0; i < scoreList.size(); i++)
            emotions.add(new BarEntry(i, scoreList.get(i).getScore()));

        BarDataSet barDataSet = new BarDataSet(emotions, context.getText(R.string.emotions).toString());
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);
        barDataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return Integer.toString((int)(value / 10)) + context.getText(R.string.times);
            }
        });

        barData = new BarData(barDataSet);
    }
}
