package com.e.cloud_login.Data.JSON;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class FileJson {
    public int code;
    public String msg;
    public String data;
    public static class DataStateDeserializer implements JsonDeserializer<FileJson>{

        @Override
        public FileJson deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            FileJson fileJson = new FileJson();
            fileJson.code = jsonObject.get("code").getAsInt();
            fileJson.msg = jsonObject.get("msg").getAsString();
            fileJson.data="";
            return fileJson;
        }

    }
}
