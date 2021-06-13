package com.e.cloud_login.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.e.cloud_login.Adapter.FileListBeanAdapter;
import com.e.cloud_login.Adapter.SearchWordsAdapter;
import com.e.cloud_login.Data.FilelistBean;
import com.e.cloud_login.Data.JSON.FileJson;
import com.e.cloud_login.Data.JSON.FilePhotoJson;
import com.e.cloud_login.Data.SearchWords;
import com.e.cloud_login.Main_Funcation.CanvasActivity;
import com.e.cloud_login.Main_Funcation.HomeActivity;
import com.e.cloud_login.Main_Funcation.Pan_Funcation.FileUtil;
import com.e.cloud_login.Main_Funcation.Pan_Funcation.PanRepositroy;
import com.e.cloud_login.Main_Funcation.Pan_Funcation.RealPathFromUriUtils;
import com.e.cloud_login.Main_Funcation.TranslateActivity;
import com.e.cloud_login.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;
import org.litepal.LitePal;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import static org.litepal.LitePalApplication.getContext;

public class HomeFragment extends BaseFragment {
    private FloatingActionButton btn_add;
    boolean longclickflag =false;
    private final int ADD_PDF_CODE = 0x01;
    RecyclerView recyclerView_file;
    private FileListBeanAdapter adapter;
    //在AlertDialog里的
    private String pdf_path;
    private EditText et_title,et_discirption ;
    Button btn_original;
    Button btn_collect;
    private String detail,title;//文件的描述和标题
    //按钮
    private LinearLayout ll_translate,ll_card;
    //数据
    private List<SearchWords> list = new ArrayList<>();
    private List<FilelistBean> beanList = new ArrayList<>();
    private List<FilelistBean> resultList = new ArrayList<>();
    private PanRepositroy panRepositroy = new PanRepositroy();
    private  AlertDialog.Builder builder ;
    private AlertDialog alertDialog;//添加页面
    private SharedPreferences getUserinfo;
    private SharedPreferences.Editor writeUserinfo;
    private int isPublic=1;
    SharedPreferences fileinfo;
    SharedPreferences.Editor changeinfo ;

    @Override
    public View initView() {
        /**
         * 在Fragment里面添加控件一定要在这里声明
         * 因为在BaseFragment里面的onCreateView里面返回的是initView()
         */
        LitePal.initialize(getContext());
        FilelistBean bean = new FilelistBean();
        beanList  =LitePal.findAll(FilelistBean.class);
        CheckFileisExisted();
        getUserinfo = getActivity().getSharedPreferences("userinfo",Context.MODE_PRIVATE);
        writeUserinfo = getActivity().getSharedPreferences("userinfo",Context.MODE_PRIVATE).edit();
        fileinfo = getActivity().getSharedPreferences("文件信息",Context.MODE_PRIVATE);
        changeinfo= getActivity().getSharedPreferences("文件信息", Context.MODE_PRIVATE).edit();
        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(getContext(),
                R.anim.layout_from_bottom);
        MyOnClick onclick = new MyOnClick();
        View view = View.inflate(getActivity(), R.layout.fragment_home,null);
        ll_card = view.findViewById(R.id.ll_card);
        ll_translate = view.findViewById(R.id.ll_translate);
        recyclerView_file = view.findViewById(R.id.home_rv_file);
        adapter = new FileListBeanAdapter(beanList);
        adapter.setMyItemClick(new FileListBeanAdapter.MyItemClick() {
            @Override
            public void ItemClick(View view, int position) {
                if(longclickflag == false){
                FilelistBean filelistBean = beanList.get(position);
                Intent intent = new Intent(getContext(), CanvasActivity.class);
                intent.putExtra("path",filelistBean.img_url);
                intent.putExtra("position",position);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                }
            }
        });
        adapter.setMyItemLongClick(new FileListBeanAdapter.MyItemLongClick() {
            @Override
            public void ItemLongClick(View view, int position) {
                longclickflag = true;
                PopupMenu popupMenu = new PopupMenu(getContext(),view);
                popupMenu.getMenuInflater().inflate(R.menu.menu_file,popupMenu.getMenu());
                popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
                    @Override
                    public void onDismiss(PopupMenu menu) {
                        longclickflag = false;
                    }
                });
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        FilelistBean file = beanList.get(position);
                        switch (item.getItemId()){
                            case R.id.removeItem:{
                                file.delete();
                                beanList.remove(file);
                                adapter.notifyDataSetChanged();
                                break;
                            }
                            case R.id.uploadItem:{
                                PanRepositroy panRepositroy = new PanRepositroy();
                                FilePhotoJson photo = panRepositroy.savePhoto(file.img);
                                switch (photo.msg){
                                    case "\"typeWrong\"":{
                                        Toast.makeText(getContext(),"文件类型错误",Toast.LENGTH_SHORT).show();
                                        break;
                                    }
                                    case "\"existWrong\"":{
                                        Toast.makeText(getContext(),"文件为空",Toast.LENGTH_SHORT).show();
                                        break;
                                    }
                                    case "\"success\"":{
                                        Log.d("TAG","上传文件图片成功");
                                        String imageurl = photo.data;
                                        String uploadfile = panRepositroy.uploadFile(file.img_url,getUserinfo.getString("id",null),imageurl,file.title,file.isPublic);
                                        switch (uploadfile){
                                            case "spaceWrong":{
                                                Toast.makeText(getContext(),"用户空间不足",Toast.LENGTH_SHORT).show();
                                                break;
                                            }
                                            case "typeWrong":{
                                                Toast.makeText(getContext(),"文件类型错误",Toast.LENGTH_SHORT).show();
                                                break;
                                            }
                                            case "existWrong":{
                                                Toast.makeText(getContext(),"文件为空",Toast.LENGTH_SHORT).show();
                                                break;
                                            }
                                            case "success":{
                                                Toast.makeText(getContext(),"上传成功",Toast.LENGTH_SHORT).show();
                                                //通知接收
                                                break;
                                            }
                                            default:{
                                                Toast.makeText(getContext(),"网络错误",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                        break;
                                    }
                                    default: {
                                        Toast.makeText(getContext(),"网络错误",Toast.LENGTH_SHORT).show();
                                        break;
                                    }
                                }
                            }
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        recyclerView_file.setAdapter(adapter);
        recyclerView_file.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerView_file.addItemDecoration(new FileListBeanAdapter.MyDecoration());
        recyclerView_file.setLayoutAnimation(controller);
        btn_add = view.findViewById(R.id.home_add);
        btn_add.setOnClickListener(onclick);
        return view;
    }

    /**
     * 检查文件是否存在
     */
    public void CheckFileisExisted(){
        for (FilelistBean bean:beanList
             ) {
            if(!FileUtil.fileIsExists(bean.img_url)){
                LitePal.deleteAll(FilelistBean.class,"img_url = ?",bean.img_url);
                //不存在就将其从数据库中移除
            }
        }
    }
    public class MyOnClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.home_add:{
                    createAddDialog();
                    break;
                }
                case R.id.dialog_btn_start:{
                    //检测是否为空
                    title = et_title.getText().toString();
                    if(title.equals("")){
                        Toast.makeText(getContext(),"标题不能为空",Toast.LENGTH_SHORT).show();}
                    else{
                    changeinfo.putString("title",title);
                    }
                    detail = et_discirption.getText().toString();
                    if(detail.equals("")){
                        Toast.makeText(getContext(),"描述不能为空",Toast.LENGTH_SHORT).show();
                    }else{
                        changeinfo.putString("detail",detail);
                    }
                    if (!detail.equals("")&&!title.equals("")) {
                        changeinfo.commit();
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("application/pdf");
                        intent.addCategory(Intent.CATEGORY_OPENABLE);
                        startActivityForResult(intent,ADD_PDF_CODE);
                        alertDialog.dismiss();
                    }
                    break;
                }
                case R.id.dialog_btn_collect:{
                    if(isPublic==1) {
                        isPublic = 2;
                        btn_collect.setBackgroundResource(R.drawable.shape_btn_black);
                        btn_collect.setTextColor(Color.WHITE);
                        btn_original.setTextColor(Color.BLACK);
                        btn_original.setBackgroundResource(R.drawable.shape_btn_white);
                    }
                    break;
                }
                case R.id.dialog_btn_original:{
                    if(isPublic==2) {
                        isPublic = 1;//私有
                        btn_collect.setBackgroundResource(R.drawable.shape_btn_white);
                        btn_collect.setTextColor(Color.BLACK);
                        btn_original.setTextColor(Color.WHITE);
                        btn_original.setBackgroundResource(R.drawable.shape_btn_black);
                    }
                    break;
                }
                case R.id.dialog_btn_out:{
                    alertDialog.dismiss();
                    break;
                }
                case R.id.ll_translate:{
                    startActivity(new Intent(getContext(), TranslateActivity.class));
                }
            }
        }
    }



    public void createAddDialog(){
        MyOnClick myOnClick = new MyOnClick();
        builder= new AlertDialog.Builder(getContext());
        View view = View.inflate(getContext(),R.layout.dialog_select,null);
        ImageButton btn_out = view.findViewById(R.id.dialog_btn_out);
        TextView tv_head = view.findViewById(R.id.dialog_tv_head);
        TextView tv_kind = view.findViewById(R.id.dialog_tv_kind);
        TextView tv_discription = view.findViewById(R.id.dialog_tv_discription);
        btn_original = view.findViewById(R.id.dialog_btn_original);
        btn_collect = view.findViewById(R.id.dialog_btn_collect);
        btn_collect.setOnClickListener(myOnClick);
        btn_original.setOnClickListener(myOnClick);
        btn_out.setOnClickListener(myOnClick);
        et_title = view.findViewById(R.id.dialog_et_title);
        et_discirption = view.findViewById(R.id.dialog_et_discripiton);
        Button btn_start = view.findViewById(R.id.dialog_btn_start);
        btn_start.setOnClickListener(myOnClick);
        // 设置参数
        builder.setView(view);
        builder.create();
        // 创建对话框
        alertDialog = builder.show();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case ADD_PDF_CODE:{
                if(resultCode == getActivity().RESULT_OK){
                Uri uri = data.getData();
                Log.d("TAG",uri.toString());
                if(uri!=null){
                    pdf_path = RealPathFromUriUtils.getRealPathFromUri(getContext(),uri);
                    changeinfo.putString("file_path",pdf_path);
                    changeinfo.commit();
                    FilelistBean filelistBean = new FilelistBean();
                    filelistBean.detail = fileinfo.getString("detail","描述");
                    filelistBean.title = fileinfo.getString("title","标题");
                    filelistBean.img_url = pdf_path;
                    filelistBean.isPublic = isPublic;
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.error2);
                    byte[] bytes = FileUtil.BitmapToBytes(bitmap);
                    filelistBean.img = bytes;
                    beanList.add(filelistBean);
                    adapter.notifyDataSetChanged();
                    filelistBean.save();
                    //返回true代表添加成功，返回flase代表添加失败
                    if (filelistBean.save()) {
                        Toast.makeText(getContext(), "创建成功", Toast.LENGTH_SHORT).show();
                    }
                  }
                }
                break;
             }
    }
  }
}
