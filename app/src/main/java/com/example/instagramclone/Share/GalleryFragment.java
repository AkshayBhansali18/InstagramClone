package com.example.instagramclone.Share;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.instagramclone.AccountSettings_Activity;
import com.example.instagramclone.R;
import com.example.instagramclone.Utilities.FilePaths;
import com.example.instagramclone.Utilities.FileSearch;
import com.example.instagramclone.Utilities.GridImageAdapter;
import com.example.instagramclone.Utilities.NextActivity;

import java.util.ArrayList;

public class GalleryFragment extends Fragment {
    ImageView closeimageView,galleryImageView;
    TextView nextTextview;
    Spinner spinner;
    ArrayList<Uri> files=new ArrayList<>();
    GridView gridView;
    Uri imguri;
    ArrayList<Uri> directories=new ArrayList<>();
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
        init();
        nextTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ShareActivity.flagValue==0) {
                    Intent intent = new Intent(getActivity(), NextActivity.class);
                    intent.setData(imguri);
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(getActivity(), AccountSettings_Activity.class);
                    intent.setData(imguri);
                    intent.putExtra("change_photo",1);
                    startActivity(intent);
                }
            }
        });
        return view;
    }
    public void init()
    {
        if(FileSearch.DirectoryPaths(FilePaths.pictures)!=null)
        directories=(FileSearch.DirectoryPaths(FilePaths.pictures));
        files=FileSearch.FilePaths((FilePaths.pictures));
        directories.addAll(FileSearch.FilePaths(FilePaths.camera));
        directories.addAll(files);
        ArrayAdapter<Uri> adapter=new ArrayAdapter<>(getActivity(),R.layout.support_simple_spinner_dropdown_item,directories);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("position",directories.get(position).toString());
                setupGridAdapter(directories);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    public void setupGridAdapter(ArrayList<Uri> dir)
    {

        GridImageAdapter gridImageAdapter=new GridImageAdapter(dir,getActivity());
       final ArrayList<Uri> dirs=dir;
        gridView.setAdapter(gridImageAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                galleryImageView.setImageURI(dirs.get(position));
                 imguri=dirs.get(position);
            }
        });

    }

}
