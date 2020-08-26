package com.elatienda.kaytamarka.sofra.view.fragment;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.elatienda.kaytamarka.sofra.data.model.general_response_not_paginated.GeneralResponseNotPaginated;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaseViewModel extends ViewModel {

    public MutableLiveData<GeneralResponseNotPaginated> spinnerMutableLiveData;
    public MutableLiveData<Boolean> mutableLiveDataError;
    public String ErrorText, onFailureMsg;

    public void getSpinnerData(Call<GeneralResponseNotPaginated> call){
        spinnerMutableLiveData = new MutableLiveData<>();
        mutableLiveDataError = new MutableLiveData<>();

        call.enqueue(new Callback<GeneralResponseNotPaginated>() {
            @Override
            public void onResponse(@NonNull Call<GeneralResponseNotPaginated> call, @NonNull Response<GeneralResponseNotPaginated> response) {
                spinnerMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<GeneralResponseNotPaginated> call, @NonNull Throwable t) {
                ErrorText = null;
                onFailureMsg = t.getMessage();
                mutableLiveDataError.setValue(true);
            }
        });
    }
}
