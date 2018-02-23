package com.example.seba_.mebav5.Objetos;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by seba_ on 16-10-2017.
 */

public class Punto_de_interes {
    private String IDpunInt;
    private Double Lat;
    private Double Lng;
    private String Titulopi;
    private String Descripcionpi;
    private String Fotopi;
    private float Rating;

    public Punto_de_interes(){

    }


    public Punto_de_interes(String IDpunInt, Double lat, Double lng, String titulopi, String descripcionpi, String fotopi,float rating) {
        this.IDpunInt = IDpunInt;
        this.Lat = lat;
        this.Lng = lng;
        this.Titulopi = titulopi;
        this.Descripcionpi = descripcionpi;
        this.Fotopi = fotopi;
        this.Rating = rating;
    }

   public Punto_de_interes(String iDpunInt,float Rrating){
        this.IDpunInt = iDpunInt;
        this.Rating=Rrating;
    }



    public float getRating() {
        return Rating;
    }

    public void setRating(float rating) {
        Rating = rating;
    }

    public String getIDpunInt() {
        return IDpunInt;
    }

    public void setIDpunInt(String IDpunInt) {
        this.IDpunInt = IDpunInt;
    }

    public Double getLat() {
        return Lat;
    }

    public void setLat(Double lat) {
        this.Lat = lat;
    }

    public Double getLng() {
        return Lng;
    }

    public void setLng(Double lng) {
        this.Lng = lng;
    }

    public String getTitulopi() {
        return Titulopi;
    }

    public void setTitulopi(String titulopi) {
        this.Titulopi = titulopi;
    }

    public String getDescripcionpi() {
        return Descripcionpi;
    }

    public void setDescripcionpi(String descripcionpi) {
        this.Descripcionpi = descripcionpi;
    }

    public String getFotopi() {
        return Fotopi;
    }

    public void setFotopi(String fotopi) {
        this.Fotopi = fotopi;
    }

    /**
     *private Double Lat;
     private Double Lng;
     private String Titulopi;
     private String Descripcionpi;
     private String Fotopi;
     private float Rating;
     */

    public Map<String, Object> toMap(){
        HashMap<String,Object> result = new HashMap<>();
        result.put("IDpunInt",IDpunInt);
        result.put("Lat",Lat);
        result.put("Lng",Lng);
        result.put("Titulopi",Titulopi);
        result.put("Descripcionpi",Descripcionpi);
        result.put("Fotopi",Fotopi);
        result.put("Rating",Rating);
        return result;
    }

}
