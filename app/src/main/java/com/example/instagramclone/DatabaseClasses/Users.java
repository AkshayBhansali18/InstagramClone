package com.example.instagramclone.DatabaseClasses;

public class Users {
    String email,phone_number,userid,username;

    public Users(String email, String phone_number,String userid, String username) {
        this.email = email;
        this.phone_number = phone_number;
        this.username = username;
        this.userid=userid;

    }
    public Users()
    {

    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
