package com.upvmaster.carlos.audiolibros.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.upvmaster.carlos.audiolibros.Main.MainActivity;
import com.upvmaster.carlos.audiolibros.R;

public class SplashActivity extends AppCompatActivity implements Animation.AnimationListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ImageView logo = (ImageView) findViewById(R.id.iv_splash_logo);
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.splash_logo);
        anim.setAnimationListener(this);
        logo.startAnimation(anim);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.entrada_zoom, R.anim.salida_zoom);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
