package com.example.cofit19;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.cofit19.Model.Exercise;

import java.util.ArrayList;
import java.util.List;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class Daily_Training extends AppCompatActivity {

    Button btnStart;
    ImageView ex_image;
    TextView txtGetReady,txtCountDown, txtTimer, ex_name;
    ProgressBar progressBar;
    LinearLayout layoutGetReady;

    int ex_id=0;

    List<Exercise> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily__training);

        initData();

        btnStart = (Button) findViewById(R.id.btnstart);

        ex_image = (ImageView) findViewById(R.id.ex_img);
        txtCountDown = (TextView)findViewById(R.id.txtCountDown);
        txtGetReady = (TextView) findViewById(R.id.txtCountDown);
        txtTimer = (TextView) findViewById(R.id.timer);
        ex_name = (TextView) findViewById(R.id.ex_name);

        layoutGetReady = (LinearLayout) findViewById(R.id.layout_get_ready);
        progressBar = (MaterialProgressBar)findViewById(R.id.progressbaract);

        //Set Data
        progressBar.setMax(list.size());

        setExerciseInformation(ex_id);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnStart.getText().toString().toLowerCase().equals("start")){
                    showGetReady();
                    btnStart.setText("done");
                }else if(btnStart.getText().toString().toLowerCase().equals("done")){

                }else{
                    showFinished();
                }
            }
        });

    }

    private void showGetReady() {
        ex_image.setVisibility(View.INVISIBLE);
        btnStart.setVisibility(View.INVISIBLE);
        txtTimer.setVisibility(View.VISIBLE);

        layoutGetReady.setVisibility(View.VISIBLE);

        txtGetReady.setText("GET READY");
        new CountDownTimer(6000,1000){

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {

            }
        }
    }

    private void setExerciseInformation(int id) {
        ex_image.setImageResource(list.get(id).getImage_id());
        ex_name.setText(list.get(id).getName());
        btnStart.setText("Start");

        ex_image.setVisibility(View.VISIBLE);
        btnStart.setVisibility(View.VISIBLE);
        txtTimer.setVisibility(View.VISIBLE);

        layoutGetReady.setVisibility(View.INVISIBLE);

    }

    private void initData() {

        exerciseList.add(new Exercise(R.drawable.logo_log_reg,"Easy Pose"));
        exerciseList.add(new Exercise(R.drawable.logo_log_reg,"Easy Pose"));
        exerciseList.add(new Exercise(R.drawable.logo_log_reg,"Easy Pose"));
        exerciseList.add(new Exercise(R.drawable.logo_log_reg,"Easy Pose"));
        exerciseList.add(new Exercise(R.drawable.logo_log_reg,"Easy Pose"));
        exerciseList.add(new Exercise(R.drawable.logo_log_reg,"Easy Pose"));
        exerciseList.add(new Exercise(R.drawable.logo_log_reg,"Easy Pose"));
        exerciseList.add(new Exercise(R.drawable.logo_log_reg,"Easy Pose"));
    }
}
