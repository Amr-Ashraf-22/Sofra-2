package com.elatienda.kaytamarka.sofra.view.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import com.elatienda.kaytamarka.sofra.R;
import com.elatienda.kaytamarka.sofra.databinding.ActivityHomeCycleBinding;
import com.elatienda.kaytamarka.sofra.util.HelperMethod;
import com.elatienda.kaytamarka.sofra.view.fragment.home_cycle.client_cycle.home_cycle.DisplayRestaurentsFragment;
import com.elatienda.kaytamarka.sofra.view.fragment.home_cycle.client_cycle.home_cycle.RestaurantContainerFragment;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import static com.elatienda.kaytamarka.sofra.util.Constants.API_TOKEN_FILE;
import static com.elatienda.kaytamarka.sofra.util.Constants.API_TOKEN_VALUE;
import static com.elatienda.kaytamarka.sofra.util.Constants.USER_CLIENT;
import static com.elatienda.kaytamarka.sofra.util.Constants.USER_RESTAURANT;
import static com.elatienda.kaytamarka.sofra.util.Constants.USER_TYPE_FILE;
import static com.elatienda.kaytamarka.sofra.util.Constants.USER_TYPE_KEY;

public class HomeCycleActivity extends BaseActivity {

    private ActivityHomeCycleBinding binding;
    private String apiTokenValue = null;
    private String userType;
    public static ImageView toolbarImgLeft = null, toolbarImgRight = null;
    public static TextView toolbarTitle = null;
    public static BottomNavigationView navBar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_home_cycle);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        setSupportActionBar(binding.toolbar);
        toolbarImgLeft = binding.toolbarImgVLeft;
        toolbarImgRight = binding.toolbarImgVRight;
        toolbarTitle = binding.toolbarTvTitle;
        navBar = binding.activityHomeCycleBtnNav;

        SharedPreferences apiPreferences = getSharedPreferences(API_TOKEN_FILE, Context.MODE_PRIVATE);
        apiTokenValue = apiPreferences.getString(API_TOKEN_VALUE,null);

        SharedPreferences userTypePreference = getSharedPreferences(USER_TYPE_FILE, Context.MODE_PRIVATE);
        userType = userTypePreference.getString(USER_TYPE_KEY,null);

        if (userType!=null){
            if (userType.equals(USER_CLIENT)){
                // if API Token == null // Not Login
                //if (apiTokenValue == null){
                    HelperMethod.replaceFragment(getSupportFragmentManager() , R.id.activity_home_cycle_fl_container ,new DisplayRestaurentsFragment());
                    //HelperMethod.replaceFragment(getSupportFragmentManager() , R.id.activity_home_cycle_fl_container ,new RestaurantContainerFragment());
                //}else {
                    // if API Token != null // if he login
                    // lw lsa fat7 el app w 3aml login ==> Display Restaurants Fragment
                    // lw gay mn saf7t el cart ==>  go to ta2keed el talab
                    // N3ml shart fe ta2keed el talab la m4 3aml login 2w lw 3aml login
                //}
            }else if (userType.equals(USER_RESTAURANT)){
                // تصنيفات
            }
        }

        BottomNavigationMenuView mbottomNavigationMenuView =
                (BottomNavigationMenuView) binding.activityHomeCycleBtnNav.getChildAt(0);

        View view = mbottomNavigationMenuView.getChildAt(1);
        BottomNavigationItemView itemView = (BottomNavigationItemView) view;

        binding.activityHomeCycleBtnNav.setOnNavigationItemSelectedListener(navListener);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            switch (menuItem.getItemId()){
                case R.id.nav_home:
                    if (userType.equals(USER_CLIENT)){
                        //HelperMethod.replaceFragment(getSupportFragmentManager() , R.id.activity_home_cycle_fl_container ,new HomeFragment());
                    }else if (userType.equals(USER_RESTAURANT)){
                        //HelperMethod.replaceFragment(getSupportFragmentManager() , R.id.activity_home_cycle_fl_container ,new HomeFragment());
                    }
                    break;
                case R.id.nav_requests:
                    if (userType.equals(USER_CLIENT)){
                        //HelperMethod.replaceFragment(getSupportFragmentManager() , R.id.activity_home_cycle_fl_container ,new ProfileFragment());
                    }else if (userType.equals(USER_RESTAURANT)){
                        //HelperMethod.replaceFragment(getSupportFragmentManager() , R.id.activity_home_cycle_fl_container ,new ProfileFragment());
                    }
                    break;
                case R.id.nav_profile:
                    if (userType.equals(USER_CLIENT)){
                        //HelperMethod.replaceFragment(getSupportFragmentManager() , R.id.activity_home_cycle_fl_container ,new NotificationFragment());
                    }else if (userType.equals(USER_RESTAURANT)){
                        //HelperMethod.replaceFragment(getSupportFragmentManager() , R.id.activity_home_cycle_fl_container ,new NotificationFragment());
                    }
                    break;
                case R.id.nav_more:
                    if (userType.equals(USER_CLIENT)){
                        //HelperMethod.replaceFragment(getSupportFragmentManager() , R.id.activity_home_cycle_fl_container ,new MoreFragment());
                    }else if (userType.equals(USER_RESTAURANT)){
                        //HelperMethod.replaceFragment(getSupportFragmentManager() , R.id.activity_home_cycle_fl_container ,new MoreFragment());
                    }
                    break;
                default:
                    return false;
            }
            return true;
        }
    };
}
