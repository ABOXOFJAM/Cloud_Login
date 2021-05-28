package com.e.cloud_login.Main_Funcation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.e.cloud_login.Fragment.AddFragment;
import com.e.cloud_login.Fragment.FileFragment;
import com.e.cloud_login.Fragment.HomeFragment;
import com.e.cloud_login.Fragment.MineFragment;
import com.e.cloud_login.Fragment.PhotoFragment;
import com.e.cloud_login.R;
import com.google.android.material.tabs.TabLayout;

import org.intellij.lang.annotations.JdkConstants;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private List<Fragment> fragmentList;
    private String[] titles = {"首页","文件","添加","照片","我的"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        viewPager =findViewById(R.id.home_vp);
        tabLayout =findViewById(R.id.home_tablayout);
        initData();
    }

    /**
     * 添加PagerView里的Fragment碎片
     */
    public void initData(){
        fragmentList = new ArrayList<>();
        fragmentList.add(new HomeFragment());
        fragmentList.add(new FileFragment());
        fragmentList.add(new AddFragment());
        fragmentList.add(new PhotoFragment());
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
                case 4: return fragmentList.get(4);
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
            View view = View.inflate(HomeActivity.this,R.layout.home_tab_item,null);
            ImageView imageView = view.findViewById(R.id.home_tab_img);
            TextView textView = view.findViewById(R.id.home_tab_tv);
            textView.setTextColor(tabLayout.getTabTextColors());
            textView.setText(titles[position]);
            return view;
        }
    }
}
