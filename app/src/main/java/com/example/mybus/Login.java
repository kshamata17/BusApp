package com.example.mybus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    Button callSignUp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

         callSignUp = findViewById(R.id.signup_btn);


         callSignUp.setOnClickListener(new OnClickListener() {
             @Override

                 public void onClick(View v) {
                 Intent intent = new Intent(Login.this, SignUp.class);
                 startActivity(intent);

             }
         });



    }
}


