package com.e.cloud_login;

import com.e.cloud_login.Data.JSON.FindPassWordJson;
import com.e.cloud_login.Data.JSON.LoginJson;
import com.e.cloud_login.Data.JSON.RegisterJson;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface WebService {
    @GET("login")
    Call<LoginJson> userLogin(@Query("username") String username ,
                              @Query("password") String password);//登入请求
    @GET("register")
    Call<RegisterJson> userRegiser(@Query("username")String username,
                                   @Query("password")String password,
                                   @Query("email") String email);
    @POST("sendEmail")
    Call<FindPassWordJson> userGetEmail(@Query("username") String username);
//    @POST("changePassword")
//    Call<FindPassWordJson> userChangePassword(@Query("username")String username,
//                                              @Query(""));
     static WebService create(){//Retrofit的实例化
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://47.115.128.193:8081/")//baseurl
                .addConverterFactory(GsonConverterFactory.create())
                .build();
         WebService accountService =retrofit.create(WebService.class);
        return accountService;
    }
}
