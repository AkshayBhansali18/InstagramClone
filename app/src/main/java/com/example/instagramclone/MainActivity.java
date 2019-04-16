package com.example.instagramclone;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.instagramclone.Home.CameraFragment;
import com.example.instagramclone.Home.HomeActivity;
import com.example.instagramclone.Home.HomeFragment;
import com.example.instagramclone.Home.MessagesFragment;
import com.example.instagramclone.Home.SectionsPagerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class MainActivity extends AppCompatActivity {
    BottomNavigationViewEx bottomNavigationViewEx;
    BottomNavigator bottomNavigator;
    ViewPager viewPager;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationViewEx=findViewById(R.id.bottom_navigation);
        viewPager=findViewById(R.id.viewPager);
        BottomNavigator.editNavigation(bottomNavigationViewEx);
        BottomNavigator.shiftingActivity(MainActivity.this,bottomNavigationViewEx);
        setUpPager();
        mAuth = FirebaseAuth.getInstance();

    }
    //set up view pager and add 3 tabs
    public void setUpPager()
    {
        SectionsPagerAdapter adapter=new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new CameraFragment());
        adapter.addFragment(new HomeFragment());
        adapter.addFragment(new MessagesFragment());
        viewPager.setAdapter(adapter);
        TabLayout tabLayout=(TabLayout)findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_camera_alt_black_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.instagram);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_send_black_24dp);


    }
    //--------------------------------Firebase------------------------------
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null)
        {
            String email=currentUser.getEmail();
            Toast.makeText(this, email, Toast.LENGTH_SHORT).show();

        }
        if(currentUser==null)
        {
            Intent intent=new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }


}
