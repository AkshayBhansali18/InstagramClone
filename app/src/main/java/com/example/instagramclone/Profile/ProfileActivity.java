package com.example.instagramclone.Profile;

import android.content.Intent;
import android.support.annotation.NonNull;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.instagramclone.AccountSettings_Activity;
import com.example.instagramclone.BottomNavigator;
import com.example.instagramclone.DatabaseClasses.UserAccountSettings;
import com.example.instagramclone.DatabaseClasses.Users;
import com.example.instagramclone.LoginActivity;
import com.example.instagramclone.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    BottomNavigator bottomNavigator;
    ArrayList<String> imageurls = new ArrayList<>();
    BottomNavigationViewEx bottomNavigationViewEx;
    int item;
    ImageView profileMenu;
    ProgressBar profileProgressBar;
    RelativeLayout rellayout2;
    GridView gridView;
    FirebaseAuth mAuth=FirebaseAuth.getInstance();
    FirebaseAuth.AuthStateListener mAuthStateListener;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    CollectionReference users;
    CollectionReference user_accounts_settings;
    TextView profileUsername,profileDescription,editprofile,profileWebsite,profiletopUsername,textViewPosts,textViewFollowers,textViewFollowing;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        bottomNavigationViewEx = findViewById(R.id.bottom_navigation);
        bottomNavigator.editNavigation(bottomNavigationViewEx);
        profileUsername=findViewById(R.id.profile_username);
        profiletopUsername=findViewById(R.id.profiletop_username);
        profileWebsite=findViewById(R.id.profile_website);
        editprofile=findViewById(R.id.textView_editprofile);
        profileDescription=findViewById(R.id.profile_description);
        bottomNavigator.shiftingActivity(ProfileActivity.this, bottomNavigationViewEx);
        profileProgressBar = findViewById(R.id.profile_progressbar);
        gridView = findViewById(R.id.gridView);
        textViewFollowers=findViewById(R.id.textView_followers);
        textViewFollowing=findViewById(R.id.textView_following);
        textViewPosts=findViewById(R.id.textView_posts);
        users=db.collection("users");
        user_accounts_settings=db.collection("user_accounts_settings");
        CircularImageView profile_photo = (CircularImageView) findViewById(R.id.profile_photo);
        Picasso.get().load("https://cdn.pixabay.com/photo/2014/04/09/17/48/man-320276_960_720.png").centerCrop().fit().into(profile_photo);
        profileProgressBar.setVisibility(View.GONE);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(4);
        menuItem.setChecked(true);
        setuptoolbar();
        profileMenu = (ImageView) findViewById(R.id.profileMenu);
        profileMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, AccountSettings_Activity.class);
                startActivity(intent);
            }
        });
        setupurls();
        authchangeListener();
        getUserData();
        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ProfileActivity.this,AccountSettings_Activity.class);
                intent.putExtra("profile_activity","edit_profile");
                startActivity(intent);
            }
        });
    }

    private void getUserData() {
        String uid=mAuth.getCurrentUser().getUid();
        user_accounts_settings.whereEqualTo("userid",uid).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot queryDocumentSnapshot:queryDocumentSnapshots)
                {
                    UserAccountSettings userAccountSettings=queryDocumentSnapshot.toObject(UserAccountSettings.class);
                    String username=userAccountSettings.getUsername();
                    String description=userAccountSettings.getDescription();
                    String website=userAccountSettings.getWebsite();
                    String posts=userAccountSettings.getPosts();
                    String followers=userAccountSettings.getFollowers();
                    String following=userAccountSettings.getFollowing();
                    profileUsername.setText(username);
                    profileDescription.setText(description);
                    profileWebsite.setText(website);
                    profiletopUsername.setText(username);
                    textViewPosts.setText(posts);
                    textViewFollowers.setText(followers);
                    textViewFollowing.setText(following);
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ProfileActivity.this, "An Error Occurred", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void authchangeListener() {
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // Sign in logic here
                    // .
                } else {
                    Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }


        };
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
        GridViewAdapter adapter = new GridViewAdapter(imageurls, this);
        gridView.setAdapter(adapter);

    }

    private void setuptoolbar() {
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.profile_toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onStop() {
        if(mAuthStateListener!=null)
        {
            mAuth.removeAuthStateListener(mAuthStateListener);
        }
        super.onStop();
    }
}
