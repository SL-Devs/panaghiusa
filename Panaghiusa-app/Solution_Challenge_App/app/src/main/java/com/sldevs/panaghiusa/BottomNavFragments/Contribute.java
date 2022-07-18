package com.sldevs.panaghiusa.BottomNavFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.sldevs.panaghiusa.ContributionSteps_Organic.O_S1;
import com.sldevs.panaghiusa.ContributionSteps_Pane;
import com.sldevs.panaghiusa.ContributionSteps_Plastic.P_S1;
import com.sldevs.panaghiusa.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Contribute#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Contribute extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FrameLayout flContribution;
    ImageView ivPlastic,ivFood,ivDonate;

    public Contribute() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Contribute.
     */
    // TODO: Rename and change types and number of parameters
    public static Contribute newInstance(String param1, String param2) {
        Contribute fragment = new Contribute();
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
        View view = inflater.inflate(R.layout.fragment_contribute, container, false);
        ivPlastic = view.findViewById(R.id.ivPlastic);
        ivFood = view.findViewById(R.id.ivFood);

        ivPlastic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), P_S1.class);
                startActivity(i);
            }
        });
        ivFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), O_S1.class);
                startActivity(i);
            }
        });
//        flContribution = view.findViewById(R.id.flContribution);




        return view;
    }
}