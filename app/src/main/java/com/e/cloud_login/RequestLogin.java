package com.e.cloud_login;

import com.e.cloud_login.JSON.LoginJson;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RequestLogin {
    @GET("login")
    Call<LoginJson> userLogin(@Query("username") String username ,
                              @Query("password") String password);
    //登入接口

     static RequestLogin create(){//Retrofit的实例化
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://47.115.128.193:8081/")//baseurl
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestLogin accountService =retrofit.create(RequestLogin.class);
        return accountService;
    }
}
