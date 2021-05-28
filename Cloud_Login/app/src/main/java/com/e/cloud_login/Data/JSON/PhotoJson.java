package com.e.cloud_login.Data.JSON;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PhotoJson {
    @Expose
    @SerializedName("PhotoUploadStatus")
    public String status;
}
