package com.google.firebase.udacity.yanmenu;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class KayitActivity extends AppCompatActivity {

    private EditText emailEdittext,parolaEdittext,parolaTekrarEdittext;

    ProgressBar progressBar;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit);

        emailEdittext=(EditText)findViewById(R.id.email_edittext);
        parolaEdittext=(EditText)findViewById(R.id.parola_edittext);
        parolaTekrarEdittext=(EditText)findViewById(R.id.parola_tekrar_edittext);

        progressBar=(ProgressBar)findViewById(R.id.progressbar);

        firebaseAuth=FirebaseAuth.getInstance();
    }

    public void kayitIslemiYap(View view) {

        progressBar.setVisibility(View.VISIBLE);

        String email=emailEdittext.getText().toString();
        String parola=parolaEdittext.getText().toString();
        String parolaTekrar=parolaTekrarEdittext.getText().toString();

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
        } if(!parolaTekrar.equals(parola)){
            parolaTekrarEdittext.setError("parolaları aynı giriniz");
            parolaTekrarEdittext.requestFocus();
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(email,parola).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Başarılı",Toast.LENGTH_LONG).show();
                    Intent i=new Intent(getApplicationContext(),anasayfa.class);
                    startActivity(i);

                }else if(task.getException() instanceof FirebaseAuthUserCollisionException){
                    Toast.makeText(getApplicationContext(),"Bu email adresi daha önceden kayıt edilmiş",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Hata: "+task.getException().getMessage(),Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    public void loginSayfasinaDon(View view) {
        Intent i=new Intent(getApplicationContext(),anasayfa.class);
        startActivity(i);
    }
}
