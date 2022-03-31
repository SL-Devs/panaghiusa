package com.sldevs.panaghiusa;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sldevs.panaghiusa.BottomNavFragments.Contribute;
import com.sldevs.panaghiusa.BottomNavFragments.Guide;
import com.sldevs.panaghiusa.BottomNavFragments.Home;
import com.sldevs.panaghiusa.BottomNavFragments.Points_System;
import com.sldevs.panaghiusa.BottomNavFragments.Profile;


public class Home_Screen extends FragmentActivity {

    BottomNavigationView bottomNavigationView;
    FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setBackground(null);

        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame,new Home()).commit();

        floatingActionButton = findViewById(R.id.contribute);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame,new Contribute()).commit();
            }
        });

    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()){
                        case R.id.home:
                            selectedFragment = new Home();
                            break;
                        case R.id.guide:
                            selectedFragment = new Guide();
                            break;
                        case R.id.contribute:
                            selectedFragment = new Contribute();
                            break;
                        case R.id.holder:
                            selectedFragment = new Contribute();
                            break;
                        case R.id.points:
                            selectedFragment = new Points_System();
                            break;
                        case R.id.profile:
                            selectedFragment = new Profile();
                            break;

                    }
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame,selectedFragment).commit();
                    return true;
                }
            };

    @Override
    protected void onStart() {
        super.onStart();
        View decorView = getWindow().getDecorView();

        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);

    }
}