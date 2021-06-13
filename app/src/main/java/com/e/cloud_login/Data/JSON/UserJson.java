package com.e.cloud_login.Data.JSON;

import com.e.cloud_login.Data.User;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class UserJson {
    public int code;
    public String msg;
    public User data;
    public static class DataStateDeserializer implements JsonDeserializer<UserJson>{

        @Override
        public UserJson deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            UserJson response = new UserJson();
            JsonObject jsonObject = json.getAsJsonObject();
            int code = jsonObject.get("code").getAsInt();
            User user = new User();
            if(code==200){
                response.code = 200;
                response.msg = jsonObject.get("msg").getAsString();
                JsonObject data = jsonObject.get("data").getAsJsonObject();
                JsonElement userdata = data.get("user");
                Gson gson = new Gson();
                user = gson.fromJson(userdata,User.class);
                response.data=user;
                return response;
            }
            else {
                response.code = code;
                response.msg =jsonObject.get("msg").getAsString();
                response.data = null;
                return  response;
            }
        }
    }
}
