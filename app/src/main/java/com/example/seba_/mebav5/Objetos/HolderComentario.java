package com.example.seba_.mebav5.Objetos;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.seba_.mebav5.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by seba_ on 04-11-2017.
 */

public class HolderComentario extends RecyclerView.ViewHolder {
    private TextView nombre;
    private TextView comentario;
    private TextView hora;
    private CircleImageView fotoComentarioPerfil;
    private ImageView fotoComentario;

    public HolderComentario(View itemView) {
        super(itemView);
        nombre = (TextView) itemView.findViewById(R.id.nombreMensaje);
        comentario = (TextView) itemView.findViewById(R.id.mensajeMensaje);
        hora = (TextView) itemView.findViewById(R.id.horaMensaje);
        fotoComentarioPerfil = (CircleImageView) itemView.findViewById(R.id.fotoPerfilComentario);
        fotoComentario = (ImageView) itemView.findViewById(R.id.mensajeFoto);
    }

    public TextView getNombre() {
        return nombre;
    }

    public void setNombre(TextView nombre) {
        this.nombre = nombre;
    }

    public TextView getComentario() {
        return comentario;
    }

    public void setComentario(TextView comentario) {
        this.comentario = comentario;
    }

    public TextView getHora() {
        return hora;
    }

    public void setHora(TextView hora) {
        this.hora = hora;
    }

    public CircleImageView getFotoComentarioPerfil() {
        return fotoComentarioPerfil;
    }

    public void setFotoComentarioPerfil(CircleImageView fotoComentarioPerfil) {
        this.fotoComentarioPerfil = fotoComentarioPerfil;
    }

    public ImageView getFotoComentario() {
        return fotoComentario;
    }

    public void setFotoComentario(ImageView fotoComentario) {
        this.fotoComentario = fotoComentario;
    }
}
