package com.e.cloud_login;

import android.text.TextUtils;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class WidgetSetting {
    String getMD5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString((int)b&127);
                if (temp.length() == 1) {
                    temp =String.format(0+temp);
                }
                result += temp;
            }
            if(result.length()<15) {
                //Log.i("----MD5----",result);
                return result;}
            else {
                //Log.i("-----MD5-----",result.substring(0,15));
                return result.substring(0,15);}


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();

        }
        return "";
    }
}