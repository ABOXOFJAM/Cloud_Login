package com.e.cloud_login;

import com.e.cloud_login.Data.JSON.ChangeFileNameJson;
import com.e.cloud_login.Data.JSON.CreatePackageJson;
import com.e.cloud_login.Data.JSON.DeleteFileJson;
import com.e.cloud_login.Data.JSON.DeletePackageJson;
import com.e.cloud_login.Data.JSON.FindPassWordJson;
import com.e.cloud_login.Data.JSON.LoadFilesJson;
import com.e.cloud_login.Data.JSON.LoginJson;
import com.e.cloud_login.Data.JSON.PhoneCodeJson;
import com.e.cloud_login.Data.JSON.PhoneLoginJson;
import com.e.cloud_login.Data.JSON.PhotoJson;
import com.e.cloud_login.Data.JSON.RegisterJson;
import com.e.cloud_login.Data.JSON.UploadFileJson;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface WebService {
    @POST("code")
    Call<PhoneCodeJson> getCode(@Query("phone") String phonenum,
                                @Query("type") Integer type);//1.注册 2.修改密码 3.找回密码 4.登录
    @POST("loginByCode")
    Call<PhoneLoginJson> phoneLogin(@Query("code")String code,
                                    @Query("phone")String phonenum);
    @GET("login")
    Call<LoginJson> userLogin(@Query("username") String username ,
                              @Query("password") String password);//登入请求
    @GET("register")
    Call<RegisterJson> userRegiser(@Query("username")String username,
                                   @Query("password")String password,
                                   @Query("email") String email);
    @POST("getFileInformation")
    Call<LoadFilesJson> getFileInformation(@Query("username") String username,
                                           @Query("parentFile")String parentFile,
                                           @Header("token") String token);
    @POST("sendEmail")
    Call<FindPassWordJson> userGetEmail(@Query("username") String username);
//    @POST("changePassword")
//    Call<FindPassWordJson> userChangePassword(@Query("username")String username,
//                                              @Query(""));
    @Multipart
    @POST("upload")
    Call<UploadFileJson> uploadFile(@Part MultipartBody.Part file,
                                    @Part("username")String username,
                                    @Part("parentFile")String parentFile);
    @POST("delete")
    Call<DeleteFileJson> deleteFile(@Query("username") String username,
                                    @Query("url") String url,
                                    @Header("token")String token);
    @POST("deleteFile")
    Call<DeletePackageJson> deletePackage(@Query("username")String username,
                                          @Query("url")String url,
                                          @Header("token")String token);
    @POST("createNewFile")
    Call<CreatePackageJson> createPackage(@Query("username")String username,
                                          @Query("Filename")String package_name,
                                          @Query("parentFile")String parentFile,
                                          @Header("token")String token);
    @POST("changeFilename")
    Call<ChangeFileNameJson> changeFilename(@Query("username")String username,
                                            @Query("newFilename")String newFilename,
                                            @Query("url")String url,
                                            @Header("token")String token);
    @POST("download")
    Call<ResponseBody>downloadFile(@Query("username")String username,
                                   @Query("url")String url,
                                   @Header("token")String token);

    /**
     * 从服务端接收上传的头像，比如换手机的时候
     * @param username
     * @return
     */
    @POST("getPhoto")
    Call<ResponseBody> getUserPhoto(@Query("username")String username);
    @Multipart
    @POST("photoUpload")
    Call<PhotoJson> userPhoto(@Part MultipartBody.Part file,@Part("username") String username);

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
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://47.115.128.193:8081/")//baseurl
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        WebService accountService =retrofit.create(WebService.class);
        return accountService;
    }
}
