package com.example.hp.k_tech_task;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private EditText emailText;
    private EditText passwdText;
    private EditText confirmText;
    private FirebaseAuth mAuth;
    private Button regbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        emailText=(EditText)findViewById(R.id.emailAddrText);
        passwdText=(EditText)findViewById(R.id.passwdText);
        confirmText=(EditText)findViewById(R.id.confirmText);
        regbtn=(Button)findViewById(R.id.registerbtn2);
        mAuth=FirebaseAuth.getInstance();

        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=emailText.getText().toString().trim();
                String password=passwdText.getText().toString().trim();
                String confirmPwd=confirmText.getText().toString().trim();
                registerFunction(email,password,confirmPwd);
            }
        });

    }

    private void registerFunction(String email,String password,String confirmPwd)
    {
        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(getApplicationContext(),"Enter a email address", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password) || password.length() < 6)
        {
            Toast.makeText(getApplicationContext(),"Enter a valid password", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(confirmPwd) || !confirmPwd.equals(password))
        {
            Toast.makeText(getApplicationContext(),"Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(),"Registration Successful", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
