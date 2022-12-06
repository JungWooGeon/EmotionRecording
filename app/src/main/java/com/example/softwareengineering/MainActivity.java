package com.example.softwareengineering;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    private CheckBox cb1;
    private CheckBox cb2;
    private CheckBox cb3;
    private CheckBox cb4;
    private CheckBox cb5;
    private CheckBox cb6;
    private Button record_activity_toggle;
    private LinearLayout emotion_add_layout;
    private boolean isToggle = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cb1 = (CheckBox) findViewById(R.id.checkbox1);
        cb2 = (CheckBox) findViewById(R.id.checkbox2);
        cb3 = (CheckBox) findViewById(R.id.checkbox3);
        cb4 = (CheckBox) findViewById(R.id.checkbox4);
        cb5 = (CheckBox) findViewById(R.id.checkbox5);
        cb6 = (CheckBox) findViewById(R.id.checkbox6);
        CheckBox[] cb_array = {cb1, cb2, cb3, cb4, cb5, cb6};
        record_activity_toggle = (Button) findViewById(R.id.record_activity_toggle);
        emotion_add_layout = (LinearLayout) findViewById(R.id.emotion_add_layout);
        record_activity_toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isToggle){
                    record_activity_toggle.setText("▲");
                    emotion_add_layout.setVisibility(View.VISIBLE);
                    isToggle = false;
                }else{
                    record_activity_toggle.setText("▼");
                    emotion_add_layout.setVisibility(View.GONE);
                    isToggle = true;
                }
            }
        });

        initCheckBox(cb_array);
    }

    private void initCheckBox(CheckBox[] cb_array){
        for(int i = 0 ; i < 6 ; i++){
            int num = i;
            cb_array[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for(int j = 0 ; j < 6 ; j++){
                        cb_array[j].setChecked(false);
                    }
                    cb_array[num].setChecked(true);
                }
            });
        }
    }
}