package com.example.jadecsilveira.financas.activity;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.jadecsilveira.financas.R;

/**
 * Created by jadecsilveira on 18/04/2016.
 */
public class RendimentoCursorAdapter extends CursorAdapter {

    public RendimentoCursorAdapter(Context context, Cursor cursor){
        super(context, cursor);
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.lista_rendimentos, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvDescricao = (TextView) view.findViewById(R.id.tvDescricao);
        TextView tvValor = (TextView) view.findViewById(R.id.tvValor);

        String descricao = cursor.getString(cursor.getColumnIndex("descricao"));
        String valor = cursor.getString(cursor.getColumnIndex("valor"));

        tvDescricao.setText(descricao);
        tvValor.setText(valor);
    }
}
