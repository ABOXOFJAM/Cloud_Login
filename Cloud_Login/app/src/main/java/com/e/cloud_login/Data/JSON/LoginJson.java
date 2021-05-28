package com.e.cloud_login.Data.JSON;

import android.util.Log;

import com.e.cloud_login.Data.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginJson {
    @Expose
    @SerializedName("LoginStatus")
    public Boolean status;
    @Expose
    public User user;
    @Expose
    public String token;

    public void setToken(String token) {
        this.token = token;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getStatus() {
        return status;
    }

    public String getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }

    public void show() {
        Log.i("------SHOW方法------",token);
    }
}
