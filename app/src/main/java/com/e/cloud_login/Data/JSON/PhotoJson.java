package com.e.cloud_login.Data.JSON;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class PhotoJson {
    public int code;
    public String msg;
    public String data;
    public static class DataStateDeserializer implements JsonDeserializer<PhotoJson>{

        @Override
        public PhotoJson deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            PhotoJson response = new PhotoJson();
            JsonObject jsonObject = json.getAsJsonObject();
            response.code =jsonObject.get("code").getAsInt();
            response.msg = jsonObject.get("msg").getAsString();
            if(response.code == 200){
                response.data = jsonObject.get("data").getAsJsonObject()
                        .get("url").getAsString();
            }
            else {
                response.data=null;
            }
            return  response;
        }
    }
}
