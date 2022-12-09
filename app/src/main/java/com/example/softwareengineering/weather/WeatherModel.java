package com.example.softwareengineering.weather;

import android.content.Context;

import com.example.softwareengineering.R;
import com.example.softwareengineering.emotion_database.EmotionDataBase;
import com.example.softwareengineering.emotion_database.EmotionInfo;

import java.util.List;

public class WeatherModel {

    private String weatherEmotion = "";

    public String getWeatherEmotion() { return weatherEmotion; }

    public void calculateScore(Context context) {
        final String PLEASURE = "기쁨";
        final String SADNESS = "슬픔";
        final String AGGRO = "화남";
        final String FLUTTER = "설렘";
        final String EXCITED = "신남";
        final String ANNOYANCE = "짜증";

        // DB에서 탐색
        int totalScore = 0;
        List<EmotionInfo> infos = EmotionDataBase.getInstance(context).emotionInfoDao().getAll();
        for (int i = 0; i < infos.size(); i++) {
            if (infos.get(i).getEmotionType().contentEquals(context.getText(R.string.default_emotion))) {
                switch (infos.get(i).getEmotionName()) {
                    case PLEASURE:
                        totalScore += 1;
                        break;
                    case SADNESS:
                        totalScore -= 1;
                        break;
                    case AGGRO:
                        totalScore -= 3;
                        break;
                    case FLUTTER:
                        totalScore += 2;
                        break;
                    case EXCITED:
                        totalScore += 3;
                        break;
                    case ANNOYANCE:
                        totalScore -= 2;
                        break;
                    default:
                        break;
                }
            }
        }

        // 날씨 설정
        if (totalScore <= 5 && totalScore >= -5) {
            weatherEmotion = context.getText(R.string.cloud).toString();
        } else if (totalScore > 5) {
            weatherEmotion = context.getText(R.string.sunny).toString();
        } else {
            weatherEmotion = context.getText(R.string.rain).toString();
        }
    }
}
