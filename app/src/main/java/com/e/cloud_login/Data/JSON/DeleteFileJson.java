package com.e.cloud_login.Data.JSON;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class DeleteFileJson {
    public int code;
    public String msg;
    public static class DataStateDeserializer implements JsonDeserializer<DeleteFileJson>{
        @Override
        public DeleteFileJson deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            DeleteFileJson response = new DeleteFileJson();
            JsonObject jsonObject = json.getAsJsonObject();
            if(jsonObject.has("code")) {
                response.code =jsonObject.get("code").getAsInt();
                response.msg = jsonObject.get("msg").getAsString();
            }
            else {
                response.msg = null;
                response.code = 0;
            }
            return response;
        }
    }
}
