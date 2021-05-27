package com.e.cloud_login.Data.JSON;

import com.e.cloud_login.Data.User;
import com.google.gson.annotations.Expose;

public class RegisterJson {
    @Expose
    public boolean RegisterStatus;
    @Expose
    public User user;
}
