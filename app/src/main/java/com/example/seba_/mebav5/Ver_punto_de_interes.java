package com.example.seba_.mebav5;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.seba_.mebav5.Objetos.AnecList;
import com.example.seba_.mebav5.Objetos.Anecdota;
import com.example.seba_.mebav5.Objetos.Punto_de_interes;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class Ver_punto_de_interes extends AppCompatActivity {


    TextView title,descrip;
    Button AddAnecdota;
    DatabaseReference dataAnec;
    DatabaseReference DataPI;
    DatabaseReference dataIDpi;
    ListView listViewAnec;
    List<Anecdota> anecList;
    ImageView imageView ;
    public static final String ANEC_TITLE = "tituloanec";
    public static final String ANEC_ID = "idanec";
    public static final String ANEC_FOTO = "fotoanec";
    public static final String ANEC_CATEGORI = "Categoriaanec";
    public static final String ANEC_DESCRIP = "descripcionanec";
    public static final String CORREO_ELECTRONICO_PI="mail";
    public static final String ANEC_FKUSER_MAIL="fkuser";
    private RatingBar ratingBar;
    private  TextView ratingtct;
    public static float ratingFloat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_punto_de_interes);

//        Log.i("VALORES",getIntent().getStringExtra(MainActivity.CORREO_ELECTRONICO));
        String Titulo = getIntent().getStringExtra("Titulo");
        String Descrip = getIntent().getStringExtra("Descripcion");
        final String ID = getIntent().getStringExtra("IDPI");
        dataAnec = FirebaseDatabase.getInstance().getReference("anecdota").child(ID);
        imageView = (ImageView)findViewById(R.id.fotoPIjpj);
        ratingBar = (RatingBar)findViewById(R.id.ratingid);
        ratingtct = (TextView)findViewById(R.id.ratingtxt);
        FirebaseDatabase data =FirebaseDatabase.getInstance();
        dataIDpi = data.getReference("punto_de_interes");
        dataIDpi.child(ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Punto_de_interes pi = dataSnapshot.getValue(Punto_de_interes.class);
                Log.e("URLfotu",pi.getFotopi().toString());
                String URLfoto = pi.getFotopi();
                ratingFloat = pi.getRating();
                Glide.with(Ver_punto_de_interes.this).load(URLfoto).fitCenter()
                        .centerCrop().into(imageView);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

       //aqui trabajamos con el RAtingbar hay que trabajar en ello

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                DataPI= FirebaseDatabase.getInstance().getReference("punto_de_interes");
                float total = (ratingFloat+v)/2;
                Update(ID,v);

            ratingtct.setText("Popularidad: "+ total);

            }
        });


        title = (TextView)findViewById(R.id.tituloPItxt);
        descrip = (TextView)findViewById(R.id.descripcionPItxt);
        AddAnecdota = (Button)findViewById(R.id.cargarAnec);
        listViewAnec = (ListView)findViewById(R.id.lisrviewAnes);
        anecList = new ArrayList<>();

        /*---
        Glide.with(Ver_punto_de_interes.this).load(URLfoto).fitCenter()
                .centerCrop().into(imageView);
        */
        title.setText(Titulo);
        descrip.setText(Descrip);
        AddAnecdota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Ver_punto_de_interes.this,Agregar_anecdota.class);
                intent.putExtra("id_pi",ID);
                intent.putExtra(CORREO_ELECTRONICO_PI,getIntent().getStringExtra(MainActivity.CORREO_ELECTRONICO));
                startActivity(intent);
            }
        });

        listViewAnec.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Anecdota anecdota = anecList.get(i);
                Intent intent = new Intent(getApplicationContext(),Ver_anecdota.class);
                intent.putExtra(ANEC_ID,anecdota.getIDanec());
                intent.putExtra(ANEC_TITLE,anecdota.getTituloanec());
                intent.putExtra(ANEC_DESCRIP,anecdota.getDescripcionanec());
                intent.putExtra(ANEC_FOTO,anecdota.getFotoanec());
                intent.putExtra(ANEC_CATEGORI,anecdota.getCategoriaanec());
                intent.putExtra(ANEC_FKUSER_MAIL,anecdota.getFkUser());
                //esto es para otra cosa
                intent.putExtra(CORREO_ELECTRONICO_PI,getIntent().getStringExtra(MainActivity.CORREO_ELECTRONICO));
                startActivity(intent);
            }
        });

        final AlertDialog.Builder alerta = new AlertDialog.Builder(Ver_punto_de_interes.this);

       /* listViewAnec.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
               Anecdota anecdota = anecList.get(i);
               final String idanec = anecdota.getIDanec();
                alerta.setTitle("Eliminar");
                alerta.setMessage("¿desea eliminar esta anécdota? ");
                alerta.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                       Delate(idanec);

                    }
                })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .show();
                return false;
            }
        });*/

    }


    public void Update(String IDpunInt, float rating){

        String KeyID = dataIDpi.child("punto_de_interes").push().getKey();
        Punto_de_interes de_interes = new Punto_de_interes(IDpunInt,rating);
        Map<String, Object> piValue =de_interes.toMap();

        Map<String,Object> chieldUpdate = new HashMap<>();
        chieldUpdate.put("/punto_de_interes"+KeyID,piValue);
    }

    public void Delate (String ID) {
        final DatabaseReference dataanes = FirebaseDatabase.getInstance().getReference("anecdota").child(ID);
        final String mail= getIntent().getStringExtra(MainActivity.CORREO_ELECTRONICO);

        dataanes.child(ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Anecdota ane = dataSnapshot.getValue(Anecdota.class);

                String mailBD = ane.getFkUser();
                if(mailBD.equals(mail)){
                    dataanes.removeValue();
                }else {
                    Toast.makeText(Ver_punto_de_interes.this, "No tienes permiso para borrar es anécdota", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        dataAnec.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                anecList.clear();
                for(DataSnapshot aanecSnap : dataSnapshot.getChildren())
                {
                    Anecdota anecdota =aanecSnap.getValue(Anecdota.class);
                    anecList.add(anecdota);
                }

                AnecList adapter = new AnecList(Ver_punto_de_interes.this,anecList);
                listViewAnec.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
