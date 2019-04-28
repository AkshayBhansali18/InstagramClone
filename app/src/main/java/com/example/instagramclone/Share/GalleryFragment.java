package com.example.instagramclone.Share;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.instagramclone.R;

public class GalleryFragment extends Fragment {
    ImageView closeimageView,galleryImageView;
    TextView nextTextview;
    Spinner spinner;
    GridView gridView;
    ProgressBar progressBar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_gallery,container,false);
       closeimageView=view.findViewById(R.id.close_imageView);
       nextTextview=view.findViewById(R.id.next_textView);
       spinner=view.findViewById(R.id.spinner);
       progressBar=view.findViewById(R.id.gallery_progressbar);
       galleryImageView=view.findViewById(R.id.gallery_imageView);
       gridView=view.findViewById(R.id.gallery_gridview);
       closeimageView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               getActivity().finish();
           }
       });


        return view;
    }
}
