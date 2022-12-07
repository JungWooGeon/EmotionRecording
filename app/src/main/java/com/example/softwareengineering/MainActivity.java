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

/**
 * 메인 첫 화면으로 감정을 기록의 첫 시작을 할 수 있는 화면
 * 기본 감정, 커스텀 감정 등을 기록하고, RecordActivity 로 전환
 */
public class MainActivity extends AppCompatActivity {

    CheckBox[] cb_array = null;
    String[] versionArray = null;
    private boolean isToggle = true;
    private ActivityMainBinding binding;

    private String title = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setActivity(this);

        cb_array = new CheckBox[]{binding.checkbox1, binding.checkbox2, binding.checkbox3, binding.checkbox4, binding.checkbox5, binding.checkbox6};
        versionArray = new String[]{getText(R.string.grinning_face_emoji).toString(), getText(R.string.grinning_face_with_big_eyes).toString(), getText(R.string.face_with_tears_of_joy).toString(), getText(R.string.smiling_face_with_halo).toString(),
                getText(R.string.winking_face).toString(), getText(R.string.smiling_face_with_heart_eyes).toString(), getText(R.string.sleepy_face).toString()};
        initButton();
        initCheckBox();
    }

    private void initButton() {
        // toggle 버튼 기능 구현 (custom 감정 기록 부분 보이기, 숨기기)
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

        // '+' 모양의 이미지 클릭시 유사한 감정 고르기 기능 (Alertdialog 를 띄워서 감정 모양 선택)
        binding.addEmotionButton.setOnClickListener(view -> {
            AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
            dlg.setTitle(getText(R.string.select_emotion)); //제목

            dlg.setSingleChoiceItems(versionArray, 0, (dialog, which) -> binding.addEmotionButton.setText(versionArray[which]));
            dlg.setPositiveButton(getText(R.string.confirm), (dialog, which) -> Toast.makeText(MainActivity.this, getText(R.string.complete_select_emotion), Toast.LENGTH_SHORT).show());
            dlg.show();
        });

        // Intent 에 감정 이미지, 제목, 커스텀 감정 정보 등을 담아서 activity 전환
        binding.nextButton.setOnClickListener(view -> {
            // 감정 선택, 감정 제목, 감정 설명, 비슷한 감정
            int emotionId = checkSelectedCheckbox();
            String customEmotionImage = binding.addEmotionButton.getText().toString();
            String customEmotionTitle = binding.addEmotionName.getText().toString();
            String customEmotionDescription = binding.editTextTextPersonName.getText().toString();
            String similarEmotion = versionArray[checkSelectedRadioButton()];

            if (emotionId == 0 && (customEmotionTitle.equals("") || customEmotionImage.equals(getText(R.string.plus)))) {
                // 감정 1~6 중 선택되지 않고, 커스텀 감정 제목이나 이미지가 기록되지 않았을 때
                Toast.makeText(this, getText(R.string.select_emotion), Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(getApplicationContext(), RecordActivity.class);
            intent.putExtra(getText(R.string.emotionId).toString(), emotionId);
            intent.putExtra(getString(R.string.emotionTitle), title);

            intent.putExtra(getText(R.string.customEmotionImage).toString(), customEmotionImage);
            intent.putExtra(getText(R.string.customEmotionTitle).toString(), customEmotionTitle);
            intent.putExtra(getText(R.string.customEmotionDescription).toString(), customEmotionDescription);
            intent.putExtra(getText(R.string.similarEmotion).toString(), similarEmotion);
            startActivity(intent);
        });

        // 비슷한 감정 선택 부분 기본 설정
        binding.closestEmotion.check(binding.radioButton1.getId());
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

    /**
     * 기본 감정 중 선택한 부분이 있다면 왼쪽 위부터 오른쪽 아래 순서로 1 ~ 6 return
     * 선택한 부분이 없다면 0 return
     */
    private int checkSelectedCheckbox() {
        int checkId = 0;

        if (binding.checkbox1.isChecked()) {
            checkId = 1;
            title = getText(R.string.pleasure).toString();
        } else if (binding.checkbox2.isChecked()) {
            checkId = 2;
            title = getText(R.string.sadness).toString();
        } else if (binding.checkbox3.isChecked()) {
            checkId = 3;
            title = getText(R.string.aggro).toString();
        } else if (binding.checkbox4.isChecked()) {
            checkId = 4;
            title = getText(R.string.flutter).toString();
        } else if (binding.checkbox5.isChecked()) {
            checkId = 5;
            title = getText(R.string.excited).toString();
        } else if (binding.checkbox6.isChecked()) {
            checkId = 6;
            title = getText(R.string.annoyance).toString();
        }

        return checkId;
    }

    /**
     * 커스텀 감정 선택 -> 유사한 감정 선택에서 RadioButton 부분 선택한 버튼 확인
     * 위에서부터 0 ~ 5 로 return
     */
    private int checkSelectedRadioButton() {
        if (binding.radioButton1.isChecked()) {
            return 0;
        } else if (binding.radioButton2.isChecked()) {
            return 1;
        } else if (binding.radioButton3.isChecked()) {
            return 2;
        } else if (binding.radioButton4.isChecked()) {
            return 3;
        } else if (binding.radioButton5.isChecked()) {
            return 4;
        } else if (binding.radioButton6.isChecked()) {
            return 5;
        } else {
            return -1;
        }
     }
}