package com.example.instagramclone;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.instagramclone.Profile.ProfileActivity;
import com.example.instagramclone.Utilities.EditProfileFragment;
import com.example.instagramclone.Utilities.SectionsPagerAdapterEditProfile;
import com.example.instagramclone.Utilities.SignOutFragment;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AccountSettings_Activity extends AppCompatActivity {
    ListView accountlistView;
    ViewPager viewPager;
    SectionsPagerAdapterEditProfile sectionsPagerAdapter;
    RelativeLayout rel_layout1,rel_layout2,rel_layout3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings_);
        accountlistView=findViewById(R.id.accountlistView);
        rel_layout1=findViewById(R.id.rel_layout1);
        rel_layout2=findViewById(R.id.rel_layout2);
        rel_layout3=findViewById(R.id.rel_layout3);
        viewPager=findViewById(R.id.viewPager);
        setuplistView();
        setupfragments();
        ImageView backArrowImageView=(ImageView)findViewById(R.id.backArrowImageView);
        backArrowImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AccountSettings_Activity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

    }
    //add fragments to the method addFragment in sectionsviewpager class

    private void setupfragments() {
        sectionsPagerAdapter=new SectionsPagerAdapterEditProfile(getSupportFragmentManager());
        sectionsPagerAdapter.addFragment(new EditProfileFragment(),"EditProfileFragment");
        sectionsPagerAdapter.addFragment(new SignOutFragment(),"SignOutFragment");

    }
    //set up view pager with sectionsViewPagerAdapter object

    private void setupviewpager(int fragmentnumber) {
        rel_layout1.setVisibility(View.GONE);
        rel_layout2.setVisibility(View.GONE);
        rel_layout3.setVisibility(View.GONE);
        viewPager.setAdapter(sectionsPagerAdapter);
        viewPager.setCurrentItem(fragmentnumber);
        Log.d("Setupviewpager","Fragment number"+fragmentnumber);





    }



    //set up listview

    private void setuplistView() {
        ArrayList<String> profileSettings=new ArrayList<>();
        profileSettings.add(getString(R.string.edit_profile));
        profileSettings.add(getString(R.string.sign_out));
        ArrayAdapter adapter=new ArrayAdapter(AccountSettings_Activity.this,android.R.layout.simple_list_item_1,profileSettings);
        accountlistView.setAdapter(adapter);
        accountlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setupviewpager(position);
                Log.d("listview","List view on click fragment"+position);
            }
        });
    }

}
