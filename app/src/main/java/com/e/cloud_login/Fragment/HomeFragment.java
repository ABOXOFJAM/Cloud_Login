package com.e.cloud_login.Fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.e.cloud_login.Adapter.FileListBeanAdapter;
import com.e.cloud_login.Adapter.SearchWordsAdapter;
import com.e.cloud_login.Data.FilelistBean;
import com.e.cloud_login.Data.SearchWords;
import com.e.cloud_login.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.e.cloud_login.R.id.fg_home_et;

public class HomeFragment extends BaseFragment {
    private TextView tv1,tv2,tv_cancel;
    private EditText editText;
    private ImageButton btn_scan,btn_search;
    private PopupWindow mPopWindow;
    private final int ADD_PDF_CODE = 0x01;
    GridView gridView_hot,gridView_recent;
    RecyclerView recyclerView_file;
    LinearLayout linearLayout;
    private String pdf_url;
    int mHeight;
    private List<SearchWords> list = new ArrayList<>();
    private List<FilelistBean> beanList = new ArrayList<>();
    @Override
    public View initView() {
        /**
         * 在Fragment里面添加控件一定要在这里声明
         * 因为在BaseFragment里面的onCreateView里面返回的是initView()
         */
        Onclick onclick = new Onclick();
        View view = View.inflate(getActivity(), R.layout.fragment_home,null);
        tv1 = view.findViewById(R.id.home_tv_hot);
        tv2 = view.findViewById(R.id.home_tv_recent);
        tv_cancel = view.findViewById(R.id.home_tv_cancel);
        editText =view.findViewById(fg_home_et);
        recyclerView_file = view.findViewById(R.id.home_rv_file);
        FileListBeanAdapter adapter = new FileListBeanAdapter(beanList);
        recyclerView_file.setAdapter(adapter);
        recyclerView_file.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerView_file.addItemDecoration(new MyDecoration());
        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(getContext(),
               R.anim.layout_from_bottom);
        recyclerView_file.setLayoutAnimation(controller);
        linearLayout = view.findViewById(R.id.home_filelinlayout);
        mHeight = linearLayout.getHeight();
        editText.setOnClickListener(onclick);
        btn_search = view.findViewById(R.id.home_btn_search);
        btn_scan = view.findViewById(R.id.fg_home_btn_scan);
        initData();
        return view;

    }
    public class Onclick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case fg_home_et:{
                    showPopupWindow();
                }
            }
        }
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
        Bundle bundle=getArguments();
        String path=bundle.getString("path");
        File file = new File(path);

//        SearchWords searchWords = new SearchWords();
//        searchWords.text="测试";
//        list.add(searchWords);
//        FilelistBean filelistBean = new FilelistBean();
//        filelistBean.detail = "测试";
//        filelistBean.img_url = "测试";
//        beanList.add(filelistBean);

    }
    class MyDecoration extends RecyclerView.ItemDecoration{
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            //outRect.set()中的参数分别对应左、上、右、下的间隔
            outRect.set(20,0,20,20);
        }
    }
}
