package com.sldevs.panaghiusa.ContributionSteps_Organic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.shuhart.stepview.StepView;
import com.sldevs.panaghiusa.ContributionSteps_Plastic.P_S3;
import com.sldevs.panaghiusa.Home_Screen;
import com.sldevs.panaghiusa.Organic_Contribution;
import com.sldevs.panaghiusa.Plastic_Contribution;
import com.sldevs.panaghiusa.ProfilePane.MyContribution;
import com.sldevs.panaghiusa.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class O_S3 extends AppCompatActivity {
    public StepView stepView;
    TextView tvTrack,tvLabel;
    Button btnSubmit,btnCancel,btnTrack;
    ImageView btnBackS3;
    ProgressBar pbLoadingContribution;
    private FirebaseAuth mAuth;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_os3);

        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        pbLoadingContribution = findViewById(R.id.pbLoadingContribution);
        tvTrack = findViewById(R.id.tvTrack);
        tvLabel = findViewById(R.id.tvLabel);
        stepView = findViewById(R.id.step_view);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnCancel = findViewById(R.id.btnCancel);
        btnTrack = findViewById(R.id.btnTrack);
        btnBackS3 = findViewById(R.id.btnBackS3);

        stepView.getState()
                .animationType(StepView.ANIMATION_ALL)
                .stepsNumber(3)
                .animationDuration(getResources().getInteger(android.R.integer.config_shortAnimTime))
                .commit();
        stepView.go(2, true);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pbLoadingContribution.setVisibility(View.VISIBLE);
                String contribution = getIntent().getStringExtra("contributions");;
                String days = getIntent().getStringExtra("days");;
                String contrbutionID = getIntent().getStringExtra("contributionID");
                String fullname = getIntent().getStringExtra("fullname");
                String number = getIntent().getStringExtra("number");
                String address = getIntent().getStringExtra("address");
                String latandlong = getIntent().getStringExtra("latandlong");

                String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

                int timeIdentifier = Integer.parseInt(currentTime.substring(0,2));
                if(timeIdentifier >= 12){
                    currentTime = currentTime + " PM";
                }else{
                    currentTime = currentTime + " AM";
                }

                String id = FirebaseAuth.getInstance().getUid();
                Organic_Contribution pc = new Organic_Contribution(contribution,days,contrbutionID,id,fullname,number,address,latandlong,currentDate,currentTime);
                FirebaseDatabase.getInstance().getReference("TBC_OrganicSpecific")
                        .child(id +"/"+contrbutionID)
                        .setValue(pc).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            FirebaseDatabase.getInstance().getReference("TBC_OrganicAll").child(contrbutionID).setValue(pc);
                            tvLabel.setText("Thank you for contributing!");
                            Toast.makeText(O_S3.this,"Thank you for contributing!",Toast.LENGTH_LONG).show();
                            stepView.done(true);
                            pbLoadingContribution.setVisibility(View.GONE);
                            btnBackS3.setVisibility(View.GONE);
                            btnSubmit.setVisibility(View.GONE);
                            btnCancel.setVisibility(View.GONE);
                            tvTrack.setVisibility(View.VISIBLE);
                            btnTrack.setVisibility(View.VISIBLE);

                        }else{
                            Toast.makeText(O_S3.this,"Contribution Failed!",Toast.LENGTH_LONG).show();
                            pbLoadingContribution.setVisibility(View.GONE);
                            Intent i = new Intent(O_S3.this, Home_Screen.class);
                            startActivity(i);
                            finish();
                        }
                    }
                });

            }
        });

        btnTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(O_S3.this, MyContribution.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });

    }
    @Override
    public void onBackPressed() {

        Intent i = new Intent(O_S3.this,Home_Screen.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }
}