package com.elatienda.kaytamarka.sofra.view.fragment.user_cycle;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.elatienda.kaytamarka.sofra.R;
import com.elatienda.kaytamarka.sofra.data.model.login.Login;
import com.elatienda.kaytamarka.sofra.databinding.FragmentLoginBinding;
import com.elatienda.kaytamarka.sofra.util.HelperMethod;
import com.elatienda.kaytamarka.sofra.view.activity.SplashActivity;
import com.elatienda.kaytamarka.sofra.view.fragment.BaseFragment;

import static com.elatienda.kaytamarka.sofra.data.api.ApiClient.getClient;
import static com.elatienda.kaytamarka.sofra.util.Constants.API_TOKEN_FILE;
import static com.elatienda.kaytamarka.sofra.util.Constants.API_TOKEN_VALUE;
import static com.elatienda.kaytamarka.sofra.util.Constants.USER_CLIENT;
import static com.elatienda.kaytamarka.sofra.util.Constants.USER_RESTAURANT;
import static com.elatienda.kaytamarka.sofra.util.Constants.USER_TYPE_FILE;
import static com.elatienda.kaytamarka.sofra.util.Constants.USER_TYPE_KEY;
import static com.elatienda.kaytamarka.sofra.util.HelperMethod.closeKeypad;

public class LoginFragment extends BaseFragment {

    //private ActivityHomeCycleBinding navBinding;
    private UserViewModel viewModel;
    private FragmentLoginBinding binding;
    private String userType;

    public LoginFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_login,container,false);
        View view = binding.getRoot();
        setUpActivity();

//        BottomNavigationView navBar = navBinding.activityHomeCycleBtnNav;
//        navBar.setVisibility(View.GONE);

        if(getActivity()!=null){
            SharedPreferences userTypePreference = getActivity().getSharedPreferences(USER_TYPE_FILE, Context.MODE_PRIVATE);
            userType = userTypePreference.getString(USER_TYPE_KEY,null);

            //initialize viewModel
            viewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);
        }

        binding.fragmentLoginBtnForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getActivity()!=null){
                    closeKeypad(getActivity());
                    HelperMethod.replaceFragment(getActivity().getSupportFragmentManager(), R.id.activity_user_cycle_fl_frame, new ForgotPasswordFragment());
                }
            }
        });

        binding.fragmentLoginBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getActivity()!=null){
                    closeKeypad(getActivity());
                    if(userType!=null){
                        if(userType.equals(USER_CLIENT)){
                            HelperMethod.replaceFragment(getActivity().getSupportFragmentManager(), R.id.activity_user_cycle_fl_frame, new RegisterClientFragment());
                        }else if (userType.equals(USER_RESTAURANT)){
                            HelperMethod.replaceFragment(getActivity().getSupportFragmentManager(), R.id.activity_user_cycle_fl_frame, new RegisterRestaurantFragment());
                        }
                    }
                }
            }
        });

        binding.fragmentLoginBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity()!=null){
                    closeKeypad(getActivity());
                }
                if(binding.fragmentLoginEtEmail.getText().toString().isEmpty() ||
                        binding.fragmentLoginEtPassword.getText().toString().isEmpty()){

                    if (binding.fragmentLoginEtEmail.getText().toString().isEmpty()){
                        binding.fragmentLoginEtEmail.setError(getString(R.string.login_et_email_error));
                    }

                    if (binding.fragmentLoginEtPassword.getText().toString().isEmpty()){
                        binding.fragmentLoginEtPassword.setError(getString(R.string.login_et_password_error));
                    }

                }else {
                    onLogin();
                }
            }
        });

        return view;
    }

    // check for entered Values Valid or Not
    private void onLogin() {
        if (userType.equals(USER_CLIENT)){
            viewModel.onLogin(getClient().onClientLogin(binding.fragmentLoginEtEmail.getText().toString(),
                    binding.fragmentLoginEtPassword.getText().toString()));
        }else if (userType.equals(USER_RESTAURANT)){
            viewModel.onLogin(getClient().onRestaurantLogin(binding.fragmentLoginEtEmail.getText().toString(),
                    binding.fragmentLoginEtPassword.getText().toString()));
        }
        setObserve();
    }

    private void setObserve() {
        if (getActivity()!=null){
            viewModel.loginMutableLiveData.observe(getActivity(), new Observer<Login>() {
                @Override
                public void onChanged(Login login) {
                    try {
                        if (login.getStatus() == 1 ){
                            // Save API Token
                            if(getActivity()!= null){
                                getActivity().getSharedPreferences(API_TOKEN_FILE, Context.MODE_PRIVATE)
                                        .edit()
                                        .putString(API_TOKEN_VALUE,login.getData().getApiToken())
                                        .apply();
                            }
                            if (userType.equals(USER_CLIENT)){
//                                // Go To Home Page
//                                startActivity(new Intent(getActivity(), HomeCycleActivity.class));
                                Toast.makeText(getActivity(), "client ok", Toast.LENGTH_SHORT).show();
                            }else if (userType.equals(USER_RESTAURANT)){
                                Toast.makeText(getActivity(), "restaurant ok", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(getActivity(), "onResponse status-0: \n" + login.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                        Toast.makeText(getActivity(), "Exeption: \n" +  e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            viewModel.mutableLiveDataError.observe(getActivity(), new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean aBoolean) {
                    if (aBoolean && viewModel.ErrorText == null){
                        Toast.makeText(getActivity(), "onFailure : " + viewModel.onFailureMsg,Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    @Override
    public void onBack() {
        startActivity(new Intent(getActivity(), SplashActivity.class));
    }
}