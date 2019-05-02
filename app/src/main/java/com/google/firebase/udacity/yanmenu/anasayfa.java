package com.google.firebase.udacity.yanmenu;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class anasayfa extends AppCompatActivity {

    private EditText emailEdittext,parolaEdittext;

    ProgressBar progressBar;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anasayfa);
        emailEdittext=(EditText)findViewById(R.id.email_edittext);
        parolaEdittext=(EditText)findViewById(R.id.parola_edittext);
        firebaseAuth=FirebaseAuth.getInstance();
        progressBar=(ProgressBar)findViewById(R.id.progressbar);
    }

    public void anasayfayaGit(View view) {
        progressBar.setVisibility(View.VISIBLE);

        String email=emailEdittext.getText().toString();
        String parola=parolaEdittext.getText().toString();

        if(email.isEmpty()){
            emailEdittext.setError("Email gerekli!");
            emailEdittext.requestFocus();
            return;
        }  if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEdittext.setError("Geçerli adres giriniz!");
            emailEdittext.requestFocus();
            return;
        }  if(parola.isEmpty()){
            parolaEdittext.setError("Email gerekli!");
            parolaEdittext.requestFocus();
            return;
        }   if(parola.length()<6){
            parolaEdittext.setError("parola 6 karakter olmalı");
            parolaEdittext.requestFocus();
            return;
        }


        firebaseAuth.signInWithEmailAndPassword(email,parola).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Başarılı",Toast.LENGTH_LONG).show();
                    Intent i=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(i);

                }else{
                    Toast.makeText(getApplicationContext(),"Hata: "+task.getException().getMessage(),Toast.LENGTH_LONG).show();

                }
            }
        });



    }

    public void uyelikSayfasinaGit(View view) {
        Intent i=new Intent(getApplicationContext(),KayitActivity.class);
        startActivity(i);
    }
}
