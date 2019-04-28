package com.example.instagramclone.Share;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.instagramclone.BottomNavigator;
import com.example.instagramclone.Home.CameraFragment;
import com.example.instagramclone.Home.SectionsPagerAdapter;
import com.example.instagramclone.R;
import com.example.instagramclone.Utilities.Permissions;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class ShareActivity extends AppCompatActivity {
    BottomNavigator bottomNavigator;
    BottomNavigationViewEx bottomNavigationViewEx;
    int item;
    static int checked=0;
    ViewPager viewPager;
    static TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        viewPager=findViewById(R.id.viewPager);
        bottomNavigationViewEx=findViewById(R.id.bottom_navigation);
        tabLayout=findViewById(R.id.share_tablayout);
//        bottomNavigator.editNavigation(bottomNavigationViewEx);
//        bottomNavigator.shiftingActivity(ShareActivity.this,bottomNavigationViewEx);
//
//        Menu menu=bottomNavigationViewEx.getMenu();
//        MenuItem menuItem=menu.getItem(2);
//        menuItem.setChecked(true);

        if(checkpermissions(Permissions.permissions))
        {
             checked=1;

        }
        else
        {
            verifypermissions(Permissions.permissions);
        }
        SectionsPagerAdapter sectionsPagerAdapter=new SectionsPagerAdapter(getSupportFragmentManager());
        sectionsPagerAdapter.addFragment(new GalleryFragment());
        sectionsPagerAdapter.addFragment(new PhotoFragment());
        viewPager.setAdapter(sectionsPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText("GALLERY");
        tabLayout.getTabAt(1).setText("CAMERA");

    }

    void verifypermissions(String[] permissions)
    {
        ActivityCompat.requestPermissions(ShareActivity.this,permissions,1);
    }
    boolean checkpermissions(String[] permissions)
    {
        for(int i=0;i<permissions.length;i++)
        {
            String check=permissions[i];
           if(!confirmpermissions(check))
               return false;
        }
        return true;
    }
    public boolean confirmpermissions(String check)
    {
        int permissionrequest= ActivityCompat.checkSelfPermission(ShareActivity.this,check);
        if(permissionrequest== PackageManager.PERMISSION_GRANTED)
            return true;
        else
            return false;
    }
    public static int getnumber()
    {
        return tabLayout.getSelectedTabPosition();
    }

}
