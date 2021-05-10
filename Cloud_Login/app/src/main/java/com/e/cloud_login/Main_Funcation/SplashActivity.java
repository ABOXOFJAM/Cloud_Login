package com.e.cloud_login.Main_Funcation;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.e.cloud_login.Login_Funcation.LoginActivity;
import com.e.cloud_login.R;

public class SplashActivity extends Activity {
    private LinearLayout linearLayout;
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
            intent.setClass(this, HomeActivity.class);
//        }
        //如果登入了就直接进主页面
        //如果登出后退出app继续进入登入页面
        //如果没注册过就进入注册界面
//        else{
//            intent.setClass(this, GuideActivity.class);
//        }
        startActivity(intent);
        finish();
    }
}
