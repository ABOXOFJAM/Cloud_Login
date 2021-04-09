package com.e.cloud_login.JSON;

import android.util.Log;

import com.e.cloud_login.User;

public class LoginJson {
    public Boolean status;
    public User user;
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
