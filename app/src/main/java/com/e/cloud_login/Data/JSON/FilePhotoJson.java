package com.e.cloud_login.Data.JSON;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class FilePhotoJson {
    public int code;
    public String msg;
    public String data;
    public static class DataStateDeserializer implements JsonDeserializer<FilePhotoJson>{

        @Override
        public FilePhotoJson deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            FilePhotoJson response = new FilePhotoJson();
            JsonObject jsonObject = json.getAsJsonObject();
            int code = jsonObject.get("code").getAsInt();
            if(code==200){
                response.code = 200;
                if(jsonObject.has("msg")){
                    JsonElement elem = jsonObject.get("msg");
                    if(elem!=null&& !elem.isJsonNull()){
                        response.msg = elem.toString();
                        JsonObject jsondata =  jsonObject.get("data").getAsJsonObject();
                        if(jsondata.has("url")){
                            response.data = jsondata.get("url").getAsString();
                        }
                    }
                }
            }
            else {
                response.code = code;
                response.msg = jsonObject.get("msg").getAsString();
                response.data =null;
            }
            return response;
        }
    }
}
