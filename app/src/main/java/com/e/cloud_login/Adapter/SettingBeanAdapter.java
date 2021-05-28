package com.e.cloud_login.Adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.e.cloud_login.Data.SettingBean;

public class SettingBeanAdapter extends ArrayAdapter<SettingBean> {
    public SettingBeanAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }
}
