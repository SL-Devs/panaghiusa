package com.sldevs.panaghiusa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import com.google.zxing.WriterException;
import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class Sign_Up extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private EditText etFullName, etEmail, etMobileNo, etPassword;
    private TextView tvLogIn;
    private Button btnSignUp;
    private ProgressBar pbLoading;
    private Spinner city,barangay;
    Bitmap bitmap;
    QRGEncoder qrgEncoder;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    UploadTask uploadTask;

    String selectedCity,selectedBarangay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        etFullName = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.etEmail);
        etMobileNo = findViewById(R.id.etMobileNo);
        etPassword = findViewById(R.id.etPassword);

        tvLogIn = findViewById(R.id.tvLogIn);
        btnSignUp = findViewById(R.id.btnCreateAccount);

        pbLoading = findViewById(R.id.pbLoading);


        city = findViewById(R.id.sCity);
        barangay = findViewById(R.id.sBarangay);

        String[] cityList = getResources().getStringArray(R.array.initialCities);
        Arrays.sort(cityList);
        ArrayAdapter<String> cityAdap = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,cityList);
//        ArrayAdapter<CharSequence> cityAdapter = ArrayAdapter.createFromResource().createFromResource(Sign_Up.this, cityList, android.R.layout.simple_spinner_item);
        cityAdap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        city.setAdapter(cityAdap);
        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String city = adapterView.getItemAtPosition(i).toString();
                selectedCity = city;
                cityDetect(city);


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

        tvLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Sign_Up.this, Log_In.class);
                startActivity(i);
                finish();
            }
        });

    }
    public void registerUser(){
        String fullname = etFullName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String number = etMobileNo.getText().toString().trim();
        String city1 = selectedCity;
        String password = etPassword.getText().toString().trim();

        if(fullname.isEmpty()){
            etFullName.setError("Full name is required!");
            etFullName.requestFocus();
            return;
        }
        if(email.isEmpty()){
            etEmail.setError("Email is required!");
            etEmail.requestFocus();
            return;
        }
        if(number.isEmpty()){
            etMobileNo.setError("Mobile Number is required!");
            etMobileNo.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etEmail.setError("Please provide a valid email!");
            etEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            etPassword.setError("Password is required!");
            etPassword.requestFocus();
            return;
        }
        if(password.length() < 6){
            etPassword.setError("Minimum password length should be 6 characters!");
            etPassword.requestFocus();
            return;
        }

        pbLoading.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                            String currentTime = new SimpleDateFormat("hh:mm:ss aa", Locale.getDefault()).format(new Date());
                            String id = FirebaseAuth.getInstance().getUid();

                            int timeIdentifier = Integer.parseInt(currentTime.substring(0,2));
//                            if(timeIdentifier >= 12){
//                                currentTime = currentTime + " PM";
//                            }else{
//                                currentTime = currentTime + " AM";
//                            }

                            User user = new User(id,"https://firebasestorage.googleapis.com/v0/b/panaghiusa-28480.appspot.com/o/ProfilePicture%2F" + id+"user_profile.png?alt=media&token=7f2781fc-0fd8-425b-b766-9d03bad7595d",fullname,email,number,selectedCity,selectedBarangay,password,"0",currentDate,currentTime);
                            Contribution_Plastic contribution_plastic = new Contribution_Plastic(id,"0");
                            Contribution_Organic contribution_organic = new Contribution_Organic(id,"0");
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        FirebaseDatabase.getInstance().getReference("UsersPlasticContributions").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(contribution_plastic);
                                        FirebaseDatabase.getInstance().getReference("UsersOrganicContributions").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(contribution_organic);
                                        generateQR();
                                        defaultProfile();
                                        Toast.makeText(Sign_Up.this,"You created an account successfully",Toast.LENGTH_LONG).show();
                                        passUserToken();
                                        pbLoading.setVisibility(View.GONE);
                                    }else{
                                        Toast.makeText(Sign_Up.this,"Failed to register",Toast.LENGTH_LONG).show();
                                        pbLoading.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(Sign_Up.this,task.getException().toString(),Toast.LENGTH_LONG).show();
                            pbLoading.setVisibility(View.GONE);
                        }
                    }
                });

    }
    public void defaultProfile(){
        String id = FirebaseAuth.getInstance().getUid();
        StorageReference profilePic = storageReference.child(id + ".png");
        StorageReference profilePicRef = storageReference.child("ProfilePicture/" + id + "user_profile.png");
        profilePic.getName().equals(profilePicRef.getName());
        profilePic.getPath().equals(profilePicRef.getPath());


        Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(),
                R.drawable.avatar_img);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask upload = profilePicRef.putBytes(data);
        upload.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Sign_Up.this,e.toString(),Toast.LENGTH_LONG).show();
            }
        });

    }
    public void passUserToken(){
        Intent i = new Intent(Sign_Up.this,Home_Screen.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
    public void generateQR(){
        String id = FirebaseAuth.getInstance().getUid();
        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);

        // initializing a variable for default display.
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);

        // getting width and
        // height of a point
        int width = point.x;
        int height = point.y;

        // generating dimension from width and height.
        int dimen = width < height ? width : height;
        dimen = dimen * 3 / 4;

        // setting this dimensions inside our qr code
        // encoder to generate our qr code.
        qrgEncoder = new QRGEncoder("User ID: " + id + "\nEmail: " + etEmail.getText().toString(), null, QRGContents.Type.TEXT, dimen);
        try {
            StorageReference qrCode = storageReference.child(id + ".png");
            StorageReference qrCodeRef = storageReference.child("QRCodes/" + id + "user_qr.png");
            qrCode.getName().equals(qrCodeRef.getName());
            qrCode.getPath().equals(qrCodeRef.getPath());
            // getting our qrcode in the form of bitmap.
            bitmap = qrgEncoder.encodeAsBitmap();
            // the bitmap is set inside our image
            // view using .setimagebitmap method.
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] data = baos.toByteArray();

            UploadTask upload = qrCodeRef.putBytes(data);
            upload.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Sign_Up.this,e.toString(),Toast.LENGTH_LONG).show();
                }
            });
        } catch (WriterException e) {
            Log.e("Tag", e.toString());
        }
    }
    public String cityDetect(String city){
        if(city.equalsIgnoreCase("City")){
            ArrayAdapter<CharSequence> barangayAdapter = ArrayAdapter.createFromResource(Sign_Up.this,R.array.empty, android.R.layout.simple_spinner_item);
            barangayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            barangay.setAdapter(barangayAdapter);
            barangay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }else if(city.equalsIgnoreCase("Cagayan de Oro")){
            ArrayAdapter<CharSequence> barangayAdapter = ArrayAdapter.createFromResource(Sign_Up.this,R.array.cdo, android.R.layout.simple_spinner_item);
            barangayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            barangay.setAdapter(barangayAdapter);
            barangay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    String barangay = adapterView.getItemAtPosition(i).toString();
                    selectedBarangay = barangay;
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }else{
            ArrayAdapter<CharSequence> barangayAdapter = ArrayAdapter.createFromResource(Sign_Up.this,R.array.emptyCity, android.R.layout.simple_spinner_item);
            barangayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            barangay.setAdapter(barangayAdapter);
            barangay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
        return city;
    }

}