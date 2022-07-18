package com.sldevs.panaghiusa.ContributionSteps_Plastic;

import static androidx.camera.core.CameraX.getContext;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shuhart.stepview.StepView;
import com.sldevs.panaghiusa.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class P_S2 extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String uid;

    public StepView stepView;
    ImageView btnBackS2;
    TextView tvContributions,tvFullnameReport,tvMobileNo, tvAddress, tvLatandLong, btnGetLocation,tvReportID;
    LocationManager locationManager;
    Button btnNextS2,btnPreviousS2,btnShowCart;
    private static final int REQUEST_LOCATION = 1;
    String latitude, longitude;
    Random r;
    int generatedID_1,generatedID_2,generatedID_3,generatedID_4,generatedID_5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ps2);

        ActivityCompat.requestPermissions(this, new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION);

        String plasticURL = getIntent().getStringExtra("plasticURL");
        Bundle typePlastic = getIntent().getExtras();
        ArrayList<String> plasticType = typePlastic.getStringArrayList("plasticTypeList");

        firebaseDatabase = FirebaseDatabase.getInstance();
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference = firebaseDatabase.getReference("Users/" + uid);

        btnGetLocation = findViewById(R.id.btnGetLocation);
        tvContributions= findViewById(R.id.tvContributions);
        tvFullnameReport = findViewById(R.id.tvFullnameReport);
        tvMobileNo = findViewById(R.id.tvMobileNo);
        tvAddress = findViewById(R.id.tvAddress);
        tvLatandLong = findViewById(R.id.tvLatandLong);
        tvReportID = findViewById(R.id.tvReportID);
        btnBackS2 = findViewById(R.id.btnBackS2);
        stepView = findViewById(R.id.step_view);
        btnPreviousS2 = findViewById(R.id.btnPreviousS2);
        btnNextS2 = findViewById(R.id.btnNextS2);
        btnShowCart = findViewById(R.id.btnShowCart);
        generateContributionID();
        getData();



        btnGetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                    onGPS();
                }else{
                    getLocation();
                }
            }
        });

        btnBackS2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnPreviousS2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnShowCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(P_S2.this, C.class);
                i.putStringArrayListExtra("plasticTypeList",plasticType);
                startActivity(i);
            }
        });
        btnNextS2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String items = getIntent().getStringExtra("itemNo");
                Intent i = new Intent(P_S2.this,P_S3.class);
                i.putExtra("contributionID", tvReportID.getText().toString());
                i.putExtra("plasticItems", tvContributions.getText().toString());
                i.putExtra("numberOfItems", items);
                i.putExtra("fullname", tvFullnameReport.getText().toString());
                i.putExtra("number", tvMobileNo.getText().toString());
                i.putExtra("address", tvAddress.getText().toString());
                i.putExtra("latandlong", tvLatandLong.getText().toString());
                startActivity(i);
            }
        });

        stepView.getState()
                .animationType(StepView.ANIMATION_ALL)
                .steps(new ArrayList<String>() {{
                    add("First Step");
                    add("Second Step");
                    add("Third Step");
                }})
                .stepsNumber(3)
                .animationDuration(getResources().getInteger(android.R.integer.config_shortAnimTime))
                .commit();
        stepView.go(1, true);

    }

    private void generateContributionID() {
        generatedID_1 = new Random().nextInt((20 - 10) + 1);
        generatedID_2 = new Random().nextInt((40 - 9) + 2);
        generatedID_3 = new Random().nextInt((60 - 8) + 3);
        generatedID_4 = new Random().nextInt((80 - 7) + 4);
        generatedID_5 = new Random().nextInt((90 - 6) + 5);
        String firstPin = String.valueOf(generatedID_1);
        String firstPin2 = String.valueOf(generatedID_2);
        String firstPin3 = String.valueOf(generatedID_3);
        String firstPin4 = String.valueOf(generatedID_4);
        String firstPin5 = String.valueOf(generatedID_5);
        tvReportID.setText(firstPin + firstPin2 + firstPin3 + firstPin4 + firstPin5);

    }

    private void getLocation() {
        //Check Permissions again

        if (ActivityCompat.checkSelfPermission(P_S2.this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(P_S2.this,

                Manifest.permission.ACCESS_COARSE_LOCATION) !=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }
        else
        {
            Location LocationGps= locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location LocationNetwork=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location LocationPassive=locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            if (LocationGps !=null)
            {
                double lat=LocationGps.getLatitude();
                double longi=LocationGps.getLongitude();

                latitude=String.valueOf(lat);
                longitude=String.valueOf(longi);

                tvLatandLong.setText(latitude + "," + longitude);
            }
            else if (LocationNetwork !=null)
            {
                double lat=LocationNetwork.getLatitude();
                double longi=LocationNetwork.getLongitude();

                latitude=String.valueOf(lat);
                longitude=String.valueOf(longi);

                tvLatandLong.setText(latitude + "," + longitude);
            }
            else if (LocationPassive !=null)
            {
                double lat=LocationPassive.getLatitude();
                double longi=LocationPassive.getLongitude();

                latitude=String.valueOf(lat);
                longitude=String.valueOf(longi);

                tvLatandLong.setText(latitude + "," + longitude);
            }
            else
            {
                Toast.makeText(this, "Can't Get Your Location", Toast.LENGTH_SHORT).show();
            }


        }

    }

    private void onGPS() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

   public void getData(){
       databaseReference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {

               String fullname = snapshot.child("fullname").getValue(String.class);
               String email = snapshot.child("email").getValue(String.class);
               String number = snapshot.child("number").getValue(String.class);
               String city = snapshot.child("city").getValue(String.class);
               String barangay = snapshot.child("barangay").getValue(String.class);
               String fullAddress = barangay + ", " + city;
               String items = getIntent().getStringExtra("items");


               tvContributions.setText(items);
               tvFullnameReport.setText(fullname);
               tvMobileNo.setText(number);
               tvAddress.setText(fullAddress);


           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {
               Toast.makeText(P_S2.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
           }
       });
   }

}