package com.elatienda.kaytamarka.sofra.view.fragment.home_cycle.client_cycle.home_cycle;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.elatienda.kaytamarka.sofra.R;
import com.elatienda.kaytamarka.sofra.adapter.TabLayoutAdapter;
import com.elatienda.kaytamarka.sofra.data.model.restaurant_details.RestaurantDetails;
import com.elatienda.kaytamarka.sofra.databinding.FragmentRestaurantContainerBinding;
import com.elatienda.kaytamarka.sofra.util.HelperMethod;
import com.elatienda.kaytamarka.sofra.view.fragment.BaseFragment;

import static com.elatienda.kaytamarka.sofra.data.api.ApiClient.getClient;

public class RestaurantContainerFragment extends BaseFragment {

    private FragmentRestaurantContainerBinding binding;
    private RestaurantsViewModel viewModel;
    private String titleToolbar = null;

    public RestaurantContainerFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_restaurant_container,container,false);
        View view = binding.getRoot();
        setUpActivity();

        if(getActivity()!= null){
            viewModel = new ViewModelProvider(getActivity()).get(RestaurantsViewModel.class);
        }

        HelperMethod.customToolbar(getActivity(),false, "" , 0);


        getRestaurantName();

        addPages();

        binding.fragmentRestaurantContainerVpPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //Toast.makeText(getActivity(), "onPageScrolled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageSelected(int position) {
                //Toast.makeText(getActivity(), "onPageSelected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //Toast.makeText(getActivity(), "onPageScrollStateChanged", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }

    private void getRestaurantName() {
        viewModel.getRestaurantDetails(getClient().getRestaurantDetails(43));
        startNameCall();
    }

    private void startNameCall() {
        if (getActivity()!=null){
            viewModel.detailsMutableLiveData.observe(getActivity(), new Observer<RestaurantDetails>() {
                @Override
                public void onChanged(RestaurantDetails restaurantDetails) {
                    try {
                        if (restaurantDetails.getStatus() == 1) {
                            titleToolbar = restaurantDetails.getData().getName();
                            if (titleToolbar!=null){
                                HelperMethod.customToolbar(getActivity(),true, titleToolbar , Color.parseColor("#FF4057"));
                            }
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

    private void addPages(){
        if (getActivity()!=null){                       //getActivity().getSupportFragmentManager()
            TabLayoutAdapter adapter = new TabLayoutAdapter(getChildFragmentManager());
            adapter.addPager(new FoodMenuFragment(),getResources().getString(R.string.restaurant_container_tl_food_menu));
            adapter.addPager(new RestaurantReviewsFragment(),getResources().getString(R.string.restaurant_container_tl_comments_rating));
            adapter.addPager(new StoreInformationFragment(),getResources().getString(R.string.restaurant_container_tl_store_info));
            binding.fragmentRestaurantContainerVpPager.setAdapter(adapter);
            //binding.fragmentRestaurantContainerVpPager.setCurrentItem(1);
            binding.fragmentRestaurantContainerTlTabs.setupWithViewPager(binding.fragmentRestaurantContainerVpPager);
        }
    }

    @Override
    public void onBack() {
        super.onBack();
    }
}