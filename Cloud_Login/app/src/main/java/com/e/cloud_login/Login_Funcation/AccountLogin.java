package com.e.cloud_login.Login_Funcation;

import android.util.Log;

import com.e.cloud_login.Data.JSON.LoginJson;
import com.e.cloud_login.Data.JSON.RegisterJson;
import com.e.cloud_login.Data.User;
import com.e.cloud_login.WebService;
import com.e.cloud_login.WidgetSetting;

import java.io.IOException;

import retrofit2.Call;
/*******此函数为与登入有关功能的实现********/
public class AccountLogin<res>
{
    public User user=null;
    public Boolean status=null ;
    public String token=null;
    private WebService accountService = WebService.create();//接口的创建
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
         User user = new User(account, pwd, email, "", 1024.0);
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

}




