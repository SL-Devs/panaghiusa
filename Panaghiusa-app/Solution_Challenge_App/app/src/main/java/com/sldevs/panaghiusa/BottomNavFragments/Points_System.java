package com.sldevs.panaghiusa.BottomNavFragments;



import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
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
import com.sldevs.panaghiusa.Point_System;
import com.sldevs.panaghiusa.Point_SystemPane.Nearest_Branch;
import com.sldevs.panaghiusa.Point_SystemPane.Redeemable;
import com.sldevs.panaghiusa.R;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Points_System#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Points_System extends Fragment {

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
    String uid;
    Button btnShowQR,btnBranch,btnRedeem;
    DialogFragment showQR;
    ImageView imageViewClose,ivQR;
    TextView tvPoints;
    public Points_System() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Points_System.
     */
    // TODO: Rename and change types and number of parameters
    public static Points_System newInstance(String param1, String param2) {
        Points_System fragment = new Points_System();
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
        View view = inflater.inflate(R.layout.fragment_points__system, container, false);

        firebaseDatabase = FirebaseDatabase.getInstance();
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference = firebaseDatabase.getReference("Users/" + uid);
        btnBranch = view.findViewById(R.id.btnBranch);
        btnRedeem = view.findViewById(R.id.btnRedeem);
        tvPoints = view.findViewById(R.id.tvPoints);
        btnShowQR = view.findViewById(R.id.btnShowQR);

        getData();

        btnBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), Nearest_Branch.class);
                startActivity(i);
            }
        });

        btnRedeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), Redeemable.class);
                startActivity(i);
            }
        });

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
        return view;
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
    private void getData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String points = snapshot.child("points").getValue(String.class);


                tvPoints.setText(points + " pts");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}