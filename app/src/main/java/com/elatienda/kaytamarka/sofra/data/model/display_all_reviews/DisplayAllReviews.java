
package com.elatienda.kaytamarka.sofra.data.model.display_all_reviews;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DisplayAllReviews {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private DisplayAllReviewsPagination data;

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

    public DisplayAllReviewsPagination getData() {
        return data;
    }

    public void setData(DisplayAllReviewsPagination data) {
        this.data = data;
    }

}
