package com.e.cloud_login.Data.JSON;

import android.util.JsonReader;

import com.e.cloud_login.Data.User;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import org.json.JSONObject;

import java.lang.reflect.Type;

public class PhoneCodeJson {
    public int code;
    public String msg;
    public User data;
}
