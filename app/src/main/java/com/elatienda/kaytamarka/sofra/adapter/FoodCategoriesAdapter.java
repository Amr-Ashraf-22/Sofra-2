package com.elatienda.kaytamarka.sofra.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.elatienda.kaytamarka.sofra.R;
import com.elatienda.kaytamarka.sofra.data.model.food_categories.FoodCategoriesData;
import com.elatienda.kaytamarka.sofra.data.model.restaurant_items.RestaurantItems;
import com.elatienda.kaytamarka.sofra.data.model.restaurant_items.RestaurantItemsData;
import com.elatienda.kaytamarka.sofra.databinding.ItemCustomFoodCategoriesBinding;
import com.elatienda.kaytamarka.sofra.view.fragment.home_cycle.client_cycle.home_cycle.RestaurantsViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.elatienda.kaytamarka.sofra.data.api.ApiClient.getClient;

public class FoodCategoriesAdapter extends RecyclerView.Adapter<FoodCategoriesAdapter.DataViewHolder> {

    private Context mContext;
    private Activity mActivity;
    private List<FoodCategoriesData> categoriesDataList = new ArrayList<>();
    private FragmentManager mFragmentManager;
    public static int categoriesId;
    private ItemCustomFoodCategoriesBinding binding;
    private RestaurantsViewModel viewModel;
    private RecyclerView recyclerView;
    private RestaurantItemsAdapter itemsAdapter;
    private List<RestaurantItemsData> itemsDataList = new ArrayList<>();
    //private int getPosition = 0;

    public FoodCategoriesAdapter(Context context,
                                 List<FoodCategoriesData> categoriesData,
                                 FragmentManager fragmentManager,
                                 Activity activity) {
        this.mContext = context;
        this.categoriesDataList = categoriesData;
        this.mFragmentManager = fragmentManager;
        this.mActivity = activity;
    }

    public FoodCategoriesAdapter(Context context,
                                 List<FoodCategoriesData> categoriesData,
                                 FragmentManager fragmentManager,
                                 Activity activity,
                                 RecyclerView recyclerView,
                                 List<RestaurantItemsData> itemsDataList) {
        this.mContext = context;
        this.categoriesDataList = categoriesData;
        this.mFragmentManager = fragmentManager;
        this.mActivity = activity;
        if(mContext!= null){
            viewModel = new ViewModelProvider((ViewModelStoreOwner) mContext).get(RestaurantsViewModel.class);
        }
        this.recyclerView = recyclerView;
        this.itemsDataList = itemsDataList;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_custom_food_categories,parent,false);

        return new DataViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        //getPosition = position;
        setData(holder, position);
    }

    public void setData(DataViewHolder holder, int position) {
        FoodCategoriesData categoriesData = categoriesDataList.get(position);
        holder.binding.itemCustomFoodCategoriesTvCategory.setText(categoriesData.getName());
        Glide.with(mContext)
                .load(categoriesData.getPhotoUrl())
                .centerCrop()
                .into(holder.binding.itemCustomFoodCategoriesImgVCategory);

        categoriesId = categoriesDataList.get(position).getId();

        holder.binding.itemCustomFoodCategoriesRlContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterItems(position);
                //Toast.makeText(mContext, "adapter", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void filterItems(int position) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        if (mContext != null) {
            itemsAdapter = new RestaurantItemsAdapter(mContext, itemsDataList , mFragmentManager, mActivity);
            recyclerView.setAdapter(itemsAdapter);
        }
        getFilterItems(position);
    }

    private void getFilterItems(int position) {
        Toast.makeText(mContext,position+"" , Toast.LENGTH_SHORT).show();
        viewModel.getRestaurantItems(getClient().getRestaurantItems(43,position));
        startFilterItemsCall();
    }

    private void startFilterItemsCall() {
        if (mContext!=null){
            viewModel.itemsMutableLiveData.observe((LifecycleOwner) mContext, new Observer<RestaurantItems>() {
                @Override
                public void onChanged(RestaurantItems restaurantItems) {
                    try {
                        if (restaurantItems.getStatus() == 1) {
                            itemsDataList.addAll(restaurantItems.getData().getData());
                            itemsAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(mContext, "onResponse status-0: \n" + restaurantItems.getMsg(), Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(mContext, "Exception : \n" + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }

    public void setAction(DataViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return categoriesDataList.size();
    }

    class DataViewHolder extends RecyclerView.ViewHolder {

        private ItemCustomFoodCategoriesBinding binding;

        public DataViewHolder(ItemCustomFoodCategoriesBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }

    }
}
