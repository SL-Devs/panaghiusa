package com.sldevs.panaghiusa.ContributionSteps_Plastic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sldevs.panaghiusa.Cart_Item_List_Adaptor;
import com.sldevs.panaghiusa.Cart_Item_Values;
import com.sldevs.panaghiusa.Cart_Temp_Log;
import com.sldevs.panaghiusa.Home_Screen;
import com.sldevs.panaghiusa.R;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Queue;

public class C extends AppCompatActivity {


    FirebaseDatabase firebaseDatabase;
    FirebaseListAdapter adaptor;

    DatabaseReference databaseReference;

    Intent intent = getIntent();
    int images = 0;
    ArrayList<Bitmap> bitmapArray = new ArrayList<Bitmap>();
    String[] converted;
    ListView cart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c);

        cart = findViewById(R.id.lvCart);
        firebaseDatabase = FirebaseDatabase.getInstance();



        Bundle typePlastic = getIntent().getExtras();
        ArrayList<String> plasticType = typePlastic.getStringArrayList("plasticTypeList");
        converted = plasticType.toArray(new String[0]);
        String id = FirebaseAuth.getInstance().getUid();
        Query query = FirebaseDatabase.getInstance().getReference().child("UsersCartTempLog/" + id);
        FirebaseListOptions<Cart_Temp_Log> o = new FirebaseListOptions.Builder<Cart_Temp_Log>()
                .setLayout(R.layout.cart_item_layout)
                .setQuery(query,Cart_Temp_Log.class)
                .build();
        adaptor = new FirebaseListAdapter(o) {
            @Override
            protected void populateView(@NonNull View v, @NonNull Object model, int position) {
                TextView tvPlasticType = v.findViewById(R.id.tvPlasticType);
                ImageView ivCartItems = v.findViewById(R.id.ivCartItems);



                Cart_Temp_Log cart_temp_log = (Cart_Temp_Log) model;
                tvPlasticType.setText(cart_temp_log.getPlasticType());
                Picasso.with(C.this).load(cart_temp_log.getPlasticImage().toString()).into(ivCartItems);
                cart.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Toast.makeText(C.this,"Position: " + String.valueOf(i),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };

        cart.setAdapter(adaptor);


//        String id = FirebaseAuth.getInstance().getUid();
//        databaseReference = firebaseDatabase.getReference("UsersCartTempLog/" + id);
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                String retrieveImage = snapshot.child("image").getValue(String.class);
//
////                Cart_Item_Values std = (Cart_Item_Values) model;
////                Picasso.with(C_Plastic.this).load()
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getContext(), "Fail to get data.", Toast.LENGTH_SHORT).show();
//            }
//        });
//        for(images = 0; images <= plasticType.size();images++){
//            String id = FirebaseAuth.getInstance().getUid();
//            FirebaseStorage storage = FirebaseStorage.getInstance();
//            StorageReference storageRef = storage.getReference();
//            StorageReference getQR = storageRef.child("UsersCartTempLog/" + id + "/" + id + "cartItem_" + images + ".png");
//
//
//            final long ONE_MEGABYTE = 1024 * 1024;
//            getQR.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
//                @Override
//                public void onSuccess(byte[] bytes) {
//                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//                    bitmapArray.add(images,bitmap);
//
//                }
//            });
//        }


//        Cart_Item_List_Adaptor cart_item_list_adaptor = new Cart_Item_List_Adaptor(getApplicationContext(),converted,bitmapArray);

//        cart.setAdapter(cart_item_list_adaptor);

    }

    @Override
    protected void onStart() {
        super.onStart();
        adaptor.startListening();

    }

    @Override
    protected void onStop() {
        super.onStop();
        adaptor.stopListening();
    }
}