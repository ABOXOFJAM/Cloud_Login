package com.e.cloud_login.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.e.cloud_login.Data.FilelistBean;
import com.e.cloud_login.R;

import java.util.List;

public class FileListBeanAdapter extends ArrayAdapter<FilelistBean> {

    public FileListBeanAdapter(@NonNull Context context, int resource, List<FilelistBean> list) {
        super(context, resource, list);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       FilelistBean filelistBean = getItem(position);
       View view = View.inflate(getContext(), R.layout.bean_filelist,null);
        TextView name = view.findViewById(R.id.filebean_name);
        TextView time = view.findViewById(R.id.filebean_time);
        ImageView image =view.findViewById(R.id.filebean_img);
        name.setText(filelistBean.getName());
        image.setImageResource(filelistBean.getImage_id());
        //time这里设置一个时间函数来显示距离当前的时间
        return view;
    }
}
