package com.example.instagramclone.Share;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.instagramclone.AccountSettings_Activity;
import com.example.instagramclone.Profile.ProfilePhoto;
import com.example.instagramclone.R;
import com.example.instagramclone.Utilities.NextActivity;
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
import com.squareup.picasso.Picasso;

public class PhotoFragment extends Fragment {
    int camera_request = 1;
    Button button;
    String data;
    CollectionReference profile_photo = FirebaseFirestore.getInstance().collection("profile_photo");
    StorageReference reference = FirebaseStorage.getInstance().getReference();
    StorageReference value = reference.child("images/photos/" + FirebaseAuth.getInstance().getUid() + "/profilephoto");
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference useraccount = db.collection("user_accounts_settings");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo, container, false);
        button = view.findViewById(R.id.photo_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ShareActivity.checked == 1 && ShareActivity.getnumber() == 1) {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, camera_request);
                } else {
                    Intent intent = new Intent(getActivity(), ShareActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }

            }
        });

        return view;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == camera_request) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            Intent intent=new Intent(getActivity(), NextActivity.class);
            intent.putExtra("camera_photo",bitmap);
            startActivity(intent);
            Log.d("photo fragment", "Camera started");


        }
    }


}