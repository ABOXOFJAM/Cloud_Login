package com.e.cloud_login.Main_Funcation.Pan_Funcation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Pair;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.e.cloud_login.Data.FileData;
import com.e.cloud_login.Data.JSON.DeleteFileJson;
import com.e.cloud_login.Data.JSON.FileBean;
import com.e.cloud_login.Data.JSON.FileJson;
import com.e.cloud_login.Data.JSON.FileListJson;
import com.e.cloud_login.Data.JSON.FilePhotoJson;
import com.e.cloud_login.Data.JSON.PhoneLoginJson;
import com.e.cloud_login.Data.JSON.UserJson;
import com.e.cloud_login.Data.User;
import com.e.cloud_login.Login_Funcation.AccountLogin;
import com.e.cloud_login.WebService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import kotlin.UByteArray;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*******仓库的函数调用*******/
public class PanRepositroy {
    String token = new Intent().getStringExtra("token");
    String DOWNLOAD_PATH = null;
    MutableLiveData<String> current_dir = new MutableLiveData<>();//存储最近的目录
    Stack<String> parent_dir =new Stack<String>();
    MutableLiveData<Boolean> flushCheck = new MutableLiveData<>();//检测flushCheck的状态，如果改变就刷新,需要设置观察者
    private WebService panService =WebService.create();
    MutableLiveData<Integer> selectedCount = new MutableLiveData<>();//勾选的项
    ArrayList<FileData> selectedItem =new ArrayList<FileData>();
    public PanRepositroy(){
        current_dir.setValue("/ck/data");
        flushCheck.setValue(false);
        selectedCount.setValue(0);
    }
    public String uploadFile(String fileUrl, String id, String photoUrl,String title,int isPublic){
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(FileJson.class,new FileJson.DataStateDeserializer())
                .setLenient()
                .create();
        final String[] msg = {new String()};
        File file = new File(fileUrl);
        WebService webService =WebService.create(gson);
        RequestBody requetFile = RequestBody.create(MediaType.parse("multipart/for-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requetFile);
        try{
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    Call<FileJson> upload = webService.uploadFile(body,id,isPublic,photoUrl,title);
                    try {
                        FileJson fileJson = upload.execute().body();
                        msg[0] = fileJson.msg;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            try{
                thread.start();
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return msg[0];
    }
    public UserJson loadUserinfo(String phone){
        Gson gson =  new GsonBuilder()
                .registerTypeAdapter(UserJson.class, new UserJson.DataStateDeserializer())
                .setLenient()
                .create();
        WebService panservice = WebService.create(gson);
        final UserJson[] body = {new UserJson()};
         Thread thread = new Thread(new Runnable() {
             @Override
             public void run() {
                 try {
                     Call<UserJson> call = panservice.loadUserInfo(phone);
                     body[0] = call.execute().body();
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
             }
         });
         try{
             thread.start();
             thread.join();
         } catch (InterruptedException e) {
             e.printStackTrace();
         }
         return body[0];
    }
    public FilePhotoJson savePhoto(byte[] photo){
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(FilePhotoJson.class,new FilePhotoJson.DataStateDeserializer())
                .setLenient()
                .create();
        Bitmap bitmap = FileUtil.BytesToBitmap(photo);
        File file = FileUtil.compressImage(bitmap);
        RequestBody requetFile = RequestBody.create(MediaType.parse("multipart/for-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("photo", file.getName(), requetFile);
        final FilePhotoJson[] filePhotoJson = {new FilePhotoJson()};
        WebService webService = WebService.create(gson);
        try{
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Call<FilePhotoJson> savephoto = webService.updataFilePhoto(body);
                        filePhotoJson[0] = savephoto.execute().body();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            try{
                thread.start();
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filePhotoJson[0];
    }
    public FileListJson getFileList(int cnt,String id,int orderType,int page){
        Gson gson = new GsonBuilder()
                       .registerTypeAdapter(FileListJson.class,new FileListJson.DataStateDeserializer())
                       .setLenient()
                       .create();
        final FileListJson[] body = {new FileListJson()};
        WebService webService = WebService.create(gson);
        try{
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Call<FileListJson> getFile = webService.getFile(cnt,id,orderType,page);
                        body[0] = getFile.execute().body();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            try{
                thread.start();
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return body[0];
    }
    public FileListJson getServerFileList(int cnt,int orderType,int page){
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(FileListJson.class,new FileListJson.DataStateDeserializer())
                .setLenient()
                .create();
        final FileListJson[] body = {new FileListJson()};
        WebService webService = WebService.create(gson);
        try{
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Call<FileListJson> getFile = webService.getServerFile(cnt,"pdf",orderType,page);
                        body[0] = getFile.execute().body();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            try{
                thread.start();
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return body[0];
    }
    public Boolean writeResponseBodyToDisk(ResponseBody body,String filepath){
        try {
            File futureStudioIconFile = new File(filepath);
            InputStream inputStream =null;
            OutputStream outputStream =null;
           try{
               byte[] fileReader = {};

            long fileSize = body.contentLength();
            long fileSizeDownloaded = 0;
            inputStream = body.byteStream();
            //FileOutputStream用于将数据写入本地文件Input则是读取本地文件
            outputStream = new FileOutputStream(futureStudioIconFile);//传入可以传入URL或者File对象
            while(true){
                int read = inputStream.read(fileReader);
                if(read == -1) break;
                outputStream.write(fileReader,0,read);
                fileSizeDownloaded+=(long) read;
            }

            outputStream.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }finally {//不管try的值是否正常进行，在结束时都会执行
               inputStream.close();
               outputStream.close();
           }
    } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public String DeleteFile(String id,String number){
        final String[] msg = new String[1];
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(DeleteFileJson.class,new DeleteFileJson.DataStateDeserializer())
                .setLenient()
                .create();
        WebService webService = WebService.create(gson);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Call<DeleteFileJson> delete = webService.deleteFile(id,number);
                    DeleteFileJson body = delete.execute().body();
                    msg[0] = body.msg;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        try {
            thread.start();
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return msg[0];
    }
    public String Download(String phone_url, FileBean fileBean){
        Uri uri = Uri.parse(fileBean.url);
        final String[] msg = new String[1];
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Call<ResponseBody> download = panService.download(uri);
                    ResponseBody body = download.execute().body();
                    if(download == null){
                        msg[0] = "fail";
                    }
                    else{
                        byte[] bys = new byte[0];
                        bys = body.bytes();
                        FileUtil.byteArrayToFile(bys,phone_url,fileBean.fileName);//创建文件
                        File file = new File(phone_url+"/DOWNLOAD_PDF/"+fileBean.fileName);
                        if(file.exists()){
                            msg[0] = "success";
                        }
                        else {
                            msg[0] = "makefail";
                        }

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        try{
            thread.start();
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return msg[0];
    }
}