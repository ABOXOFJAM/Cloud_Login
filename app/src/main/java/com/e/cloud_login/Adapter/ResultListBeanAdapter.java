package com.e.cloud_login.Adapter;

import android.graphics.Rect;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.e.cloud_login.Data.FilelistBean;
import com.e.cloud_login.Data.JSON.FileBean;
import com.e.cloud_login.Main_Funcation.Pan_Funcation.FileUtil;
import com.e.cloud_login.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ResultListBeanAdapter extends RecyclerView.Adapter<ResultListBeanAdapter.MyViewHolder> {
    public List<FileBean> list;
    private View view;
    private MyItemClickListener myItemClickListener;
    private MyItemLongClickListener myItemLongClickListener;
    public void setOnItemClickListener(MyItemClickListener onItemClickListener){
        this.myItemClickListener = onItemClickListener;
    }

    public void setMyItemLongClickListener(MyItemLongClickListener myItemLongClickListener) {
        this.myItemLongClickListener = myItemLongClickListener;
    }

    public ResultListBeanAdapter(List<FileBean> list){
        this.list = list;
    }
    public interface MyItemClickListener{
        void onItemClick(View view,int position);
    }
    public interface  MyItemLongClickListener{
        void onItemLongClick(View view,int position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
       MyItemClickListener onItemClick;
       MyItemLongClickListener itemLongClickListener;
       ImageView imageView;
       TextView tv_title,tv_download;
        /**
         * 构造bean的
         * @param view
         */
        public MyViewHolder(@NonNull @NotNull View view,MyItemClickListener onItemClick,
                            MyItemLongClickListener myItemLongClickListener) {
            super(view);
            this.onItemClick = onItemClick;
            itemLongClickListener = myItemLongClickListener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            imageView = view.findViewById(R.id.result_file_img);
            tv_title = view.findViewById(R.id.reuslt_file_title);
            tv_download = view.findViewById(R.id.result_file_download);
        }
        @Override
        public void onClick(View v) {
            if (onItemClick != null) {
                onItemClick.onItemClick(v, getPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if(itemLongClickListener !=null){
                itemLongClickListener.onItemLongClick(v,getPosition());
            }
            return false;
        }
    }
    @NonNull
    @NotNull
    @Override
    public ResultListBeanAdapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bean_file_result, parent, false);
        ResultListBeanAdapter.MyViewHolder myViewHoder = new ResultListBeanAdapter.MyViewHolder(view,myItemClickListener,myItemLongClickListener);
        return myViewHoder;
    }
    /**
     * 对recyclerview有含positing的操作
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        holder.tv_title.setText(list.get(position).fileTitle);
        Log.d("TAG",list.get(position).filePhoto);
        if(list.get(position).filePhoto!= null){
            Glide.with(view)//在ViewHolder初始化的时候创建view
                    .load(list.get(position).filePhoto)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .placeholder(R.drawable.error3)
                    .error(R.drawable.error1)
                    .centerCrop()
                    .into(holder.imageView);
        }

    }
    public static class MyDecoration extends RecyclerView.ItemDecoration{
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            //outRect.set()中的参数分别对应左、上、右、下的间隔
            outRect.set(10,10,10,10);
        }
    }
}
