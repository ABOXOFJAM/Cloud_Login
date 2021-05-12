package com.e.cloud_login.Fragment;

import android.accounts.Account;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.e.cloud_login.Data.JSON.PhotoJson;
import com.e.cloud_login.Login_Funcation.AccountLogin;
import com.e.cloud_login.R;


import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

import static android.app.Activity.RESULT_OK;
import static com.e.cloud_login.R.*;
import static com.e.cloud_login.R.id.fg_mine_head;

public class MineFragment extends BaseFragment {
    private AccountLogin accountService = new AccountLogin();
    private TextView detail,setting;
    private ImageView head;
    private String LOGIN_STATE ="login_state";
    private File imageFile = null;//操作的图片对象
    private Uri imageUri = null; //裁剪后的图片URI
    private final int LOGIN_ACTIVITY = 0X01;
    private final int SPACE_ACTIVITY = 0X02;
    private final int CROP_PHOTO = 0x12;//剪裁图片的申请码
    private final int REQUEST_CODE_GALLERY= 0x03;//图库选取图片标识请求码


    @Override
    public View initView() {
        View view = View.inflate(getActivity(), layout.fragment_mine,null);
        detail = view.findViewById(id.fg_mine_detail);
        setting = view.findViewById(id.fg_mine_setting);
        head = view.findViewById(fg_mine_head);
        setClickEvent();
        return view;
    }
    private void setClickEvent(){
        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Head","更新头像，按下");
                gallery();
            }
        });
    }
    @Override
    public void initData() {
        super.initData();
    }
    /**
     * 获得选中图片返回路径的函数
     */
    private void gallery(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,REQUEST_CODE_GALLERY);
    }

    /**
     * 回调函数
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case LOGIN_ACTIVITY:{
                if(resultCode == Activity.RESULT_CANCELED){
                   // finish();
                }
                else{
                   // defaultLogin();
                }
                break;
            }
            case REQUEST_CODE_GALLERY:{
                   if(resultCode ==RESULT_OK){
                       Log.d("PORTRAIT_TEXT","获取头像返回");
                       Uri uri = data.getData();//获取图片的uri
                       Intent intent_gallery_crop = new Intent("com.android.camera.action.CROP");
                       intent_gallery_crop.setDataAndType(uri,"image/*");
                       //设置裁剪
                       intent_gallery_crop.putExtra("crop","ture");
                       intent_gallery_crop.putExtra("scale",true);
                       //aspectX aspectY是宽和高的比例
                       intent_gallery_crop.putExtra("aspectX",1);
                       intent_gallery_crop.putExtra("aspectY",1);
                       //outputX outputY是裁剪图片宽高
                       intent_gallery_crop.putExtra("outputX",400);
                       intent_gallery_crop.putExtra("outputY",400);
                       intent_gallery_crop.putExtra("return-data",false);
                       //创建保存裁剪的图片文件
                       createImangeFile();
                       imageUri = Uri.fromFile(imageFile);
                       if(imageUri!=null){
                           intent_gallery_crop.putExtra(MediaStore.EXTRA_OUTPUT,
                                   imageUri);
                           intent_gallery_crop.putExtra("outputFormat",
                                   Bitmap.CompressFormat.JPEG.toString());
                       }
                       startActivityForResult(intent_gallery_crop,CROP_PHOTO);
                       break;
                   }
                }
            case CROP_PHOTO:{
                if(resultCode == RESULT_OK){
                    try {
                        if (imageUri!=null){
                            Log.d("TAG",imageUri.toString());
                            displayImage(imageUri);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
            case SPACE_ACTIVITY:{
                try{
                   //
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }

            default: break;
        }

        }

    /**
     * 显示图片
      */
    private void displayImage(Uri imageUri){
        Log.d("PORTRAIT_TEXT","显示头像");
        File file = new File(imageUri.getPath());
        RequestBody requetFile = RequestBody.create(MediaType.parse("multipart/for-data"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requetFile);
        PhotoJson result = accountService.accountPhoto(getContext(), part, accountService.user.username);
        if(result != null && result.status == "success"){
            Log.d("PORTRAIT_TEXT","加载头像成功");
            Glide.with(getContext())
                    .load(imageUri)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .placeholder(drawable.ic_launcher_background)
                    .error(drawable.cathead)
                    .centerCrop()
                    .into(head);
        }
        else {//如果加载头像不成功，则显示默认图片
            Log.d("PORTRAIT_TEXT","加载头像失败");
            String PORTRAIT = "portrait.png";
            String filepath = "${accountService.PORTRAIT_PATH}/${AccountRepository.user?.username}${PORTRAIT}";
            File portraitImg = new File(filepath);
            if (portraitImg.exists()){
                Glide.with(getContext())
                        .load(imageUri)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .placeholder(drawable.ic_launcher_background)
                        .error(drawable.cathead)
                        .centerCrop()
                        .into(head);
            }
        }
    }

    /**
     * 创建File保存图片
     */
    private void createImangeFile(){
        try{
            if(imageFile !=null&&imageFile.exists()){
                imageFile.delete();
            }
            //新建文件
            String PORTRAIT = "portrait.png";
            Long time =System.currentTimeMillis();
            String filepath = "${accountService.PORTRAIT_PATH}/${accountService.user.username}${PORTRAIT}";
            /**
             * 通过Context.getExternalFilesDir()方法可以获取到 SDCard/Android/data/你的应用的包名/files/ 目录，
             * 一般放一些长时间保存的数据
             * 通过Context.getExternalCacheDir()方法可以获取到 SDCard/Android/data/你的应用包名/cache/目录，
             * 一般存放临时缓存数据
             */
            imageFile = new File(getContext().getExternalFilesDir(null),
                    time.toString()+PORTRAIT);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
