package com.sldevs.panaghiusa_courierapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

public class PickUp_Update_Plastic extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseStorage storage;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private StorageReference storageReference;

    TextView tvID,tvUserId,tvItems,tvName,tvAddress,tvNumber,tvFormula,tvResult,tvPlasticContribution;
    Button btnUpload,btnAdd;
    EditText etKilo;
    Spinner sType;
    String selectedPlasticType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_up_update);


        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        tvID = findViewById(R.id.tvContributionID);
        tvUserId = findViewById(R.id.tvUserID);
        tvName = findViewById(R.id.tvName);
        tvAddress = findViewById(R.id.tvAddress);
        tvNumber = findViewById(R.id.tvNumber);
        tvItems = findViewById(R.id.tvItems);
        tvFormula = findViewById(R.id.tvFormula);
        tvResult = findViewById(R.id.tvResult);
        tvPlasticContribution = findViewById(R.id.tvPlasticContribution);



        btnAdd = findViewById(R.id.btnAdd);
        btnUpload = findViewById(R.id.btnUpload);
        etKilo = findViewById(R.id.etKilo);

        sType = findViewById(R.id.sType);

        ArrayAdapter<CharSequence> plasticType = ArrayAdapter.createFromResource(PickUp_Update_Plastic.this,R.array.plasticTypes, android.R.layout.simple_spinner_item);
        plasticType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sType.setAdapter(plasticType);

        String id = getIntent().getStringExtra("id");
        String contributions = getIntent().getStringExtra("contributions");
        String userId = getIntent().getStringExtra("userId");
        String fullname = getIntent().getStringExtra("fullname");
        String addrees = getIntent().getStringExtra("address");
        String number = getIntent().getStringExtra("number");

        tvID.setText(id);
        tvItems.setText(contributions);
        tvUserId.setText(userId);
        tvName.setText(fullname);
        tvAddress.setText(addrees);
        tvNumber.setText(number);
        getData();

        String[] plasticList = getResources().getStringArray(R.array.plasticTypes);
        Arrays.sort(plasticList);

        ArrayAdapter<String> plasticlistAdaptor = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,plasticList);
        plasticlistAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sType.setAdapter(plasticlistAdaptor);

        sType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String plastic = adapterView.getItemAtPosition(i).toString();
                selectedPlasticType = plastic;
                plasticFormula(plastic);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        etKilo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if(!charSequence.equals("")){
                        if(selectedPlasticType.equalsIgnoreCase("Plastic Bottle"))
                        {
                            if(!etKilo.getText().toString().equals("")){
                                double kiloAmount = Double.parseDouble(etKilo.getText().toString());
                                calculateKilo("Plastic Bottle", kiloAmount);
                            }else{
                                tvResult.setText("0.0 pt");
                            }
                        }
                    }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PickUp_Update_Plastic.this, QRScanner.class);
                startActivity(i);
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uid = tvUserId.getText().toString().trim();
                String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                String currentTime = new SimpleDateFormat("hh:mm:ss aa", Locale.getDefault()).format(new Date());

                ContributionUpdate_Data contributionUpdate_data = new ContributionUpdate_Data(id,contributions,uid,fullname,addrees,number,currentDate,currentTime);

                FirebaseDatabase.getInstance().getReference("C_PlasticSpecific").child(uid + "/"+ id).setValue(contributionUpdate_data).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            FirebaseDatabase.getInstance().getReference("C_PlasticAll").child(id).setValue(contributionUpdate_data);
                            updateUser();
                            FirebaseDatabase.getInstance().getReference("TBP_PlasticAll").child(id).removeValue();
                            FirebaseDatabase.getInstance().getReference("TBP_PlasticSpecific").child(uid).removeValue();
                            Toast.makeText(PickUp_Update_Plastic.this,"Successfully Updated!",Toast.LENGTH_LONG).show();
                        }else{

                        }
                    }
                });
            }
        });


    }

    public void plasticFormula(String plasticType){
        if(plasticType.equalsIgnoreCase("Plastic Bottle")){
            tvFormula.setText("Note: Plastic Bottle = 1pt/Kilo");
        }else if(plasticType.equalsIgnoreCase("CD")){
            tvFormula.setText("Note: Plastic Bottle = 1.5pt/Kilo");
        }else if(plasticType.equalsIgnoreCase("Perfume Bottle")){
            tvFormula.setText("Note: Plastic Bottle = 1.5pt/Kilo");
        }
    }

    public void calculateKilo(String plasticType, double kilo){
        double result = kilo * 1;
        if(result == 1.0){
            tvResult.setText(result + " pt");
        }else{
            tvResult.setText(result + " pts");
        }

    }


    public void getData(){
        String uid = tvUserId.getText().toString().trim();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("UsersPlasticContributions/" + uid);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String plasticContribution = snapshot.child("plasticKiloContribution").getValue(String.class);

                tvPlasticContribution.setText(plasticContribution);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PickUp_Update_Plastic.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void updateUser(){
        String uid = tvUserId.getText().toString().trim();
        double currentPoints = Double.parseDouble(tvPlasticContribution.getText().toString());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("UsersPlasticContributions");
        currentPoints = currentPoints + Double.parseDouble(etKilo.getText().toString());
        Contribution_Plastic contribution_plastic = new Contribution_Plastic(tvUserId.getText().toString(),String.valueOf(currentPoints));
        databaseReference.child(uid).setValue(contribution_plastic);


    }
}