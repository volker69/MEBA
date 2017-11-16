package com.example.seba_.mebav5.Objetos;

/**
 * Created by seba_ on 16-10-2017.
 */

public class Anecdota {
    private String IDanec;
    private String Tituloanec;
    private String Descripcionanec;
    private String Fotoanec;
    private Double Ratinganec;
    private String Categoriaanec;
    private String fkUser;

    public Anecdota (){

    }

    public Anecdota(String IDanec, String tituloanec, String descripcionanec, String fotoanec, Double ratinganec, String categoriaanec,String fkUser){
        this.IDanec = IDanec;
        this.Tituloanec = tituloanec;
        this.Descripcionanec = descripcionanec;
        this.Fotoanec = fotoanec;
        this.Ratinganec = ratinganec;
        this.Categoriaanec = categoriaanec;
        this.fkUser=fkUser;
    }

    public String getIDanec() {
        return IDanec;
    }

    public void setIDanec(String IDanec) {
        this.IDanec = IDanec;
    }

    public String getTituloanec() {
        return Tituloanec;
    }

    public void setTituloanec(String tituloanec) {
        this.Tituloanec = tituloanec;
    }

    public String getDescripcionanec() {
        return Descripcionanec;
    }

    public void setDescripcionanec(String descripcionanec) {
        this.Descripcionanec = descripcionanec;
    }

    public String getFotoanec() {
        return Fotoanec;
    }

    public void setFotoanec(String fotoanec) {
        this.Fotoanec = fotoanec;
    }

    public Double getRatinganec() {
        return Ratinganec;
    }

    public void setRatinganec(Double ratinganec) {
        this.Ratinganec = ratinganec;
    }

    public String getCategoriaanec() {
        return Categoriaanec;
    }

    public void setCategoriaanec(String categoriaanec) {
        this.Categoriaanec = categoriaanec;
    }

    public String getFkUser() {
        return fkUser;
    }

    public void setFkUser(String fkUser) {
        this.fkUser = fkUser;
    }
}
