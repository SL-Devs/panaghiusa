package com.sldevs.panaghiusa.ProfilePane;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sldevs.panaghiusa.Home_Screen;
import com.sldevs.panaghiusa.R;

public class MyContribution extends FragmentActivity {

    ImageView ivPlastic,ivFood;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_contribution);


        ivPlastic = findViewById(R.id.ivPlastic);
        ivFood = findViewById(R.id.ivFood);

        ivPlastic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MyContribution.this,Plastic_Contribution.class);
                startActivity(i);
            }
        });


        ivFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MyContribution.this,Organic_Contribution.class);
                startActivity(i);
            }
        });

    }
    @Override
    public void onBackPressed() {

        Intent i = new Intent(MyContribution.this, Home_Screen.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }
}