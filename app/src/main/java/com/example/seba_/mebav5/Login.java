package com.example.seba_.mebav5;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.seba_.mebav5.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
  Button Registrar,iniciar,ver;
  EditText Correo,Contra;
    public static final String USER_MAIL="mail";
    FirebaseAuth.AuthStateListener authStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Correo = (EditText)findViewById(R.id.mailLogedittxt);
        Contra = (EditText)findViewById(R.id.passLogedittxt);
        Registrar = (Button)findViewById(R.id.Regusterbtn);
        iniciar=(Button)findViewById(R.id.Loginbtn);
      //  ver =(Button)findViewById(R.id.solover);

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user =   firebaseAuth.getCurrentUser();//FirebaseAuth.getInstance().getCurrentUser();
                if (user!=null){
                    String usermail = user.getEmail();
                    Intent intent = new Intent(Login.this,MainActivity.class);
                    intent.putExtra(USER_MAIL,usermail);
                    startActivity(intent);
                    finish();
                    Log.i("SESION: ","Sesion Iniciada");
                }else {
                    Log.i("SESION: ","Sesion cerrada");
                }
            }
        };

        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               try {
                   String e = Correo.getText().toString();
                   String p = Contra.getText().toString();
                   Iniciarsesion(e,p);
               }catch (Exception e ){
                   Toast.makeText(Login.this, "Complete los campos", Toast.LENGTH_SHORT).show();
               }
            }
        });

        Registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this,Registro.class);
                startActivity(intent);
            }
        });
    }

    private void Iniciarsesion(String mail, String pass)
    {
      FirebaseAuth.getInstance().signInWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
          @Override
          public void onComplete(@NonNull Task<AuthResult> task) {
             if(task.isSuccessful())
             {
                 Intent intent = new Intent(Login.this,MainActivity.class);
                 startActivity(intent);
                 finish();
             }else{
                 Toast.makeText(Login.this, task.getException().getMessage()+" ", Toast.LENGTH_SHORT).show();
             }
          }
      });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (authStateListener !=null)
        {
            FirebaseAuth.getInstance().removeAuthStateListener(authStateListener);
        }
    }
}
