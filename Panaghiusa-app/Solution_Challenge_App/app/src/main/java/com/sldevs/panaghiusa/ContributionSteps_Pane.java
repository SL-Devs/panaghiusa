package com.sldevs.panaghiusa;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.widget.Button;

import com.shuhart.stepview.StepView;

public class ContributionSteps_Pane extends FragmentActivity {

    public StepView stepView;
    Button btnNextStep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contribution_steps_pane);

        stepView = findViewById(R.id.step_view);
        stepView.getState()
                .animationType(StepView.ANIMATION_ALL)
                .stepsNumber(4)
                .animationDuration(getResources().getInteger(android.R.integer.config_shortAnimTime))
                .commit();

//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.steps_frame,new S1()).commit();

    }

    public void nextStep() {
        stepView.go(1,true);

    }
}