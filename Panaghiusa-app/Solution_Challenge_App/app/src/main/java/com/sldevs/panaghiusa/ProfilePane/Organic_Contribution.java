package com.sldevs.panaghiusa.ProfilePane;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.sldevs.panaghiusa.Home_Screen;
import com.sldevs.panaghiusa.R;

public class Organic_Contribution extends FragmentActivity {

    TextView tvTBC,tvTBP,tvC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organic_contribution);

        tvTBC = findViewById(R.id.btnTBC);
        tvTBP = findViewById(R.id.btnTBP);
        tvC = findViewById(R.id.btnC);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contributionFrame,new TBC_Organic()).commit();

        tvTBC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contributionFrame,new TBC_Organic()).commit();
            }
        });

        tvTBP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contributionFrame,new TBP_Organic()).commit();
            }
        });

        tvC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contributionFrame,new C_Organic()).commit();
            }
        });





    }
    @Override
    public void onBackPressed() {

        Intent i = new Intent(Organic_Contribution.this, Home_Screen.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }
}