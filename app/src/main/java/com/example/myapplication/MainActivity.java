package com.example.myapplication;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {

    // define values
    private Button btnLogin;
    private EditText etUsername;
    private EditText etPassword;
    private Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize class values
        btnLogin = findViewById(R.id.btnLogin);
        etPassword = findViewById(R.id.etPassword);
        etUsername = findViewById(R.id.etUsername);
        btnSignUp = findViewById(R.id.btnSignUp);

        // if login button pressed try to login with values entered
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();
                login(username,password);
            }
        });

        // if sign up button pressed switch to sign up activity
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Intent i = new Intent(MainActivity.this,SignUpActivity.class);
                startActivity(i);
            }
        });

        persistUser();

        startColorAnimation();
    }



    private void startColorAnimation()
    {
        // get reference to background to change colors
        ConstraintLayout background = findViewById(R.id.background);

        // run background color animation
        AnimationDrawable animationDrawable = (AnimationDrawable) background.getBackground();
        animationDrawable.setEnterFadeDuration(5000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();
    }

    private void persistUser()
    {
        // if there is already a user logged in go to home page
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            startHomePage();
        }
    }

    private void startHomePage()
    {
        // start homepage activity
        final Intent i = new Intent(MainActivity.this,HomeActivity.class);
        startActivity(i);
        finish();
    }

    private void login(String username, String password)
    {
        // try to login in background
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e == null) {
                    startHomePage();
                } else {
                    e.printStackTrace();
                }
            }
        });
    }
}
