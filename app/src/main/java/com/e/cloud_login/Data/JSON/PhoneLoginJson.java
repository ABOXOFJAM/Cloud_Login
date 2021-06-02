package com.e.cloud_login.Data.JSON;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import org.json.JSONObject;

import java.lang.reflect.Type;

import okhttp3.ResponseBody;

public class PhoneLoginJson{
    public int code;
    public String msg;
    public String data;
    public static class DataStateDeserializer implements JsonDeserializer<PhoneLoginJson>{//反序列号适配器
        @Override
        public PhoneLoginJson deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            PhoneLoginJson response = new Gson().fromJson(json,PhoneLoginJson.class);
            JsonObject jsonObject = json.getAsJsonObject();
            if(jsonObject.has("data")){
                JsonElement elem = jsonObject.get("data");
                if(elem!=null&& !elem.isJsonNull()){
                    if(elem.isJsonPrimitive()) response.msg = elem.toString();
                    else{
                        if(elem.getAsJsonObject().has("token")){
                            response.data = elem.getAsJsonObject().get("token").toString();
                        }
                    }
                }

            }
            return response;
        }
    }

    @Override
    public String toString() {
        return "PhoneLoginJson{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
