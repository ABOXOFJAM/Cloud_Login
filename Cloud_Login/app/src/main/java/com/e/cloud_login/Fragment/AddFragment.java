package com.e.cloud_login.Fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.e.cloud_login.R;

public class AddFragment extends BaseFragment{
    private TextView tv;
    @Override
    public View initView() {
        View view = View.inflate(getActivity(), R.layout.fragment_add,null);
        tv = view.findViewById(R.id.fg_add_title);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
    }
}
