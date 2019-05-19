package com.example.instagramclone.Utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;


import com.example.instagramclone.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
/**
 * Created by User on 6/4/2017.
 */


public class GridImageAdapter extends BaseAdapter
{
    ArrayList<Uri> imgurls;
    Context context;

    public GridImageAdapter(ArrayList<Uri> imgurls, Context context) {
        this.imgurls = imgurls;
        this.context = context;
    }

    @Override
    public int getCount() {
        return imgurls.size();
    }

    @Override
    public Object getItem(int position) {
        return imgurls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_grid_imageview, parent, false);
        ImageView gridImageView = view.findViewById(R.id.gridImageView);
        ProgressBar progressBar = view.findViewById(R.id.gridImageProgressbar);
        progressBar.setVisibility(View.INVISIBLE);
        Picasso.get().load(imgurls.get(position)).resize(0,200).centerCrop().into(gridImageView);
            return view;
    }
}



