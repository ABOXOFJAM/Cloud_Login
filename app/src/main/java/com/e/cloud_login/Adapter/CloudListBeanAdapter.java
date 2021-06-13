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
import com.e.cloud_login.Data.JSON.DownLoadBean;
import com.e.cloud_login.Data.JSON.FileBean;
import com.e.cloud_login.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CloudListBeanAdapter extends RecyclerView.Adapter<CloudListBeanAdapter.MyViewHolder> {
    public List<DownLoadBean> list;
    private View view;
    private MyItemClickListener myItemClickListener;
    private MyItemLongClickListener myItemLongClickListener;
    public void setOnItemClickListener(MyItemClickListener onItemClickListener){
        this.myItemClickListener = onItemClickListener;
    }
    public CloudListBeanAdapter(List<DownLoadBean> list){
        this.list = list;
    }
    public interface MyItemClickListener{
        void onItemClick(View view,int position);
    }public interface  MyItemLongClickListener{
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
       TextView tv_title,tv_time;
        /**
         * 构造bean的
         * @param view
         */
        public MyViewHolder(@NonNull @NotNull View view,MyItemClickListener onItemClick,MyItemLongClickListener myItemLongClickListener) {
            super(view);
            this.onItemClick = onItemClick;
            itemLongClickListener = myItemLongClickListener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            imageView = view.findViewById(R.id.bean_cloud_img);
            tv_title = view.findViewById(R.id.bean_cloud_title);
            tv_time = view.findViewById(R.id.bean_cloud_time);
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
    public CloudListBeanAdapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bean_cloud, parent, false);
        CloudListBeanAdapter.MyViewHolder myViewHoder = new CloudListBeanAdapter.MyViewHolder(view,myItemClickListener,myItemLongClickListener);
        return myViewHoder;
    }
    /**
     * 对recyclerview有含positing的操作
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        holder.tv_title.setText(list.get(position).title);
            Glide.with(view)//在ViewHolder初始化的时候创建view
                    .load(list.get(position).filePhoto)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .placeholder(R.drawable.error3)
                    .error(R.drawable.error1)
                    .centerCrop()
                    .into(holder.imageView);
        holder.tv_time.setText(list.get(position).time);

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
