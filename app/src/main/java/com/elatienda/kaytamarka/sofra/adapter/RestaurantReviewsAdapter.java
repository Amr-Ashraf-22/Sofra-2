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
import com.elatienda.kaytamarka.sofra.data.model.display_all_reviews.DisplayAllReviewsData;
import com.elatienda.kaytamarka.sofra.data.model.restaurant_items.RestaurantItemsData;
import com.elatienda.kaytamarka.sofra.databinding.ItemCustomRestaurantItemBinding;
import com.elatienda.kaytamarka.sofra.databinding.ItemCustomRestaurantReviewsBinding;

import java.util.ArrayList;
import java.util.List;

public class RestaurantReviewsAdapter extends RecyclerView.Adapter<RestaurantReviewsAdapter.DataViewHolder> {

    private Context mContext;
    private Activity mActivity;
    private List<DisplayAllReviewsData> reviewsDataList = new ArrayList<>();
    private FragmentManager mFragmentManager;
    public static int itemId;

    public RestaurantReviewsAdapter(Context context, List<DisplayAllReviewsData> reviewsData, FragmentManager fragmentManager, Activity activity) {
        this.mContext = context;
        this.reviewsDataList = reviewsData;
        this.mFragmentManager = fragmentManager;
        this.mActivity = activity;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCustomRestaurantReviewsBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_custom_restaurant_reviews ,parent,false);

        return new DataViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        setData(holder, position);
    }

    public void setData(DataViewHolder holder, int position) {
        DisplayAllReviewsData reviewsData = reviewsDataList.get(position);

        holder.binding.itemCustomRestaurantReviewsTvName.setText(reviewsData.getClient().getName());

        holder.binding.itemCustomRestaurantReviewsTvComment.setText(reviewsData.getComment());

        if (reviewsData.getRate().equals("1")){
            holder.binding.itemCustomRestaurantReviewsImgVEmoji.setImageResource(R.drawable.ic_smile_icon);
        }else if (reviewsData.getRate().equals("2")){
            holder.binding.itemCustomRestaurantReviewsImgVEmoji.setImageResource(R.drawable.ic_sad_icon);
        }else if (reviewsData.getRate().equals("3")){
            holder.binding.itemCustomRestaurantReviewsImgVEmoji.setImageResource(R.drawable.ic_smile_icon);
        }else if (reviewsData.getRate().equals("4")){
            holder.binding.itemCustomRestaurantReviewsImgVEmoji.setImageResource(R.drawable.ic_sad_icon);
        }else if (reviewsData.getRate().equals("5")){
            holder.binding.itemCustomRestaurantReviewsImgVEmoji.setImageResource(R.drawable.ic_smile_icon);
        }

        itemId = reviewsDataList.get(position).getId();
    }

    public void setAction(DataViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return reviewsDataList.size();
    }

    class DataViewHolder extends RecyclerView.ViewHolder {

        private ItemCustomRestaurantReviewsBinding binding;

        public DataViewHolder(ItemCustomRestaurantReviewsBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
