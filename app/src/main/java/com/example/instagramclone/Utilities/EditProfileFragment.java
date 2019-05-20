package com.example.instagramclone.Utilities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.instagramclone.DatabaseClasses.UserAccountSettings;
import com.example.instagramclone.DatabaseClasses.Users;
import com.example.instagramclone.LoginActivity;
import com.example.instagramclone.Profile.ProfileActivity;
import com.example.instagramclone.Profile.ProfilePhoto;
import com.example.instagramclone.R;
import com.example.instagramclone.Share.ShareActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class EditProfileFragment extends Fragment {
    String usernameString;
    FirebaseAuth mAuth=FirebaseAuth.getInstance();
    FirebaseAuth.AuthStateListener mAuthStateListener;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    CollectionReference users;
    EditText username,description,displayname,website,phonenumber,email;
    CollectionReference user_accounts_settings;
    DocumentReference docref;
    String path;
    String username_key="username";
    String email_key="email";
    String userid_key="userid";
    String id;
    String phone_key="phone_number";
    String description_key="description";
    String displayname_key="display_name";
    String website_key="website";
    CollectionReference profile_photo=FirebaseFirestore.getInstance().collection("profile_photo");
    ImageView tickImageView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("Edit profile fragment ", "onCreateView Called");
        View view = inflater.inflate(R.layout.fragment_editprofilelayout2, container, false);
        final CircularImageView profile_imageView = view.findViewById(R.id.profile_imageView);
       username=view.findViewById(R.id.username_editText);
       users=db.collection("users");

       user_accounts_settings=db.collection("user_accounts_settings");
       description=view.findViewById(R.id.description);
       TextView changePhoto=view.findViewById(R.id.changePhoto);
       displayname=view.findViewById(R.id.displayname_editText);
       email=view.findViewById(R.id.email);
        id=mAuth.getCurrentUser().getUid();
       tickImageView=view.findViewById(R.id.tick_imageView);
       website=view.findViewById(R.id.website_editText);
       phonenumber=view.findViewById(R.id.phoneNumber);
        ImageView backarrowimageView = view.findViewById(R.id.backArrowImageView);
        backarrowimageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        users=db.collection("users");

        user_accounts_settings=db.collection("user_accounts_settings");
    profile_photo.whereEqualTo("userid",FirebaseAuth.getInstance().getUid()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
        @Override
        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
            for(QueryDocumentSnapshot queryDocumentSnapshot:queryDocumentSnapshots)
            {
                ProfilePhoto profilePhoto=queryDocumentSnapshot.toObject(ProfilePhoto.class);
                String path=profilePhoto.getPath();
                Picasso.get().load(path).centerCrop().fit().into(profile_imageView);
                Log.d(getActivity().toString(),"Profile photo change success");
            }

        }
    }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            Toast.makeText(getActivity(), "An Error Occurred", Toast.LENGTH_SHORT).show();
            Log.d(getActivity().toString(),e.toString());
        }
    });
    authstateMethod();
        loadData();
        changePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), ShareActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        tickImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String usernamefinal=username.getText().toString();
                if(!usernamefinal.equals(usernameString))
                {
                    users.whereEqualTo("username",usernamefinal).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            String str="";
                            for(QueryDocumentSnapshot queryDocumentSnapshot:queryDocumentSnapshots)
                            {

                                Users user=queryDocumentSnapshot.toObject(Users.class);
                                str+=user.getUsername();
                            }
                            if(!str.isEmpty())
                            {
                                Toast.makeText(getActivity(), "Username Already Exists, Please Choose Another Username", Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                final Map<String,Object> map=new HashMap<>();
                                map.put(username_key,usernamefinal);
                                map.put(phone_key,phonenumber.getText().toString());
                                map.put(email_key,email.getText().toString());
                                users.whereEqualTo("userid",id).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                        for(QueryDocumentSnapshot queryDocumentSnapshot:queryDocumentSnapshots)
                                        {
                                            String id=queryDocumentSnapshot.getId();
                                            docref=db.document("users/"+id);
                                            docref.update(map);
                                        }

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getActivity(), "An Error Occurred", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                user_accounts_settings.whereEqualTo("userid",id).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                        DocumentReference accountsDocref;

                                        final Map<String,Object> map2=new HashMap<>();
                                        map2.put(username_key,username.getText().toString());
                                        map2.put(website_key,website.getText().toString());
                                        map2.put(description_key,description.getText().toString());
                                        for(QueryDocumentSnapshot queryDocumentSnapshot:queryDocumentSnapshots)
                                        {
                                            String id=queryDocumentSnapshot.getId();
                                            accountsDocref=db.document("user_accounts_settings/"+id);
                                            accountsDocref.update(map2);
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getActivity(), "An Error Occurred", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                Intent intent=new Intent(getActivity(),ProfileActivity.class);
                                startActivity(intent);

                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "An Errror Occurred", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else
                {
                    users.whereEqualTo("username",usernamefinal).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            final Map<String,Object> map=new HashMap<>();
                                map.put(username_key,username.getText().toString());
                                map.put(phone_key,phonenumber.getText().toString());
                                map.put(email_key,email.getText().toString());
                                users.whereEqualTo("userid",id).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                        for(QueryDocumentSnapshot queryDocumentSnapshot:queryDocumentSnapshots)
                                        {
                                            String id=queryDocumentSnapshot.getId();
                                            docref=db.document("users/"+id);
                                            docref.update(map);
                                        }

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getActivity(), "An Error Occurred", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                user_accounts_settings.whereEqualTo("userid",id).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                        DocumentReference accountsDocref;

                                        final Map<String,Object> map2=new HashMap<>();
                                        map2.put(username_key,username.getText().toString());
                                        map2.put(website_key,website.getText().toString());
                                        map2.put(description_key,description.getText().toString());
                                        for(QueryDocumentSnapshot queryDocumentSnapshot:queryDocumentSnapshots)
                                        {

                                            String id=queryDocumentSnapshot.getId();
                                            accountsDocref=db.document("user_accounts_settings/"+id);
                                            accountsDocref.update(map2);
                                        }
                                        Log.d(getActivity().toString(),"Added to account settings");
                                        Intent intent=new Intent(getActivity(),ProfileActivity.class);
                                        startActivity(intent);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getActivity(), "An Error Occurred", Toast.LENGTH_SHORT).show();
                                    }
                                });


                            }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "An Errror Occurred", Toast.LENGTH_SHORT).show();
                        }
                    });

                }

            }
        });
        return view;

    }


    private void loadData() {
        String uid=mAuth.getCurrentUser().getUid();
        user_accounts_settings.whereEqualTo("userid",uid).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot queryDocumentSnapshot:queryDocumentSnapshots) {
                    UserAccountSettings userAccountSettings = queryDocumentSnapshot.toObject(UserAccountSettings.class);
                    usernameString = userAccountSettings.getUsername();
                    String descriptionString = userAccountSettings.getDescription();
                    String websiteString = userAccountSettings.getWebsite();
                    username.setText(usernameString);
                    description.setText(usernameString);
                    website.setText(websiteString);

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "An Error Occurred", Toast.LENGTH_SHORT).show();
            }
        });
        users.whereEqualTo("userid",uid).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot queryDocumentSnapshot:queryDocumentSnapshots)
                {
                    Users user=queryDocumentSnapshot.toObject(Users.class);
                    String emailString=user.getEmail();
                    String phnoString=user.getPhone_number();
                    email.setText(emailString);
                    phonenumber.setText(phnoString);

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "An Error Occurred", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void authstateMethod() {
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // Sign in logic here
                    // .
                } else {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        };

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



