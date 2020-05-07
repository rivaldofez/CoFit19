package com.example.cofit19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainExercise extends AppCompatActivity {

    Button btnExercise,btnSetting,btnCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_exercise);

        btnExercise = (Button) findViewById(R.id.btnExercise);
        btnSetting = (Button) findViewById(R.id.btnSetting);

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainExercise.this,SettingPage.class);
                startActivity(intent);
            }
        });


        btnExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainExercise.this,ListExercise.class);
                startActivity(intent);
            }
        });
    }
}
