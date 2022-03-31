package com.sldevs.panaghiusa.Point_SystemPane;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.sldevs.panaghiusa.ProfilePane.TBC_Organic;
import com.sldevs.panaghiusa.R;

public class Redeemable extends FragmentActivity {
    Button btnFoods,btnELoad,btnOthers;
    ImageView ivBackRedeem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redeemable);

        btnFoods = findViewById(R.id.btnFoods);
        btnELoad = findViewById(R.id.btnELoad);
        btnOthers = findViewById(R.id.btnOthers);

        ivBackRedeem = findViewById(R.id.ivBackRedeem);

        ivBackRedeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contributionFrame,new Redeemable_Food()).commit();


        btnFoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contributionFrame,new Redeemable_Food()).commit();
            }
        });

        btnELoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contributionFrame,new Redeemable_ELoad()).commit();
            }
        });

        btnOthers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contributionFrame,new Redeemable_Others()).commit();
            }
        });

    }
}