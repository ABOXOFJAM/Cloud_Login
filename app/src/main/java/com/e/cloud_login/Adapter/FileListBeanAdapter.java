package com.e.cloud_login.Adapter;

import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.cloud_login.Data.FilelistBean;
import com.e.cloud_login.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FileListBeanAdapter extends RecyclerView.Adapter<FileListBeanAdapter.MyViewHolder> {
    List<FilelistBean> file_list;
    public FileListBeanAdapter(List<FilelistBean> list){
        file_list = list;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv1,tv2,detail,shape;
        ImageView img;
        public MyViewHolder(@NonNull @NotNull View view) {
            super(view);
            tv1 = view.findViewById(R.id.bean_file_tv1);
            tv2 = view.findViewById(R.id.bean_file_tv2);
            img = view.findViewById(R.id.bean_file_img);
            detail =view.findViewById(R.id.bean_file_detail);
            shape = view.findViewById(R.id.bean_shape);
            //View.VISIBLE ：    常量值0，可见
            //View.INVISIBLE :   常量值4，不可见，占用布局空间
            //View.GONE :         常量值8，不可见，不占用空间
        }
    }

    /**
     * 对view进行含有position的操作
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        if(position%2==0){
           holder.tv1.setVisibility(View.GONE);
        }
        else{
            holder.tv2.setVisibility(View.GONE);
        }

    }

    @NonNull
    @NotNull
    @Override
    public FileListBeanAdapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bean_file, parent, false);
        MyViewHolder myViewHoder = new MyViewHolder(view);
        return myViewHoder;
    }

    @Override
    public int getItemCount() {
        return file_list.size();
    }
    class MyDecoration extends RecyclerView.ItemDecoration{
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            //outRect.set()中的参数分别对应左、上、右、下的间隔
            outRect.set(10,10,10,10);
        }
    }

}

