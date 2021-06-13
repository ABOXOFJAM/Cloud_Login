package com.e.cloud_login.Main_Funcation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.e.cloud_login.Login_Funcation.PhoneLoginActivity;
import com.e.cloud_login.R;

public class SettingActivity extends AppCompatActivity {
    private ImageView img_left,img_global,img_style,img_brush;
    private TextView tv_title,tv_global,tv_style,tv_brush,tv_remain;
    private ListView lv2,lv3;
    private Button btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        hide_tool();
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        InitWidget();
    }
    public void InitWidget(){
        OnClick onClick = new OnClick();
        img_left = findViewById(R.id.setting_left);
        img_global = findViewById(R.id.setting_img_global);
        img_style = findViewById(R.id.setting_img_style);
        img_brush = findViewById(R.id.setting_img_brush);
        tv_global = findViewById(R.id.setting_tv_global);
        tv_style = findViewById(R.id.setting_tv_style);
        tv_remain = findViewById(R.id.setting_tv_remain);
        tv_title = findViewById(R.id.setting_tv_center);
        lv2 = findViewById(R.id.setting_lv2);
        lv3 = findViewById(R.id.setting_lv3);
        btn = findViewById(R.id.setting_btn_out);
        btn.setOnClickListener(onClick);
    }
    public class OnClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            switch(v.getId()){
                case R.id.setting_btn_out:{
                    SharedPreferences.Editor userinfo = SettingActivity.this.getSharedPreferences("userinfo", Context.MODE_PRIVATE).edit();
                    userinfo.clear();
                    userinfo.apply();
                    intent.setClass(SettingActivity.this, PhoneLoginActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                }
            }
        }
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
