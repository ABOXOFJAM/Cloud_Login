package com.e.cloud_login.Data.JSON;

import android.graphics.Bitmap;

import org.litepal.crud.LitePalSupport;

import java.util.Calendar;
import java.util.List;

public class DownLoadBean extends LitePalSupport {
    public String url;
    public String time;
    public String title;
    public String filePhoto;
    public DownLoadBean(){
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        time = month+"/"+day+"  "+hour+":"+minute;
    }
}
