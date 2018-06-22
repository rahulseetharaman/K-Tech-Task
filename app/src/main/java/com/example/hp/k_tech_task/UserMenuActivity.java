package com.example.hp.k_tech_task;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class UserMenuActivity extends AppCompatActivity {

    Spinner levelSpinner;
    Button quizBtn;
    Button leaderbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_menu);
        String tag[]={"EASY","MEDIUM","HARD"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, tag);
        levelSpinner=(Spinner)findViewById(R.id.spinner2);
        levelSpinner.setAdapter(adapter);
        quizBtn=(Button)findViewById(R.id.quizbtn);
        quizBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String level=levelSpinner.getSelectedItem().toString();
                Log.d("MYTAG",level);
                Intent i=new Intent(UserMenuActivity.this,QuizActivity.class);
                i.putExtra("Difficulty",level.toLowerCase());
                startActivity(i);
            }
        });
        leaderbtn=(Button)findViewById(R.id.leaderbtn);
        leaderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserMenuActivity.this,LeaderboardActivity.class));
            }
        });

    }
}
