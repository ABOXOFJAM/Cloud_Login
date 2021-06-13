package com.e.cloud_login;


import android.net.Uri;

import com.e.cloud_login.Data.JSON.DeleteFileJson;
import com.e.cloud_login.Data.JSON.FileJson;
import com.e.cloud_login.Data.JSON.FileListJson;
import com.e.cloud_login.Data.JSON.FilePhotoJson;
import com.e.cloud_login.Data.JSON.LoginJson;
import com.e.cloud_login.Data.JSON.PhoneCodeJson;
import com.e.cloud_login.Data.JSON.PhoneLoginJson;
import com.e.cloud_login.Data.JSON.PhotoJson;
import com.e.cloud_login.Data.JSON.UserJson;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface WebService {
    @POST("code")
    Call<PhoneCodeJson> getCode(@Query("phone") String phonenum,
                                @Query("type") Integer type);//1.注册 2.修改密码 3.找回密码 4.登录
    @POST("loginByCode")
    Call<PhoneLoginJson> phoneLogin(@Query("code")String code,
                                    @Query("phone")String phonenum);
    @Multipart
    @POST("userPhoto")
    Call<PhotoJson> updataPhoto(@Query("id") String id,
                                @Part MultipartBody.Part photo );

    @GET("login")
    Call<LoginJson> userLogin(@Query("username") String username ,
                              @Query("password") String password);//登入请求
    @POST("download")
    Call<ResponseBody>downloadFile(@Query("username")String username,
                                   @Query("url")String url,
                                   @Header("token")String token);
    @GET("user")
    Call<UserJson> loadUserInfo(@Query("phone")String phone);

    @POST("getPhoto")
    Call<ResponseBody> getUserPhoto(@Query("username")String username);
    @Multipart
    @POST("filePhoto")
    Call<FilePhotoJson> updataFilePhoto(@Part MultipartBody.Part photo);
    @Multipart
    @POST("fileUpload")
    Call<FileJson> uploadFile(@Part MultipartBody.Part file,
                              @Query("id") String id,
                              @Query("isPublic")int ispublic,
                              @Query("photo")String imageurl,
                              @Query("title")String title);
    @GET("userFile")
    Call<FileListJson> getFile(@Query("cnt")int cnt,
                               @Query("id")String id,
                               @Query("orderType") int ordertype,
                               @Query("page") int page);
    @GET
    Call<ResponseBody> download(@Url Uri pdf_uri);
    @DELETE("userFile")
    Call<DeleteFileJson> deleteFile(@Query("id") String userid,
                                    @Query("number")String file_number);
    @GET("searchFile")
    Call<FileListJson> getServerFile(@Query("cnt")int cnt,
                                     @Query("fileType")String pdf,
                                     @Query("orderType") int orderType,
                                     @Query("page")int page);
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
    static WebService create(Gson gson){//Retrofit的实例化
        OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(60000, TimeUnit.MILLISECONDS)
                .readTimeout(60000, TimeUnit.MILLISECONDS)//60秒连接时长
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://47.115.128.193:8081/")//baseurl
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
        WebService accountService =retrofit.create(WebService.class);
        return accountService;
    }
}
