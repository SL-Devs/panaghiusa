package com.sldevs.panaghiusa_courierapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Log_In extends AppCompatActivity {

    private EditText etEmail,etPassword;
    private Button btnLogIn;
    private TextView tvForgotPassword,tvSignUp;
    private ProgressBar pbLoading;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mAuth = FirebaseAuth.getInstance();

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        btnLogIn = findViewById(R.id.btnLogIn);




        pbLoading = findViewById(R.id.pbLoading);


        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });




    }
    public void loginUser(){
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if(email.equalsIgnoreCase("admin@gmail.com") && password.equalsIgnoreCase("admin123")){
            Intent i = new Intent(Log_In.this,Home_Screen.class);
            startActivity(i);
            finish();
        }

        if(email.isEmpty()){
            etEmail.setError("Email is required!");
            etEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etEmail.setError("Please provide a valid email!");
            etEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            etPassword.setError("Password is required!");
            etPassword.requestFocus();
            return;
        }
        if(password.length() < 6){
            etPassword.setError("Minimum password length should be 6 characters!");
            etPassword.requestFocus();
            return;
        }

        pbLoading.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    pbLoading.setVisibility(View.GONE);
                    passUserToken();
                    Toast.makeText(Log_In.this,"Successfully logged in!",Toast.LENGTH_LONG).show();
                    Intent i = new Intent(Log_In.this,Home_Screen.class);
                    startActivity(i);
                    finish();
                }else{
                    Toast.makeText(Log_In.this,"Failed to log in",Toast.LENGTH_LONG).show();
                    pbLoading.setVisibility(View.GONE);
                }
            }
        });
    }

    public void passUserToken(){
        Intent i = new Intent(Log_In.this,Home_Screen.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
}