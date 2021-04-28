package com.e.cloud_login.Data.JSON;

import com.e.cloud_login.Data.FileData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoadFilesJson {
    @Expose
    @SerializedName("FileDataList")
   public List<FileData> file_data_list;
    @Expose
    public String getFileInformationStatus;
}
