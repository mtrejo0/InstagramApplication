package com.example.myapplication;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {

    private Button btnLogin;
    private EditText etUsername;
    private EditText etPassword;
    private Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin = findViewById(R.id.btnLogin);
        etPassword = findViewById(R.id.etPassword);
        etUsername = findViewById(R.id.etUsername);
        btnSignUp = findViewById(R.id.btnSignUp);


        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            startHomePage();
        }


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();

                login(username,password);

            }
        });


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Intent i = new Intent(MainActivity.this,SignUpActivity.class);
                startActivity(i);
            }
        });




        ConstraintLayout background = findViewById(R.id.background);
        // onCreate
        AnimationDrawable animationDrawable = (AnimationDrawable) background.getBackground();
        animationDrawable.setEnterFadeDuration(5000);
        animationDrawable.setExitFadeDuration(2000);
        // onResume
        animationDrawable.start();

        getSupportActionBar();

    }


    private void startHomePage()
    {
        final Intent i = new Intent(MainActivity.this,HomeActivity.class);
        startActivity(i);
        finish();
    }

    private void login(String username, String password)
    {
        Log.d("MainActivity","Login attempted");

        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(e == null)
                {
                    Log.d("LoginActivity","Login successful!");
                    startHomePage();
                }
                else
                {
                    Log.d("LoginActivity","Login failure!");
                    e.printStackTrace();
                }




            }
        });
    }
}
