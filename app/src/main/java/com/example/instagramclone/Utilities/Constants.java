package com.example.instagramclone.Utilities;

import com.google.firebase.auth.FirebaseAuth;

public class Constants {
   static String profilePhotoPath="images/photos/"+ FirebaseAuth.getInstance().getUid()+"/profilephoto";

}
