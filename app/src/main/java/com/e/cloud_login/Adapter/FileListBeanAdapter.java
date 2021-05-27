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

import org.w3c.dom.Text;

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
      // FilelistBean filelistBean = getItem(position);
       View view = View.inflate(getContext(), R.layout.bean_file,null);
       TextView tv1 = view.findViewById(R.id.bean_file_tv1);
       TextView tv2 = view.findViewById(R.id.bean_file_tv2);
       ImageView img = view.findViewById(R.id.bean_file_img);
       TextView detail =view.findViewById(R.id.bean_file_detail);
       //View.VISIBLE ：    常量值0，可见
        //View.INVISIBLE :   常量值4，不可见，占用布局空间
        //View.GONE :         常量值8，不可见，不占用空间
       if(position %2 ==0){
           tv2.setVisibility(View.GONE);
       }
       else{
           tv1.setVisibility(View.GONE);
       }
        return view;
    }
}
