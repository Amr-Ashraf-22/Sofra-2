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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.elatienda.kaytamarka.sofra.R;
import com.elatienda.kaytamarka.sofra.adapter.FoodCategoriesAdapter;
import com.elatienda.kaytamarka.sofra.adapter.RestaurantItemsAdapter;
import com.elatienda.kaytamarka.sofra.data.model.food_categories.FoodCategories;
import com.elatienda.kaytamarka.sofra.data.model.food_categories.FoodCategoriesData;
import com.elatienda.kaytamarka.sofra.data.model.restaurant_items.RestaurantItems;
import com.elatienda.kaytamarka.sofra.data.model.restaurant_items.RestaurantItemsData;
import com.elatienda.kaytamarka.sofra.databinding.ActivityHomeCycleBinding;
import com.elatienda.kaytamarka.sofra.databinding.FragmentFoodMenuBinding;
import com.elatienda.kaytamarka.sofra.view.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import static com.elatienda.kaytamarka.sofra.data.api.ApiClient.getClient;

public class FoodMenuFragment extends BaseFragment {

    private FragmentFoodMenuBinding binding;
    private FoodCategoriesAdapter categoriesAdapter;
    private RestaurantItemsAdapter itemsAdapter;
    private List<FoodCategoriesData> categoriesDataList = new ArrayList<>();
    private List<RestaurantItemsData> itemsDataList = new ArrayList<>();
    private RestaurantsViewModel viewModel;
    private ActivityHomeCycleBinding activityBinding;

    public FoodMenuFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_food_menu,container,false);
        View view = binding.getRoot();
        setUpActivity();

        if(getActivity()!= null){
            viewModel = new ViewModelProvider(getActivity()).get(RestaurantsViewModel.class);
        }

        activityBinding = DataBindingUtil.inflate(inflater, R.layout.activity_home_cycle ,container,false);

        initCategories();
        initItems();

        return view;
    }

    private void initItems() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.fragmentFoodMenuRvItems.setHasFixedSize(true);
        binding.fragmentFoodMenuRvItems.setLayoutManager(layoutManager);
        if (getActivity() != null) {
            itemsAdapter = new RestaurantItemsAdapter(getContext(), itemsDataList , getActivity().getSupportFragmentManager(), getActivity());
            binding.fragmentFoodMenuRvItems.setAdapter(itemsAdapter);
        }

        getItems();
    }

    private void getItems() {
        viewModel.getRestaurantItems(getClient().getRestaurantItems(43,0));
        startItemsCall();
    }

    private void startItemsCall() {
        if (getActivity()!=null){
            viewModel.itemsMutableLiveData.observe(getActivity(), new Observer<RestaurantItems>() {
                @Override
                public void onChanged(RestaurantItems restaurantItems) {
                    try {
                        if (restaurantItems.getStatus() == 1) {
                            itemsDataList.addAll(restaurantItems.getData().getData());
                            itemsAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getActivity(), "onResponse status-0: \n" + restaurantItems.getMsg(), Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "Exception : \n" + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    private void initCategories() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.fragmentFoodMenuRvCategories.setHasFixedSize(true);
        binding.fragmentFoodMenuRvCategories.setLayoutManager(layoutManager);

        if (getActivity() != null) {
            categoriesAdapter = new FoodCategoriesAdapter(getContext(), categoriesDataList , getActivity().getSupportFragmentManager(), getActivity(), binding.fragmentFoodMenuRvItems, itemsDataList );

            LinearLayoutManager HorizontalLayout = new LinearLayoutManager( getActivity(), LinearLayoutManager.HORIZONTAL, false);
            binding.fragmentFoodMenuRvCategories.setLayoutManager(HorizontalLayout);

            binding.fragmentFoodMenuRvCategories.setAdapter(categoriesAdapter);
        }

        getCategories();
    }

    private void getCategories() {
        viewModel.getFoodCategories(getClient().getFoodCategories(43,0));
        startCategoriesCall();
    }

    private void startCategoriesCall() {
        if (getActivity()!=null){
            viewModel.categoriesMutableLiveData.observe(getActivity(), new Observer<FoodCategories>() {
                @Override
                public void onChanged(FoodCategories foodCategories) {
                    try {
                        if (foodCategories.getStatus() == 1) {
                            categoriesDataList.addAll(foodCategories.getData());
                            categoriesAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getActivity(), "onResponse status-0: \n" + foodCategories.getMsg(), Toast.LENGTH_LONG).show();
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