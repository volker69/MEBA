package com.example.seba_.mebav5.Objetos;

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

    public Punto_de_interes(){

    }

    public Punto_de_interes(String IDpunInt, Double lat, Double lng, String titulopi, String descripcionpi, String fotopi) {
        this.IDpunInt = IDpunInt;
        this.Lat = lat;
        this.Lng = lng;
        this.Titulopi = titulopi;
        this.Descripcionpi = descripcionpi;
        this.Fotopi = fotopi;
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
}
