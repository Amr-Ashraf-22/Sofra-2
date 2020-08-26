package com.elatienda.kaytamarka.sofra.view.fragment.home_cycle.client_cycle.home_cycle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.elatienda.kaytamarka.sofra.R;
import com.elatienda.kaytamarka.sofra.databinding.FragmentBlankBinding;
import com.elatienda.kaytamarka.sofra.view.fragment.BaseFragment;

public class FoodItemFragment extends BaseFragment {

    private FragmentBlankBinding binding;

    public FoodItemFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_food_item,container,false);
        View view = binding.getRoot();
        setUpActivity();


        return view;
    }

    @Override
    public void onBack() {
        super.onBack();
    }
}