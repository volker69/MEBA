package com.example.seba_.mebav5;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.seba_.mebav5.Objetos.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Registro extends AppCompatActivity implements View.OnClickListener {

    EditText nombre,pass,confpass,correo,fecha;
    Button Registrar,Cancel,Subirfoto;
    StorageReference storageReference;
    private static final int GALLERY_INTENT = 1;
    ImageView imageView;
    public static String urlss;
    DatabaseReference referenceUser;
    FirebaseAuth.AuthStateListener authStateListener;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        storageReference = FirebaseStorage.getInstance().getReference();
        referenceUser = FirebaseDatabase.getInstance().getReference("usuario");
        nombre = (EditText) findViewById(R.id.regisnombretxt);
        correo = (EditText) findViewById(R.id.regismailLogedittxt);
        pass = (EditText) findViewById(R.id.regispassLogedittxt);
        confpass = (EditText) findViewById(R.id.confirpassLogedittxt);
        fecha = (EditText) findViewById(R.id.fechaText);
        Registrar = (Button) findViewById(R.id.registrarbtn);
        Cancel  = (Button) findViewById(R.id.regisCancelarbtn);
        Subirfoto = (Button) findViewById(R.id.RegisAddfile);
        imageView  = (ImageView) findViewById(R.id.previewPerfil);
        dialog = new ProgressDialog(this);

        Registrar.setOnClickListener(this);
        Cancel.setOnClickListener(this);
        Subirfoto.setOnClickListener(this);
        imageView= (ImageView)findViewById(R.id.previewPI);
        Subirfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,GALLERY_INTENT);
            }
        });

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user =   firebaseAuth.getCurrentUser();//FirebaseAuth.getInstance().getCurrentUser();

                if (user!=null){
                     Intent intent = new Intent(Registro.this,Login.class);
                     startActivity(intent);
                }else {
                    Log.i("SESION: ","Sesion cerrada");
                }
            }
        };

    }

    //cagar foto
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_INTENT&&resultCode==RESULT_OK){

            dialog.setTitle("Subiendo… ");
            dialog.setMessage("Subiendo imagen a la nube");
            dialog.setCancelable(false);
            dialog.show();

            Uri uri = data.getData();
            StorageReference filePath = storageReference.child("Foto").child(uri.getLastPathSegment());
            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    dialog.dismiss();
                    Uri desUri1 = taskSnapshot.getDownloadUrl();
                    Glide.with(Registro.this).load(desUri1).fitCenter()
                            .centerCrop().into(imageView);
                    Toast.makeText(Registro.this, "Imagen subida", Toast.LENGTH_SHORT).show();
                    urlss = desUri1.toString();

                }
            });
        }
    }

    private void RegistrarFirebase(String Correo,String Contrasena)
    {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(Correo,Contrasena).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    agregarUsuer();

                    Log.i("SESION","Usuario creado");
                }else {
                    Log.e("SESION","Su contraseña debe tener como mínimo 6 caracteres");
                }
            }
        });
    }
    public void agregarUsuer(){
        String ID= referenceUser.push().getKey();
        String name = nombre.getText().toString().trim();
        String contra = pass.getText().toString().trim();
        String confcontra = confpass.getText().toString().trim();
        String Email = correo.getText().toString().trim();
        String cumple = fecha.getText().toString().trim();
        String foto;
        if(urlss==null||urlss==""){
            foto="https://firebasestorage.googleapis.com/v0/b/mebav55.appspot.com/o/Foto%2Fperfil.png?alt=media&token=4e421758-3f7a-44e3-b7b2-df74f8e14a5c";
        }else
        {foto = urlss;}

        if (!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(contra)&&!TextUtils.isEmpty(confcontra)&&!TextUtils.isEmpty(Email)&&!TextUtils.isEmpty(cumple)){
            Usuario usuario = new Usuario(ID,name,contra,cumple,foto,Email);
            //referenceUser.child(Email).setValue(usuario);
            referenceUser.child(ID).setValue(usuario);
        }

    }
    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.registrarbtn:
                String name = nombre.getText().toString().trim();
                String contra = pass.getText().toString().trim();
                String confcontra = confpass.getText().toString().trim();
                String Email = correo.getText().toString().trim();
                String cumple = fecha.getText().toString().trim();
                if (!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(contra)&&!TextUtils.isEmpty(confcontra)&&!TextUtils.isEmpty(Email)&&!TextUtils.isEmpty(cumple)){
                   if(contra.equals(confcontra)){
                       RegistrarFirebase(Email,contra);
                      // agregarUsuer();
                      // Intent intent = new Intent(Registro.this,Login.class);
                       //startActivity(intent);
                   }else{
                       Toast.makeText(this, "Verifique su contraseña ", Toast.LENGTH_SHORT).show();
                   }
                }else {
                    Toast.makeText(this, "Llene todos los campos ", Toast.LENGTH_SHORT).show();
                }
            break;
            case R.id.regisCancelarbtn:
                Intent intent = new Intent(Registro.this,Login.class);
                startActivity(intent);
                break;

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        FirebaseAuth.getInstance().removeAuthStateListener(authStateListener);
    }
}
