package com.elatienda.kaytamarka.sofra.view.fragment.home_cycle.client_cycle.home_cycle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.elatienda.kaytamarka.sofra.data.model.add_restaurant_review.AddRestaurantReview;
import com.elatienda.kaytamarka.sofra.data.model.display_all_reviews.DisplayAllReviews;
import com.elatienda.kaytamarka.sofra.data.model.display_restaurants.DisplayRestaurants;
import com.elatienda.kaytamarka.sofra.data.model.food_categories.FoodCategories;
import com.elatienda.kaytamarka.sofra.data.model.restaurant_details.RestaurantDetails;
import com.elatienda.kaytamarka.sofra.data.model.restaurant_items.RestaurantItems;
import com.elatienda.kaytamarka.sofra.view.fragment.BaseViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantsViewModel extends BaseViewModel {

    public MutableLiveData<DisplayRestaurants> restaurantsMutableLiveData;
    public MutableLiveData<RestaurantDetails> detailsMutableLiveData;
    public MutableLiveData<FoodCategories> categoriesMutableLiveData;
    public MutableLiveData<RestaurantItems> itemsMutableLiveData;
    public MutableLiveData<DisplayAllReviews> reviewsMutableLiveData;
    public MutableLiveData<AddRestaurantReview> addReviewsMutableLiveData;
    public MutableLiveData<Boolean> mutableLiveDataError;
    public String ErrorText, onFailureMsg;

    public void getRestaurants(Call<DisplayRestaurants> call){
        restaurantsMutableLiveData = new MutableLiveData<>();
        mutableLiveDataError = new MutableLiveData<>();
        call.enqueue(new Callback<DisplayRestaurants>() {
            @Override
            public void onResponse(@NonNull Call<DisplayRestaurants> call, @NonNull Response<DisplayRestaurants> response) {
                restaurantsMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<DisplayRestaurants> call, @NonNull Throwable t) {
                ErrorText = null;
                onFailureMsg = t.getMessage();
                mutableLiveDataError.setValue(true);
            }
        });
    }

    public void getFoodCategories(Call<FoodCategories> call){
        categoriesMutableLiveData = new MutableLiveData<>();
        mutableLiveDataError = new MutableLiveData<>();
        call.enqueue(new Callback<FoodCategories>() {
            @Override
            public void onResponse(@NonNull Call<FoodCategories> call, @NonNull Response<FoodCategories> response) {
                categoriesMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<FoodCategories> call, @NonNull Throwable t) {
                ErrorText = null;
                onFailureMsg = t.getMessage();
                mutableLiveDataError.setValue(true);
            }
        });
    }

    public void getRestaurantItems(Call<RestaurantItems> call){
        itemsMutableLiveData = new MutableLiveData<>();
        mutableLiveDataError = new MutableLiveData<>();
        call.enqueue(new Callback<RestaurantItems>() {
            @Override
            public void onResponse(@NonNull Call<RestaurantItems> call, @NonNull Response<RestaurantItems> response) {
                itemsMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<RestaurantItems> call, @NonNull Throwable t) {
                ErrorText = null;
                onFailureMsg = t.getMessage();
                mutableLiveDataError.setValue(true);
            }
        });
    }

    public void getRestaurantDetails(Call<RestaurantDetails> call){
        detailsMutableLiveData = new MutableLiveData<>();
        mutableLiveDataError = new MutableLiveData<>();
        call.enqueue(new Callback<RestaurantDetails>() {
            @Override
            public void onResponse(@NonNull Call<RestaurantDetails> call, @NonNull Response<RestaurantDetails> response) {
                detailsMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<RestaurantDetails> call, @NonNull Throwable t) {
                ErrorText = null;
                onFailureMsg = t.getMessage();
                mutableLiveDataError.setValue(true);
            }
        });
    }

    public void getReviews(Call<DisplayAllReviews> call){
        reviewsMutableLiveData = new MutableLiveData<>();
        mutableLiveDataError = new MutableLiveData<>();
        call.enqueue(new Callback<DisplayAllReviews>() {
            @Override
            public void onResponse(@NonNull Call<DisplayAllReviews> call, @NonNull Response<DisplayAllReviews> response) {
                reviewsMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<DisplayAllReviews> call, @NonNull Throwable t) {
                ErrorText = null;
                onFailureMsg = t.getMessage();
                mutableLiveDataError.setValue(true);
            }
        });
    }

    public void addReview(Call<AddRestaurantReview> call){
        addReviewsMutableLiveData = new MutableLiveData<>();
        mutableLiveDataError = new MutableLiveData<>();
        call.enqueue(new Callback<AddRestaurantReview>() {
            @Override
            public void onResponse(@NonNull Call<AddRestaurantReview> call, @NonNull Response<AddRestaurantReview> response) {
                addReviewsMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<AddRestaurantReview> call, @NonNull Throwable t) {
                ErrorText = null;
                onFailureMsg = t.getMessage();
                mutableLiveDataError.setValue(true);
            }
        });
    }
}