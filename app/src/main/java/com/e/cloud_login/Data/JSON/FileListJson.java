package com.e.cloud_login.Data.JSON;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FileListJson {
    public int code;
    public String msg;
    public int pages;
    public int counts;
    public List<FileBean> fileList;

    public static class DataStateDeserializer implements JsonDeserializer<FileListJson> {

        @Override
        public FileListJson deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            FileListJson response = new FileListJson();
            JsonObject jsonObject = json.getAsJsonObject();
            response.code = jsonObject.get("code").getAsInt();
            response.msg = jsonObject.get("msg").getAsString();
            JsonObject dataList = jsonObject.get("data").getAsJsonObject();
            if(dataList.has("counts")){
                List<FileBean> beanList = new ArrayList<>();
                response.pages = dataList.get("pages").getAsInt();
                response.counts = dataList.get("counts").getAsInt();
                JsonArray list = dataList.get("fileList").getAsJsonArray();
                Gson gson = new Gson();
                Iterator it = list.iterator();
                while(it.hasNext()){
                    JsonElement jsonElement = (JsonElement) it.next();
                    String temp = jsonElement.toString();
                    FileBean file =gson.fromJson(temp,FileBean.class);
                    beanList.add(file);
                }
                response.fileList = beanList;
            }
            else {
                response.pages = 0;
                response.counts =0;
                response.fileList = null;
            }
            return response;
        }
    }
}

