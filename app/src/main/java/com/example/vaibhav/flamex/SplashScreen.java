package com.example.vaibhav.flamex;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Rohan on 01-Nov-15.
 */
public class SplashScreen extends Activity {

    // Set Duration of the Splash Screen
    long Delay = 2000;
    private ImageView mImageView;
    private ProgressBar progressBar;
    Animation zoomin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen);
        progressBar=(ProgressBar)findViewById(R.id.ProgressBar);
        mImageView = (ImageView) findViewById(R.id.LogoImage);
        progressBar.getIndeterminateDrawable().setColorFilter(
                getResources().getColor(R.color.DarkOrange),
                android.graphics.PorterDuff.Mode.SRC_IN);

        zoomin = AnimationUtils.loadAnimation(this, R.anim.zoomin);
        mImageView.setAnimation(zoomin);
        mImageView.startAnimation(zoomin);

        // Create a Timer
        Timer RunSplash = new Timer();

        // Task to do when the timer ends
        TimerTask ShowSplash = new TimerTask() {
            @Override
            public void run() {
                // Close SplashScreenActivity.class
                finish();

                // Start MainActivity.class
                Intent myIntent = new Intent(SplashScreen.this,
                        HomeActivity.class);
                startActivity(myIntent);
            }
        };

        // Start the timer
        RunSplash.schedule(ShowSplash, Delay);
    }
}


