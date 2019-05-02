package com.google.firebase.udacity.yanmenu;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewNoteFragment extends Fragment {

    private EditText notEdittext;
    private Button noteklebuton,donbuton;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_newnote,container,false);

        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child("notlar");


        notEdittext=(EditText)view.findViewById(R.id.not_edittext);
        noteklebuton=(Button)view.findViewById(R.id.noteEkle_buton);
        donbuton=(Button)view.findViewById(R.id.don_button);

        noteklebuton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               try{
                   databaseReference.push().setValue(notEdittext.getText().toString());
                   Toast.makeText(getLayoutInflater().getContext(),"Not kayıt edildi",Toast.LENGTH_LONG).show();
                   notEdittext.setText("");

               }catch (Exception e){
                   Toast.makeText(getLayoutInflater().getContext(),"Hata oluştu",Toast.LENGTH_LONG).show();

               }

            }
        });



        donbuton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyNotesFragment fragment=new MyNotesFragment();
                FragmentManager fragmentManager=getFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout,fragment);
                fragmentTransaction.commit();

            }
        });


        return view;
    }
}


