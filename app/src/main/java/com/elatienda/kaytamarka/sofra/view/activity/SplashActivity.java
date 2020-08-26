package com.elatienda.kaytamarka.sofra.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.databinding.DataBindingUtil;
import com.elatienda.kaytamarka.sofra.R;
import com.elatienda.kaytamarka.sofra.databinding.ActivitySplashBinding;

import static com.elatienda.kaytamarka.sofra.util.Constants.USER_CLIENT;
import static com.elatienda.kaytamarka.sofra.util.Constants.USER_RESTAURANT;
import static com.elatienda.kaytamarka.sofra.util.Constants.USER_TYPE_FILE;
import static com.elatienda.kaytamarka.sofra.util.Constants.USER_TYPE_KEY;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ActivitySplashBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_splash);

        // Client
        // Restaurant
        binding.activitySplashBtnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSharedPreferences(USER_TYPE_FILE,MODE_PRIVATE)
                        .edit()
                        .putString(USER_TYPE_KEY,USER_CLIENT)
                        .apply();
                // Go to Display All Restaurants                            //UserCycleActivity //HomeCycleActivity
                startActivity(new Intent(SplashActivity.this,UserCycleActivity.class));
            }
        });

        binding.activitySplashBtnSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSharedPreferences(USER_TYPE_FILE,MODE_PRIVATE)
                        .edit()
                        .putString(USER_TYPE_KEY,USER_RESTAURANT)
                        .apply();

                startActivity(new Intent(SplashActivity.this,UserCycleActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
