package com.example.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class signup extends AppCompatActivity {

    TextInputEditText usernameRegisterText, passwordRegisterText;
    Button ButtonRegister;
    TextView TextViewLogin;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        usernameRegisterText = findViewById(R.id.username);
        passwordRegisterText = findViewById(R.id.password);
        ButtonRegister = findViewById(R.id.buttonSignUp);
        TextViewLogin = findViewById(R.id.loginText);
        progressBar=findViewById(R.id.progress);
        TextViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), login.class);
                startActivity(intent);
                finish();
            }
        });
        ButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username, password;
                username = String.valueOf(usernameRegisterText.getText().toString());
                password = String.valueOf(passwordRegisterText.getText().toString());
                if (!username.equals("") && !password.equals("")) {
                    progressBar.setVisibility(View.VISIBLE);
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[2];
                            field[0] = "username";
                            field[1] = "password";
                            //Creating array for data
                            String[] data = new String[2];
                            data[0] = username;
                            data[1] = password;
                            PutData putData = new PutData("http://192.168.50.36/LoginRegister/signup.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    progressBar.setVisibility(View.GONE);
                                    String result = putData.getResult();
                                    if (result.equals("Sign Up Success")) {
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), login.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                            //End Write and Read data with URL
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "All fields are required", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}