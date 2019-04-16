package com.example.instagramclone.Utilities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.instagramclone.R;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

public class EditProfileFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       Log.d("Edit profile fragment ","onCreateView Called");
        View view=inflater.inflate(R.layout.fragment_editprofilelayout2,container,false);
        CircularImageView profile_imageView=(CircularImageView) view.findViewById(R.id.profile_imageView);
       ImageView backarrowimageView=(ImageView)view.findViewById(R.id.backArrowImageView);
       backarrowimageView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               getActivity().finish();
           }
       });
        Picasso.get().load("https://cdn.pixabay.com/photo/2019/04/06/06/44/astronaut-4106766_960_720.jpg").fit().centerCrop().into(profile_imageView);
        return view;
    }
}
