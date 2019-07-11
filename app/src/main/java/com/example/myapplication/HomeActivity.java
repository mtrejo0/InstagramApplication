package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.myapplication.fragments.ComposeFragment;
import com.example.myapplication.fragments.HomeFragment;
import com.parse.ParseUser;

public class HomeActivity extends AppCompatActivity {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        // define manager to decide which fragment to display
        final FragmentManager fragmentManager = getSupportFragmentManager();

        // define bottom navigation bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.btnLock:
                        // log out user
                        ParseUser.logOut();

                        // start log in page activity
                        Intent i = new Intent(HomeActivity.this, MainActivity.class);
                        startActivity(i);

                        // kill current screen
                        finish();
                        break;
                    case R.id.btnCreate:

                        // switch to compose fragment
                        fragment = new ComposeFragment();
                        fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();

                        break;
                    case R.id.btnHome:

                        // switch to home fragment
                        fragment = new HomeFragment();
                        fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();

                        break;

                    default:
                        return true;
                }


                return true;
            }
        });

        // default fragment in home fragment
        bottomNavigationView.setSelectedItemId(R.id.btnHome);


    }

}
