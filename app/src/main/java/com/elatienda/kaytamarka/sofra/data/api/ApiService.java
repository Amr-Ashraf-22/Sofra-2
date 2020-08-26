package com.elatienda.kaytamarka.sofra.data.api;

import com.elatienda.kaytamarka.sofra.data.model.add_restaurant_review.AddRestaurantReview;
import com.elatienda.kaytamarka.sofra.data.model.display_all_reviews.DisplayAllReviews;
import com.elatienda.kaytamarka.sofra.data.model.display_restaurants.DisplayRestaurants;
import com.elatienda.kaytamarka.sofra.data.model.food_categories.FoodCategories;
import com.elatienda.kaytamarka.sofra.data.model.general_response_not_paginated.GeneralResponseNotPaginated;
import com.elatienda.kaytamarka.sofra.data.model.login.Login;
import com.elatienda.kaytamarka.sofra.data.model.new_password.NewPassword;
import com.elatienda.kaytamarka.sofra.data.model.register.ClientRegister;
import com.elatienda.kaytamarka.sofra.data.model.register.RestaurantRegister;
import com.elatienda.kaytamarka.sofra.data.model.reset_password.ResetPassword;
import com.elatienda.kaytamarka.sofra.data.model.restaurant_details.RestaurantDetails;
import com.elatienda.kaytamarka.sofra.data.model.restaurant_items.RestaurantItems;

import java.io.File;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiService {

    // General Response
    @GET("cities-not-paginated")
    Call<GeneralResponseNotPaginated> getCitiesNotPaginated();

    @GET("regions-not-paginated")
    Call<GeneralResponseNotPaginated> getRegionsNotPaginated(@Query("city_id") int cityId);

    // Client User Cycle

    @POST("client/login")
    @FormUrlEncoded
    Call<Login> onClientLogin(@Field("email") String clientEmail,
                           @Field("password") String clientPassword);


    @POST("client/sign-up")
    @Multipart
    Call<ClientRegister> onClientRegister(@Part("name") RequestBody clientName,
                                          @Part("email") RequestBody clientEmail,
                                          @Part("password") RequestBody clientPassword,
                                          @Part("password_confirmation") RequestBody clientPasswordConfirmation,
                                          @Part("phone") RequestBody clientPhone,
                                          @Part("region_id") RequestBody clientRegionId,
                                          @Part MultipartBody.Part clientProfileImage);

    @POST("client/reset-password")
    @FormUrlEncoded
    Call<ResetPassword> onClientResetPassword(@Field("email") String clientEmail);

    @POST("client/new-password")
    @FormUrlEncoded
    Call<NewPassword> onClientNewPassword(@Field("code") int clientCode,
                                          @Field("password") String clientPassword,
                                          @Field("password_confirmation") String clientPasswordConfirmation);

    // Restaurant User Cycle

    @POST("restaurant/login")
    @FormUrlEncoded
    Call<Login> onRestaurantLogin(@Field("email") String restaurantEmail,
                              @Field("password") String restaurantPassword);


    @POST("restaurant/sign-up")
    @Multipart
    Call<RestaurantRegister> onRestaurantRegister(@Part("name") RequestBody restaurantName,
                                                  @Part("email") RequestBody restaurantEmail,
                                                  @Part("password") RequestBody restaurantPassword,
                                                  @Part("password_confirmation") RequestBody restaurantPasswordConfirmation,
                                                  @Part("phone") RequestBody restaurantPhone,
                                                  @Part("whatsapp") RequestBody restaurantWhatsapp,
                                                  @Part("region_id") RequestBody restaurantRegionId,
                                                  @Part("delivery_cost") RequestBody restaurantDeliveryCost, // double , float
                                                  @Part("minimum_charger") RequestBody restaurantMinimumCharger, // double , float
                                                  @Part MultipartBody.Part restaurantPhoto,
                                                  @Part("delivery_time") RequestBody restaurantDeliveryTime); // double , float

    @POST("restaurant/reset-password")
    @FormUrlEncoded
    Call<ResetPassword> onRestaurantResetPassword(@Field("email") String restaurantEmail);

    @POST("restaurant/new-password")
    @FormUrlEncoded
    Call<NewPassword> onRestaurantNewPassword(@Field("code") int restaurantCode,
                                          @Field("password") String restaurantPassword,
                                          @Field("password_confirmation") String restaurantPasswordConfirmation);

    // Home Client Cycle

    @GET("restaurants")
    Call<DisplayRestaurants> getRestaurants(@Query("page") int page);

    @GET("restaurants")
    Call<DisplayRestaurants> getRestaurantsFilter(@Query("page") int page,
                                            @Query("keyword") String keyword,
                                            @Query("city_id") int cityId);

    @GET("categories")
    Call<FoodCategories> getFoodCategories(@Query("restaurant_id") int restaurantId,
                                              @Query("category_id") int categoryId);

    @GET("items")
    Call<RestaurantItems> getRestaurantItems(@Query("restaurant_id") int restaurantId,
                                            @Query("category_id") int categoryId);

    @GET("restaurant")
    Call<RestaurantDetails> getRestaurantDetails(@Query("restaurant_id") int restaurantId);


    @GET("restaurant/reviews")
    Call<DisplayAllReviews> getAllReviews( //@Query("api_token") String apiToken,
                                           @Query("restaurant_id") int restaurantId,
                                           @Query("page") int page);


    @POST("client/restaurant/review")
    @FormUrlEncoded
    Call<AddRestaurantReview> addReview(@Field("rate") int rate,
                                        @Field("comment") String comment,
                                        @Field("restaurant_id") int restaurantId,
                                        @Field("api_token") String apiToken);


}