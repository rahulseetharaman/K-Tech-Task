package com.example.hp.k_tech_task;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Collections;

public class QuizActivity extends AppCompatActivity {

    private static final String JSON_URL="https://opentdb.com/api.php?amount=1&category=9&type=multiple";
    private static final String TAG="MYTAG";
    private TextView questionView;
    private RadioButton radio1,radio2,radio3,radio4;
    private static Integer score=0;
    private static Integer count=0;
    private RadioGroup radioGroup;
    private ProgressBar progressBar;
    private Button nextQuestionBtn;
    private Handler handler;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private int progressStatus=0;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        questionView=(TextView)findViewById(R.id.textView);
        radio1=(RadioButton)findViewById(R.id.radio1);
        radio2=(RadioButton)findViewById(R.id.radio2);
        radio3=(RadioButton)findViewById(R.id.radio3);
        radio4=(RadioButton)findViewById(R.id.radio4);
        nextQuestionBtn=(Button)findViewById(R.id.nextQuestion);
        radioGroup=(RadioGroup)findViewById(R.id.radioGroup);
        radioGroup.setVisibility(View.INVISIBLE);
        questionView.setVisibility(View.INVISIBLE);
        nextQuestionBtn.setVisibility(View.INVISIBLE);
        mAuth=FirebaseAuth.getInstance();
        sharedPreferences=getSharedPreferences("SharedPref",MODE_MULTI_PROCESS);
        handler=new Handler();
        editor= sharedPreferences.edit();
        progressBar=(ProgressBar)findViewById(R.id.progressBar3);
        Log.d(TAG,"Count is"+  String.valueOf(count));
        if(count==0)
            nextQuestion();
        count++;
        nextQuestionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(checkAnswer())
                    score++;
                nextQuestion();
                count++;
            }
        });
        Log.d(TAG,"Count is"+  String.valueOf(count));


    }


    public void nextQuestion()
    {
        if(count==5)
        {
            Log.d(TAG,"Start new Activity");
            Bundle b=new Bundle();
            b.putString("Score",String.valueOf(score));
            if(mAuth.getCurrentUser() !=null)
            {
                String email=mAuth.getCurrentUser().getEmail();
                Toast.makeText(getApplicationContext(),"Signing out "+email, Toast.LENGTH_SHORT).show();
                mAuth.signOut();
            }
            Intent i=new Intent(QuizActivity.this,ScoreCardActivity.class);
            i.putExtras(b);
            startActivity(i);
            return;
        }
        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    while(progressStatus < 100)
                                    {
                                        progressStatus++;
                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                progressBar.setProgress(progressStatus);
                                            }
                                        });
                                    }
                                    try
                                    {
                                        Thread.sleep(200);
                                    }
                                    catch(InterruptedException ie)
                                    {
                                        ie.printStackTrace();
                                    }
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (progressStatus == 100) {
                                                progressBar.setVisibility(View.GONE);
                                                radioGroup.setVisibility(View.VISIBLE);
                                                questionView.setVisibility(View.VISIBLE);
                                                nextQuestionBtn.setVisibility(View.VISIBLE);
                                            }
                                        }
                                    });

                                }
                            }).start();
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray arr=jsonObject.getJSONArray("results");
                            JSONObject question=arr.getJSONObject(0);
                            questionView.setText(Html.fromHtml(question.getString("question")).toString());
                            ArrayList<String>options=new ArrayList<String>();
                            JSONArray allOptions=question.getJSONArray("incorrect_answers");
                            options.add(question.getString("correct_answer"));
                            options.add(Html.fromHtml(allOptions.getString(0)).toString());
                            options.add(Html.fromHtml(allOptions.getString(1)).toString());
                            options.add(Html.fromHtml(allOptions.getString(2)).toString());
                            Collections.shuffle(options);
                            radio1.setText(options.get(0));
                            radio2.setText(options.get(1));
                            radio3.setText(options.get(2));
                            radio4.setText(options.get(3));
                            editor.putString("Correct_Answer",Html.fromHtml(question.getString("question")).toString());
                            editor.commit();


                        } catch (JSONException e) {
                            Log.d(TAG, "Volley Error");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();

                    }
                }

        );

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private boolean checkAnswer()
    {
        Log.d(TAG,String.valueOf(radioGroup.getChildCount()));
        int answerId=radioGroup.getCheckedRadioButtonId();
        RadioButton r = (RadioButton)  radioGroup.findViewById(answerId);
        if(r==null) {
            Log.d(TAG, "Radio Button is null");
            return false;
        }
        String selectedtext = r.getText().toString();
        String answer=sharedPreferences.getString("Correct_Answer","Something");
        Log.d(TAG,answer+" "+selectedtext);
        return selectedtext.equals(answer);
    }
}
