package com.example.softwareengineering;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.softwareengineering.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    CheckBox[] cb_array = null;
    private boolean isToggle = true;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setActivity(this);

        cb_array = new CheckBox[]{binding.checkbox1, binding.checkbox2, binding.checkbox3, binding.checkbox4, binding.checkbox5, binding.checkbox6};
        initButton();
        initCheckBox();
    }

    private void initButton() {
        binding.recordActivityToggleButton.setOnClickListener(view -> {
            if (isToggle) {
                binding.recordActivityToggleButton.setText(getText(R.string.up_arrow));
                binding.emotionAddLayout.setVisibility(View.VISIBLE);
                isToggle = false;
            } else {
                binding.recordActivityToggleButton.setText(R.string.down_arrow);
                binding.emotionAddLayout.setVisibility(View.GONE);
                isToggle = true;
            }
        });

        binding.addEmotionButton.setOnClickListener(view -> {
            AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
            dlg.setTitle(getText(R.string.select_emotion)); //제목
            final String[] versionArray = new String[]{getText(R.string.grinning_face_emoji).toString(), getText(R.string.grinning_face_with_big_eyes).toString(), getText(R.string.face_with_tears_of_joy).toString(), getText(R.string.smiling_face_with_halo).toString(),
                                                        getText(R.string.winking_face).toString(), getText(R.string.smiling_face_with_heart_eyes).toString(), getText(R.string.sleepy_face).toString()};

            dlg.setSingleChoiceItems(versionArray, 0, (dialog, which) -> binding.addEmotionButton.setText(versionArray[which]));
            dlg.setPositiveButton(getText(R.string.confirm), (dialog, which) -> Toast.makeText(MainActivity.this, getText(R.string.complete_select_emotion), Toast.LENGTH_SHORT).show());
            dlg.show();
        });

        binding.nextButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), RecordActivity.class);
            startActivity(intent);
        });
    }

    private void initCheckBox() {
        for (int i = 0; i < 6; i++) {
            int num = i;
            cb_array[i].setOnClickListener(view -> {
                for (int j = 0; j < 6; j++) {
                    cb_array[j].setChecked(false);
                }
                cb_array[num].setChecked(true);
            });
        }
    }
}