package com.example.jadecsilveira.financas.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import com.example.jadecsilveira.financas.util.Constantes;
import com.example.jadecsilveira.financas.util.MetodosComuns;
import com.example.jadecsilveira.financas.vo.Balanco;
import com.example.jadecsilveira.financas.vo.DespesaVO;
import com.example.jadecsilveira.financas.vo.RendimentoVO;

import java.util.ArrayList;
import java.util.Date;
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
        String query = "SELECT * FROM ".concat(Constantes.TABELA_DESPESA).
                concat(Constantes.WHERE).concat(Constantes.USUARIO_INCLUSAO).concat(Constantes.IGUAL).concat(Constantes.INTERROGACAO);

        ArrayList<DespesaVO> despesas = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(query, new String[]{Constantes.LOGIN});

        if(MetodosComuns.isNotNull(cursor)){
            while (cursor.moveToNext()){
                DespesaVO despesa = new DespesaVO();
                despesa.setDescricao(cursor.getString(cursor.getColumnIndex(Constantes.DESCRICAO)));
                despesa.setValor(cursor.getString(cursor.getColumnIndex(Constantes.VALOR)));
                despesa.setDataDespesa(MetodosComuns.convertToDate(cursor.getString(cursor.getColumnIndex(Constantes.DATA_DESPESA))));
                despesas.add(despesa);
            }
        }
        return despesas;
    }

    public ArrayList<RendimentoVO> getRendimentos(){
        String query = "SELECT * FROM ".concat(Constantes.TABELA_RENDIMENTO).
                concat(Constantes.WHERE).concat(Constantes.USUARIO_INCLUSAO).concat(Constantes.IGUAL).concat(Constantes.INTERROGACAO);

        ArrayList<RendimentoVO> rendimentos = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(Constantes.LOGIN)});

        if(MetodosComuns.isNotNull(cursor)){
            while (cursor.moveToNext()){
                RendimentoVO rendimento = new RendimentoVO();
                rendimento.setDescricao(cursor.getString(cursor.getColumnIndex(Constantes.DESCRICAO)));
                rendimento.setValor(cursor.getString(cursor.getColumnIndex(Constantes.VALOR)));
                rendimento.setDataPagamento(MetodosComuns.convertToDate(cursor.getString(cursor.getColumnIndex(Constantes.DATA_PAGAMENTO))));
                rendimentos.add(rendimento);
            }
        }
        return rendimentos;
    }

    public ArrayList<Balanco> getBalancos(){
        String dataPagamentoAnterior = "0000-00-00";
        ArrayList<RendimentoVO> rendimentos = getRendimentos();
        ArrayList<Balanco> balancos = new ArrayList<>();

        if(!rendimentos.isEmpty()) {
            for (RendimentoVO rendimento : rendimentos) {
                ArrayList<DespesaVO> despesas = getDespesasEntreDatas(dataPagamentoAnterior, MetodosComuns.convertToDateSQL(rendimento.getDataPagamento().toString()));
                Double somaDespesas = 0., balanco = 0.;

                if(!despesas.isEmpty()) {
                    for (DespesaVO despesa : despesas) {
                        somaDespesas = somaDespesas + new Double(despesa.getValor());
                    }
                }
                balanco = (new Double(rendimento.getValor()) - somaDespesas) + balanco;
                Balanco balancoVO = new Balanco();
                balancoVO.setValor(balanco.toString());
                balancoVO.setData(rendimento.getDataPagamento());
                balancos.add(balancoVO);
            }
        }
        return balancos;
    }

    public ArrayList<DespesaVO> getDespesasEntreDatas(String dataInicio, String dataFim){
        String query = "SELECT * FROM ".concat(Constantes.TABELA_DESPESA).
                concat(Constantes.WHERE).concat(Constantes.USUARIO_INCLUSAO).concat(Constantes.IGUAL).concat(Constantes.INTERROGACAO).
                concat(Constantes.AND).concat(Constantes.DATA_DESPESA).concat(Constantes.MAIOR_IGUAL).concat(Constantes.INTERROGACAO).
                concat(Constantes.AND).concat(Constantes.DATA_DESPESA).concat(Constantes.MENOR).concat(Constantes.INTERROGACAO);

        ArrayList<DespesaVO> despesas = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(query, new String[]{Constantes.LOGIN, dataInicio, dataFim});

        if(MetodosComuns.isNotNull(cursor)){
            while (cursor.moveToNext()){
                DespesaVO despesa = new DespesaVO();
                despesa.setDescricao(cursor.getString(cursor.getColumnIndex(Constantes.DESCRICAO)));
                despesa.setValor(cursor.getString(cursor.getColumnIndex(Constantes.VALOR)));
                despesa.setDataDespesa(MetodosComuns.convertToDate(cursor.getString(cursor.getColumnIndex(Constantes.DATA_DESPESA))));
                despesas.add(despesa);
            }
        }
        return despesas;
    }
}
