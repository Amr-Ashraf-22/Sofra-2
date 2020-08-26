package com.elatienda.kaytamarka.sofra.view.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.elatienda.kaytamarka.sofra.R;
import com.elatienda.kaytamarka.sofra.databinding.ActivityUserCycleBinding;
import com.elatienda.kaytamarka.sofra.util.HelperMethod;
import com.elatienda.kaytamarka.sofra.view.fragment.user_cycle.LoginFragment;

public class UserCycleActivity extends BaseActivity {

    ActivityUserCycleBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_user_cycle);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        HelperMethod.replaceFragment(getSupportFragmentManager(), R.id.activity_user_cycle_fl_frame, new LoginFragment());
    }
}
