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
import com.example.seba_.mebav5.Objetos.Punto_de_interes;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Agregar_PI extends AppCompatActivity {
    Button cargarFotoM;
    ImageButton addPIbtn;
    EditText tituloPiEdiText;
    EditText descripcionPiEdiTxt;
    double lat,lng,val;
    DatabaseReference DataPI;
    //CARGAR FOTO
    StorageReference storageReference;
    private static final int GALLERY_INTENT = 1;
    ImageView imageView;
    private ProgressDialog dialog;
    //----
    public static String urlss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar__pi);
        //CARGAR FOTO
        storageReference = FirebaseStorage.getInstance().getReference();
        //-----
        DataPI= FirebaseDatabase.getInstance().getReference("punto_de_interes");


        String Lat = getIntent().getStringExtra("latitud");
        String Lng = getIntent().getStringExtra("longitud");
        lat = Double.parseDouble(Lat);
        lng = Double.parseDouble(Lng);

        cargarFotoM = (Button)findViewById(R.id.Addfile);
        addPIbtn = (ImageButton)findViewById(R.id.agrepi);
        tituloPiEdiText = (EditText)findViewById(R.id.Titulo);
        descripcionPiEdiTxt = (EditText)findViewById(R.id.descripcion);
        dialog = new ProgressDialog(this);
       //CARGAR FOTO
        imageView= (ImageView)findViewById(R.id.previewPI);
        cargarFotoM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,GALLERY_INTENT);
            }
        });
         //-----
        addPIbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPI();
                Intent intent = new Intent(Agregar_PI.this,MainActivity.class);
                startActivity(intent);
                Toast.makeText(Agregar_PI.this, "punto de interés agregado", Toast.LENGTH_SHORT).show();
                tituloPiEdiText.setText("");
                descripcionPiEdiTxt.setText("");
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
                    Glide.with(Agregar_PI.this).load(desUri1).fitCenter()
                            .centerCrop().into(imageView);
                    Toast.makeText(Agregar_PI.this, "Imagen subida", Toast.LENGTH_SHORT).show();
                    urlss = desUri1.toString();
                }
            });

        }
    }
//

    // AÑADIR PUNTO DE INTERES
    private void addPI(){

        String titulo = tituloPiEdiText.getText().toString().trim();
        String descripcion = descripcionPiEdiTxt.getText().toString().trim();
        String foto;
        float rating=0;

        if(urlss==null||urlss==""){
            foto="https://firebasestorage.googleapis.com/v0/b/mebav55.appspot.com/o/Foto%2Fpaisaje.png?alt=media&token=c334a9f0-26db-49fa-b893-68591baafc62";
        }else
        {foto = urlss;}
        if (!TextUtils.isEmpty(titulo)&&!TextUtils.isEmpty(descripcion))
        {
          String id = DataPI.push().getKey();
            Punto_de_interes pi = new Punto_de_interes(id,lat,lng,titulo,descripcion,foto,rating);
            DataPI.child(id).setValue(pi);
        }else {
            Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
        }

    }
}
