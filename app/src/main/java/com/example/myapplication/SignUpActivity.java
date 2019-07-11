package com.example.myapplication;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignUpActivity extends AppCompatActivity {


    private EditText etEmail;
    private EditText etUsernmae;
    private EditText etPassword;
    private Button btnSignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etUsernmae = findViewById(R.id.etUsername);
        btnSignUp = findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                final String[] userValues = new String[3];
                userValues[0] = etUsernmae.getText().toString();
                userValues[1] = etPassword.getText().toString();
                userValues[2] = etEmail.getText().toString();


                final Intent i = new Intent(SignUpActivity.this,ProfilePictureActivity.class);
                i.putExtra("userValues",userValues);
                startActivity(i);
                finish();


            }
        });

        // create reference to the background
        ConstraintLayout background = findViewById(R.id.background);

        // start animation
        AnimationDrawable animationDrawable = (AnimationDrawable) background.getBackground();
        animationDrawable.setEnterFadeDuration(5000);
        animationDrawable.setExitFadeDuration(2000);

        animationDrawable.start();



    }
}
