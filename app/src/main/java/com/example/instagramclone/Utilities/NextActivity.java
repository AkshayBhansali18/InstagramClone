package com.example.instagramclone.Utilities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.instagramclone.R;
import com.example.instagramclone.Share.ShareActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.ServerTimestamp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.util.Date;

public class NextActivity extends AppCompatActivity {
    EditText nextEditText;
    ImageView nextImageView;
    TextView share_textView;
    ProgressBar next_progressbar;
    private FirebaseAuth mAuth;
    StorageReference storageRef=FirebaseStorage.getInstance().getReference();
    Uri uri;
    int count;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    CollectionReference photos=db.collection("photos").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection(FirebaseAuth.getInstance().getCurrentUser().getUid());
    DocumentReference photodoc=db.collection("user_photos").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
   CollectionReference user_photos=db.collection("user_photos").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
           .collection(FirebaseAuth.getInstance().getCurrentUser().getUid());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);
        share_textView=findViewById(R.id.share_textView);
        Intent intent=getIntent();
         uri=intent.getData();
         ImageView backImageView=(ImageView)findViewById(R.id.backImageView);
        mAuth = FirebaseAuth.getInstance();
        next_progressbar=findViewById(R.id.next_progressbar);
        nextEditText=findViewById(R.id.next_editText);

        next_progressbar.setVisibility(View.INVISIBLE);
        nextImageView=findViewById(R.id.next_imageView);
        if (intent.hasExtra("camera_photo")) {
            Bitmap bitmap = intent.getParcelableExtra("camera_photo");
            nextImageView.setImageBitmap(bitmap);
        }
        share_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next_progressbar.setVisibility(View.VISIBLE);
                count = count + 1;

                final StorageReference ref = storageRef.child("images/photos/" + FirebaseAuth.getInstance().getUid() + "/photo" + count + Math.random());
                final Intent intent = getIntent();
                if (intent.hasExtra("camera_photo")) {
                    Bitmap bitmap = intent.getParcelableExtra("camera_photo");
                    String path = bitmap.toString();


                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] data = baos.toByteArray();

                    UploadTask uploadTask = ref.putBytes(data);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(NextActivity.this, "An Error Occurred", Toast.LENGTH_SHORT).show();
                            // Handle unsuccessful uploads
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ref.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    Uri uri2=task.getResult();
                                    String path=uri2.toString();
                                    String caption = nextEditText.getText().toString();
                                    String user_id = FirebaseAuth.getInstance().getUid();
                                    String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                                    Photo photo = new Photo(caption, currentDateTimeString, path, FirebaseAuth.getInstance().getUid());
                                    photos.add(photo).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Log.d("Next Activity", "Firebase database uplaod successful");
                                            next_progressbar.setVisibility(View.GONE);
                                            Intent newintent=new Intent(NextActivity.this,ShareActivity.class);

                                            startActivity(newintent);
                                            NextActivity.this.finish();
                                        }

                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d("database upload", "Error" + e.toString());
                                        }
                                    });

                                }
                            });
                            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                            // ...
                        }
                    });



                } else {
                    Log.d("Next activity",uri.toString());
                    nextImageView.setImageURI(uri);


                    ref.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            Toast.makeText(NextActivity.this, "Uploaded image successfully", Toast.LENGTH_SHORT).show();
                            next_progressbar.setVisibility(View.GONE);
                            ref.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    Uri uri1 = task.getResult();
                                    String path = uri1.toString();
                                    String caption = nextEditText.getText().toString();
                                    String user_id = FirebaseAuth.getInstance().getUid();
                                    String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                                    Photo photo = new Photo(caption, currentDateTimeString, path, FirebaseAuth.getInstance().getUid());
                                    photos.add(photo).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Toast.makeText(NextActivity.this, "Image Shared Successfully", Toast.LENGTH_SHORT).show();
                                            Log.d("Next Activity", "Firebase database uplaod successful");

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d("database upload", "Error" + e.toString());
                                        }
                                    });
                                }
                            });
                        }
                    });

                }
            }


        });




        getImageCount();
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(NextActivity.this, ShareActivity.class);
                startActivity(intent);
            }
        });


    }

    private void getImageCount() {
        photos.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                int count = 0;
                for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
                    count = count + 1;
                }

            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {


            }
        });

    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

}
