package com.example.seba_.mebav5.Objetos;

/**
 * Created by seba_ on 04-11-2017.
 */

public class ComentarioRecibir extends Comentario{
    private long hora;

    public ComentarioRecibir()
    {

    }

    public ComentarioRecibir(long hora) {
        this.hora = hora;
    }

    public ComentarioRecibir(String fidAnec, String comentario, String urlFoto, String nombre, String fotoPerfil, String type_mensaje, long hora) {
        super(fidAnec, comentario, urlFoto, nombre, fotoPerfil, type_mensaje);
        this.hora = hora;
    }

    public long getHora() {
        return hora;
    }

    public void setHora(long hora) {
        this.hora = hora;
    }
}
