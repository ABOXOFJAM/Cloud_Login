package com.e.cloud_login.Data;

import android.os.Bundle;

import org.litepal.crud.LitePalSupport;

public class FilelistBean extends LitePalSupport {
    public String title;
    public int isPublic;
    public String img_url;//存路径
    public String detail;//存描述
    public byte[] img;//存主图片
    //每次使用时候都检查路径，如果文件被删除了，则将其移除
}
