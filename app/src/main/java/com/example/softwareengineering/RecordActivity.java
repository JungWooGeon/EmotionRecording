package com.example.softwareengineering;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.example.softwareengineering.databinding.ActivityRecordBinding;

public class RecordActivity extends AppCompatActivity {
    private ActivityRecordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_record);
        binding.setActivity(this);

        binding.nextButton.setOnClickListener(view -> {
            Intent intent=new Intent(getApplicationContext(), StatisticsActivity.class);
            startActivity(intent);
        });
    }
}