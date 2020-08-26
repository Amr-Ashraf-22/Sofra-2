package com.elatienda.kaytamarka.sofra.adapter;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import androidx.databinding.DataBindingUtil;
import com.elatienda.kaytamarka.sofra.R;
import com.elatienda.kaytamarka.sofra.data.model.general_response_not_paginated.GeneralResponseNotPaginatedData;
import com.elatienda.kaytamarka.sofra.databinding.ItemCustomSpinnerBinding;
import java.util.ArrayList;
import java.util.List;

public class SpinnerAdapter extends BaseAdapter {
    // extends BaseAdapter
    private Context context;
    public List<GeneralResponseNotPaginatedData> generalResponseNotPaginatedData = new ArrayList<>();
    //private LayoutInflater inflater;
    public int selectedId = 0;
    int textColor = 0;
    int textSize = 0;

    private ItemCustomSpinnerBinding binding;

    public SpinnerAdapter(Context applicationContext, int textColor, int textSize){
        this.context = applicationContext;
        this.textColor = textColor;
        this.textSize = textSize;
        //inflater = (LayoutInflater.from(applicationContext));
    }

    public void setData(List<GeneralResponseNotPaginatedData> generalResponseDataList, String hint) {
        generalResponseDataList.add(0, new GeneralResponseNotPaginatedData(0,hint));
        this.generalResponseNotPaginatedData = new ArrayList<>();
        this.generalResponseNotPaginatedData = generalResponseDataList;
    }

    public void clear() {
        this.generalResponseNotPaginatedData.clear();
    }

    @Override
    public int getCount() {
        return generalResponseNotPaginatedData.size();
    }

    @Override
    public Object getItem(int position) {
        return generalResponseNotPaginatedData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.item_custom_spinner, parent ,false);

            convertView = binding.getRoot();
        }

        binding.itemCustomSpinnerTvText.setText(generalResponseNotPaginatedData.get(position).getName());
        // if spinner in user cycle ==> White
        // if spinner in home cycle ==> fragment_forgot_password_et_hint
        if(textColor!=0){
            binding.itemCustomSpinnerTvText.setTextColor(textColor);
        }
        if (textSize!=0){
            binding.itemCustomSpinnerTvText.setTextSize(TypedValue.COMPLEX_UNIT_PX,context.getResources().getDimensionPixelSize(textSize));
        }
        selectedId = generalResponseNotPaginatedData.get(position).getId();

        return convertView;
    }
}
