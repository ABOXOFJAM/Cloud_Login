package com.e.cloud_login.Main_Funcation;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.e.cloud_login.Login_Funcation.LoginActivity;
import com.e.cloud_login.R;

import java.lang.reflect.Array;
import java.util.Arrays;

public class SplashActivity extends Activity {
    private LinearLayout linearLayout;
    private int STORAGE_PERMISSION = 0x20;//动态申请储存权限标识
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
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
        SharedPreferences sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
        String First = sharedPreferences.getString("isFirst","0");//设置是否第一次访问
        //获取isFirst的值,这个值在GuideActivity设置
        Intent intent =new Intent();
//        if("0".equals(First)){
            intent.setClass(this, LoginActivity.class);
//        }
        //如果登入了就直接进主页面
        //如果登出后退出app继续进入登入页面
        //如果没注册过就进入注册界面
//        else{
//            intent.setClass(this, GuideActivity.class);
//        }
        /**
         * 询问储存权限
         */
        requestStoragePermission();
        startActivity(intent);
        finish();
    }
    private void requestStoragePermission(){
        int hasCameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        Log.e("TAG","开始$hasCameraPermission");
        if(hasCameraPermission == PackageManager.PERMISSION_GRANTED){
            //拥有权限
            Log.e("TAG","已经授权权限");
        }else{
            //没权限，向用户申请权限
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                Log.e("TAG","向用户申请该组权限");
                requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},0x20);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
