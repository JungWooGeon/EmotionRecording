package com.example.softwareengineering;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

    private TextView add_emotion_button;

    private Button next_button;

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

        add_emotion_button = (TextView) findViewById(R.id.imageView7);
        add_emotion_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
                dlg.setTitle("이모티콘을 선택해주세요"); //제목
                final String[] versionArray = new String[] {"\uD83D\uDE00","\uD83D\uDE04","\uD83D\uDE02","\uD83D\uDE07","\uD83D\uDE09","\uD83D\uDE0D","\uD83D\uDE2A"};

                dlg.setSingleChoiceItems(versionArray, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        add_emotion_button.setText(versionArray[which]);
                    }
                });
                dlg.setPositiveButton("확인",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this,"이모티콘을 선택하셨습니다.",Toast.LENGTH_SHORT).show();
                    }
                });
                dlg.show();
            }
        });

        next_button = (Button) findViewById(R.id.next_button);
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), RecordActivity.class);
                startActivity(intent);
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