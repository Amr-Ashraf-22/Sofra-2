package com.elatienda.kaytamarka.sofra.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.elatienda.kaytamarka.sofra.R;
import com.elatienda.kaytamarka.sofra.databinding.FragmentBlankBinding;

public class BlankFragment extends BaseFragment {

    private FragmentBlankBinding binding;

    public BlankFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_blank,container,false);
        View view = binding.getRoot();
        setUpActivity();


        return view;
    }

    @Override
    public void onBack() {
        super.onBack();
    }
}