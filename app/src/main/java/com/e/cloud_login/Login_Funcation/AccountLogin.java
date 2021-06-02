package com.e.cloud_login.Login_Funcation;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.e.cloud_login.Data.JSON.LoginJson;
import com.e.cloud_login.Data.JSON.PhoneCodeJson;
import com.e.cloud_login.Data.JSON.PhoneLoginJson;
import com.e.cloud_login.Data.JSON.PhotoJson;
import com.e.cloud_login.Data.JSON.RegisterJson;
import com.e.cloud_login.Data.User;
import com.e.cloud_login.Main_Funcation.Pan_Funcation.PanRepositroy;
import com.e.cloud_login.R;
import com.e.cloud_login.WebService;
import com.e.cloud_login.WidgetSetting;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.ConnectException;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;

/*******此函数为与登入有关功能的实现********/
public class AccountLogin
{
    public User user=null;
    public Boolean status=null ;
    public String token=null;
    String PORTRAIT_PATH = null;
    private WebService accountService = WebService.create();//接口的创建
    private  Integer REPEAT_WRONG = 0;
    private  Integer EXIST_WRONG = 1;
    private Integer SUCCESS = 3;
    private Integer INTERNET = 4;

    /**
     * 获取验证码
     * @param num
     * @param type
     * @return
     */
    Integer getcode(String num,Integer type){
        Call<PhoneCodeJson> phonecode = accountService.getCode(num,type);
        final String[] msg = {""};
        try {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        PhoneCodeJson body = phonecode.execute().body();
                        msg[0] = body.msg;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
            try{
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
       switch (msg[0]){
           case "repeatWrong":{
               return REPEAT_WRONG;
           }
           case "existWrong":{
               return EXIST_WRONG;
           }
           case "success":{
               return SUCCESS;
           }
           default: return INTERNET;
       }
    }
    String phoneLogin(String phone,String code){
        Gson gson = new GsonBuilder()
                 .registerTypeAdapter(PhoneLoginJson.class, new PhoneLoginJson.DataStateDeserializer())
                .setLenient()
                .create();
        WebService loginService = WebService.create(gson);
        final String[] phonetoken = {""};
        try {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Call<PhoneLoginJson> phonelogin = loginService.phoneLogin(code,phone);
                        Log.d("TAG",phonelogin.execute().body().data.toString());
                        PhoneLoginJson body = phonelogin.execute().body();
                        phonetoken[0] =body.data;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } finally {
            return phonetoken[0];
        }
    }
    /*****登入功能的调用接口实现*****/
    void accountLogin(String account, String pswd) throws InterruptedException {
        Call<LoginJson> login = accountService.userLogin(account, new WidgetSetting().getMD5(pswd));
        try {
            LoginJson body = login.execute().body();
            user=body.user;
            status=body.status;
            token=body.token;

        }
        catch (Exception e){

        }
        }
     /******注册功能的实现******/
     RegisterJson accountRegister(String account,String pwd,String email) {
         User user = new User(account, pwd, email, "");
         Call<RegisterJson> register = accountService.userRegiser(user.username, user.password, user.email);
         RegisterJson res = null;
         try {
             res = register.execute().body();
             Log.i("注册是否成功", res.toString());
         } catch (Exception e) {

         }
         return res;
     }
     /*******忘记密码的实现*****/

    /**
     * 在该目录下新建目录
     */
    private void makeDir(String dir){
        File file = new File(dir);
        if(file.mkdir()){
            Log.d("TEXT_TTT",dir);
        }
        else {
            //该目录已经存在
            Log.d("TEXT_TTT","existed");
        }

    }
    /**
     * 头像上传函数
     */
    public PhotoJson accountPhoto(Context context, MultipartBody.Part photo, String username){
        PhotoJson res = null;
        Boolean connect = true;
        Call<PhotoJson> upload =accountService.userPhoto(photo,username);
        try{
           PhotoJson body =upload.execute().body();
            Log.d("PHOTO_UPLOAD",body.toString());
            if(body.status =="success") res=body;
        } catch (ConnectException e) {
            connect = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(!connect) Toast.makeText(context, "连接服务器失败", Toast.LENGTH_SHORT).show();
        return res;
    }

    /**
     * 头像更新函数,从服务器接受头像
     */
    public void accountGetHead(Context context, String username, ImageView portrait_pic){
        String PORTRAIT = "portrait.png";
        String filepath =  "${PORTRAIT_PATH}/${System.currentTimeMillis()}${username}${PORTRAIT}";
        Call<ResponseBody> photo = accountService.getUserPhoto(username);
    photo.enqueue(new Callback<ResponseBody>() {
        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            ResponseBody body = response.body();
            if(body !=null){
                makeDir(PORTRAIT_PATH);
                PanRepositroy panRepositroy = new PanRepositroy();
                Boolean photo =  panRepositroy.writeResponseBodyToDisk(body,filepath);
                if(photo){
                    Log.d("PHOTO_TEXT","读取成功");
                    Glide.with(context)
                            .load(filepath)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)//不缓存
                            .placeholder(R.drawable.ic_launcher_background)//占位图：加载过程中显示的图片
                            .error(R.drawable.cathead)//占位异常图片
                            .centerCrop()
                            .into(portrait_pic);
                }
                else{
                    Log.d("PHOTO_TEXT","读取失败");
                    File portrait = new File(filepath);
                    if(portrait.exists()){
                        Glide.with(context)
                                .load(filepath)
                                .diskCacheStrategy(DiskCacheStrategy.NONE)//不缓存
                                .placeholder(R.drawable.ic_launcher_background)//占位图：加载过程中显示的图片
                                .error(R.drawable.cathead)//占位异常图片
                                .centerCrop()
                                .into(portrait_pic);
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {
            Toast.makeText(context,"头像更新失败",Toast.LENGTH_SHORT).show();
            File portrait = new File(filepath);
            if(portrait.exists()){
                Log.d("TEXT_TTT","默认头像");
                //如果此处改用缓存是否可不用加载？
                Glide.with(context)
                        .load(filepath)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)//不缓存
                        .placeholder(R.drawable.ic_launcher_background)//占位图：加载过程中显示的图片
                        .error(R.drawable.cathead)//占位异常图片
                        .centerCrop()
                        .into(portrait_pic);
            }
        }
    });
    }
}




