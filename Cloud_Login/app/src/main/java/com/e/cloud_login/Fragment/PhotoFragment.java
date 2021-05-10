package com.e.cloud_login.Fragment;

import android.view.View;
import android.widget.TextView;

import com.e.cloud_login.R;

public class PhotoFragment extends BaseFragment{
    private TextView tv;
    @Override
    public View initView() {
        View view = View.inflate(getActivity(), R.layout.fragment_photo,null);
        tv = view.findViewById(R.id.fg_photo_tv);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
    }
}
