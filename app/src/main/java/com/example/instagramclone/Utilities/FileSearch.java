package com.example.instagramclone.Utilities;

import android.net.Uri;

import java.io.File;
import java.util.ArrayList;

public class FileSearch {
    public static ArrayList<Uri> DirectoryPaths(String directory)
    {
        ArrayList<Uri >pathArray=new ArrayList<>();
        File file=new File(directory);
        File listfile[]=file.listFiles();
        for(int i=0;i<listfile.length;i++)
        {
            if(listfile[i].isDirectory())
            {
                pathArray.add(Uri.fromFile(listfile[i]));
            }
        }
        return pathArray;

    }
    public static ArrayList<Uri> FilePaths(String directory)
    {
        ArrayList<Uri >pathArray=new ArrayList<>();
        File file=new File(directory);
        File listfile[]=file.listFiles();
        for(int i=0;i<listfile.length;i++)
        {
            if(listfile[i].isFile())
            {
                pathArray.add(Uri.fromFile(listfile[i]));
            }
        }
        return pathArray;

    }
}
