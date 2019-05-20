package com.example.instagramclone.Profile;

public class ProfilePhoto {
    String path,userid;
    public ProfilePhoto()
    {

    }

    public ProfilePhoto(String path, String userid) {
        this.path = path;
        this.userid = userid;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
