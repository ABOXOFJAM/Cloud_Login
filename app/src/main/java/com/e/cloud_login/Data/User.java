package com.e.cloud_login.Data;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    public String id;
    public String username;
    public String password;
    public String email;
    public String photo;
    public String sex;
    public String phone;
    public String introduction;
    public int fileCounts;
    public int fansCounts;
    public int followCounts;
    public String createTime;
    public String updateTime;
}

