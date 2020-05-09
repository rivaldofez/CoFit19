package com.example.cofit19;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.cofit19.Model.Exercise;
import com.example.cofit19.Utils.Commons;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class Daily_Training extends AppCompatActivity {

    Button btnStart;
    ImageView ex_image;
    TextView txtGetReady,txtCountDown, txtTimer, ex_name;
    ProgressBar progressBar;
    LinearLayout layoutGetReady;
    TextView type;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore ;
    String userID ;

    int ex_id=0, limit_time=0;
    List<Exercise> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily__training);

        initData();

        type = findViewById(R.id.type);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fStore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                String kater = documentSnapshot.getString("mode");
                if(kater.equals("0")){
                    type.setText("Pemula");
                }else if(kater.equals("1")){
                    type.setText("Menengah");
                }else if(kater.equals("2")){
                    type.setText("Sulit");
                }
            }
        });


        btnStart = (Button) findViewById(R.id.btnStart_ex);

        ex_image = (ImageView) findViewById(R.id.detail_image);
        txtCountDown = (TextView)findViewById(R.id.txtCountDown);
        txtGetReady = (TextView) findViewById(R.id.txtGetReady);
        txtTimer = (TextView) findViewById(R.id.timer);
        ex_name = (TextView) findViewById(R.id.title);

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

                    if(type.getText().toString().equals("Pemula")){
                        exercisesEasyModeCountDown.cancel();
                    }else if(type.getText().toString().equals("Menengah")){
                        exercisesMediumModeCountDown.cancel();
                    }else if(type.getText().toString().equals("Sulit")){
                        exercisesHardModeCountDown.cancel();
                    }
                    restTimeCountDown.cancel();

                    if(ex_id < list.size()){
                        showRestTime();
                        ex_id++;
                        progressBar.setProgress(ex_id);
                        txtTimer.setText("");
                    }else{
                        showFinished();
                    }


                }else{

                    if(type.getText().toString().equals("Pemula")){
                        exercisesEasyModeCountDown.cancel();
                    }else if(type.getText().toString().equals("Menengah")){
                        exercisesMediumModeCountDown.cancel();
                    }else if(type.getText().toString().equals("Sulit")){
                        exercisesHardModeCountDown.cancel();
                    }

                    restTimeCountDown.cancel();

                    if(ex_id<list.size())
                        setExerciseInformation(ex_id);
                    else
                        showFinished();

                }
            }
        });

    }

    private void showRestTime() {
        ex_image.setVisibility(View.INVISIBLE);
        txtTimer.setVisibility(View.INVISIBLE);
        btnStart.setVisibility(View.VISIBLE);
        btnStart.setText("Skip");
        layoutGetReady.setVisibility(View.VISIBLE);

        txtGetReady.setText("REST TIME");
        restTimeCountDown.start();

    }

    private void showGetReady() {
        ex_image.setVisibility(View.INVISIBLE);
        btnStart.setVisibility(View.INVISIBLE);
        txtTimer.setVisibility(View.VISIBLE);

        layoutGetReady.setVisibility(View.VISIBLE);

        txtGetReady.setText("GET READY");
        new CountDownTimer(6000,1000){

            @Override
            public void onTick(long l) {
                txtCountDown.setText(""+(l-1000)/1000);
            }

            @Override
            public void onFinish() {
                showExercises();
            }
        }.start();
    }

    private void showExercises() {
        if (ex_id<list.size()){// ukuran list sama dengan semua exercises
            ex_image.setVisibility(View.VISIBLE);
            btnStart.setVisibility(View.VISIBLE);
            layoutGetReady.setVisibility(View.INVISIBLE);

            if(type.getText().toString().equals("Pemula")){
                exercisesEasyModeCountDown.start();
            }else if(type.getText().toString().equals("Menengah")){
                exercisesMediumModeCountDown.start();
            }else if(type.getText().toString().equals("Sulit")){
                exercisesHardModeCountDown.start();
            }

            //Set Data
            ex_image.setImageResource(list.get(ex_id).getImage_id());
            ex_name.setText(list.get(ex_id).getName());

        }else{
            showFinished();
        }
    }

    private void showFinished() {
        ex_image.setVisibility(View.INVISIBLE);
        btnStart.setVisibility(View.INVISIBLE);
        txtTimer.setVisibility(View.INVISIBLE);
        layoutGetReady.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);

        txtGetReady.setText("Finished");
        txtCountDown.setText("Congratulation !\nYoure Done with today exercise");
        txtCountDown.setTextSize(20);

        //Save Workout done
        saveDay(""+Calendar.getInstance().getTimeInMillis());
    }

    //Countdown
    CountDownTimer exercisesEasyModeCountDown = new CountDownTimer(Commons.TIME_LIMIT_EASY,1000) {
        @Override
        public void onTick(long l) {
            txtTimer.setText(""+(l/1000));
        }

        @Override
        public void onFinish() {
            if(ex_id<list.size()-1){
                ex_id++;
                progressBar.setProgress(ex_id);

                setExerciseInformation(ex_id);
                btnStart.setText("Start");
            }else{
                showFinished();
            }
        }
    };
    CountDownTimer exercisesMediumModeCountDown = new CountDownTimer(Commons.TIME_LIMIT_MEDIUM,1000) {
        @Override
        public void onTick(long l) {
            txtTimer.setText(""+(l/1000));
        }

        @Override
        public void onFinish() {
            if(ex_id<list.size()-1){
                ex_id++;
                progressBar.setProgress(ex_id);

                setExerciseInformation(ex_id);
                btnStart.setText("Start");
            }else{
                showFinished();
            }
        }
    };
    CountDownTimer exercisesHardModeCountDown = new CountDownTimer(Commons.TIME_LIMIT_HARD,1000) {
        @Override
        public void onTick(long l) {
            txtTimer.setText(""+(l/1000));
        }

        @Override
        public void onFinish() {
            if(ex_id<list.size()-1){
                ex_id++;
                progressBar.setProgress(ex_id);

                setExerciseInformation(ex_id);
                btnStart.setText("Start");
            }else{
                showFinished();
            }
        }
    };

    CountDownTimer restTimeCountDown = new CountDownTimer(10000,1000) {
        @Override
        public void onTick(long l) {
            txtCountDown.setText(""+(l/1000));
        }

        @Override
        public void onFinish() {
            setExerciseInformation(ex_id);
            showExercises();
        }
    };

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

        list.add(new Exercise(R.drawable.jumpingjacks,"Jumping Jacks"));
        list.add(new Exercise(R.drawable.tricep_dip,"Tricep Dips"));
        list.add(new Exercise(R.drawable.wall_push,"Wall Push-Ups"));
        list.add(new Exercise(R.drawable.stepuponto,"Step Up Onto Chair"));
        list.add(new Exercise(R.drawable.squats,"Squats"));
        list.add(new Exercise(R.drawable.push_up,"Push Up"));
        list.add(new Exercise(R.drawable.plank,"Plank"));
        list.add(new Exercise(R.drawable.mountain,"Mountain Climber"));
        list.add(new Exercise(R.drawable.bird_dog,"Bird Dog"));
        list.add(new Exercise(R.drawable.bird_dog,"Abdominal Crunches"));
    }

    public void saveDay(String Value){
            FirebaseAuth fAuth = FirebaseAuth.getInstance();
            FirebaseFirestore fStore = FirebaseFirestore.getInstance();
            String userID = fAuth.getCurrentUser().getUid();
            DocumentReference dailyReference = fStore.collection("/users/"+userID+"/exercises").document(Value);

            Map<String,Object> daily = new HashMap<>();
            daily.put("days",Value);
            dailyReference.set(daily);
    }
}
