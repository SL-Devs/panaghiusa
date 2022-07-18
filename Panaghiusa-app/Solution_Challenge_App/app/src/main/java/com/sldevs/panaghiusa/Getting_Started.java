package com.sldevs.panaghiusa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.github.appintro.AppIntro;
import com.github.appintro.AppIntro2;
import com.github.appintro.AppIntroFragment;
import com.github.appintro.AppIntroPageTransformerType;

public class Getting_Started extends AppIntro {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(AppIntroFragment.createInstance(
                "Unity",
                "The app aims to build Unity to the entire world in terms of fighting against the issue of Plastic and Organic Type of waste.",
                R.drawable.unity,
                R.color.white,
                R.color.white,
                R.color.white,
                R.font.bbold,
                R.font.light,
                R.drawable.gradient_1

        ));
        addSlide(AppIntroFragment.createInstance(
                "Help",
                "It also give some help to those people who are really driven upon contributing their plastic and organic type of waste. Upon giving them some helpful and interesting incentives.",
                R.drawable.help,
                R.color.white,
                R.color.white,
                R.color.white,
                R.font.bbold,
                R.font.light,
                R.drawable.gradient_1

        ));
        addSlide(AppIntroFragment.createInstance(
                "Reduce, Reuse and Recycle",
                "\"Panaghiusa\" is a step upon changing the world by starting reducing the plastic and organic type of waste and reuse them to create some wonderful and useful outputs to the environment.",
                R.drawable.reduce,
                R.color.white,
                R.color.white,
                R.color.white,
                R.font.bbold,
                R.font.light,
                R.drawable.gradient_1

        ));

        setIndicatorColor(getResources().getColor(R.color.green),getResources().getColor(R.color.black));
        setTransformer(AppIntroPageTransformerType.Fade.INSTANCE);
        setImmersive(true);
        isColorTransitionsEnabled();
//        askForPermissions(
//                permissions = arrayOf(
//                        Manifest.permission.ACCESS_FINE_LOCATION,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE
//                ),
//                slideNumber = 3,
//                required = false)
    }
    @Override
    protected void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        Intent mainIntent = new Intent(Getting_Started.this,Sign_Up.class);
        startActivity(mainIntent);
        finish();
    }

    @Override
    protected void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        Intent mainIntent = new Intent(Getting_Started.this,Sign_Up.class);
        startActivity(mainIntent);
        finish();

    }

    @Override
    protected void onStart() {
        super.onStart();
        View decorView = getWindow().getDecorView();

        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);

    }
}