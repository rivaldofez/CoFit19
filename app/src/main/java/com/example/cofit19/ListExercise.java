package com.example.cofit19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.cofit19.Adapter.RecyclerViewAdapter;
import com.example.cofit19.Model.Exercise;

import java.util.ArrayList;
import java.util.List;

public class ListExercise extends AppCompatActivity {

    List<Exercise> exerciseList = new ArrayList<>();
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_exercise);

        initData();

        recyclerView = (RecyclerView) findViewById(R.id.list_ex);
        adapter = new RecyclerViewAdapter(exerciseList,getBaseContext());
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void initData() {
        exerciseList.add(new Exercise(R.drawable.jumpingjacks,"Jumping Jacks"));
        exerciseList.add(new Exercise(R.drawable.tricep_dip,"Tricep Dips"));
        exerciseList.add(new Exercise(R.drawable.wall_push,"Wall Push-Ups"));
        exerciseList.add(new Exercise(R.drawable.stepuponto,"Step Up Onto Chair"));
        exerciseList.add(new Exercise(R.drawable.squats,"Squats"));
        exerciseList.add(new Exercise(R.drawable.push_up,"Push Up"));
        exerciseList.add(new Exercise(R.drawable.plank,"Plank"));
        exerciseList.add(new Exercise(R.drawable.mountain,"Mountain Climber"));
        exerciseList.add(new Exercise(R.drawable.bird_dog,"Bird Dog"));
        exerciseList.add(new Exercise(R.drawable.bird_dog,"Abdominal Crunches"));
    }
}
