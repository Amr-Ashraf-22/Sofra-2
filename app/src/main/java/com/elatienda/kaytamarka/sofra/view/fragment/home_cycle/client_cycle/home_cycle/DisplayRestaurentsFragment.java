package com.elatienda.kaytamarka.sofra.view.fragment.home_cycle.client_cycle.home_cycle;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.elatienda.kaytamarka.sofra.R;
import com.elatienda.kaytamarka.sofra.adapter.RestaurantsAdapter;
import com.elatienda.kaytamarka.sofra.adapter.SpinnerAdapter;
import com.elatienda.kaytamarka.sofra.data.model.display_restaurants.DisplayRestaurants;
import com.elatienda.kaytamarka.sofra.data.model.display_restaurants.DisplayRestaurantsData;
import com.elatienda.kaytamarka.sofra.data.model.general_response_not_paginated.GeneralResponseNotPaginated;
import com.elatienda.kaytamarka.sofra.databinding.ActivityHomeCycleBinding;
import com.elatienda.kaytamarka.sofra.databinding.FragmentDisplayRestaurantsBinding;
import com.elatienda.kaytamarka.sofra.util.HelperMethod;
import com.elatienda.kaytamarka.sofra.util.OnEndLess;
import com.elatienda.kaytamarka.sofra.view.activity.SplashActivity;
import com.elatienda.kaytamarka.sofra.view.fragment.BaseFragment;
import com.elatienda.kaytamarka.sofra.view.fragment.BaseViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import static com.elatienda.kaytamarka.sofra.data.api.ApiClient.getClient;
import static com.elatienda.kaytamarka.sofra.util.HelperMethod.keyboardVisibility;

public class DisplayRestaurentsFragment extends BaseFragment {

    private FragmentDisplayRestaurantsBinding binding;
    private ActivityHomeCycleBinding activityBinding;
    private BottomNavigationView navBar;
    private BaseViewModel baseModel;
    private SpinnerAdapter cityAdapter;
    private Integer maxPage = 0;
    private OnEndLess onEndLess;
    private boolean Filter = false;
    private RestaurantsAdapter restaurantsAdapter;
    private List<DisplayRestaurantsData> restaurantDataList = new ArrayList<>();
    private RestaurantsViewModel viewModel;

    public DisplayRestaurentsFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_display_restaurants,container,false);
        View view = binding.getRoot();
        setUpActivity();

        cityAdapter = new SpinnerAdapter(getActivity(), Color.parseColor("#B5B5B5"),R.dimen._8ssp);

        if (getActivity()!=null){
            //initialize viewModel
            viewModel = new ViewModelProvider(getActivity()).get(RestaurantsViewModel.class);
            baseModel = new ViewModelProvider(getActivity()).get(BaseViewModel.class);
        }

        activityBinding = DataBindingUtil.inflate(inflater, R.layout.activity_home_cycle ,container,false);

        HelperMethod.customToolbar(getActivity(),false,"", R.color.white);

        if (navBar != null){
            navBar.setVisibility(View.VISIBLE);
        }

        keyboardVisibility(view);

        getCities();
        initRecyclerView();

        binding.fragmentDisplayRestaurantsEtRestaurant.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(binding.fragmentDisplayRestaurantsSpCity.getSelectedItemPosition() != 0){
                    onFilter(1);
                }
            }
        });

        binding.fragmentDisplayRestaurantsSpCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               if (position != 0){
                   onFilter(1);
               }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    private void initRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.fragmentDisplayRestaurantsRvRestaurants.setHasFixedSize(true);
        binding.fragmentDisplayRestaurantsRvRestaurants.setLayoutManager(layoutManager);

        onEndLess = new OnEndLess(layoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= maxPage) {
                    if (maxPage != 0 && current_page != 1) {
                        onEndLess.previous_page = current_page;

                        if (Filter){
                            onFilter(current_page);
                        }else {
                            getRestaurants(current_page);
                        }

                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }
                } else {
                    onEndLess.current_page = onEndLess.previous_page;
                }

            }
        };
        binding.fragmentDisplayRestaurantsRvRestaurants.addOnScrollListener(onEndLess);

        if(getActivity()!=null){
            restaurantsAdapter = new RestaurantsAdapter(getActivity(), restaurantDataList, getActivity().getSupportFragmentManager(), getActivity());
            binding.fragmentDisplayRestaurantsRvRestaurants.setAdapter(restaurantsAdapter);
        }
        getRestaurants(1);
    }

    private void getRestaurants(int page) {
        viewModel.getRestaurants(getClient().getRestaurants(page));
        startRestaurantsCall(page);
    }

    private void onFilter(int page) {
        Filter = true;
        if (page==1) {
            onEndLess.previousTotal = 0;
            onEndLess.current_page = 1;
            onEndLess.previous_page = 1;
            restaurantDataList = new ArrayList<>();
            if (getActivity() != null) {
                restaurantsAdapter = new RestaurantsAdapter(getActivity(), restaurantDataList, getActivity().getSupportFragmentManager(), getActivity());
                binding.fragmentDisplayRestaurantsRvRestaurants.setAdapter(restaurantsAdapter);
            }
        }

        viewModel.getRestaurants(getClient().getRestaurantsFilter(
                page,
                binding.fragmentDisplayRestaurantsEtRestaurant.getText().toString(),
                binding.fragmentDisplayRestaurantsSpCity.getSelectedItemPosition() ));

        startRestaurantsCall(page);
    }

    private void startRestaurantsCall(int page) {
        if (getActivity() != null){
            viewModel.restaurantsMutableLiveData.observe(getActivity(), new Observer<DisplayRestaurants>() {
                @Override
                public void onChanged(DisplayRestaurants displayRestaurants) {
                    try {
                        if (displayRestaurants.getStatus() == 1) {
                            maxPage = displayRestaurants.getData().getLastPage();
                            restaurantDataList.addAll(displayRestaurants.getData().getData());
                            restaurantsAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getActivity(), "onResponse status-0: \n" + displayRestaurants.getMsg(), Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "Exception : \n" + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
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
                    try {
                        if(generalResponseNotPaginated.getStatus() == 1){
                            cityAdapter.clear();
                            binding.fragmentDisplayRestaurantsSpCity.setAdapter(null);
                            cityAdapter.setData(generalResponseNotPaginated.getData(),getString(R.string.spinner_city));
                            binding.fragmentDisplayRestaurantsSpCity.setAdapter(cityAdapter);
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

    @Override
    public void onBack() {
        startActivity(new Intent(getActivity(), SplashActivity.class));
    }
}