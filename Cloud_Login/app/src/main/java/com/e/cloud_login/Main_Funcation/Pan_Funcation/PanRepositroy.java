package com.e.cloud_login.Main_Funcation.Pan_Funcation;

import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.e.cloud_login.Data.FileData;
import com.e.cloud_login.Data.JSON.ChangeFileNameJson;
import com.e.cloud_login.Data.JSON.CreatePackageJson;
import com.e.cloud_login.Data.JSON.DeleteFileJson;
import com.e.cloud_login.Data.JSON.DeletePackageJson;
import com.e.cloud_login.Data.JSON.LoadFilesJson;
import com.e.cloud_login.Data.JSON.UploadFileJson;
import com.e.cloud_login.Login_Funcation.AccountLogin;
import com.e.cloud_login.WebService;
import com.google.gson.annotations.Expose;

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
import okhttp3.MultipartBody;
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
    void selectedItemAdd(FileData fileData){
        selectedItem.add(fileData);
        selectedCount.setValue(selectedItem.size());
    }
    void selectedItemRemove(FileData fileData){
        selectedItem.remove(fileData);
        selectedCount.setValue(selectedItem.size());
    }
    boolean selectedItemExists(FileData  fileData){
        return selectedItem.contains(fileData);
    }
    void selectedItemAddAll(List<FileData> fileData){
        for (FileData file: fileData) {
            if(!selectedItemExists(file)) selectedItemAdd(file);
        }
    }
    void selectedItemRemoveAll(List<FileData> fileData){
        for (FileData file: fileData
             ) {
            if(selectedItemExists(file)) selectedItemRemove(file);
        }
    }
    /*******请求加载服务器上的文件信息*******/
    Pair<Boolean,List<FileData>> loadFileInformation (Context context,String username,String parentFile) throws IOException {
        List<FileData> res = null;//存URL父路径下的文件信息
        boolean success =true;
        Call<LoadFilesJson> fileInfo =null;
        try{
            fileInfo =panService.getFileInformation(username,parentFile,token);
        }catch (Exception e){
            success = false;
        }
        if(success){
                    LoadFilesJson body = null;
                    try {
                        body = fileInfo.execute().body();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    switch (body.getFileInformationStatus){
                        case "success":{
                            res =body.file_data_list;
                            success = true;
                            break;
                        }
                        case "ParentFileWrong":{
                            success = false;
                        }
                    }
                }
         if(success){
             flushCheck.setValue(true);
        }
       return new Pair<>(success,res);
    }
    void uploadFile(Context context, MultipartBody.Part file,String username,String parentFile){
        Call<UploadFileJson> upload = panService.uploadFile(file,username,parentFile);
        upload.enqueue(new Callback<UploadFileJson>(){//请求回调
            @Override
            public void onResponse(Call<UploadFileJson> call, Response<UploadFileJson> response) {
              UploadFileJson body = response.body();
              if(body != null){
                  switch (body.status){
                      case "success":{
                          Toast.makeText(context,"上传成功，用时${body.status}ms",Toast.LENGTH_SHORT).show();
                          flushCheck.setValue(true);
                          break;
                      }
                      case "EmptyWrong":{
                          Toast.makeText(context,"文件为空",Toast.LENGTH_SHORT).show();
                          break;
                      }
                      case "FullOrUserWrong":{
                          Toast.makeText(context,"用户云盘空间已满或用户不存在",Toast.LENGTH_SHORT).show();
                          break;
                      }
                      case "FileNameWrong":{
                          Toast.makeText(context,"文件名为空",Toast.LENGTH_SHORT).show();
                          break;
                      }
                      case "FileTypeWrong":{
                          Toast.makeText(context,"文件类型不支持",Toast.LENGTH_SHORT).show();
                          break;
                      }
                      case "InternetWrong":{
                          Toast.makeText(context,"网络错误",Toast.LENGTH_SHORT).show();
                          break;
                      }
                      default: Toast.makeText(context,"未知错误",Toast.LENGTH_SHORT).show();
                  }
              }
            }

            @Override
            public void onFailure(Call<UploadFileJson> call, Throwable t) {
                Toast.makeText(context,"服务器无响应",Toast.LENGTH_SHORT).show();
            }
        });
    }
    void deleteFile(Context context,String username,String url){
        Call<DeleteFileJson> delete = panService.deleteFile(username,url,token);
        delete.enqueue(new Callback<DeleteFileJson>() {
            @Override
            public void onResponse(Call<DeleteFileJson> call, Response<DeleteFileJson> response) {
                DeleteFileJson body = response.body();
                if(body!=null){
                    switch (body.status){
                        case "success":{
                            Toast.makeText(context,"删除成功",Toast.LENGTH_SHORT).show();
                            flushCheck.setValue(true);
                            break;
                        }
                        case "UrlWrong":{
                            Toast.makeText(context,"url不匹配",Toast.LENGTH_SHORT).show();
                            break;
                        }
                        case "DeleteWrong":{
                            Toast.makeText(context,"服务器删除文件内部错误",Toast.LENGTH_SHORT).show();
                            break;
                        }
                        case "FileWrong":{
                            Toast.makeText(context,"文件不存在",Toast.LENGTH_SHORT).show();
                            break;
                        }
                        case "TokenWrong":{
                            Toast.makeText(context,"登入时间过长,Token失效,请重新登入",Toast.LENGTH_SHORT).show();
                            break;
                        }
                        default: Toast.makeText(context,"未知错误",Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<DeleteFileJson> call, Throwable t) {
                Toast.makeText(context,"服务器无响应",Toast.LENGTH_SHORT).show();
            }
        });
    }
    void deletePackage(Context context,String username,String url){
      Call<DeletePackageJson> delete = panService.deletePackage(username,url,token);
      delete.enqueue(new Callback<DeletePackageJson>() {
          @Override
          public void onResponse(Call<DeletePackageJson> call, Response<DeletePackageJson> response) {
              DeletePackageJson body = response.body();
              if(body !=null){
                  switch (body.status){
                      case "success":{
                          Toast.makeText(context,"删除成功",Toast.LENGTH_SHORT).show();
                          flushCheck.setValue(true);
                          break;
                      }
                      case "TokenWrong":{
                          Toast.makeText(context, "登入时间过长，Token失效", Toast.LENGTH_SHORT).show();
                          break;
                      }
                      default:Toast.makeText(context,"未知错误",Toast.LENGTH_SHORT).show();
                  }
              }
          }

          @Override
          public void onFailure(Call<DeletePackageJson> call, Throwable t) {
                   Toast.makeText(context,"服务器无响应",Toast.LENGTH_SHORT).show();
          }
      });
    }
    void createPackage(Context context,String username,String package_name,String parentFile){
        Call<CreatePackageJson> create = panService.createPackage(username,package_name,parentFile,token);
        create.enqueue(new Callback<CreatePackageJson>() {
            @Override
            public void onResponse(Call<CreatePackageJson> call, Response<CreatePackageJson> response) {
                CreatePackageJson body = response.body();
                if(body!=null){
                    switch (body.status){
                        case "success":{
                            Toast.makeText(context,"创建成功",Toast.LENGTH_SHORT).show();
                            flushCheck.setValue(true);
                            break;
                        }
                        case "TokenWrong":{
                            Toast.makeText(context,"登入时间过长，Token失效，请重新登入",Toast.LENGTH_SHORT).show();
                            break;
                        }
                        default:Toast.makeText(context,"未知错误",Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<CreatePackageJson> call, Throwable t) {
                      Toast.makeText(context,"服务器无响应",Toast.LENGTH_SHORT).show();
            }
        });
    }
    void downloadFile(Context context,String username ,String url,String filename){
        Call<ResponseBody> download = panService.downloadFile(username,url,token);
        download.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                ResponseBody body = response.body();
                if(body!=null){
                    String fe = filename;
                    int i = 1;
                    String[] hp = filename.split(".");
                    while(new File("$DOWNLOAD_PATH/$fe").exists()){
                        fe= "${hp[0]}(${i}).${hp[i]}";
                                i+=1;
                    }
                    writeResponseBodyToDisk(body,"$DOWNLOAD_PATH/$fe");
                    Toast.makeText(context,"下载成功",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(context,"下载失败",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                  Toast.makeText(context,"服务器无响应",Toast.LENGTH_SHORT).show();
            }
        });
    }
    void changeFilename(Context context,String username,String newFilename,String url){
       Call<ChangeFileNameJson> change = panService.changeFilename(username,newFilename,url,token);
       change.enqueue(new Callback<ChangeFileNameJson>() {
           @Override
           public void onResponse(Call<ChangeFileNameJson> call, Response<ChangeFileNameJson> response) {
               ChangeFileNameJson body =response.body();
               if (body!=null){
                   switch (body.status){
                       case "success":{
                           Toast.makeText(context,"修改成功",Toast.LENGTH_SHORT).show();
                           flushCheck.setValue(true);
                           break;
                       }
                       case "UrlWrong":{
                           Toast.makeText(context,"文件路径错误",Toast.LENGTH_SHORT).show();
                           break;
                       }
                       case "UserWrong":{
                           Toast.makeText(context,"文件不属于该用户",Toast.LENGTH_SHORT).show();
                           break;
                       }
                       case "RepeatWrong":{
                           Toast.makeText(context,"文件名重复",Toast.LENGTH_SHORT).show();
                           break;
                       }
                       case "TokenWrong":{
                           Toast.makeText(context,"登入时间过长,Token失效，请重新登入",Toast.LENGTH_SHORT).show();
                           break;
                       }
                       default:Toast.makeText(context,"未知错误",Toast.LENGTH_SHORT).show();
                   }
               }
           }

           @Override
           public void onFailure(Call<ChangeFileNameJson> call, Throwable t) {
               Toast.makeText(context,"服务器无响应",Toast.LENGTH_SHORT).show();
           }
       });
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
}