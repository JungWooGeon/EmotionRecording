package com.example.softwareengineering.statistics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.example.softwareengineering.R;
import com.example.softwareengineering.WeatherActivity;
import com.example.softwareengineering.databinding.ActivityStatisticsBinding;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

public class StatisticsActivity extends AppCompatActivity {

    private StatisticsActivityViewModel viewModel;

    private ActivityStatisticsBinding binding;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_statistics);
        binding.setActivity(this);

        viewModel = new ViewModelProvider(this).get(StatisticsActivityViewModel.class);
        viewModel.init(this);

        // 그래프 설정 spinner
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        binding.radarChart.setVisibility(View.VISIBLE);
                        binding.pieChart.setVisibility(View.GONE);
                        binding.barChart.setVisibility(View.GONE);
                        break;
                    case 1:
                        binding.radarChart.setVisibility(View.GONE);
                        binding.pieChart.setVisibility(View.VISIBLE);
                        binding.barChart.setVisibility(View.GONE);
                        break;
                    case 2:
                        binding.radarChart.setVisibility(View.GONE);
                        binding.pieChart.setVisibility(View.GONE);
                        binding.barChart.setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        binding.nextButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), WeatherActivity.class);
            startActivity(intent);
        });

        // 레이더 그래프 업데이트
        viewModel.getRadarData().observe(this, radarData -> {
            binding.radarChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(viewModel.getLabels()));
            binding.radarChart.setData(radarData);
            binding.radarChart.getDescription().setEnabled(false);
        });

        // 파이 그래프 업데이트
        viewModel.getPieData().observe(this, pieData -> {
            binding.pieChart.setData(pieData);
            binding.pieChart.getDescription().setEnabled(false);
            binding.pieChart.setCenterText(getText(R.string.emotions));
            binding.pieChart.animate();
        });

        // 막대 그래프 업데이트
        viewModel.getBarData().observe(this, barData -> {
            binding.barChart.setFitBars(true);
            binding.barChart.setData(barData);
            binding.barChart.getDescription().setEnabled(false);
            binding.barChart.animateY(2000);

            binding.barChart.getAxisLeft().setDrawGridLines(false);
            binding.barChart.getAxisRight().setDrawGridLines(false);

            // x 축 라벨
            binding.barChart.getXAxis().setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    return viewModel.getLabels()[(int) value];
                }
            });
        });

        // 통계 내용 업데이트
        viewModel.getScore().observe(this, scoreList -> {
            binding.statisticsContent1.setText(getText(R.string.priority) + " 1 : " + scoreList.get(0).getName());
            binding.statisticsContent2.setText(getText(R.string.priority) + " 2 : " + scoreList.get(1).getName());
            binding.statisticsContent3.setText(getText(R.string.priority) + " 3 : " + scoreList.get(2).getName());
            binding.statisticsContent4.setText(getText(R.string.priority) + " 4 : " + scoreList.get(3).getName());
        });
    }
}