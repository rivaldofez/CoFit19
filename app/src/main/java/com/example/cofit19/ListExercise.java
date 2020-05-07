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
