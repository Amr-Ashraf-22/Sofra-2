package com.elatienda.kaytamarka.sofra.view.fragment.user_cycle;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.elatienda.kaytamarka.sofra.R;
import com.elatienda.kaytamarka.sofra.adapter.SpinnerAdapter;
import com.elatienda.kaytamarka.sofra.data.model.general_response_not_paginated.GeneralResponseNotPaginated;
import com.elatienda.kaytamarka.sofra.data.model.register.ClientRegister;
import com.elatienda.kaytamarka.sofra.databinding.FragmentRegisterClientBinding;
import com.elatienda.kaytamarka.sofra.util.HelperMethod;
import com.elatienda.kaytamarka.sofra.view.fragment.BaseFragment;
import com.elatienda.kaytamarka.sofra.view.fragment.BaseViewModel;

import static android.app.Activity.RESULT_OK;
import static com.elatienda.kaytamarka.sofra.data.api.ApiClient.getClient;
import static com.elatienda.kaytamarka.sofra.util.Constants.CLIENT_GALLERY_REQUEST_CODE;
import static com.elatienda.kaytamarka.sofra.util.Constants.USER_CLIENT;
import static com.elatienda.kaytamarka.sofra.util.Constants.USER_TYPE_FILE;
import static com.elatienda.kaytamarka.sofra.util.Constants.USER_TYPE_KEY;
import static com.elatienda.kaytamarka.sofra.util.HelperMethod.closeKeypad;
import static com.elatienda.kaytamarka.sofra.util.HelperMethod.convertFileToMultipart;
import static com.elatienda.kaytamarka.sofra.util.HelperMethod.convertToRequestBody;

public class RegisterClientFragment extends BaseFragment {

    //private ActivityHomeCycleBinding navBinding;
    private UserViewModel viewModel;
    private BaseViewModel baseModel;
    private SpinnerAdapter cityAdapter, regionAdapter;
    private boolean imageFlag = false;
    private FragmentRegisterClientBinding binding;
    private String imagePath = null;
    private String userType;

    public RegisterClientFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_register_client,container,false);
        View view = binding.getRoot();
        setUpActivity();

        cityAdapter = new SpinnerAdapter(getActivity(),Color.parseColor("#FFFFFF"),R.dimen._15ssp);
        regionAdapter = new SpinnerAdapter(getActivity(),Color.parseColor("#FFFFFF"),R.dimen._15ssp);


//        BottomNavigationView navBar = navBinding.activityHomeCycleBtnNav;
//        navBar.setVisibility(View.GONE);

        if (getActivity()!=null){
            SharedPreferences userTypePreference = getActivity().getSharedPreferences(USER_TYPE_FILE, Context.MODE_PRIVATE);
            userType = userTypePreference.getString(USER_TYPE_KEY,null);

            //initialize viewModel
            viewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);
            baseModel = new ViewModelProvider(getActivity()).get(BaseViewModel.class);
        }

        getCities();

        binding.fragmentRegisterClientCImgVProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity()!=null){
                    closeKeypad(getActivity());
                }
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Choose an image"), CLIENT_GALLERY_REQUEST_CODE);
            }
        });

        binding.fragmentRegisterClientBtnRegister.setOnClickListener(new View.OnClickListener() {
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

    private void getCities() {
        baseModel.getSpinnerData(getClient().getCitiesNotPaginated());
        setBaseObserve();
    }

    private void setBaseObserve() {
        if (getActivity()!=null){
            baseModel.spinnerMutableLiveData.observe(getActivity(), new Observer<GeneralResponseNotPaginated>() {
                @Override
                public void onChanged(GeneralResponseNotPaginated generalResponseNotPaginated) {
                    try{
                        if(generalResponseNotPaginated.getStatus() == 1){
                            cityAdapter.clear();
                            binding.fragmentRegisterClientSpCity.setAdapter(null);
                            cityAdapter.setData(generalResponseNotPaginated.getData(), getString(R.string.spinner_city));
                            binding.fragmentRegisterClientSpCity.setAdapter(cityAdapter);
                            binding.fragmentRegisterClientSpCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    if(position != 0){
                                        baseModel.getSpinnerData(getClient().getRegionsNotPaginated(cityAdapter.selectedId));
                                        if(getActivity()!=null){
                                            baseModel.spinnerMutableLiveData.observe(getActivity(), new Observer<GeneralResponseNotPaginated>() {
                                                @Override
                                                public void onChanged(GeneralResponseNotPaginated generalResponseNotPaginated) {
                                                    try{
                                                        if(generalResponseNotPaginated.getStatus() == 1){
                                                            regionAdapter.clear();
                                                            binding.fragmentRegisterClientSpDistrict.setAdapter(null);
                                                            binding.fragmentRegisterClientRlRegions.setVisibility(View.VISIBLE);
                                                            regionAdapter.setData(generalResponseNotPaginated.getData(), getString(R.string.spinner_region));
                                                            binding.fragmentRegisterClientSpDistrict.setAdapter(regionAdapter);
                                                        }else{
                                                            Toast.makeText(getActivity(), "onResponse status-0: \n" + generalResponseNotPaginated.getMsg() , Toast.LENGTH_LONG).show();
                                                        }
                                                    }catch (Exception e){
                                                        Toast.makeText(getActivity(), "Exeption: \n" + e.getMessage() , Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            });
                                        }
                                    }else{
                                        binding.fragmentRegisterClientRlRegions.setVisibility(View.GONE);
                                        binding.fragmentRegisterClientSpDistrict.setAdapter(null);
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        }else{
                            Toast.makeText(getActivity(), "onResponse status-0: \n" + generalResponseNotPaginated.getMsg() , Toast.LENGTH_LONG).show();
                        }
                    }catch (Exception e){
                        Toast.makeText(getActivity(), "Exeption: \n" + e.getMessage() , Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    private void onCheck() {
        if(!imageFlag ||
                binding.fragmentRegisterClientEtName.getText().toString().isEmpty() ||
                binding.fragmentRegisterClientEtEmail.getText().toString().isEmpty() ||
                binding.fragmentRegisterClientEtPhone.getText().toString().isEmpty() ||
                binding.fragmentRegisterClientSpCity.getSelectedItemPosition() == 0 ||
                binding.fragmentRegisterClientSpDistrict.getSelectedItemPosition() == 0 ||
                binding.fragmentRegisterClientEtPassword.getText().toString().isEmpty() ||
                binding.fragmentRegisterClientEtConfirmPassword.getText().toString().isEmpty()){

            if(!imageFlag){
                binding.fragmentRegisterClientCImgVProfile.setBorderWidth(5);
                binding.fragmentRegisterClientCImgVProfile.setBorderColor(Color.RED);
            }

            if (binding.fragmentRegisterClientEtName.getText().toString().isEmpty()){
                binding.fragmentRegisterClientEtName.setError(getString(R.string.register_client_et_name_error));
            }

            if (binding.fragmentRegisterClientEtEmail.getText().toString().isEmpty()){
                binding.fragmentRegisterClientEtEmail.setError(getString(R.string.login_et_email_error));
            }

            if (binding.fragmentRegisterClientEtPhone.getText().toString().isEmpty()){
                binding.fragmentRegisterClientEtPhone.setError(getString(R.string.register_client_et_phone_error));
            }

            if(binding.fragmentRegisterClientSpDistrict.getSelectedItemPosition() == 0 ||
                    binding.fragmentRegisterClientSpDistrict.getSelectedItemPosition() == 0){
                Toast.makeText(getActivity(), getString(R.string.et_empty_field_error), Toast.LENGTH_SHORT).show();
            }
            if (binding.fragmentRegisterClientEtPassword.getText().toString().isEmpty()){
                binding.fragmentRegisterClientEtPassword.setError(getString(R.string.register_client_et_password_error));
            }

            if (binding.fragmentRegisterClientEtConfirmPassword.getText().toString().isEmpty()){
                binding.fragmentRegisterClientEtConfirmPassword.setError(getString(R.string.reset_password_et_confirm_password_error));
            }

        }else {
            onRegister();
        }
    }

    private void onRegister() {
        if (userType.equals(USER_CLIENT)) {
            viewModel.onClientRegister(getClient().onClientRegister(
                    convertToRequestBody(binding.fragmentRegisterClientEtName.getText().toString().trim()),
                    convertToRequestBody(binding.fragmentRegisterClientEtEmail.getText().toString().trim()),
                    convertToRequestBody(binding.fragmentRegisterClientEtPassword.getText().toString().trim()),
                    convertToRequestBody(binding.fragmentRegisterClientEtConfirmPassword.getText().toString().trim()),
                    convertToRequestBody(binding.fragmentRegisterClientEtPhone.getText().toString().trim()),
                    convertToRequestBody(String.valueOf(binding.fragmentRegisterClientSpDistrict.getSelectedItemPosition())),
                    convertFileToMultipart(imagePath ,"profile_image")
            ));
            setRegisterObserve();
        }
    }

    private void setRegisterObserve() {
        if (getActivity()!=null){

            viewModel.clientRegisterMutableLiveData.observe(getActivity(), new Observer<ClientRegister>() {
                @Override
                public void onChanged(ClientRegister clientRegister) {
                    try{
                        if(clientRegister.getStatus() == 1){

                            Toast.makeText(getActivity(), "Registered", Toast.LENGTH_SHORT).show();
                            // Go to Home Or Continue Confirm order

                        }else{
                            Toast.makeText(getActivity(), "onResponse status-0: \n" + clientRegister.getMsg() , Toast.LENGTH_LONG).show();
                        }
                    }catch (Exception e){
                        Toast.makeText(getActivity(), "Exeption: \n" + e.getMessage() , Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
    // Error : the profile image must be an image
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CLIENT_GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null){
            imagePath = data.getData().getPath();
            //imageFile = new File(Objects.requireNonNull(Objects.requireNonNull(data.getData()).getPath()));

            binding.fragmentRegisterClientCImgVProfile.setImageURI(data.getData());
            binding.fragmentRegisterClientCImgVProfile.setBorderWidth(0);
            binding.fragmentRegisterClientCImgVProfile.setBorderColor(Color.WHITE);
            imageFlag = true;
        }
    }

    @Override
    public void onBack() {
        if(getActivity()!=null){
            HelperMethod.replaceFragment(getActivity().getSupportFragmentManager(), R.id.activity_user_cycle_fl_frame, new LoginFragment());
        }
    }
}