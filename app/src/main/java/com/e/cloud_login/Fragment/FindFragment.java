package com.e.cloud_login.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.e.cloud_login.Adapter.ResultListBeanAdapter;
import com.e.cloud_login.Data.JSON.FileBean;
import com.e.cloud_login.Data.JSON.FileListJson;
import com.e.cloud_login.Main_Funcation.Pan_Funcation.PanRepositroy;
import com.e.cloud_login.R;

import java.util.List;

public class FindFragment extends BaseFragment{
    private TextView tv;
    private RecyclerView recyclerView;
    private LinearLayout ll_search;
    private ImageButton btn_scan;
    private List<FileBean> list; //储存服务器的list
    private PanRepositroy panRepositroy = new PanRepositroy();
    private SharedPreferences getUserInfo;
    private SharedPreferences.Editor writeUserInfo;
    @Override
    public View initView() {
        View view = View.inflate(getActivity(), R.layout.fragment_find,null);
        recyclerView = view.findViewById(R.id.find_rv);
        ll_search = view.findViewById(R.id.find_search);
        btn_scan = view.findViewById(R.id.find_scan);
        initData();
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        getUserInfo = getActivity().getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        writeUserInfo = getActivity().getSharedPreferences("userinfo",Context.MODE_PRIVATE).edit();
        FileListJson fileListJson = panRepositroy.getServerFileList(8,1,1);
        if(fileListJson.fileList!=null) {
            LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(getContext(),
                    R.anim.layout_from_bottom);
            list = fileListJson.fileList;
            ResultListBeanAdapter adapter = new ResultListBeanAdapter(list);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
            recyclerView.addItemDecoration(new ResultListBeanAdapter.MyDecoration());
            recyclerView.setLayoutAnimation(controller);
        }
    }
}
