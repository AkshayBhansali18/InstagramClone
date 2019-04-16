package com.example.instagramclone.Share;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.instagramclone.BottomNavigator;
import com.example.instagramclone.R;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class ShareActivity extends AppCompatActivity {
    BottomNavigator bottomNavigator;
    BottomNavigationViewEx bottomNavigationViewEx;
    int item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        bottomNavigationViewEx=findViewById(R.id.bottom_navigation);
        bottomNavigator.editNavigation(bottomNavigationViewEx);
        bottomNavigator.shiftingActivity(ShareActivity.this,bottomNavigationViewEx);

        Menu menu=bottomNavigationViewEx.getMenu();
        MenuItem menuItem=menu.getItem(2);
        menuItem.setChecked(true);
    }
}
