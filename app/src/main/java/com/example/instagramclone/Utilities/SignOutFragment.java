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
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.instagramclone.LoginActivity;
import com.example.instagramclone.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignOutFragment extends Fragment {
    ProgressBar signoutProgressbar;
    Button signOutButton;
    FirebaseAuth mAuth=FirebaseAuth.getInstance();
    FirebaseAuth.AuthStateListener mAuthStateListener;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      Log.d("Sign out fragment","Oncreate called");
       View view=inflater.inflate(R.layout.fragment_signout,container,false);
       signoutProgressbar=view.findViewById(R.id.signoutProgressbar);
       signOutButton=view.findViewById(R.id.signoutButton);
       signoutProgressbar.setVisibility(View.GONE);
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // Sign in logic here
                    // .
                }
                else
                {
                    signoutProgressbar.setVisibility(View.VISIBLE);
                    Intent intent=new Intent(getActivity(), LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        };
       signOutButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               mAuth.signOut();
           }
       });
       return view;
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

