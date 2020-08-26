
package com.elatienda.kaytamarka.sofra.data.model.register;

import com.elatienda.kaytamarka.sofra.data.model.user.LoginData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestaurantRegister {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private Object data; // Object // LoginData

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

    public Object getData() {
        return data;
    } // Object // LoginData

    public void setData(Object data) {
        this.data = data;
    } // Object // LoginData

}
