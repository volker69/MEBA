package com.example.seba_.mebav5;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.seba_.mebav5.Objetos.Anecdota;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Agregar_anecdota extends AppCompatActivity {

    EditText Title;
    EditText Descrip;
    Button Agregarphoto;
    Spinner Categorice;
    DatabaseReference DataAnec;
    ImageButton Agregaranecdota;
    //CARGAR FOTO
    StorageReference storageReference;
    private static final int GALLERY_INTENT = 1;
    ImageView imageView;
    public static String urlss;
    private ProgressDialog dialog;
    //----


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_anecdota);
        //CARGAR FOTO
        storageReference = FirebaseStorage.getInstance().getReference();
        //-----
        String ID_pi = getIntent().getStringExtra("id_pi");
        DataAnec = FirebaseDatabase.getInstance().getReference("anecdota").child(ID_pi);

        Log.e("VALORID",ID_pi);
        dialog = new ProgressDialog(this);
        Title = (EditText)findViewById(R.id.TituloAnecEditxt);
        Descrip = (EditText)findViewById(R.id.DescripcionAnecEditxt);
        Agregaranecdota = (ImageButton)findViewById(R.id.addAnecbtnimg);
        Categorice = (Spinner)findViewById(R.id.Categoriaspin);
        Agregarphoto = (Button)findViewById(R.id.CargarFotoAnecbtn);
        //CARGAR FOTO
        imageView= (ImageView)findViewById(R.id.previewAnec);
        Agregarphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,GALLERY_INTENT);
            }
        });
        //-----

        Agregaranecdota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAnec();
                Intent intent = new Intent(Agregar_anecdota.this,MainActivity.class);
                Toast.makeText(Agregar_anecdota.this, "anédota agregada", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });


    }


    //CARGAR FOTO
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
                    Glide.with(Agregar_anecdota.this).load(desUri1).fitCenter()
                            .centerCrop().into(imageView);
                    Toast.makeText(Agregar_anecdota.this, "Imagen subida", Toast.LENGTH_SHORT).show();
                    urlss = desUri1.toString();
                }


            });
        }
    }
//


    public void addAnec(){


        String title = Title.getText().toString().trim();
        String descrip = Descrip.getText().toString().trim();
        String cagorie = Categorice.getSelectedItem().toString();
        String foto;
        String fkuser=getIntent().getStringExtra(Ver_punto_de_interes.CORREO_ELECTRONICO_PI);
        if(urlss==null||urlss==""){
            foto="https://firebasestorage.googleapis.com/v0/b/mebav55.appspot.com/o/Foto%2Fpaisaje.png?alt=media&token=c334a9f0-26db-49fa-b893-68591baafc62";
        }else
        {foto = urlss;}
        Double rating = 1.0;
        if (!TextUtils.isEmpty(title)&&!TextUtils.isEmpty(descrip))
        {
            String idAne = DataAnec.push().getKey();
            Anecdota anecdota = new Anecdota(idAne,title,descrip,foto,rating,cagorie,fkuser);
            DataAnec.child(idAne).setValue(anecdota);
        }else {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
        }
    }
}
