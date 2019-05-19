package com.example.instagramclone.Utilities;

public class PhotoContents {
    String photo_url,description;

    public PhotoContents(String photo_url, String description) {
        this.photo_url = photo_url;
        this.description = description;
    }
    public PhotoContents()
    {
        
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
