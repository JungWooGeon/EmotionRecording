package com.example.softwareengineering;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.softwareengineering.databinding.ActivityRecordBinding;

public class RecordActivity extends AppCompatActivity {

    private int emotionId = 0;
    private String emotionTitle = "";
    private String customEmotionImage = "";
    private String customEmotionTitle = "";
    private String customEmotionDescription = "";
    private String similarEmotion = "";

    private ActivityRecordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_record);
        binding.setActivity(this);

        initActivity();
        initButton();
    }

    /**
     * Activity 설정 : Intent 로 받아온 정보를 토대로 감정 이미지, 제목 설정
     */
    private void initActivity() {
        Intent previousIntent = getIntent();
        emotionId = previousIntent.getIntExtra(getString(R.string.emotionId), 0);
        emotionTitle = previousIntent.getStringExtra(getString(R.string.emotionTitle));
        customEmotionImage = previousIntent.getStringExtra(getString(R.string.customEmotionImage));

        if (emotionId != 0 && customEmotionImage.contentEquals(getText(R.string.plus)) && customEmotionTitle.equals("")) {
            // 기본 감정 중 선택하였을 때, 이미지와 제목 설정
            setEmotionImage(emotionId);
            binding.EmotionName.setText(emotionTitle);
        } else {
            // 커스텀 감정을 설정하였을 때, 부가적인 정보 저장 후 이미지와 제목 설정
            customEmotionTitle = previousIntent.getStringExtra(getString(R.string.customEmotionTitle));
            customEmotionDescription = previousIntent.getStringExtra(getString(R.string.customEmotionDescription));
            similarEmotion = previousIntent.getStringExtra(getString(R.string.similarEmotion));

            binding.emotionImage.setText(customEmotionImage);
            binding.EmotionName.setText(customEmotionTitle);
        }
    }

    private void initButton() {
         // 'Next' 버튼을 누르면 SharedPrefrences 에 데이터 저장 후 activity 전환
        binding.nextButton.setOnClickListener(view -> {
            String emotionType = getEmotionType();
            String eTitle = getEmotionTitle(emotionType);

            // SharedPreferences 에 저장
            SharedPreferences sharedPreferences = getSharedPreferences(getText(R.string.recordEmotionSharedPreferencesKey).toString(), MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString(getText(R.string.emotionType).toString(), emotionType);
            editor.putString(getText(R.string.emotionImage).toString(), binding.emotionImage.getText().toString());
            editor.putString(getText(R.string.emotionName).toString(), eTitle);
            if (emotionType.equals(getText(R.string.custom_emotion).toString())) {
                editor.putString(getText(R.string.customEmotionDescription).toString(), customEmotionDescription);
                editor.putString(getText(R.string.similarEmotion).toString(), similarEmotion);
            }
            editor.putString(getText(R.string.keyword1).toString(), binding.keywordEditText1.getText().toString());
            editor.putString(getText(R.string.keyword2).toString(), binding.keywordEditText2.getText().toString());
            editor.putString(getText(R.string.emotionDescriptionEditText).toString(), binding.emotionDescriptionEditText.getText().toString());

            editor.apply();

            Intent intent = new Intent(getApplicationContext(), StatisticsActivity.class);
            startActivity(intent);
        });
    }

    /**
     * 기본 감정일 때, 감정 이모티콘 이미지 셋팅
     */
    private void setEmotionImage(int emotionId) {
        switch (emotionId) {
            case 1:
                binding.emotionImage.setText(getText(R.string.grinning_face_with_smiling_eyes));
                break;
            case 2:
                binding.emotionImage.setText(getText(R.string.crying_face));
                break;
            case 3:
                binding.emotionImage.setText(getText(R.string.angry_face));
                break;
            case 4:
                binding.emotionImage.setText(getText(R.string.face_with_hand_over_mouth));
                break;
            case 5:
                binding.emotionImage.setText(getText(R.string.star_struck));
                break;
            case 6:
                binding.emotionImage.setText(getText(R.string.enraged_face));
                break;
            default:
                break;
        }
    }

    /**
     * 기본 감정으로 셋팅하였는지, 커스텀 감정으로 셋팅하였는지를 String 으로 return
     */
    private String getEmotionType() {
        String emotionType = "";
        if (emotionId != 0 && customEmotionImage.contentEquals(getText(R.string.plus)) && customEmotionTitle.equals("")) {
            emotionType = getText(R.string.default_emotion).toString();
        } else {
            emotionType = getText(R.string.custom_emotion).toString();
        }

        return emotionType;
    }

    /**
     * 기본 감정인지 커스텀 감정인지에 따라 (인자로 들어온 emotionType 으로 판별)
     * 감정 제목(title)을 return
     */
    private String getEmotionTitle(String emotionType) {
        String eTitle = "";
        if (emotionType.equals(getText(R.string.default_emotion))) {
            eTitle = emotionTitle;
        } else {
            eTitle = customEmotionTitle;
        }
        return eTitle;
    }
}