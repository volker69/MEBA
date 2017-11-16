package com.example.seba_.mebav5.Objetos;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.seba_.mebav5.R;

import java.util.List;

/**
 * Created by seba_ on 25-10-2017.
 */

public class AnecList extends ArrayAdapter<Anecdota> {
    private Activity context;
    private List<Anecdota> anecLists;

    public AnecList(Activity context, List<Anecdota> anecLists){
        super(context, R.layout.list_anec,anecLists);
        this.context=context;
        this.anecLists=anecLists;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater =context.getLayoutInflater();

        View ListViewItem = inflater.inflate(R.layout.list_anec,null,true);
        TextView TextLtitulo = (TextView)ListViewItem.findViewById(R.id.titulolist);
        TextView TextLdescrip = (TextView)ListViewItem.findViewById(R.id.descrip_list);

        Anecdota anecdota = anecLists.get(position);

        TextLtitulo.setText(anecdota.getTituloanec());
        TextLdescrip.setText(anecdota.getDescripcionanec());
        return ListViewItem;
    }
}
