package com.e.cloud_login.Data;

public class FilelistBean {
    public String time_ago;
    public String time_now;
    public int image_id;
    public String name;

    public int getImage_id() {
        return image_id;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }

    public String getTime_ago() {
        return time_ago;
    }

    public void setTime_ago(String time_ago) {
        this.time_ago = time_ago;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getTime_now() {
        return time_now;
    }

    public void setTime_now(String time_now) {
        this.time_now = time_now;
    }
}
