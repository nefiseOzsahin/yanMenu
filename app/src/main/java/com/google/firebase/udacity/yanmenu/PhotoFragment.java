package com.google.firebase.udacity.yanmenu;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class PhotoFragment extends Fragment {

    private static final int SELECT_IMAGE = 1;
    private Button fotografSec;
    private ImageView profilImageView;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_photo,container,false);

        firebaseStorage=FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference().child("profil_photos");

        fotografSec=(Button)view.findViewById(R.id.fotograf_sec_button);
        profilImageView=(ImageView)view.findViewById(R.id.profil_imageview);
        fotografSec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"),SELECT_IMAGE);
            }
        });

        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getLayoutInflater().getContext().getContentResolver(), data.getData());
                        profilImageView.setImageBitmap(bitmap);
                        Uri selectedImageUri=data.getData();
                        StorageReference photoref=storageReference.child(selectedImageUri.getLastPathSegment());
                        photoref.putFile(selectedImageUri).addOnSuccessListener((Activity) getLayoutInflater().getContext(),
                                new OnSuccessListener<UploadTask.TaskSnapshot>() {

                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                            }
                        });


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED)  {
                Toast.makeText(getLayoutInflater().getContext(), "Canceled", Toast.LENGTH_SHORT).show();
            }
        }
    }



}
