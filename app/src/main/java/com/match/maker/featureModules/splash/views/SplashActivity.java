package com.match.maker.featureModules.splash.views;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.match.maker.R;
import com.match.maker.application.MatchMakerApplication;
import com.match.maker.databinding.ActivitySplashBinding;
import com.match.maker.featureModules.landing.views.HomeActivity;
import com.match.maker.featureModules.login.LoginActivity;
import com.match.maker.featureModules.splash.di.DaggerSplashActivityComponent;
import com.match.maker.featureModules.splash.di.SplashActivityComponent;
import com.match.maker.featureModules.splash.di.SplashActivityModule;
import com.match.maker.preferences.CommonPreferences;

import javax.inject.Inject;

/**
 * Created by ravindra on 16,January,2019
 */
public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;

    @Inject
    CommonPreferences prefs;

    private ActivitySplashBinding binding;
    private SplashActivityComponent splashActivityComponent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);

        splashActivityComponent = DaggerSplashActivityComponent.builder()
                .applicationComponent(((MatchMakerApplication) getApplication()).getApplicationComponent())
                .splashActivityModule(new SplashActivityModule())
                .build();

        splashActivityComponent.inject(this);

        scheduleSplashScreen();

    }

    private void scheduleSplashScreen() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkLoginStatus();
            }
        }, SPLASH_TIME_OUT);
    }

    private void checkLoginStatus() {

        if (prefs.isFirstTimeLogin()) {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            startActivity(intent);
            finish();
        }

    }


}
