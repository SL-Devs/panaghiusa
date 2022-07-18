package com.sldevs.panaghiusa.BottomNavFragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sldevs.panaghiusa.Log_In;
import com.sldevs.panaghiusa.ProfilePane.MyContribution;
import com.sldevs.panaghiusa.ProfilePane.My_Impact;
import com.sldevs.panaghiusa.R;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profile extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FirebaseDatabase firebaseDatabase;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    ImageView profilePicture;
    private TextView tvFullname,tvEmail,tvNumber;
    String uid;
    Button btnShowQR,btnShowProfile,btnStats,btnLogout,btnTrack;
    DialogFragment showQR;
    ImageView imageViewClose,ivQR;
    public Profile() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Profile.
     */
    // TODO: Rename and change types and number of parameters
    public static Profile newInstance(String param1, String param2) {

        Profile fragment = new Profile();
        Bundle args = new Bundle();

        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        tvFullname = view.findViewById(R.id.tvFullName);
        tvEmail = view.findViewById(R.id.tvEmail);
        tvNumber = view.findViewById(R.id.tvNumber);
        profilePicture = view.findViewById(R.id.profilePicture);
        btnLogout = view.findViewById(R.id.btnLogout);
        btnTrack = view.findViewById(R.id.btnTrack);
        btnStats = view.findViewById(R.id.btnStats);

        firebaseDatabase = FirebaseDatabase.getInstance();
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference = firebaseDatabase.getReference("Users/" + uid);

        try {
            loadProfile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        getData();

        btnStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), My_Impact.class);
                startActivity(i);
            }
        });

        btnShowQR = view.findViewById(R.id.btnShowQR);

        btnShowQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View qrcode = getActivity().getLayoutInflater().inflate(R.layout.show_qrcode, new LinearLayout(getActivity()), false);
                showQR= new DialogFragment(R.layout.show_qrcode);
//                imageViewClose = showQRshowQR.findViewById(R.id.ivClose);
//                showQR.setContentView(R.layout.show_qrcode);
//                showQR.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                Dialog builder = new Dialog(getActivity());
                builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
                builder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                builder.setContentView(qrcode);
                builder.show();
                ivQR = qrcode.findViewById(R.id.ivQR);
                imageViewClose = qrcode.findViewById(R.id.ivClose);
                String id = FirebaseAuth.getInstance().getUid();
                try {
                    loadQRCode(id,ivQR);
                } catch (IOException e) {
                    e.printStackTrace();
                }


                imageViewClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        builder.dismiss();
                    }
                });

            }
        });

        btnTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), MyContribution.class);
                startActivity(i);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(getContext(), Log_In.class);
                startActivity(i);

            }
        });



        return view;

    }

    private void getData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String fullname = snapshot.child("fullname").getValue(String.class);
                String email = snapshot.child("email").getValue(String.class);
                String number = snapshot.child("number").getValue(String.class);

                tvFullname.setText(fullname);
                tvEmail.setText(email);
                tvNumber.setText(number);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void loadProfile() throws IOException {
        String id = FirebaseAuth.getInstance().getUid();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference getQR = storageRef.child("ProfilePicture/"+ id+ "user_profile.png");
        final long ONE_MEGABYTE = 1024 * 1024;
        getQR.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                profilePicture.setImageBitmap(bitmap);
            }
        });
    }
    public void loadQRCode(String id,ImageView qrcode) throws IOException {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference getQR = storageRef.child("QRCodes/"+ id+ "user_qr.png");
        final long ONE_MEGABYTE = 1024 * 1024;
        getQR.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                qrcode.setImageBitmap(bitmap);
            }
        });
    }

}