package com.elatienda.kaytamarka.sofra.view.fragment.home_cycle.client_cycle.home_cycle;

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
import com.elatienda.kaytamarka.sofra.data.model.restaurant_details.RestaurantDetails;
import com.elatienda.kaytamarka.sofra.databinding.FragmentStoreInformationBinding;
import com.elatienda.kaytamarka.sofra.view.fragment.BaseFragment;

import static com.elatienda.kaytamarka.sofra.data.api.ApiClient.getClient;

public class StoreInformationFragment extends BaseFragment {

    private FragmentStoreInformationBinding binding;
    private RestaurantsViewModel viewModel;

    public StoreInformationFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_store_information,container,false);
        View view = binding.getRoot();
        setUpActivity();

        if (getActivity()!=null){
            //initialize viewModel
            viewModel = new ViewModelProvider(getActivity()).get(RestaurantsViewModel.class);
        }

        getDetails();

        return view;
    }

    private void getDetails() {
        viewModel.getRestaurantDetails(getClient().getRestaurantDetails(43));
        startDetailsCall();
    }

    private void startDetailsCall() {
        if (getActivity() != null){
            viewModel.detailsMutableLiveData.observe(getActivity(), new Observer<RestaurantDetails>() {
                @Override
                public void onChanged(RestaurantDetails restaurantDetails) {
                    try {
                        if (restaurantDetails.getStatus() == 1) {
                            float minimumChargeAsFloat = Float.parseFloat(restaurantDetails.getData().getMinimumCharger());
                            float deliveryFeeAsFloat = Float.parseFloat(restaurantDetails.getData().getDeliveryCost());
                            int minimumChargeAsInteger = (int) minimumChargeAsFloat;
                            int deliveryFeeAsInteger = (int) deliveryFeeAsFloat;
                            String getMinimumCharge = String.valueOf(minimumChargeAsInteger);
                            String getDeliveryFee = String.valueOf(deliveryFeeAsInteger);
                            binding.fragmentStoreInformationTvStatus.setText(restaurantDetails.getData().getAvailability());
                            binding.fragmentStoreInformationTvCity.setText(restaurantDetails.getData().getRegion().getCity().getName());
                            binding.fragmentStoreInformationTvRegion.setText(restaurantDetails.getData().getRegion().getName());
                            binding.fragmentStoreInformationTvMinimumCharge.setText(getString(R.string.fragment_store_information_tv_currency, getMinimumCharge));
                            binding.fragmentStoreInformationTvDeliveryFee.setText(getString(R.string.fragment_store_information_tv_currency, getDeliveryFee));
                        } else {
                            Toast.makeText(getActivity(), "onResponse status-0: \n" + restaurantDetails.getMsg(), Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "Exception : \n" + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    @Override
    public void onBack() {
        super.onBack();
    }
}