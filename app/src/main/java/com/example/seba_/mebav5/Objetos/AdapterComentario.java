package com.example.seba_.mebav5.Objetos;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.seba_.mebav5.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by seba_ on 04-11-2017.
 */

public class AdapterComentario extends RecyclerView.Adapter<HolderComentario> {

    private List<ComentarioRecibir> listComentario = new ArrayList<>();
    private Context c;

    public AdapterComentario(Context c) {
        this.c = c;
    }

    public void addComentario(ComentarioRecibir m){
        listComentario.add(m);
        notifyItemInserted(listComentario.size());
    }

    @Override
    public HolderComentario onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.comentario_still,parent,false);
        return new HolderComentario(v);
    }

    @Override
    public void onBindViewHolder(HolderComentario holder, int position) {
        holder.getNombre().setText(listComentario.get(position).getNombre());
        holder.getComentario().setText(listComentario.get(position).getComentario());
        //--
        if(listComentario.get(position).getType_mensaje().equals("2")){
            holder.getFotoComentario().setVisibility(View.VISIBLE);
            holder.getComentario().setVisibility(View.VISIBLE);
            Glide.with(c).load(listComentario.get(position).getUrlFoto()).into(holder.getFotoComentario());
        }else if(listComentario.get(position).getType_mensaje().equals("1")){
            holder.getFotoComentario().setVisibility(View.GONE);
            holder.getComentario().setVisibility(View.VISIBLE);
        }
        if(listComentario.get(position).getFotoPerfil().isEmpty()){
            holder.getFotoComentarioPerfil().setImageResource(R.drawable.perfil);
        }else{
            Glide.with(c).load(listComentario.get(position).getFotoPerfil()).into(holder.getFotoComentarioPerfil());
        }
        Long codigoHora = listComentario.get(position).getHora();
        Date d = new Date(codigoHora);
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");//a pm o am
        holder.getHora().setText(sdf.format(d));
        //---
    }

    @Override
    public int getItemCount() {
    return listComentario.size();
    }
}
