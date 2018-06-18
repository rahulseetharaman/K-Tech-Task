package com.example.hp.k_tech_task;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ScoreCardActivity extends AppCompatActivity {

    private TextView scoreView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_card);
        scoreView=(TextView)findViewById(R.id.scoreView);
        Bundle b=getIntent().getExtras();
        String score=b.getString("Score");
        scoreView.setText("Your score is "+score);
    }
}
