package com.elatienda.kaytamarka.sofra.view.fragment.user_cycle;

import android.content.Context;
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
import com.elatienda.kaytamarka.sofra.data.model.reset_password.ResetPassword;
import com.elatienda.kaytamarka.sofra.databinding.FragmentForgotPasswordBinding;
import com.elatienda.kaytamarka.sofra.util.HelperMethod;
import com.elatienda.kaytamarka.sofra.view.fragment.BaseFragment;

import static com.elatienda.kaytamarka.sofra.data.api.ApiClient.getClient;
import static com.elatienda.kaytamarka.sofra.util.Constants.API_TOKEN_FILE;
import static com.elatienda.kaytamarka.sofra.util.Constants.API_TOKEN_VALUE;
import static com.elatienda.kaytamarka.sofra.util.Constants.USER_CLIENT;
import static com.elatienda.kaytamarka.sofra.util.Constants.USER_RESTAURANT;
import static com.elatienda.kaytamarka.sofra.util.Constants.USER_TYPE_FILE;
import static com.elatienda.kaytamarka.sofra.util.Constants.USER_TYPE_KEY;
import static com.elatienda.kaytamarka.sofra.util.HelperMethod.closeKeypad;

public class ForgotPasswordFragment extends BaseFragment {

    private FragmentForgotPasswordBinding binding;
    private String userType;
    private UserViewModel viewModel;

    public ForgotPasswordFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_forgot_password,container,false);
        View view = binding.getRoot();
        setUpActivity();

        if(getActivity()!=null){
            SharedPreferences userTypePreference = getActivity().getSharedPreferences(USER_TYPE_FILE, Context.MODE_PRIVATE);
            userType = userTypePreference.getString(USER_TYPE_KEY,null);

            //initialize viewModel
            viewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);
        }

        binding.fragmentForgotPassBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity()!=null){
                    closeKeypad(getActivity());
                }
                if(binding.fragmentForgotPassEtEmail.getText().toString().isEmpty()){
                    binding.fragmentForgotPassEtEmail.setError(getString(R.string.login_et_email_error));
                }else {
                    onSendCode();
                }
            }
        });


        return view;
    }

    private void onSendCode() {
        // Send Code To Email
        if (userType.equals(USER_CLIENT)){
            viewModel.onResetPassword(getClient().onClientResetPassword(binding.fragmentForgotPassEtEmail.getText().toString().trim()));
        }else if (userType.equals(USER_RESTAURANT)){
            viewModel.onResetPassword(getClient().onRestaurantResetPassword(binding.fragmentForgotPassEtEmail.getText().toString().trim()));
        }
        setObserve();
    }

    private void setObserve() {
        if (getActivity()!=null){
            viewModel.resetPasswordMutableLiveData.observe(getActivity(), new Observer<ResetPassword>() {
                @Override
                public void onChanged(ResetPassword resetPassword) {
                    try {
                        if (resetPassword.getStatus() == 1 ){
                            Toast.makeText(getActivity(), "Please Check your Email Address", Toast.LENGTH_LONG).show();
                            if(getActivity()!=null){
                                HelperMethod.replaceFragment(getActivity().getSupportFragmentManager(), R.id.activity_user_cycle_fl_frame, new ResetPasswordFragment());
                            }
                        }else {
                            Toast.makeText(getActivity(), "onResponse status-0: \n" + resetPassword.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                        Toast.makeText(getActivity(), "Exeption: \n" +  e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    public void onBack() {
        if(getActivity()!=null){
            HelperMethod.replaceFragment(getActivity().getSupportFragmentManager(), R.id.activity_user_cycle_fl_frame, new LoginFragment());
        }
    }
}