package com.e.cloud_login.Fragment;

import android.view.View;
import android.widget.TextView;

import com.e.cloud_login.R;

public class FileFragment extends BaseFragment {
    private TextView tv;
    @Override
    public View initView() {
        View view = View.inflate(getActivity(), R.layout.fragment_file,null);
        tv = view.findViewById(R.id.fg_file_tv);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
    }
}
