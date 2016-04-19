package com.example.jadecsilveira.financas.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import com.example.jadecsilveira.financas.util.Constantes;
import com.example.jadecsilveira.financas.util.MetodosComuns;
import com.example.jadecsilveira.financas.vo.DespesaVO;
import com.example.jadecsilveira.financas.vo.RendimentoVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jadecsilveira on 15/04/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static int VERSAO = 1;

    public DatabaseHelper(Context context){
        super(context, Constantes.BANCO_DE_DADOS, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                Constantes.CREATE_TABLE + Constantes.TABELA_DESPESA + " (" +
                        Constantes.ID + Constantes.INTEGER + Constantes.PRIMARY_KEY + Constantes.VIRGULA +
                        Constantes.DESCRICAO + Constantes.TEXT + Constantes.VIRGULA +
                        Constantes.VALOR + Constantes.DOUBLE + Constantes.VIRGULA +
                        Constantes.DATA_INICIO + Constantes.DATE + Constantes.VIRGULA +
                        Constantes.DATA_FIM + Constantes.DATE + Constantes.VIRGULA +
                        Constantes.USUARIO_INCLUSAO + Constantes.TEXT +
                        ");"
        );
        db.execSQL(
                Constantes.CREATE_TABLE + Constantes.TABELA_RENDIMENTO + " (" +
                        Constantes.ID + Constantes.INTEGER + Constantes.PRIMARY_KEY + Constantes.VIRGULA +
                        Constantes.DESCRICAO + Constantes.TEXT + Constantes.VIRGULA +
                        Constantes.VALOR + Constantes.DOUBLE + Constantes.VIRGULA +
                        Constantes.DATA_PAGAMENTO + Constantes.DATE + Constantes.VIRGULA +
                        Constantes.DATA_INCLUSAO + Constantes.DATE +
                        Constantes.USUARIO_INCLUSAO + Constantes.TEXT +
                        ");"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public ArrayList<DespesaVO> getDespesas(){
        String query = "SELECT * FROM ".concat(Constantes.TABELA_DESPESA);

        ArrayList<DespesaVO> despesas = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);

        if(MetodosComuns.isNotNull(cursor)){
            while (cursor.moveToNext()){
                DespesaVO despesa = new DespesaVO();
                despesa.setDescricao(cursor.getString(cursor.getColumnIndex(Constantes.DESCRICAO)));
                despesa.setValor(cursor.getString(cursor.getColumnIndex(Constantes.VALOR)));
                despesas.add(despesa);
            }
        }
        return despesas;
    }

    public ArrayList<RendimentoVO> getRendimentos(){
        String query = "SELECT * FROM ".concat(Constantes.TABELA_RENDIMENTO);

        ArrayList<RendimentoVO> rendimentos = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);

        if(MetodosComuns.isNotNull(cursor)){
            while (cursor.moveToNext()){
                RendimentoVO rendimento = new RendimentoVO();
                rendimento.setDescricao(cursor.getString(cursor.getColumnIndex(Constantes.DESCRICAO)));
                rendimento.setValor(cursor.getString(cursor.getColumnIndex(Constantes.VALOR)));
                rendimentos.add(rendimento);
            }
        }
        return rendimentos;
    }
}
