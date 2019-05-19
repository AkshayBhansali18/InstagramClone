package com.example.instagramclone.Utilities;

import android.net.Uri;

public class Photo {
    String caption,date_created,user_id;
    String image_path;

    public Photo(String caption, String date_created, String image_path,String user_id) {
        this.caption = caption;
        this.date_created = date_created;
        this.user_id = user_id;
        this.image_path = image_path;
    }
    public Photo(){

    }
    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }




    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }


}
