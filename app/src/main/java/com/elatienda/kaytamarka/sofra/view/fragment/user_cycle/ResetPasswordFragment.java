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
import com.elatienda.kaytamarka.sofra.data.model.new_password.NewPassword;
import com.elatienda.kaytamarka.sofra.databinding.FragmentResetPasswordBinding;
import com.elatienda.kaytamarka.sofra.util.HelperMethod;
import com.elatienda.kaytamarka.sofra.view.fragment.BaseFragment;

import static com.elatienda.kaytamarka.sofra.data.api.ApiClient.getClient;
import static com.elatienda.kaytamarka.sofra.util.Constants.USER_CLIENT;
import static com.elatienda.kaytamarka.sofra.util.Constants.USER_RESTAURANT;
import static com.elatienda.kaytamarka.sofra.util.Constants.USER_TYPE_FILE;
import static com.elatienda.kaytamarka.sofra.util.Constants.USER_TYPE_KEY;
import static com.elatienda.kaytamarka.sofra.util.HelperMethod.closeKeypad;

public class ResetPasswordFragment extends BaseFragment {

    private FragmentResetPasswordBinding binding;
    private String userType;
    private UserViewModel viewModel;

    public ResetPasswordFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_reset_password,container,false);
        View view = binding.getRoot();
        setUpActivity();

        if(getActivity()!=null){
            SharedPreferences userTypePreference = getActivity().getSharedPreferences(USER_TYPE_FILE, Context.MODE_PRIVATE);
            userType = userTypePreference.getString(USER_TYPE_KEY,null);

            //initialize viewModel
            viewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);
        }
        
        binding.fragmentResetPassBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity()!=null){
                    closeKeypad(getActivity());
                }
                onCheck();
            }
        });

        return view;
    }

    private void onCheck() {
        if(binding.fragmentResetPassEtCode.getText().toString().isEmpty() ||
                binding.fragmentResetPassEtPassword.getText().toString().isEmpty() ||
                binding.fragmentResetPassEtConfirmPassword.getText().toString().isEmpty()){

            if (binding.fragmentResetPassEtCode.getText().toString().isEmpty()){
                binding.fragmentResetPassEtCode.setError(getString(R.string.reset_password_et_code_error));
            }

            if (binding.fragmentResetPassEtPassword.getText().toString().isEmpty()){
                binding.fragmentResetPassEtPassword.setError(getString(R.string.reset_password_et_password_error));
            }

            if (binding.fragmentResetPassEtConfirmPassword.getText().toString().isEmpty()){
                binding.fragmentResetPassEtConfirmPassword.setError(getString(R.string.reset_password_et_confirm_password_error));
            }

        }else {
            onReset();
        }
    }

    private void onReset() {
        // Reset Password
        if (userType.equals(USER_CLIENT)){
            viewModel.onNewPassword(getClient().onClientNewPassword(
                    Integer.parseInt(binding.fragmentResetPassEtCode.getText().toString().trim()),
                    binding.fragmentResetPassEtPassword.getText().toString().trim(),
                    binding.fragmentResetPassEtConfirmPassword.getText().toString().trim()
            ));
        }else if (userType.equals(USER_RESTAURANT)){
            viewModel.onNewPassword(getClient().onRestaurantNewPassword(
                    Integer.parseInt(binding.fragmentResetPassEtCode.getText().toString().trim()),
                    binding.fragmentResetPassEtPassword.getText().toString().trim(),
                    binding.fragmentResetPassEtConfirmPassword.getText().toString().trim()
            ));
        }
        setObserve();
    }

    private void setObserve() {
        if (getActivity()!=null){
            viewModel.newPasswordMutableLiveData.observe(getActivity(), new Observer<NewPassword>() {
                @Override
                public void onChanged(NewPassword newPassword) {
                    try {
                        if (newPassword.getStatus() == 1 ){
                            Toast.makeText(getActivity(), "Password Changed Successfully", Toast.LENGTH_LONG).show();
                            if(getActivity()!=null){
                                HelperMethod.replaceFragment(getActivity().getSupportFragmentManager(), R.id.activity_user_cycle_fl_frame, new LoginFragment());
                            }
                        }else {
                            Toast.makeText(getActivity(), "onResponse status-0: \n" + newPassword.getMsg(), Toast.LENGTH_SHORT).show();
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
            HelperMethod.replaceFragment(getActivity().getSupportFragmentManager(), R.id.activity_user_cycle_fl_frame, new ForgotPasswordFragment());
        }
    }
}