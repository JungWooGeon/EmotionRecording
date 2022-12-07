package com.example.softwareengineering.record;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import com.example.softwareengineering.R;
import com.example.softwareengineering.StatisticsActivity;
import com.example.softwareengineering.databinding.ActivityRecordBinding;

public class RecordActivity extends AppCompatActivity {

    private ActivityRecordBinding binding;
    private RecordActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_record);
        binding.setActivity(this);

        viewModel = new ViewModelProvider(this).get(RecordActivityViewModel.class);
        viewModel.init(getIntent(), getResources());

        viewModel.getEmotionImage().observe(this, emotionImage -> binding.emotionImage.setText(emotionImage));
        viewModel.getEmotionName().observe(this, emotionName -> binding.emotionName.setText(emotionName));

        initButton();
    }

    private void initButton() {
         // 'Next' 버튼을 누르면 SharedPrefrences 에 데이터 저장 후 activity 전환
        binding.nextButton.setOnClickListener(view -> {
            viewModel.setDB(this, binding.emotionImage.getText().toString(),
                    binding.keywordEditText1.getText().toString(),
                    binding.keywordEditText2.getText().toString(),
                    binding.emotionDescriptionEditText.getText().toString());

            Intent intent = new Intent(getApplicationContext(), StatisticsActivity.class);
            startActivity(intent);
        });
    }
}