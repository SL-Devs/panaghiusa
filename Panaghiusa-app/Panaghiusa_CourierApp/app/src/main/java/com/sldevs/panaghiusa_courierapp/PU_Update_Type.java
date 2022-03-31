package com.sldevs.panaghiusa_courierapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class PU_Update_Type extends AppCompatActivity {
    ImageView ivPlastic,ivFood;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pu_update_type);

        ivPlastic = findViewById(R.id.ivPlastic);
        ivFood = findViewById(R.id.ivFood);

        ivPlastic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PU_Update_Type.this, PU_Update_Plastic.class);
                startActivity(i);
            }
        });


        ivFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PU_Update_Type.this,PU_Update_Organic.class);
                startActivity(i);
            }
        });

    }
    @Override
    public void onBackPressed() {

        Intent i = new Intent(PU_Update_Type.this, Home_Screen.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }
}