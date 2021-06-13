package com.e.cloud_login.Data.JSON;

import android.net.http.HttpResponseCache;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import org.json.JSONObject;

import java.lang.reflect.Type;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okhttp3.internal.http.RealResponseBody;
import okio.BufferedSource;

public class PhoneLoginJson{
    public int code;
    public String msg;
    public String data;
    public static class DataStateDeserializer implements JsonDeserializer<PhoneLoginJson>{//反序列号适配器
        @Override
        public PhoneLoginJson deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            PhoneLoginJson response = new PhoneLoginJson();
            JsonObject jsonObject = json.getAsJsonObject();
            int code = jsonObject.get("code").getAsInt();
            if(code==200){
                response.code = 200;
            if(jsonObject.has("msg")){
                JsonElement elem = jsonObject.get("msg");
                if(elem!=null&& !elem.isJsonNull()){
                    response.msg = elem.toString();
                    JsonObject jsondata =  jsonObject.get("data").getAsJsonObject();
                        if(jsondata.has("token")){
                            response.data = jsondata.get("token").getAsString();
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
