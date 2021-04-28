package com.e.cloud_login.Data.JSON;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeleteFileJson {
    @Expose
    @SerializedName("DeleteStatus")
    public String status;
}
