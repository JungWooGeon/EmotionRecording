package com.example.softwareengineering.weather;

import android.content.Context;

import com.example.softwareengineering.R;
import com.example.softwareengineering.emotion_database.EmotionDataBase;
import com.example.softwareengineering.emotion_database.EmotionInfo;

import java.util.List;

/**
 * StatisticsActivityViewModel 과 연결된 Model
 */
public class WeatherModel {

    // 감정을 날씨로 표현한 정보
    private String weatherEmotion = "";

    public String getWeatherEmotion() { return weatherEmotion; }

    public void calculateScore(Context context) {
        //@TODO 다른 class 에서도 사용되므로 refactoring 시, enum class 등을 사용하는 것이 좋을 것 같음 (상수 처리)
        final String PLEASURE = "기쁨";
        final String SADNESS = "슬픔";
        final String AGGRO = "화남";
        final String FLUTTER = "설렘";
        final String EXCITED = "신남";
        final String ANNOYANCE = "짜증";

        // DB 에서 탐색 후 전체 점수에 반영
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

        // 점수를 토대로 날씨 설정
        if (totalScore <= 5 && totalScore >= -5) {
            weatherEmotion = context.getText(R.string.cloud).toString();
        } else if (totalScore > 5) {
            weatherEmotion = context.getText(R.string.sunny).toString();
        } else {
            weatherEmotion = context.getText(R.string.rain).toString();
        }
    }
}
