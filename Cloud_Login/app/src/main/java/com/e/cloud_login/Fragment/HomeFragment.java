package com.e.cloud_login.Fragment;

import android.view.View;
import android.widget.TextView;

import com.e.cloud_login.R;

public class HomeFragment extends BaseFragment {
    private TextView tv;
    @Override
    public View initView() {
        View view = View.inflate(getActivity(), R.layout.fragment_home,null);
        tv = view.findViewById(R.id.fg_home_tv);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
    }
}
