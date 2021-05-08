package com.e.cloud_login.Data.JSON;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UploadFileJson {
    @Expose
    @SerializedName("UploadCostTime")
    public   int costTime;
    @Expose
    @SerializedName("UploadStatus")
    public   String status;
}
