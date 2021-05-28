package com.e.cloud_login.Data;

import java.util.Arrays;
import java.util.List;

public class FileType {
    List musicFormat = Arrays.asList("mp3","wma","rm","wav","mid","ape","flac");
    List movieFormat = Arrays.asList("mpg", "mpeg", "avi", "rm", "rmvb", "mov", "wmv", "asf", "dat", "asx", "wvx", "mpe", "mpa");
    List photoFormat = Arrays.asList("bmp", "jpg", "jpeg", "png", "gif", "ico");
    List rarFormat = Arrays.asList("zip", "rar", "7z");
    List packageFormat= Arrays.asList("wjj");
    String getFileFormat(String fileName){
        String[] parse = fileName.split("\\.");//需要转义
        String fmt =parse[1]; //后缀
        String MUSIC_STRING = "MUSIC";
        String MOVIE_STRING = "MOVIE";
        String PHOTO_STRING = "PHOTO";
        String FILE_STRING = "FILE";
        String RAR_STRING = "RAR";
        String PACKAGE_STRING = "WJJ";
        if(Arrays.asList(musicFormat).contains(fmt)){
            return MUSIC_STRING;
        }
        else if(Arrays.asList(movieFormat).contains(fmt)){
            return MOVIE_STRING;
        }
        else if(Arrays.asList(PHOTO_STRING).contains(fmt)){
            return  PHOTO_STRING;
        }
        else if(Arrays.asList(RAR_STRING).contains(fmt)){
            return  RAR_STRING;
        }
        else if(Arrays.asList(PACKAGE_STRING).contains(fmt)){
            return  PACKAGE_STRING;
        }
        else {
            return FILE_STRING;
        }
    }
    String getTypeFormat(String fileType){
        String MUSIC_STRING = "MUSIC";
        String MOVIE_STRING = "MOVIE";
        String PHOTO_STRING = "PHOTO";
        String FILE_STRING = "FILE";
        String RAR_STRING = "RAR";
        String PACKAGE_STRING = "WJJ";
        if(Arrays.asList(musicFormat).contains(fileType)){
            return MUSIC_STRING;
        }
        else if(Arrays.asList(movieFormat).contains(fileType)){
            return MOVIE_STRING;
        }
        else if(Arrays.asList(PHOTO_STRING).contains(fileType)){
            return  PHOTO_STRING;
        }
        else if(Arrays.asList(RAR_STRING).contains(fileType)){
            return  RAR_STRING;
        }
        else if(Arrays.asList(PACKAGE_STRING).contains(fileType)){
            return  PACKAGE_STRING;
        }
        else {
            return FILE_STRING;
        }

    }
}
