package com.example.jadecsilveira.financas.activity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

/**
 * Created by jadecsilveira on 15/04/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String BANCO_DE_DADOS = "Financas";
    private static int VERSAO = 1;

    public DatabaseHelper(Context context){
        super(context, BANCO_DE_DADOS, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE despesa (" +
                        "_id INTEGER PRIMARY KEY," +
                        "descricao TEXT," +
                        "valor DOUBLE," +
                        "dataInicio DATE," +
                        "dataFim DATE," +
                        "dataInclusao" +
                        ");"
        );
        db.execSQL(
                "CREATE TABLE rendimento (" +
                        "_id INTEGER PRIMARY KEY," +
                        "descricao TEXT," +
                        "valor DOUBLE," +
                        "dataPagamento DATE," +
                        "dataInclusao" +
                        ");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
