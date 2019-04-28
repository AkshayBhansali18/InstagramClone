package com.example.instagramclone;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.example.instagramclone.Likes.LikesActivity;
import com.example.instagramclone.Profile.ProfileActivity;
import com.example.instagramclone.Search.SearchActivity;
import com.example.instagramclone.Share.ShareActivity;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public  class BottomNavigator {

    public static void editNavigation(BottomNavigationViewEx bottomNavigationViewEx)
    {
        bottomNavigationViewEx.enableAnimation(true);
        bottomNavigationViewEx.setTextVisibility(false);
        bottomNavigationViewEx.enableShiftingMode(false);
    }
    public static void shiftingActivity(final Context context,BottomNavigationViewEx bottomNavigationViewEx)
    {
        bottomNavigationViewEx.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id=menuItem.getItemId();
                int activity_number;

                switch(id) {
                    case R.id.home_item://0
                        Intent intent = new Intent(context, MainActivity.class);
                        activity_number=0;
                        intent.putExtra("activity_number",activity_number);
                        context.startActivity(intent);

                        break;
                    case R.id.search_item://1
                        Intent intent1=new Intent(context, SearchActivity.class);
                        activity_number=1;
                        intent1.putExtra("activity_number",activity_number);
                        context.startActivity(intent1);
                        break;
                    case R.id.share_item://2
                        Intent intent2=new Intent(context, ShareActivity.class);
                        activity_number=2;
                        intent2.putExtra("activity_number",activity_number);
                        context.startActivity(intent2);
                        break;
                    case R.id.profile_item://3
                        activity_number=3;
                        Intent intent3=new Intent(context, ProfileActivity.class);
                        intent3.putExtra("activity_number",activity_number);

                        context.startActivity(intent3);
                        break;
                    case R.id.notification_item://4
                        activity_number=4;
                        Intent intent4=new Intent(context, LikesActivity.class);
                        intent4.putExtra("activity_number",activity_number);
                        context.startActivity(intent4);
                        break;

                }



                return false;
            }
        });
    }
}
