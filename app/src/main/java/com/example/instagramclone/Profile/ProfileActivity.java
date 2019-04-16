package com.example.instagramclone.Profile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.instagramclone.AccountSettings_Activity;
import com.example.instagramclone.BottomNavigator;
import com.example.instagramclone.R;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    BottomNavigator bottomNavigator;
    ArrayList<String> imageurls=new ArrayList<>();
    BottomNavigationViewEx bottomNavigationViewEx;
    int item;
    ImageView profileMenu;
    ProgressBar profileProgressBar;
    RelativeLayout rellayout2;
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        bottomNavigationViewEx=findViewById(R.id.bottom_navigation);
        bottomNavigator.editNavigation(bottomNavigationViewEx);
        bottomNavigator.shiftingActivity(ProfileActivity.this,bottomNavigationViewEx);
        profileProgressBar=findViewById(R.id.profile_progressbar);
        gridView=findViewById(R.id.gridView);
        CircularImageView profile_photo=(CircularImageView)findViewById(R.id.profile_photo);
        Picasso.get().load("https://cdn.pixabay.com/photo/2014/04/09/17/48/man-320276_960_720.png").centerCrop().fit().into(profile_photo);
        profileProgressBar.setVisibility(View.GONE);
        Menu menu=bottomNavigationViewEx.getMenu();
        MenuItem menuItem=menu.getItem(4);
        menuItem.setChecked(true);
        setuptoolbar();
        profileMenu=(ImageView)findViewById(R.id.profileMenu);
        profileMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ProfileActivity.this, AccountSettings_Activity.class);
                startActivity(intent);
            }
        });
        setupurls();
    }

    private void setupurls() {
        imageurls.add("https://cdn.pixabay.com/photo/2018/09/22/12/31/cat-3695213__340.jpg");
        imageurls.add("https://cdn.pixabay.com/photo/2018/04/22/05/29/star-3340185__340.png");
        imageurls.add("https://cdn.pixabay.com/photo/2017/10/25/12/33/rocket-2887845__340.png");
        imageurls.add("https://cdn.pixabay.com/photo/2019/04/01/10/32/baltic-sea-4095045__340.jpg");
        imageurls.add("https://cdn.pixabay.com/photo/2019/03/19/14/50/fantasy-4065898__340.jpg");
        imageurls.add("https://cdn.pixabay.com/photo/2015/12/01/20/28/road-1072823__340.jpg");
        imageurls.add("https://cdn.pixabay.com/photo/2016/11/14/04/45/elephant-1822636__340.jpg");
        imageurls.add("https://cdn.pixabay.com/photo/2016/03/27/22/22/fox-1284512__340.jpg");
        imageurls.add("https://cdn.pixabay.com/photo/2018/01/21/19/57/tree-3097419__340.jpg");
        imageurls.add("https://cdn.pixabay.com/photo/2016/11/12/11/51/forest-1818690__340.jpg");
        imageurls.add("https://cdn.pixabay.com/photo/2018/01/22/14/13/animal-3099035__340.jpg");
        imageurls.add("https://cdn.pixabay.com/photo/2014/11/21/17/27/frog-540812__340.jpg");
        setupgridview();
    }

    private void setupgridview() {
        GridViewAdapter adapter=new GridViewAdapter(imageurls,this);
        gridView.setAdapter(adapter);

    }

    private void setuptoolbar() {
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.profile_toolbar);
        setSupportActionBar(toolbar);
    }
}
