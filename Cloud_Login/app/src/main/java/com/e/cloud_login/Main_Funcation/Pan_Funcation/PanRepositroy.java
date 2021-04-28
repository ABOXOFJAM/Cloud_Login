package com.e.cloud_login.Main_Funcation.Pan_Funcation;

import android.content.Context;
import android.util.Pair;

import androidx.lifecycle.MutableLiveData;

import com.e.cloud_login.Data.FileData;
import com.e.cloud_login.Data.JSON.LoadFilesJson;
import com.e.cloud_login.Login_Funcation.AccountLogin;
import com.e.cloud_login.WebService;
import com.google.gson.annotations.Expose;

import java.io.IOException;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import retrofit2.Call;

public class PanRepositroy {
    String DOWNLOAD_PATH = null;
    MutableLiveData<String> current_dir = new MutableLiveData<String >();//存储最近的目录
    Stack<String> parent_dir =new Stack<String>();
    MutableLiveData<Boolean> flushCheck = false;
    private WebService panService =WebService.create();
    MutableLiveData<Integer> selectedCount = ;//勾选的项
    ArrayList<FileData> selectedItem =new ArrayList<FileData>();
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
        for (FileData file: fileData
        ) {
            if(!selectedItemExists(file)) selectedItemAdd(file);
        }
    }
    void selectedItemRemoveAll(List<FileData> fileData){
        for (FileData file: fileData
             ) {
            if(selectedItemExists(file)) selectedItemRemove(file);
        }
    }
    Pair<Boolean,List<FileData>> loadFileInformation (Context context,String username,String parentFile) throws IOException {
        List<FileData> res = null;//存URL父路径下的文件信息
        boolean success =true;
        Call<LoadFilesJson> fileInfo =null;
        try{
            fileInfo =panService.getFileInformation(username,parentFile, AccountLogin.token!!)
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
}