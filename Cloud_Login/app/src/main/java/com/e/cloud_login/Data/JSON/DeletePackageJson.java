package com.e.cloud_login.Data.JSON;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeletePackageJson {
    @Expose
    @SerializedName("DeleteFileStatus")
    public String status;
}
