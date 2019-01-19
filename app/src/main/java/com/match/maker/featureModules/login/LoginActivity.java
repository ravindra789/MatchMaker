package com.match.maker.featureModules.login;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.match.maker.R;
import com.match.maker.databinding.ActivityLoginBinding;
import com.match.maker.featureModules.landing.views.HomeActivity;

/**
 * Created by ravindra on 16,January,2019
 */
public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        binding.toolbar.setTitle("Login");
        binding.toolbar.setTitleTextAppearance(this, R.style.WhiteToolBarTitleMedium);


        setClickListeners();
    }

    private void setClickListeners() {

        binding.btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callNextActivity();
            }
        });

    }

    private void callNextActivity() {

        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        startActivity(intent);
        finish();

    }
}
