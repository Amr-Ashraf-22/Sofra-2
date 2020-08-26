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
import com.elatienda.kaytamarka.sofra.data.model.display_restaurants.DisplayRestaurantsData;
import com.elatienda.kaytamarka.sofra.databinding.ItemCustomRestaurantsBinding;

import java.util.ArrayList;
import java.util.List;

import static com.elatienda.kaytamarka.sofra.util.Constants.RESTAURANT_AVAILABILITY_CLOSED;
import static com.elatienda.kaytamarka.sofra.util.Constants.RESTAURANT_AVAILABILITY_OPENED;

public class RestaurantsAdapter extends RecyclerView.Adapter<RestaurantsAdapter.DataViewHolder> {

    private Context mContext;
    private Activity mActivity;
    private List<DisplayRestaurantsData> restaurantDataList = new ArrayList<>();
    private FragmentManager mFragmentManager;
    public static int restaurantId;

    public RestaurantsAdapter(Context context, List<DisplayRestaurantsData> restaurantData, FragmentManager fragmentManager, Activity activity) {
        this.mContext = context;
        this.restaurantDataList = restaurantData;
        this.mFragmentManager = fragmentManager;
        this.mActivity = activity;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCustomRestaurantsBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_custom_restaurants,parent,false);

        return new DataViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        setData(holder, position);
    }

    public void setData(DataViewHolder holder, int position) {
        DisplayRestaurantsData restaurantData = restaurantDataList.get(position);
        //Minimum order
        //delivery fee
        float minimumOrderAsFloat = Float.parseFloat(restaurantData.getMinimumCharger());
        float deliveryFeeAsFloat = Float.parseFloat(restaurantData.getDeliveryCost());
        int minimumOrderAsInteger = (int) minimumOrderAsFloat;
        int deliveryFeeAsInteger = (int) deliveryFeeAsFloat;
        String getMinimumOrder = String.valueOf(minimumOrderAsInteger);
        String getDeliveryFee = String.valueOf(deliveryFeeAsInteger);
        String minimumOrder = mContext.getString(R.string.fragment_display_restaurants_txt_minimum_order, getMinimumOrder);
        String deliveryFee = mContext.getString(R.string.fragment_display_restaurants_txt_delivery_fee, getDeliveryFee);


        holder.binding.itemCustomRestaurantsTvName.setText(restaurantData.getName());
        holder.binding.itemCustomRestaurantsRbRating.setEnabled(false);
        holder.binding.itemCustomRestaurantsRbRating.setRating((float) restaurantData.getRate());
        holder.binding.itemCustomRestaurantsTvMinimumOrder.setText(mContext.getString(R.string.fragment_food_menu_txt_currency, minimumOrder));
        holder.binding.itemCustomRestaurantsTvDeliveryFee.setText(mContext.getString(R.string.fragment_food_menu_txt_currency, deliveryFee));

        Glide.with(mContext)
                .load(restaurantData.getPhotoUrl())
                .centerCrop()
                .into(holder.binding.itemCustomRestaurantsImgVLogo);

        if(restaurantData.getAvailability().equals(RESTAURANT_AVAILABILITY_OPENED)){
            holder.binding.itemCustomRestaurantsImgVHours.setImageResource(R.drawable.ic_opened_icon);
            holder.binding.itemCustomRestaurantsTvHours.setText(restaurantData.getAvailability());
        }else if (restaurantData.getAvailability().equals(RESTAURANT_AVAILABILITY_CLOSED)){
            holder.binding.itemCustomRestaurantsImgVHours.setImageResource(R.drawable.ic_closed_icon);
        }
        restaurantId = restaurantDataList.get(position).getId();
    }

    public void clear() {
        restaurantDataList.clear();
    }

        public void setAction(DataViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return restaurantDataList.size();
    }

    class DataViewHolder extends RecyclerView.ViewHolder {

        private ItemCustomRestaurantsBinding binding;

        public DataViewHolder(ItemCustomRestaurantsBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
