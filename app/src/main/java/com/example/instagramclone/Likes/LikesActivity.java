package com.example.instagramclone.Likes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.instagramclone.BottomNavigator;
import com.example.instagramclone.LoginActivity;
import com.example.instagramclone.R;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class LikesActivity extends AppCompatActivity {
    BottomNavigator bottomNavigator;
    BottomNavigationViewEx bottomNavigationViewEx;
    int item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_likes);
        bottomNavigationViewEx=findViewById(R.id.bottom_navigation);
        bottomNavigator.editNavigation(bottomNavigationViewEx);
        bottomNavigator.shiftingActivity(LikesActivity.this,bottomNavigationViewEx);
        Menu menu=bottomNavigationViewEx.getMenu();
        MenuItem menuItem=menu.getItem(1);
        menuItem.setChecked(true);
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);

    }
}
