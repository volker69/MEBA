package com.example.seba_.mebav5.Objetos;

import java.util.Map;

/**
 * Created by seba_ on 04-11-2017.
 */

public class ComentarioEnviar extends Comentario {
        private Map hora;

        public ComentarioEnviar()
        {

        }

    public ComentarioEnviar(Map hora) {
        this.hora = hora;
    }

    public ComentarioEnviar(String fidAnec, String comentario, String nombre, String fotoPerfil, String type_mensaje, Map hora) {
        super(fidAnec, comentario, nombre, fotoPerfil, type_mensaje);
        this.hora = hora;
    }

    public ComentarioEnviar(String fidAnec, String comentario, String urlFoto, String nombre, String fotoPerfil, String type_mensaje, Map hora) {
        super(fidAnec, comentario, urlFoto, nombre, fotoPerfil, type_mensaje);
        this.hora = hora;

    }

    public Map getHora() {
        return hora;
    }

    public void setHora(Map hora) {
        this.hora = hora;
    }
}
