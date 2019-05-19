package com.example.instagramclone.Utilities;

import android.os.Environment;

public class FilePaths {
    public static String rootDir= Environment.getExternalStorageDirectory().getPath();
    public static String pictures=rootDir+"/Pictures";
    public static String camera=rootDir+"/DCIM/camera";
}
