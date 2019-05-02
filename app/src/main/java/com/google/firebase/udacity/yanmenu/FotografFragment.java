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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class FotografFragment extends Fragment {

    private ImageView fotoImageView;
    private Button yukleButton;
    private final int SELECT_IMAGE=1;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    String sdownload_url=null;
    Uri imageUri;
    String userId,strProfileImage;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;






    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_fotograf,container,false);




        fotoImageView=(ImageView)view.findViewById(R.id.foto_imageview);
        yukleButton=(Button)view.findViewById(R.id.foto_yukle_button);
        firebaseStorage=FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference().child("fotograflar");
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child("users");



       if(sdownload_url!=null) {
           Glide.with(getLayoutInflater().getContext()).load(sdownload_url).centerCrop().into(fotoImageView);
       }







        yukleButton.setOnClickListener(new View.OnClickListener() {
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
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                        fotoImageView.setImageBitmap(bitmap);
                        Uri secilenfotografuri=data.getData();
                        imageUri=data.getData();

                        kaydet();




                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED)  {
                Toast.makeText(getActivity(), "Canceled", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void kaydet(){

        if(imageUri!=null){
            userId= FirebaseAuth.getInstance().getCurrentUser().getUid();
            StorageReference imagePath=storageReference.child(userId).child(imageUri.getLastPathSegment());
            imagePath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    String image_firebase_uri=taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                    strProfileImage=image_firebase_uri;
                    databaseReference.child(userId).child("profile_image").setValue(strProfileImage);


                }
            });
        }
    }
}
