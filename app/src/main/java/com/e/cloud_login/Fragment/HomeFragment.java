package com.e.cloud_login.Fragment;

import android.app.ActionBar;
import android.os.Bundle;
import android.util.Pair;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.e.cloud_login.Adapter.FileListBeanAdapter;
import com.e.cloud_login.Adapter.SearchWordsAdapter;
import com.e.cloud_login.Data.FilelistBean;
import com.e.cloud_login.Data.SearchWords;
import com.e.cloud_login.Main_Funcation.HomeActivity;
import com.e.cloud_login.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment {
    private TextView tv1,tv2,tv_cancel;
    private EditText editText;
    private ImageButton btn_scan,btn_search;
    private PopupWindow mPopWindow;
    GridView gridView_hot,gridView_recent,gridView_file;
    LinearLayout linearLayout;
    int mHeight;
    private List<SearchWords> list = new ArrayList<>();
    private List<FilelistBean> beanList = new ArrayList<>();
    @Override
    public View initView() {
        /**
         * 在Fragment里面添加控件一定要在这里声明
         * 因为在BaseFragment里面的onCreateView里面返回的是initView()
         */
        View view = View.inflate(getActivity(), R.layout.fragment_home,null);
        tv1 = view.findViewById(R.id.home_tv_hot);
        tv2 = view.findViewById(R.id.home_tv_recent);
        tv_cancel = view.findViewById(R.id.home_tv_cancel);
        editText =view.findViewById(R.id.fg_home_et);
        gridView_file = view.findViewById(R.id.home_gv_file);
        FileListBeanAdapter adapter = new FileListBeanAdapter(getContext(),R.layout.bean_file,beanList);
        gridView_file.setAdapter(adapter);
        linearLayout = view.findViewById(R.id.home_filelinlayout);
        mHeight = linearLayout.getHeight();
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹窗
                showPopupWindow();
            }
        });
        btn_search = view.findViewById(R.id.home_btn_search);
        btn_scan = view.findViewById(R.id.fg_home_btn_scan);
        initData();

        return view;

    }
    /**
     * 弹窗设置
     */
    public void showPopupWindow(){
        //创建layout对象，将其绑定在mPopWindow对象上
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.popupwindow_searchwords,null);
        mPopWindow = new PopupWindow(contentView,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT,true);
        mPopWindow.setContentView(contentView);
        //设置控件的点击事件
        SearchWordsAdapter adapter = new SearchWordsAdapter(getActivity(),R.layout.bean_search,list);
        gridView_hot = contentView.findViewById(R.id.home_gv_hot);
        gridView_recent = contentView.findViewById(R.id.home_gv_recent);
        gridView_hot.setAdapter(adapter);
        gridView_recent.setAdapter(adapter);
        gridView_hot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SearchWords searchWords = list.get(position);
                Toast.makeText(getContext(),searchWords.text,Toast.LENGTH_SHORT).show();
            }
        });
        gridView_recent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SearchWords searchWords = list.get(position);
                Toast.makeText(getContext(),searchWords.text,Toast.LENGTH_SHORT).show();
            }
        });
        //显示PopupWindow
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_home,null);
        mPopWindow.showAsDropDown(editText);//让popwindow出现在某个控件下

    }
    /**
     * 往List里面加元素
     */
    @Override
    public void initData() {
        super.initData();
        SearchWords searchWords = new SearchWords();
        searchWords.text="测试";
        list.add(searchWords);
        FilelistBean filelistBean = new FilelistBean();
        filelistBean.detail = "测试";
        filelistBean.img_url = "测试";
        beanList.add(filelistBean);

    }
    //private Pair<Boolean,Pair<String,String>> getLoginState(){
    //}
}
