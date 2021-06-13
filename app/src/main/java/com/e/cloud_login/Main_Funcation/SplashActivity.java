package com.e.cloud_login.Main_Funcation;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.e.cloud_login.Login_Funcation.PhoneLoginActivity;
import com.e.cloud_login.R;

import java.lang.reflect.Array;
import java.util.Arrays;

public class SplashActivity extends Activity {
    private LinearLayout linearLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        hide_tool();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        linearLayout=findViewById(R.id.splash_ll);
        setAnimation();
    }

    /**
     * 设置动画渐变效果
     */
    public void setAnimation(){
        AlphaAnimation alphaAnimation = new AlphaAnimation(0f,1f);
        alphaAnimation.setDuration(3000);
        //给布局设置动画效果
        linearLayout.setAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                   selectActivity();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
    public void selectActivity(){
        SharedPreferences sharedPreferences = getSharedPreferences("userinfo",MODE_PRIVATE);
        String token = sharedPreferences.getString("token",null);//第二个参数为默认
       //查询是否登入过
          Intent intent =new Intent();
        if(token==null){
            intent.setClass(this, PhoneLoginActivity.class);
       }
       //如果登入了就直接进主页面
       //如果登出后退出app继续进入登入页面
        //如果没注册过就进入注册界面
        else{
            intent.setClass(this, HomeActivity.class);
             }
        startActivity(intent);
        finish();
    }

    /**
     * 隐藏标题栏
     */
    public void hide_tool(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 实现透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
}
