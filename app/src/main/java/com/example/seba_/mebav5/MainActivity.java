package com.example.seba_.mebav5;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.example.seba_.mebav5.Objetos.Punto_de_interes;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,GoogleMap.OnInfoWindowClickListener {
    private GoogleMap mMap;
    private DatabaseReference dataPI;
    public static final String PI_ID = "idpunint";
    public static final String CORREO_ELECTRONICO="mail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final FloatingActionButton p = (FloatingActionButton) findViewById(R.id.Perfil);
        p.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                try{
                    String correo = getIntent().getStringExtra(Login.USER_MAIL);

                    Intent intent = new Intent(MainActivity.this,Perfil.class);
                    intent.putExtra(CORREO_ELECTRONICO,correo);
                    startActivity(intent);
                }catch(Exception e)
                {
                    Log.e("ERROR del INTENT",e.toString());
                }
            }
        });



       /* final FloatingActionButton p2 = (FloatingActionButton) findViewById(R.id.Lugares);
        p2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,FullscreenActivity.class);
                startActivity(intent);
            }
        });*/

        dataPI = FirebaseDatabase.getInstance().getReference("punto_de_interes");
        checkLocationPermission();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        try {
            mMap = googleMap;
            agregarMarcador();



        //m//---------permiso de localizacion--------------------------------------------------
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            return;
        }
        // Add a marker in Sydney and move the camera

        //mMap.moveCamera(CameraUpdateFactory.newLatLng(inacap));
        mMap.setOnInfoWindowClickListener(this);
        AgrePI();

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);// manipulacion de localizacion
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1, 1, this);


        //----Agregar Marcador----------------------------------------------------------------------------------
        Toast.makeText(this, "Mantén presionado en un lugar del mapa para agregar marcador", Toast.LENGTH_LONG).show();
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener(){
            AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);//dialogo de alerta
            @Override
            public void onMapLongClick(final LatLng latLng) {
                //muestra coordenadas de posicion tocada dentro del mapa
                //Toast.makeText(getApplicationContext(), latLng.toString(), Toast.LENGTH_LONG).show();
                alerta.setTitle("Nuevo marcador");
                alerta.setMessage("¿Crear nuevo marcador aquí? ");
                alerta.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {



                        Punto_de_interes pi = new Punto_de_interes();
                        Intent i = new Intent(MainActivity.this,Agregar_PI.class);
                        Log.e("coordenadas: ",latLng.toString());
                        String a = latLng.toString();
                        String sCadena = a.substring(a.indexOf("(") + 1, a.indexOf(")"));
                        String[] array = sCadena.split(",");
                        Log.e("valor de Latitud",array[0]);
                        Log.e("Valor Longitud  ",array[1]);
                        String la = array[0];
                        String ln = array[1];

                        i.putExtra("latitud",la);
                        i.putExtra("longitud",ln);
                        startActivity(i);

                    }
                })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .show();


            }
        });

    }catch (Exception e){
        Toast.makeText(this, "Cargando..", Toast.LENGTH_SHORT).show();
    }
    }


    private void agregarMarcador(){
     dataPI.addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(DataSnapshot dataSnapshot) {
             for (DataSnapshot piSnap : dataSnapshot.getChildren())
             {
                 Punto_de_interes pi = piSnap.getValue(Punto_de_interes.class);
                 double latitud = pi.getLat();
                 double longitud = pi.getLng();
                 String Titulo = pi.getTitulopi();
                 String Descripcion = pi.getDescripcionpi();
                 String id = pi.getIDpunInt();
                 String ulrphoto = pi.getFotopi();
                 LatLng marquer = new LatLng(latitud, longitud);
                 MarkerOptions markerOptions = new  MarkerOptions();
                 markerOptions.title(Titulo);
                 markerOptions.position(marquer);
                 mMap.addMarker(new MarkerOptions().position(marquer).title(Titulo)
                         .snippet(Descripcion).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))).setTag(id);

             }

         }

         @Override
         public void onCancelled(DatabaseError databaseError) {

         }
     });
    }

    //---------------------TIEMPO:DE_ESPERA_DE_ACTIVAR_EL_GPS-----------------------------------------------
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        try {
            switch (requestCode) {
                case MY_PERMISSIONS_REQUEST_LOCATION: {
                    // Si la consulta es cancelada los arreglos estaran vacios
                    if (grantResults.length > 0
                            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                        // permiso concedido
                        if (ContextCompat.checkSelfPermission(this,
                                android.Manifest.permission.ACCESS_FINE_LOCATION)
                                == PackageManager.PERMISSION_GRANTED) {
                            mMap.setMyLocationEnabled(true);
                        }
                    } else {
                        // Permiso denegado
                        Toast.makeText(this, "Permiso denegado", Toast.LENGTH_LONG).show();
                    }
                    return;
                }
                // agregar mas case para mas permisos(wifi,contactos,etc)

            }
        }catch (Exception e){
            e.printStackTrace();

        }
    }
    //---------

    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //preguntar si el usuario necesita explicacion
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Mostrar explicacion del permiso
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                // pedir el permiso
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
        //abrir en la posicion del marcador con zoom
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17.0f));
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Intent i = new Intent(this,Ver_punto_de_interes.class);
        String titulo,descripcion,idpi;
        titulo=marker.getTitle().toString();
        descripcion=marker.getSnippet().toString();
        idpi = marker.getTag().toString();
        String fotourl=marker.getId().toString();

        i.putExtra("Titulo", titulo);
        i.putExtra("Descripcion", descripcion);
        i.putExtra("IDPI",idpi);
        i.putExtra("URL",fotourl);
        String correo = getIntent().getStringExtra(Login.USER_MAIL);
        i.putExtra(CORREO_ELECTRONICO,correo);
        startActivity(i);
    }

    public void AgrePI()
    {
        Toast.makeText(this, "Mantén presionado en un lugar del mapa para agregar un nuevo lugar", Toast.LENGTH_LONG).show();
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener(){
            AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);//dialogo de alerta
            @Override
            public void onMapLongClick(final LatLng latLng) {
                //muestra coordenadas de posicion tocada dentro del mapa
                alerta.setTitle("Nuevo lugar");
                alerta.setMessage("¿Crear nuevo lugar aquí? ");
                alerta.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {


                        Intent i = new Intent(MainActivity.this,Agregar_PI.class);
                        startActivity(i);

                    }
                })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .show();


            }
        });
    }

}
