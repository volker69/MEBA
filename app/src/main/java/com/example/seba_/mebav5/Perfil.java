package com.example.seba_.mebav5;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.seba_.mebav5.Objetos.Usuario;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.security.PublicKey;

public class Perfil extends AppCompatActivity {

    TextView Nombre,Cumple;
    ImageView imageView;
    DatabaseReference perfilReference;
    public static String NOMBRE_USUARIO;
    public static String FOTO_URL_UPDATE;
    public static String ID_USER;
    public static String MAIL_USER;
    public static String PASS_USER;
    public static String CUMPLE_USER;
    public String FOTO_USUARIO;
    Button update,cerrar;
    private ProgressDialog dialog;
    StorageReference storageReference;
    private static final int GALLERY_INTENT = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        storageReference = FirebaseStorage.getInstance().getReference();
        Nombre = (TextView)findViewById(R.id.namePerfiltxt);
        imageView = (ImageView)findViewById(R.id.fotoPerfil);
        update =(Button)findViewById(R.id.editarPerfil);
        Cumple=(TextView)findViewById(R.id.cumpletxt);
        dialog = new ProgressDialog(this);
        cerrar =(Button) findViewById(R.id.cerrarbtn);
        perfilReference = FirebaseDatabase.getInstance().getReference("usuario");
        perfilReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot userSnapshot : dataSnapshot.getChildren())
                {
                    Usuario usuario = userSnapshot.getValue(Usuario.class);
                    String mailBD=usuario.getMail();
                    String mailSesion= getIntent().getStringExtra(MainActivity.CORREO_ELECTRONICO);
                    if (mailBD.equals(mailSesion))
                    {
                        try {
                            Nombre.setText(usuario.getNombreusu());
                            Glide.with(Perfil.this).load(usuario.getFotousu()).fitCenter()
                                    .centerCrop().into(imageView);
                            NOMBRE_USUARIO = usuario.getNombreusu();
                            ID_USER=usuario.getIDusu();
                            MAIL_USER=usuario.getMail();
                            PASS_USER=usuario.getPasswordusu();
                            CUMPLE_USER=usuario.getCumpleaños();
                            FOTO_USUARIO = usuario.getFotousu();
                            Cumple.setText(CUMPLE_USER);
                        }catch (Exception e){
                            Toast.makeText(Perfil.this, "Errror", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,GALLERY_INTENT);
            }
        });
        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogOut();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_INTENT&&resultCode==RESULT_OK){
           dialog.setTitle("Subiendo...");
            dialog.setMessage("Actualizando foto de perfil ");
            dialog.setCancelable(false);
            dialog.show();

            Uri uri = data.getData();
            StorageReference filePath = storageReference.child("Foto").child(uri.getLastPathSegment());

            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    dialog.dismiss();
                    Uri desUri1 = taskSnapshot.getDownloadUrl();
                    Glide.with(Perfil.this).load(desUri1).fitCenter()
                            .centerCrop().into(imageView);
                  Actualizar(ID_USER,NOMBRE_USUARIO,desUri1.toString(),PASS_USER,CUMPLE_USER,MAIL_USER);
                }
            });

        }
    }
    public void Actualizar(String id,String nombre,String foto,String pass,String cumple,String mail){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("usuario").child(id);
        Usuario usuario = new Usuario(id,nombre,pass,cumple,foto,mail);
        reference.setValue(usuario);
        Toast.makeText(this, "Foto de perfil actualizada", Toast.LENGTH_SHORT).show();

    }

    public void LogOut()
    {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(Perfil.this,Login.class);
        startActivity(intent);
    }
}
