package com.example.seba_.mebav5.Objetos;

/**
 * Created by seba_ on 04-11-2017.
 */

public class Comentario {

    private String idComent;
    private String fidAnec;
    private String fidUser;
    private String comentario;
    private String urlFoto;
    private String nombre;
    private String fotoPerfil;
    private String type_mensaje;

    public Comentario()
    {

    }



    public Comentario(String fidAnec, String comentario, String nombre, String fotoPerfil, String type_mensaje) {
        this.fidAnec = fidAnec;
        this.comentario = comentario;
        this.nombre = nombre;
        this.fotoPerfil = fotoPerfil;
        this.type_mensaje = type_mensaje;
    }

    public Comentario(String fidAnec, String comentario, String urlFoto, String nombre, String fotoPerfil, String type_mensaje) {
        this.fidAnec = fidAnec;
        this.comentario = comentario;
        this.urlFoto = urlFoto;
        this.nombre = nombre;
        this.fotoPerfil = fotoPerfil;
        this.type_mensaje = type_mensaje;
    }

    public String getIdComent() {
        return idComent;
    }

    public void setIdComent(String idComent) {
        this.idComent = idComent;
    }

    public String getFidAnec() {
        return fidAnec;
    }

    public void setFidAnec(String fidAnec) {
        this.fidAnec = fidAnec;
    }

    public String getFidUser() {
        return fidUser;
    }

    public void setFidUser(String fidUser) {
        this.fidUser = fidUser;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public String getType_mensaje() {
        return type_mensaje;
    }

    public void setType_mensaje(String type_mensaje) {
        this.type_mensaje = type_mensaje;
    }
}
