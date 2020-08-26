package com.elatienda.kaytamarka.sofra.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.elatienda.kaytamarka.sofra.R;
import com.elatienda.kaytamarka.sofra.data.model.restaurant_items.RestaurantItemsData;
import com.elatienda.kaytamarka.sofra.databinding.ItemCustomRestaurantItemBinding;

import java.util.ArrayList;
import java.util.List;

public class RestaurantItemsAdapter extends RecyclerView.Adapter<RestaurantItemsAdapter.DataViewHolder> {

    private Context mContext;
    private Activity mActivity;
    private List<RestaurantItemsData> itemsDataList = new ArrayList<>();
    private FragmentManager mFragmentManager;
    public static int itemId;

    public RestaurantItemsAdapter(Context context, List<RestaurantItemsData> itemsData, FragmentManager fragmentManager, Activity activity) {
        this.mContext = context;
        this.itemsDataList = itemsData;
        this.mFragmentManager = fragmentManager;
        this.mActivity = activity;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCustomRestaurantItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_custom_restaurant_item,parent,false);

        return new DataViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        setData(holder, position);
    }

    public void setData(DataViewHolder holder, int position) {
        RestaurantItemsData itemsData = itemsDataList.get(position);

        Glide.with(mContext)
                .load(itemsData.getPhotoUrl())
                .centerCrop()
                .into(holder.binding.itemCustomRestaurantItemImgVFood);

        float priceAsFloat = Float.parseFloat(itemsData.getPrice());
        int priceAsInteger = (int) priceAsFloat;
        String getPrice = String.valueOf(priceAsInteger);
        String price = mContext.getString(R.string.fragment_food_menu_txt_price, getPrice);

        holder.binding.itemCustomRestaurantItemTvName.setText(itemsData.getName());
        holder.binding.itemCustomRestaurantItemTvDescription.setText(itemsData.getDescription());
        holder.binding.itemCustomRestaurantItemTvPrice.setText(mContext.getString(R.string.fragment_food_menu_txt_currency, price));
        itemId = itemsDataList.get(position).getId();
    }

    public void setAction(DataViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return itemsDataList.size();
    }

    class DataViewHolder extends RecyclerView.ViewHolder {

        private ItemCustomRestaurantItemBinding binding;

        public DataViewHolder(ItemCustomRestaurantItemBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
