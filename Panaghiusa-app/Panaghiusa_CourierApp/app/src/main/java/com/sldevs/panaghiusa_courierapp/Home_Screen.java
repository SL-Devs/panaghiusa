package com.sldevs.panaghiusa_courierapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Home_Screen extends AppCompatActivity {

    ImageView btnProfile,btnScanner,btnUpdate,btnLogOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        btnProfile = findViewById(R.id.btnProfile);
        btnScanner = findViewById(R.id.btnScanner);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnLogOut = findViewById(R.id.btnLogOut);


        btnScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home_Screen.this, QRScanner.class);
                startActivity(i);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home_Screen.this,PU_Update_Type.class);
                startActivity(i);

            }
        });

    }
}