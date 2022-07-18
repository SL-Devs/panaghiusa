package com.sldevs.panaghiusa.ProfilePane;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sldevs.panaghiusa.R;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

public class My_Impact extends AppCompatActivity {
    TextView tvPlastic, tvOrganic;
    PieChart pieChart;
    ImageView ivBtnBack;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth mAuth;
    DatabaseReference databaseReferencePlastic,databaseReferenceOrganic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_impact);

        tvPlastic = findViewById(R.id.tvPlastic);
        tvOrganic = findViewById(R.id.tvOrganic);

        pieChart = findViewById(R.id.piechart);

        ivBtnBack = findViewById(R.id.btnBack);

        ivBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getData();

        String plasticValue = tvPlastic.getText().toString();
        char plasticInteger = plasticValue.charAt(0);
        int p = Character.getNumericValue(plasticInteger);
        String organicValue = tvOrganic.getText().toString();
        char organicInteger = organicValue.charAt(0);
        int o = Character.getNumericValue(organicInteger);
        pieChart.addPieSlice(

                new PieModel(
                        "Plastic",
                        p,
                        Color.parseColor("#FFEB3B")));
        pieChart.addPieSlice(
                new PieModel(
                        "Organic",
                        o,
                        Color.parseColor("#4C9865")));

        pieChart.startAnimation();
    }
    public void getData(){

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferencePlastic = firebaseDatabase.getReference("UsersPlasticContributions/" + uid);
        databaseReferenceOrganic = firebaseDatabase.getReference("UsersOrganicContributions/" + uid);

        databaseReferencePlastic.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String plastic = snapshot.child("plasticKiloContribution").getValue(String.class);
                tvPlastic.setText(plastic);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(My_Impact.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });

        databaseReferenceOrganic.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String organic = snapshot.child("organicKiloContribution").getValue(String.class);
                tvOrganic.setText(organic);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(My_Impact.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}