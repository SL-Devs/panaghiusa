package com.sldevs.panaghiusa_courierapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class PU_Update_Organic extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    FirebaseListAdapter adaptor;

    ListView lvTBC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pu_update_organic);

        lvTBC = findViewById(R.id.lvTBC);

        String id = FirebaseAuth.getInstance().getUid();
        Query query = FirebaseDatabase.getInstance().getReference().child("TBP_OrganicAll");
        FirebaseListOptions<TBP_Items> o = new FirebaseListOptions.Builder<TBP_Items>()
                .setLayout(R.layout.c_items)
                .setQuery(query, TBP_Items.class)
                .build();
        adaptor = new FirebaseListAdapter(o) {
            @Override
            protected void populateView(@NonNull View v, @NonNull Object model, int position) {
                TextView tvID = v.findViewById(R.id.tvID);
                TextView tvContributions = v.findViewById(R.id.tvContributions);
                TextView tvName = v.findViewById(R.id.tvName);
                TextView tvAddress = v.findViewById(R.id.tvAddress);
                TextView tvNumber = v.findViewById(R.id.tvNumber);
                TextView tvDate = v.findViewById(R.id.tvDateTime);
                String date_time;


                TBP_Items tbp_items = (TBP_Items) model;
                tvID.setText(tbp_items.getContributionid());
                tvContributions.setText(tbp_items.getContribution());
                String userId = tbp_items.getUserId();
                tvName.setText(tbp_items.getFullname());
                tvAddress.setText(tbp_items.getAddress());
                tvNumber.setText(tbp_items.getNumber());
                date_time = tbp_items.getDate() + ", " + tbp_items.getTime();
                tvDate.setText(date_time);

                lvTBC.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        TextView tvID = view.findViewById(R.id.tvID);
                        TextView tvContributions = view.findViewById(R.id.tvContributions);
                        TextView tvName = view.findViewById(R.id.tvName);
                        TextView tvAddress = view.findViewById(R.id.tvAddress);
                        TextView tvNumber = view.findViewById(R.id.tvNumber);
                        TextView tvDate = view.findViewById(R.id.tvDateTime);

                        String id = tvID.getText().toString();
                        String contributions = tvContributions.getText().toString();
                        String fullname = tvName.getText().toString();
                        String address = tvAddress.getText().toString();
                        String number = tvNumber.getText().toString();
                        String date = tvDate.getText().toString();

                        Intent intent = new Intent(PU_Update_Organic.this, PickUp_Update_Organic.class);
                        intent.putExtra("type","Organic");
                        intent.putExtra("id",id);
                        intent.putExtra("contributions", contributions);
                        intent.putExtra("userId", userId);
                        intent.putExtra("fullname",fullname);
                        intent.putExtra("address", address);
                        intent.putExtra("number",number);
                        intent.putExtra("date",date);
                        startActivity(intent);

                        Toast.makeText(PU_Update_Organic.this,fullname,Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };

        lvTBC.setAdapter(adaptor);

    }
    @Override
    public void onStart() {
        super.onStart();
        adaptor.startListening();

    }

    @Override
    public void onStop() {
        super.onStop();
        adaptor.stopListening();
    }
}