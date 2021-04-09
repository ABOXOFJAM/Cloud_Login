package com.e.cloud_login;

import android.util.Log;

import com.e.cloud_login.JSON.LoginJson;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.PrintStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AccountLogin
{//接收参数
    public User user= null;
    public Boolean status =null;
    public String token =null;
    private RequestLogin accountService = RequestLogin.create();//接口的创建
    public void accountLogin(String account, String pswd) throws InterruptedException {
        Call<LoginJson> login = accountService.userLogin(account, new WidgetSetting().getMD5(pswd));
        new Thread(new Runnable() {
            @Override
            public void run() {
              login.enqueue(new Callback<LoginJson>() {
                  @Override
                  public void onResponse(Call<LoginJson> call, Response<LoginJson> response) {
                     user=response.body().user;
                      status=response.body().status;
                      token=response.body().token;
                      Log.i("----TOKEN-----",token);
                     Log.i("----EMAIL-----",user.email);
                  }

                  @Override
                  public void onFailure(Call<LoginJson> call, Throwable t) {

                  }
              });
           }
        }).start();

    }

}

