package com.example.myapplication.loginActivities.signUpActivities;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.R;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

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

        // sign up button
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpUser();
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

    public void signUpUser()
    {
        final ParseUser user = new ParseUser();

        user.setEmail(etEmail.getText().toString());
        user.setUsername(etUsernmae.getText().toString());
        user.setPassword(etPassword.getText().toString());

        // sign up user with inputted user pass and email
        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    //login as this user temporarily
                    login(user.getUsername(),etPassword.getText().toString());

                } else {
                    e.printStackTrace();
                }
            }
        });
    }

    private void login(String username, String password)
    {
        // try to login in background
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e == null) {
                    Intent profilePictureIntent = new Intent(SignUpActivity.this, ProfilePictureCaptureActivity.class);
                    startActivity(profilePictureIntent);
                    finish();

                } else {
                    e.printStackTrace();
                }
            }


        });
    }
}
