package com.e.cloud_login.Fragment;

import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.e.cloud_login.Adapter.FileListBeanAdapter;
import com.e.cloud_login.Data.FilelistBean;
import com.e.cloud_login.Main_Funcation.HomeActivity;
import com.e.cloud_login.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment {
    private TextView tv;
    private EditText editText;
    private Button btn_scan,btn_send;
    private List<FilelistBean> list = new ArrayList<>();
    @Override
    public View initView() {
        /**
         * 在Fragment里面添加控件一定要在这里声明
         * 因为在BaseFragment里面的onCreateView里面返回的是initView()
         */
        View view = View.inflate(getActivity(), R.layout.fragment_home,null);
        tv = view.findViewById(R.id.fg_home_tv);
        editText =view.findViewById(R.id.fg_home_et);
        btn_scan = view.findViewById(R.id.fg_home_btn_scan);
        btn_send = view.findViewById(R.id.fg_home_btn_send);
        initData();
       FileListBeanAdapter adapter = new FileListBeanAdapter(getActivity(),R.layout.bean_filelist,list);
        ListView listView = view.findViewById(R.id.fg_home_lv);
        listView.setAdapter(adapter);
        return view;

    }


    /**
     * 往List里面加元素
     */
    @Override
    public void initData() {
        super.initData();
        FilelistBean filelistBean = new FilelistBean();
        filelistBean.setImage_id(R.drawable.cathead);
        filelistBean.setName("文件");
        filelistBean.setTime_ago("0");
        filelistBean.setTime_now("1");
        list.add(filelistBean);
    }
    //private Pair<Boolean,Pair<String,String>> getLoginState(){
    //}
}
