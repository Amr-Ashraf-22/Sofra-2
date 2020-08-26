
package com.elatienda.kaytamarka.sofra.data.model.add_restaurant_review;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddRestaurantReview {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private AddRestaurantReviewData data;

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

    public AddRestaurantReviewData getData() {
        return data;
    }

    public void setData(AddRestaurantReviewData data) {
        this.data = data;
    }

}
