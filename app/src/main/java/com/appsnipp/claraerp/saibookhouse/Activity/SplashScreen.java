package com.appsnipp.claraerp.saibookhouse.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.appsnipp.claraerp.saibookhouse.R;
import com.appsnipp.claraerp.saibookhouse.Utils.Sharedpreferences;

public class SplashScreen extends AppCompatActivity {
    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 1000;
    Sharedpreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedpreferences=Sharedpreferences.getInstance(this);
        boolean islogged = sharedpreferences.getlogged();

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                if(!islogged) {
                    Intent mainIntent = new Intent(SplashScreen.this, LoginScreen.class);
                    startActivity(mainIntent);
                    finish();
                }
                else {
                    Intent dashboardIntent = new Intent(SplashScreen.this, MainPageActivity.class);
                    startActivity(dashboardIntent);
                    finish();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);

    }
}