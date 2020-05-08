package com.example.cofit19;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cofit19.Utils.Commons;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.w3c.dom.Text;

public class ViewExercise extends AppCompatActivity {

    int image_id;
    String name;

    TextView timer, title,type;
    ImageView detail_image;
    Button btnstart_ex;
    ExerciseDB exerciseDB;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore ;
    String userID ;

    boolean isRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_exercise);

        timer = (TextView) findViewById(R.id.timer);
        title = (TextView) findViewById(R.id.title);
        type = (TextView) findViewById(R.id.type);
        detail_image = (ImageView) findViewById(R.id.detail_image);
        btnstart_ex = (Button)findViewById(R.id.btnStart_ex);

        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        FirebaseFirestore fStore = FirebaseFirestore.getInstance();
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


        btnstart_ex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isRunning){

                    btnstart_ex.setText("Done");

                    int timeLimit = 0;
                    if(type.getText().toString().equals("Pemula"))
                        timeLimit = Commons.TIME_LIMIT_EASY;
                    if(type.getText().toString().equals("Menengah"))
                        timeLimit = Commons.TIME_LIMIT_MEDIUM;
                    if(type.getText().toString().equals("Sulit"))
                        timeLimit = Commons.TIME_LIMIT_HARD;


                    new CountDownTimer(timeLimit,1000){
                        @Override
                        public void onTick(long millisUntilFinished) {
                            timer.setText(""+millisUntilFinished/1000);
                        }

                        @Override
                        public void onFinish() {
                            //You Can puts ads here
                            Toast.makeText(ViewExercise.this,"Finish",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }.start();
                }else{
                    //You Can puts ads here
                    Toast.makeText(ViewExercise.this,"Finish",Toast.LENGTH_SHORT).show();
                    finish();
                }

                isRunning = !isRunning;
            }
        });

        timer.setText("");

        if(getIntent() != null){
            image_id = getIntent().getIntExtra("image_id",-1);
            name = getIntent().getStringExtra("name");

            detail_image.setImageResource(image_id);
            title.setText(name);
        }
    }
}
