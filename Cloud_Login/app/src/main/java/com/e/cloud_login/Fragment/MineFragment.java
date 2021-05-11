package com.e.cloud_login.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.e.cloud_login.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.e.cloud_login.R.*;

public class MineFragment extends BaseFragment {
    private TextView detail,setting;
    private ImageView head;
    private Unbinder unbinder;
    private int REQUEST_CODE_GALLERY= 0x10;
    @Override
    public View initView() {
        View view = View.inflate(getActivity(), layout.fragment_mine,null);
        detail = view.findViewById(id.fg_mine_detail);
        setting = view.findViewById(id.fg_mine_setting);
        head = view.findViewById(id.fg_mine_head);
        unbinder = ButterKnife.bind(this,view);//控件绑定

        return view;
    }
    private void setClickEvent(){
        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Head","更新头像，按下");
                gallery();
            }
        });
    }
    @Override
    public void initData() {
        super.initData();
    }
    /**
     * 获得选中图片返回路径的函数
     */
    private void gallery(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,REQUEST_CODE_GALLERY);
    }
}
