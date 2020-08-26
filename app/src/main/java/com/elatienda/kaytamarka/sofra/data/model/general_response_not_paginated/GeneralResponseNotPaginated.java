
package com.elatienda.kaytamarka.sofra.data.model.general_response_not_paginated;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GeneralResponseNotPaginated {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private List<GeneralResponseNotPaginatedData> data = null;

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

    public List<GeneralResponseNotPaginatedData> getData() {
        return data;
    }

    public void setData(List<GeneralResponseNotPaginatedData> data) {
        this.data = data;
    }

}
