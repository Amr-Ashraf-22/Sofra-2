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
import com.elatienda.kaytamarka.sofra.data.model.register.RestaurantRegister;
import com.elatienda.kaytamarka.sofra.databinding.FragmentRegisterRestaurantBinding;
import com.elatienda.kaytamarka.sofra.util.HelperMethod;
import com.elatienda.kaytamarka.sofra.view.fragment.BaseFragment;
import com.elatienda.kaytamarka.sofra.view.fragment.BaseViewModel;

import java.util.Objects;

import okhttp3.MultipartBody;

import static android.app.Activity.RESULT_OK;
import static com.elatienda.kaytamarka.sofra.data.api.ApiClient.getClient;
import static com.elatienda.kaytamarka.sofra.util.Constants.RESTAURANT_GALLERY_REQUEST_CODE;
import static com.elatienda.kaytamarka.sofra.util.Constants.USER_RESTAURANT;
import static com.elatienda.kaytamarka.sofra.util.Constants.USER_TYPE_FILE;
import static com.elatienda.kaytamarka.sofra.util.Constants.USER_TYPE_KEY;
import static com.elatienda.kaytamarka.sofra.util.HelperMethod.closeKeypad;
import static com.elatienda.kaytamarka.sofra.util.HelperMethod.convertFileToMultipart;
import static com.elatienda.kaytamarka.sofra.util.HelperMethod.convertToRequestBody;

public class RegisterRestaurantFragment extends BaseFragment {

    private FragmentRegisterRestaurantBinding binding;
    private UserViewModel viewModel;
    private BaseViewModel baseModel;
    private boolean imageFlag = false;
    private MultipartBody.Part imageData;
    private String userType;
    private String imagePath = null;
    private SpinnerAdapter cityAdapter;
    private SpinnerAdapter regionAdapter;

    public RegisterRestaurantFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_register_restaurant,container,false);
        View view = binding.getRoot();
        setUpActivity();

        cityAdapter = new SpinnerAdapter(getActivity(),Color.parseColor("#FFFFFF"),R.dimen._15ssp);
        regionAdapter = new SpinnerAdapter(getActivity(),Color.parseColor("#FFFFFF"),R.dimen._15ssp);

        if (getActivity()!=null){
            SharedPreferences userTypePreference = getActivity().getSharedPreferences(USER_TYPE_FILE, Context.MODE_PRIVATE);
            userType = userTypePreference.getString(USER_TYPE_KEY,null);

            //initialize viewModel
            viewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);
            baseModel = new ViewModelProvider(getActivity()).get(BaseViewModel.class);
        }

        getCities();

        binding.fragmentRegisterRestaurantCvLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity()!=null){
                    closeKeypad(getActivity());
                }
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Choose an image"), RESTAURANT_GALLERY_REQUEST_CODE);
            }
        });

        binding.fragmentRegisterRestaurantBtnRegister.setOnClickListener(new View.OnClickListener() {
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
                            binding.fragmentRegisterRestaurantSpCity.setAdapter(null);
                            cityAdapter.setData(generalResponseNotPaginated.getData(), getString(R.string.spinner_city));
                            binding.fragmentRegisterRestaurantSpCity.setAdapter(cityAdapter);
                            binding.fragmentRegisterRestaurantSpCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                                                            binding.fragmentRegisterRestaurantSpDistrict.setAdapter(null);
                                                            binding.fragmentRegisterRestaurantRlRegions.setVisibility(View.VISIBLE);
                                                            regionAdapter.setData(generalResponseNotPaginated.getData(), getString(R.string.spinner_region));
                                                            binding.fragmentRegisterRestaurantSpDistrict.setAdapter(regionAdapter);
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
                                        binding.fragmentRegisterRestaurantRlRegions.setVisibility(View.GONE);
                                        binding.fragmentRegisterRestaurantSpDistrict.setAdapter(null);
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
        if(binding.fragmentRegisterRestaurantEtName.getText().toString().isEmpty() ||
                binding.fragmentRegisterRestaurantEtEmail.getText().toString().isEmpty() ||
                binding.fragmentRegisterRestaurantEtDeliveryTime.getText().toString().isEmpty() ||
                binding.fragmentRegisterRestaurantSpCity.getSelectedItemPosition() == 0 ||
                binding.fragmentRegisterRestaurantSpDistrict.getSelectedItemPosition() == 0 ||
                binding.fragmentRegisterRestaurantEtPassword.getText().toString().isEmpty() ||
                binding.fragmentRegisterRestaurantEtConfirmPassword.getText().toString().isEmpty() ||
                binding.fragmentRegisterRestaurantEtMinimumOrder.getText().toString().isEmpty() ||
                binding.fragmentRegisterRestaurantEtDeliveryFee.getText().toString().isEmpty() ||
                binding.fragmentRegisterRestaurantEtPhone.getText().toString().isEmpty() ||
                binding.fragmentRegisterRestaurantEtWhatsApp.getText().toString().isEmpty() ||
                !imageFlag){

            if(binding.fragmentRegisterRestaurantEtName.getText().toString().isEmpty()){
                binding.fragmentRegisterRestaurantEtName.setError(getString(R.string.register_restaurant_tv_name_error));
            }

            if(binding.fragmentRegisterRestaurantEtEmail.getText().toString().isEmpty()){
                binding.fragmentRegisterRestaurantEtEmail.setError(getString(R.string.register_restaurant_tv_email_error));
            }

            if(binding.fragmentRegisterRestaurantEtDeliveryTime.getText().toString().isEmpty()){
                binding.fragmentRegisterRestaurantEtDeliveryTime.setError(getString(R.string.register_restaurant_tv_delivery_time_error));
            }

            if(binding.fragmentRegisterRestaurantSpCity.getSelectedItemPosition() == 0 ||
                    binding.fragmentRegisterRestaurantSpDistrict.getSelectedItemPosition() == 0){
                Toast.makeText(getActivity(), getString(R.string.et_empty_field_error), Toast.LENGTH_SHORT).show();
            }

            if(binding.fragmentRegisterRestaurantEtPassword.getText().toString().isEmpty()){
                binding.fragmentRegisterRestaurantEtPassword.setError(getString(R.string.register_client_et_password_error));
            }

            if(binding.fragmentRegisterRestaurantEtConfirmPassword.getText().toString().isEmpty()){
                binding.fragmentRegisterRestaurantEtConfirmPassword.setError(getString(R.string.reset_password_et_confirm_password_error));
            }

            if(binding.fragmentRegisterRestaurantEtMinimumOrder.getText().toString().isEmpty()){
                binding.fragmentRegisterRestaurantEtMinimumOrder.setError(getString(R.string.register_restaurant_tv_minimum_order_error));
            }

            if(binding.fragmentRegisterRestaurantEtDeliveryFee.getText().toString().isEmpty()){
                binding.fragmentRegisterRestaurantEtDeliveryFee.setError(getString(R.string.register_restaurant_tv_delivery_fee_error));
            }

            if(binding.fragmentRegisterRestaurantEtPhone.getText().toString().isEmpty()){
                binding.fragmentRegisterRestaurantEtPhone.setError(getString(R.string.register_restaurant_tv_phone_error));
            }

            if(binding.fragmentRegisterRestaurantEtWhatsApp.getText().toString().isEmpty()){
                binding.fragmentRegisterRestaurantEtWhatsApp.setError(getString(R.string.register_restaurant_tv_whatsapp_error));
            }

            if(!imageFlag){
                binding.fragmentRegisterRestaurantCvLogo.setStrokeWidth(5);
                binding.fragmentRegisterRestaurantCvLogo.setStrokeColor(Color.RED);
            }

        }else {
            //check if two passwords are not equal
            onRegister();
        }
    }

    private void onRegister() {
        if (userType.equals(USER_RESTAURANT)) {
            viewModel.onRestaurantRegister(getClient().onRestaurantRegister(
                    convertToRequestBody(binding.fragmentRegisterRestaurantEtName.getText().toString().trim()),
                    convertToRequestBody(binding.fragmentRegisterRestaurantEtEmail.getText().toString().trim()),
                    convertToRequestBody(binding.fragmentRegisterRestaurantEtPassword.getText().toString().trim()),
                    convertToRequestBody(binding.fragmentRegisterRestaurantEtConfirmPassword.getText().toString().trim()),
                    convertToRequestBody(binding.fragmentRegisterRestaurantEtPhone.getText().toString().trim()),
                    convertToRequestBody(binding.fragmentRegisterRestaurantEtWhatsApp.getText().toString().trim()),
                    convertToRequestBody(String.valueOf(binding.fragmentRegisterRestaurantSpDistrict.getSelectedItemPosition())),
                    convertToRequestBody(String.valueOf(Integer.parseInt(binding.fragmentRegisterRestaurantEtDeliveryFee.getText().toString().trim()))),
                    convertToRequestBody(String.valueOf(Integer.parseInt(binding.fragmentRegisterRestaurantEtMinimumOrder.getText().toString().trim()))),
                    convertFileToMultipart(imagePath,"photo"),
                    convertToRequestBody(String.valueOf(Integer.parseInt(binding.fragmentRegisterRestaurantEtDeliveryTime.getText().toString().trim() )))
            ));
            setRegisterObserve();
        }
    }

    private void setRegisterObserve() {
        if (getActivity()!=null){
            viewModel.restaurantRegisterMutableLiveData.observe(getActivity(), new Observer<RestaurantRegister>() {
                @Override
                public void onChanged(RestaurantRegister restaurantRegister) {
                    try{
                        if(restaurantRegister.getStatus() == 1){

                            Toast.makeText(getActivity(), "Registered", Toast.LENGTH_SHORT).show();

                        }else{
                            Toast.makeText(getActivity(), "onResponse status-0: \n" + restaurantRegister.getMsg() , Toast.LENGTH_LONG).show();
                        }
                    }catch (Exception e){
                        Toast.makeText(getActivity(), "Exeption: \n" + e.getMessage() , Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
// Error : the profile image field is required
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == RESTAURANT_GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null){
            //imageFile = new File(Objects.requireNonNull(Objects.requireNonNull(data.getData()).getPath()));
            imagePath = Objects.requireNonNull(data.getData()).getPath();

            binding.fragmentRegisterRestaurantImgVLogo.setImageURI(data.getData());
            binding.fragmentRegisterRestaurantCvLogo.setStrokeWidth(0);
            binding.fragmentRegisterRestaurantCvLogo.setStrokeColor(Color.WHITE);
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