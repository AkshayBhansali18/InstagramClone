package com.example.instagramclone;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
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
import android.widget.Toast;

import com.example.instagramclone.Profile.ProfileActivity;
import com.example.instagramclone.Profile.ProfilePhoto;
import com.example.instagramclone.Utilities.EditProfileFragment;
import com.example.instagramclone.Utilities.SectionsPagerAdapterEditProfile;
import com.example.instagramclone.Utilities.SignOutFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AccountSettings_Activity extends AppCompatActivity {
    ListView accountlistView;
    ViewPager viewPager;
    String data;
    CollectionReference profile_photo=FirebaseFirestore.getInstance().collection("profile_photo");
    SectionsPagerAdapterEditProfile sectionsPagerAdapter;
    StorageReference reference=FirebaseStorage.getInstance().getReference();
    StorageReference value=reference.child("images/photos/"+FirebaseAuth.getInstance().getUid()+"/profilephoto");
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    CollectionReference useraccount=db.collection("user_accounts_settings");
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
        navigatetoEditProfile();
        getIntentValue();

    }

    private void getIntentValue() {
        final Intent intent=getIntent();
        if(intent.hasExtra("camera_photo"))
        {
            Bitmap bitmap=intent.getParcelableExtra("camera_photo");


        }
        Uri uri1=intent.getData();
        if(intent.getIntExtra("change_photo",0)==1)
        {
                value.putFile(uri1).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                        value.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                 Uri url=uri;
                                 data=uri.toString();
                                ProfilePhoto profilePhoto=new ProfilePhoto(data,FirebaseAuth.getInstance().getUid());
                                profile_photo.document(FirebaseAuth.getInstance().getUid()).set(profilePhoto).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(AccountSettings_Activity.this.toString(),"Success");

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(AccountSettings_Activity.this.toString(),e.toString());
                                    }
                                });
                            }
                        });
                    }
                });

                useraccount.whereEqualTo("userid", FirebaseAuth.getInstance().getUid()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(QueryDocumentSnapshot queryDocumentSnapshot:queryDocumentSnapshots)
                        {
                           String id= queryDocumentSnapshot.getId();
                            DocumentReference docref=db.collection("user_accounts_settings").document(id);
                            docref.update("profile_photo",data).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(this.toString(),"Document changed successfully");
                                    AccountSettings_Activity.this.setupviewpager(0);
                                }
                            });

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AccountSettings_Activity.this, "An Error Occurred", Toast.LENGTH_SHORT).show();

                    }
                });
        }
    }

    private void navigatetoEditProfile() {
        Intent intent = getIntent();
            String value = intent.getStringExtra("profile_activity");
            try {
                if (value.equals("edit_profile")) {
                    rel_layout1.setVisibility(View.GONE);
                    rel_layout2.setVisibility(View.GONE);
                    rel_layout3.setVisibility(View.GONE);
                    viewPager.setAdapter(sectionsPagerAdapter);
                    viewPager.setCurrentItem(0);
                }
            }
            catch (NullPointerException e)
            {

            }
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
