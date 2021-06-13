package com.e.cloud_login.Main_Funcation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;

import com.e.cloud_login.Adapter.CloudListBeanAdapter;
import com.e.cloud_login.Adapter.ResultListBeanAdapter;
import com.e.cloud_login.Data.JSON.DownLoadBean;
import com.e.cloud_login.R;

import org.litepal.LitePal;

import java.util.List;

public class CloudActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ImageView btn_quiet;
    List<DownLoadBean> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        hide_tool();
        super.onCreate(savedInstanceState);
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_cloud);
        initData();
    }

    private void initData() {
        MyonClick myonClick = new MyonClick();
        LitePal.initialize(getApplicationContext());
        //初始化在文件夹中找到的文件
        list = LitePal.findAll(DownLoadBean.class);
        btn_quiet= findViewById(R.id.cloud_quiet);
        recyclerView = findViewById(R.id.cloud_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.addItemDecoration(new CloudListBeanAdapter.MyDecoration());
        CloudListBeanAdapter adapter = new CloudListBeanAdapter(list);
        recyclerView.setAdapter(adapter);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.file_slide_right);
        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(getApplicationContext(),
                R.anim.layout_from_bottom);
        recyclerView.setLayoutAnimation(controller);
        btn_quiet.setOnClickListener(myonClick);

    }
    public class MyonClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.cloud_quiet:{
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