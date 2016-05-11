package com.example.jadecsilveira.financas.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.jadecsilveira.financas.util.Constantes;
import com.example.jadecsilveira.financas.util.MetodosComuns;
import com.example.jadecsilveira.financas.vo.AgendamentoVO;
import com.example.jadecsilveira.financas.vo.LancamentoVO;

import java.util.ArrayList;
import java.util.Date;

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
                "CREATE TABLE agendamento(" +
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                        "id_lancamento LONG," +
                        "data DATE" +
                        ");"
        );
        db.execSQL(
                "CREATE TABLE lancamento(" +
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                        "descricao TEXT," +
                        "valor DOUBLE," +
                        "usuario TEXT," +
                        "tipo TEXT" +
                        ");"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean incluirLancamento(AgendamentoVO agendamento){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Constantes.DESCRICAO, agendamento.getLancamento().getDescricao());
        values.put(Constantes.VALOR, agendamento.getLancamento().getValor());
        values.put(Constantes.USUARIO, Constantes.LOGIN);
        values.put(Constantes.TIPO, agendamento.getLancamento().getTipo());

        long resultado = db.insert(Constantes.TABELA_LANCAMENTO, null, values);
        if(resultado != -1 ){
            agendamento.getLancamento().setId(resultado);
            if(incluirAgendamento(agendamento)){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
    private boolean incluirAgendamento(AgendamentoVO agendamento){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Constantes.DATA, MetodosComuns.convertDateToStringSQL(agendamento.getData()));
        values.put(Constantes.ID_LANCAMENTO, agendamento.getLancamento().getId());

        long resultado = db.insert(Constantes.TABELA_AGENDAMENTO, null, values);
        if(resultado != -1 ){
            return true;
        }else{
            return false;
        }
    }

    public ArrayList<AgendamentoVO> getAgendamentos(String tipo){
        String query = "SELECT L.descricao, L.valor, A.data " +
                "FROM lancamento L " +
                "INNER JOIN agendamento A " +
                "ON L._id = A.id_lancamento " +
                "WHERE L.usuario = ? AND L.tipo = '" + tipo + "' " +
                "ORDER BY A.data";

        ArrayList<AgendamentoVO> agendamentos = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(query, new String[]{Constantes.LOGIN});

        if(MetodosComuns.isNotNull(cursor)){
            while (cursor.moveToNext()){
                LancamentoVO lancamento = new LancamentoVO();
                lancamento.setDescricao(cursor.getString(cursor.getColumnIndex(Constantes.DESCRICAO)));
                lancamento.setValor(cursor.getDouble(cursor.getColumnIndex(Constantes.VALOR)));

                AgendamentoVO agendamento = new AgendamentoVO();
                agendamento.setData(MetodosComuns.convertToDate(cursor.getString(cursor.getColumnIndex(Constantes.DATA))));
                agendamento.setLancamento(lancamento);

                agendamentos.add(agendamento);
            }
        }
        return agendamentos;
    }

    public ArrayList<AgendamentoVO> getSaldos(){
        ArrayList<AgendamentoVO> saldos = new ArrayList<>();

        ArrayList<AgendamentoVO> lancamentos = getTodosAgendamentos();
        Double saldoAtual=0.;
        AgendamentoVO saldo;
        LancamentoVO lancamento;

        for (AgendamentoVO lancamentoAtual : lancamentos){
            saldo = new AgendamentoVO();
            lancamento = new LancamentoVO();
            if(lancamentoAtual.getLancamento().getTipo().equals("rendimento")){
                saldoAtual = saldoAtual + lancamentoAtual.getLancamento().getValor();
                lancamento.setValor(saldoAtual);
            }else if(lancamentoAtual.getLancamento().getTipo().equals("despesa")){
                saldoAtual = saldoAtual - lancamentoAtual.getLancamento().getValor();
                lancamento.setValor(saldoAtual);
            }
            lancamento.setDescricao(lancamentoAtual.getLancamento().getDescricao());
            saldo.setLancamento(lancamento);
            saldo.setData(lancamentoAtual.getData());
            saldos.add(saldo);
        }
        return saldos;
    }

    public ArrayList<AgendamentoVO> getTodosAgendamentos(){
        String query = "SELECT L.descricao, L.valor, L.tipo, A.data " +
                "FROM lancamento L " +
                "INNER JOIN agendamento A " +
                "ON L._id = A.id_lancamento " +
                "WHERE L.usuario = ? " +
                "ORDER BY A.data";

        ArrayList<AgendamentoVO> agendamentos = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(query, new String[]{Constantes.LOGIN});

        if(MetodosComuns.isNotNull(cursor)){
            while (cursor.moveToNext()){
                LancamentoVO lancamento = new LancamentoVO();
                lancamento.setDescricao(cursor.getString(cursor.getColumnIndex(Constantes.DESCRICAO)));
                lancamento.setValor(cursor.getDouble(cursor.getColumnIndex(Constantes.VALOR)));
                lancamento.setTipo(cursor.getString(cursor.getColumnIndex(Constantes.TIPO)));

                AgendamentoVO agendamento = new AgendamentoVO();
                agendamento.setData(MetodosComuns.convertToDate(cursor.getString(cursor.getColumnIndex(Constantes.DATA))));
                agendamento.setLancamento(lancamento);

                agendamentos.add(agendamento);
            }
        }
        return agendamentos;
    }
}
