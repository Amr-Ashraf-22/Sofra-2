
package com.elatienda.kaytamarka.sofra.data.model.restaurant_details;

import com.elatienda.kaytamarka.sofra.data.model.display_restaurants.DisplayRestaurantsData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestaurantDetails {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private DisplayRestaurantsData data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DisplayRestaurantsData getData() {
        return data;
    }

    public void setData(DisplayRestaurantsData data) {
        this.data = data;
    }

}
