package com.e.cloud_login.Adapter;

import android.content.Intent;
import android.graphics.Rect;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.cloud_login.Data.FilelistBean;
import com.e.cloud_login.Data.JSON.FileJson;
import com.e.cloud_login.Data.JSON.FilePhotoJson;
import com.e.cloud_login.Main_Funcation.CanvasActivity;
import com.e.cloud_login.Main_Funcation.Pan_Funcation.FileUtil;
import com.e.cloud_login.Main_Funcation.Pan_Funcation.PanRepositroy;
import com.e.cloud_login.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import static org.litepal.LitePalApplication.getContext;

public class FileListBeanAdapter extends RecyclerView.Adapter<FileListBeanAdapter.MyViewHolder> {
    public List<FilelistBean> file_list;
    public MyItemClick myItemClick;
    public MyItemLongClick myItemLongClick;
    // 4.定义点击事件回调接口
    public interface MyItemClick{
        void ItemClick(View view, int position);
    }
    public interface MyItemLongClick{
        void ItemLongClick(View view, int position);
    }
    public FileListBeanAdapter(List<FilelistBean> list){
        file_list = list;
    }
    public void setMyItemClick(MyItemClick myItemClick) {
        this.myItemClick = myItemClick;
    }
    public void setMyItemLongClick(MyItemLongClick myItemLongClick) {
        this.myItemLongClick = myItemLongClick;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    implements View.OnClickListener,View.OnLongClickListener{
        TextView tv1,tv2,detail;
        ImageView img;
        MyItemClick Click;
        MyItemLongClick LongClick;
        public MyViewHolder(@NonNull @NotNull View view,MyItemClick myItemClick,MyItemLongClick myItemLongClick) {
            super(view);
            Click = myItemClick;
            LongClick = myItemLongClick;
            itemView.setOnLongClickListener(this);
            itemView.setOnClickListener(this);
            tv1 = view.findViewById(R.id.bean_file_tv1);
            tv2 = view.findViewById(R.id.bean_file_tv2);
            img = view.findViewById(R.id.bean_file_img);
            detail =view.findViewById(R.id.bean_file_detail);
        }

        @Override
        public void onClick(View v) {
            if (Click != null) {
                Click.ItemClick(v, getPosition());

            }
        }

        @Override
        public boolean onLongClick(View v) {
            if(LongClick !=null){
                LongClick.ItemLongClick(v,getPosition());
            }
            return false;
        }
    }
    /**
     * 对view进行含有position的操作
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        holder.detail.setText(file_list.get(position).title);
        if(file_list.get(position).img != null){
            holder.img.setImageBitmap(FileUtil.BytesToBitmap(file_list.get(position).img));
        }
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
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bean_file, parent, false);
        MyViewHolder myViewHoder = new MyViewHolder(view,myItemClick,myItemLongClick);
        return myViewHoder;
    }
    @Override
    public int getItemCount() {
        return file_list.size();
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

