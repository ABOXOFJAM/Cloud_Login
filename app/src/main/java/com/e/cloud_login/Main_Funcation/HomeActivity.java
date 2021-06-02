package com.e.cloud_login.Main_Funcation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.e.cloud_login.Fragment.FindFragment;
import com.e.cloud_login.Fragment.HomeFragment;
import com.e.cloud_login.Fragment.MineFragment;
import com.e.cloud_login.Fragment.MessageFragment;
import com.e.cloud_login.Main_Funcation.Pan_Funcation.RealPathFromUriUtils;
import com.e.cloud_login.R;
import com.google.android.material.tabs.TabLayout;

import java.io.Console;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Button button;
    private List<Fragment> fragmentList;
    private final int ADD_PDF_CODE = 0x01;
    private String[] titles = {"首页","发现","消息","我的"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        hide_tool();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        viewPager =findViewById(R.id.home_vp);
        tabLayout =findViewById(R.id.home_tablayout);
        button = findViewById(R.id.home_add);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent,ADD_PDF_CODE);
            }
        });
        initData();

    }

    /**
     * 添加PagerView里的Fragment碎片
     */
    public void initData(){
        fragmentList = new ArrayList<>();
        fragmentList.add(new HomeFragment());
        fragmentList.add(new FindFragment());
        fragmentList.add(new MessageFragment());
        fragmentList.add(new MineFragment());
        MainTabAdapter mainTabAdapter = new MainTabAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mainTabAdapter);
        tabLayout.setupWithViewPager(viewPager);
        for(int i=0;i<tabLayout.getTabCount();i++){
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            //设置在第i个Tab(格子)的元素
            //适配器通过Position来定位Title里面的文字并定位
            //然后将TextView设置成titles{position]后返回设置好的View
            tab.setCustomView(mainTabAdapter.getView(i));
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    /**
     * TabLayout的适配器
     */
    public class MainTabAdapter extends FragmentPagerAdapter {
        public MainTabAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0: return fragmentList.get(0);
                case 1: return fragmentList.get(1);
                case 2: return fragmentList.get(2);
                case 3: return fragmentList.get(3);
                default: return fragmentList.get(0);
            }
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
        public View getView(int position){
            View view = View.inflate(HomeActivity.this,R.layout.bean_tab_item,null);
            ImageView imageView = view.findViewById(R.id.home_tab_img);
            switch(position){
                case 0:{
                    imageView.setImageResource(R.drawable.ic_instagram);
                    break;
                }
                case 1:{
                    imageView.setImageResource(R.drawable.ic_compass);
                    break;
                }
                case 2:{
                    imageView.setImageResource(R.drawable.ic_message);
                    break;
                }
                case 3:{
                    imageView.setImageResource(R.drawable.cathead);
                    break;
                }
                default:
                    Toast.makeText(HomeActivity.this, "导航栏错误", Toast.LENGTH_SHORT).show();
            }
//            TextView textView = view.findViewById(R.id.home_tab_tv);
//            textView.setTextColor(tabLayout.getTabTextColors());
//            textView.setText(titles[position]);
            return view;
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ADD_PDF_CODE: {
                if(resultCode == RESULT_OK){
                Uri uri = data.getData();
                Log.d("TAG",uri.toString());
                if(uri!=null){
                    String path = RealPathFromUriUtils.getRealPathFromUri(this,uri);
                   // File file = new File(path);
                    Bundle bundle = new Bundle();
                    bundle.putString("path",path);
                    fragmentList.get(0).setArguments(bundle);
                    android.app.FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft=fm.beginTransaction();
                    ft.commit();
                    //Intent intent = new Intent(getApplicationContext(),CanvasActivity.class).putExtra("path", path);
                    //startActivity(intent);
                }

                break;
                }
            }
            default:
                throw new IllegalStateException("Unexpected value: " + requestCode);
        }
    }
}
