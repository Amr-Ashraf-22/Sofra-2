
package com.elatienda.kaytamarka.sofra.data.model.display_restaurants;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DisplayRestaurants {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private DisplayRestaurantsPagination data;

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

    public DisplayRestaurantsPagination getData() {
        return data;
    }

    public void setData(DisplayRestaurantsPagination data) {
        this.data = data;
    }

}
