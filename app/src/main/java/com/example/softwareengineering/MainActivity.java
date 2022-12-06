package com.example.softwareengineering;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

public class MainActivity extends AppCompatActivity {
    private CheckBox cb1;
    private CheckBox cb2;
    private CheckBox cb3;
    private CheckBox cb4;
    private CheckBox cb5;
    private CheckBox cb6;


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