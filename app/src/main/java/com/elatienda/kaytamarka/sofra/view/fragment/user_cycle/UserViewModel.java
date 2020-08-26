package com.elatienda.kaytamarka.sofra.view.fragment.user_cycle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import com.elatienda.kaytamarka.sofra.data.model.login.Login;
import com.elatienda.kaytamarka.sofra.data.model.new_password.NewPassword;
import com.elatienda.kaytamarka.sofra.data.model.register.ClientRegister;
import com.elatienda.kaytamarka.sofra.data.model.register.RestaurantRegister;
import com.elatienda.kaytamarka.sofra.data.model.reset_password.ResetPassword;
import com.elatienda.kaytamarka.sofra.view.fragment.BaseViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserViewModel extends BaseViewModel {

    public MutableLiveData<Login> loginMutableLiveData ;
    public MutableLiveData<ClientRegister> clientRegisterMutableLiveData ;
    public MutableLiveData<RestaurantRegister> restaurantRegisterMutableLiveData ;
    public MutableLiveData<ResetPassword> resetPasswordMutableLiveData ;
    public MutableLiveData<NewPassword> newPasswordMutableLiveData ;
    public MutableLiveData<Boolean> mutableLiveDataError;
    public String ErrorText, onFailureMsg;

    public void onLogin(Call<Login> call){
        loginMutableLiveData = new MutableLiveData<>();
        mutableLiveDataError = new MutableLiveData<>();
        call.clone().enqueue(new Callback<Login>() {
            @Override
            public void onResponse(@NonNull Call<Login> call, @NonNull Response<Login> response) {
                loginMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<Login> call, @NonNull Throwable t) {
                ErrorText = null;
                onFailureMsg = t.getMessage();
                mutableLiveDataError.setValue(true);
            }
        });
    }

    public void onClientRegister(Call<ClientRegister> call){
        clientRegisterMutableLiveData = new MutableLiveData<>();
        mutableLiveDataError = new MutableLiveData<>();
        call.enqueue(new Callback<ClientRegister>() {
            @Override
            public void onResponse(@NonNull Call<ClientRegister> call, @NonNull Response<ClientRegister> response) {
                clientRegisterMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ClientRegister> call, @NonNull Throwable t) {
                ErrorText = null;
                onFailureMsg = t.getMessage();
                mutableLiveDataError.setValue(true);
            }
        });
    }

    public void onRestaurantRegister(Call<RestaurantRegister> call){
        restaurantRegisterMutableLiveData = new MutableLiveData<>();
        mutableLiveDataError = new MutableLiveData<>();
        call.enqueue(new Callback<RestaurantRegister>() {
            @Override
            public void onResponse(@NonNull Call<RestaurantRegister> call, @NonNull Response<RestaurantRegister> response) {
                restaurantRegisterMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<RestaurantRegister> call, @NonNull Throwable t) {
                ErrorText = null;
                onFailureMsg = t.getMessage();
                mutableLiveDataError.setValue(true);
            }
        });
    }

    public void onResetPassword(Call<ResetPassword> call){
        resetPasswordMutableLiveData = new MutableLiveData<>();
        mutableLiveDataError = new MutableLiveData<>();
        call.enqueue(new Callback<ResetPassword>() {
            @Override
            public void onResponse(@NonNull Call<ResetPassword> call, @NonNull Response<ResetPassword> response) {
                resetPasswordMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ResetPassword> call, @NonNull Throwable t) {
                ErrorText = null;
                onFailureMsg = t.getMessage();
                mutableLiveDataError.setValue(true);
            }
        });
    }


    public void onNewPassword(Call<NewPassword> call){
        newPasswordMutableLiveData = new MutableLiveData<>();
        mutableLiveDataError = new MutableLiveData<>();
        call.enqueue(new Callback<NewPassword>() {
            @Override
            public void onResponse(@NonNull Call<NewPassword> call, @NonNull Response<NewPassword> response) {
                newPasswordMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<NewPassword> call, @NonNull Throwable t) {
                ErrorText = null;
                onFailureMsg = t.getMessage();
                mutableLiveDataError.setValue(true);
            }
        });
    }
}
