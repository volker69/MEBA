package com.example.seba_.mebav5;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.seba_.mebav5.Objetos.AdapterComentario;
import com.example.seba_.mebav5.Objetos.Comentario;
import com.example.seba_.mebav5.Objetos.ComentarioEnviar;
import com.example.seba_.mebav5.Objetos.ComentarioRecibir;
import com.example.seba_.mebav5.Objetos.Usuario;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class Ver_anecdota extends AppCompatActivity {
    TextView titulo,descripcionn,categoria;
    Button comentar;
    ImageView imageView;
    CircleImageView perfilFoto;
    private CircleImageView fotoPerfil;
    private TextView nombre;
    private RecyclerView rvComentario;
    private EditText txtMensaje;
    ImageButton galeria;
    private AdapterComentario adapterComentario;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private DatabaseReference perilReference;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private static final int PHOTO_SEND = 1;
    private static final int PHOTO_PERFIL = 2;
    public static String fotoPerfilCadena;
    public static String nombreComenta;
    //public static String urlPErfil;

    android.support.v7.widget.Toolbar toolbar;
    CollapsingToolbarLayout toolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_anecdota);
        //--------
       toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_anec);
       toolbarLayout=(CollapsingToolbarLayout)findViewById(R.id.collapsing_anec);

       setSupportActionBar(toolbar);
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Context context = this;
        toolbarLayout.setContentScrimColor(ContextCompat.getColor(context,R.color.colorPrimary));
        toolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(context,R.color.white));
        toolbarLayout.setExpandedTitleColor(ContextCompat.getColor(context,R.color.white));
        //----
            perilReference = FirebaseDatabase.getInstance().getReference("usuario");


            perfilFoto = (CircleImageView)findViewById(R.id.userPerfilAnec);
           // titulo = (TextView) findViewById(R.id.tituloAnecdota);
            descripcionn = (TextView) findViewById(R.id.descripcionAnecDotatxt);
            categoria = (TextView) findViewById(R.id.CategoriaAnectxt);
            comentar = (Button) findViewById(R.id.comentarbtn);
            imageView = (ImageView) findViewById(R.id.fotoAnecdojpj);
            //----------
            fotoPerfil = (CircleImageView) findViewById(R.id.fotoPerfilComentario);
            nombre = (TextView) findViewById(R.id.NombreUserAnectxt);
            rvComentario = (RecyclerView) findViewById(R.id.recycleComentario);
            txtMensaje = (EditText) findViewById(R.id.Comentarios);
            //  galeria = (ImageButton)findViewById(R.id.gallarybtn);
            fotoPerfilCadena = "";
            //------

            perilReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {




                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren())
                    {
                        //esto es para lo de anecdota
                        Usuario usuario = userSnapshot.getValue(Usuario.class);
                        String mailBD=usuario.getMail();
                        String mailAnec = getIntent().getStringExtra(Ver_punto_de_interes.ANEC_FKUSER_MAIL);
                        if(mailBD.equals(mailAnec)){
                            try {
                                String perfilanecurl = usuario.getFotousu();
                                nombre.setText(usuario.getNombreusu());

                                Glide.with(Ver_anecdota.this).load(perfilanecurl).fitCenter()
                                        .centerCrop().into(perfilFoto);
                            }catch (Exception e){
                                Toast.makeText(Ver_anecdota.this, "Errror", Toast.LENGTH_SHORT).show();
                            }
                        }
                        //esto es para los comentarios
                        String mailSesion= getIntent().getStringExtra(Ver_punto_de_interes.CORREO_ELECTRONICO_PI);
                        if (mailBD.equals(mailSesion))
                        {
                            try {

                                fotoPerfilCadena=usuario.getFotousu();
                                Log.i("ESTO",fotoPerfilCadena);
                                nombreComenta=usuario.getNombreusu();
                                Log.i("ESTO",nombreComenta);

                            }catch (Exception e){
                                Toast.makeText(Ver_anecdota.this, "Errror", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            Intent intent = getIntent();
            final String ID = intent.getStringExtra(Ver_punto_de_interes.ANEC_ID);
            String title = intent.getStringExtra(Ver_punto_de_interes.ANEC_TITLE);
            String description = intent.getStringExtra(Ver_punto_de_interes.ANEC_DESCRIP);
            String categorie = intent.getStringExtra(Ver_punto_de_interes.ANEC_CATEGORI);
            String foto = intent.getStringExtra(Ver_punto_de_interes.ANEC_FOTO);
            //-----
            database = FirebaseDatabase.getInstance();
            databaseReference = database.getReference("comentarios").child(ID);//Sala de chat (nombre)
            storage = FirebaseStorage.getInstance();
            //---
            toolbarLayout.setTitle(title);
            //titulo.setText(title);
            descripcionn.setText(description);
            categoria.setText(categorie);
            Glide.with(Ver_anecdota.this).load(foto).fitCenter()
                    .centerCrop().into(imageView);
            //-------

        adapterComentario = new AdapterComentario(this);
        LinearLayoutManager l = new LinearLayoutManager(this);
        rvComentario.setLayoutManager(l);
        rvComentario.setAdapter(adapterComentario);

        comentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.push().setValue(new ComentarioEnviar(ID,txtMensaje.getText().toString(),nombreComenta,fotoPerfilCadena,"1", ServerValue.TIMESTAMP));
                txtMensaje.setText("");
            }
        });

       /* galeria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("image/jpeg");
                i.putExtra(Intent.EXTRA_LOCAL_ONLY,true);
                startActivityForResult(Intent.createChooser(i,"Selecciona una foto"),PHOTO_SEND);
            }
        });*/

        adapterComentario.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                setScrollbar();
            }
        });

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ComentarioRecibir recibir = dataSnapshot.getValue(ComentarioRecibir.class);
                adapterComentario.addComentario(recibir);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void setScrollbar()
    {
        rvComentario.scrollToPosition(adapterComentario.getItemCount()-1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //----
         if(requestCode == PHOTO_PERFIL && resultCode == RESULT_OK){
            Uri u = data.getData();
            storageReference = storage.getReference("foto_comentario");//imagenes_chat
            final StorageReference fotoReferencia = storageReference.child(u.getLastPathSegment());
            fotoReferencia.putFile(u).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri u = taskSnapshot.getDownloadUrl();
                    //fotoPerfilCadena = u.toString();
                    ComentarioEnviar m = new ComentarioEnviar("",fotoPerfilCadena,nombreComenta,fotoPerfilCadena,"2",ServerValue.TIMESTAMP);
                    databaseReference.push().setValue(m);
                    Glide.with(Ver_anecdota.this).load(fotoPerfilCadena).into(fotoPerfil);
                }
            });
        }
    }
        //-----

}
