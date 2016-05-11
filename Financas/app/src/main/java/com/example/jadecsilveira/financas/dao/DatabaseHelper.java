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
                "WHERE L.usuario = ? AND L.tipo = '" + tipo + "'";

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

    /* public ArrayList<BalancoVO> getBalancosTeste(){
        String dataPagamentoAnterior = "0000-00-00";
        ArrayList<RendimentoVO> rendimentos = getRendimentos();
        ArrayList<DespesaVO> despesaVOs = getDespesas();
        ArrayList<BalancoVO> balancos = new ArrayList<>();
        Double balanco = 0.;

        if(!rendimentos.isEmpty()) {
            for (RendimentoVO rendimento : rendimentos) {
                ArrayList<DespesaVO> despesas = getDespesasEntreDatas(dataPagamentoAnterior, new SimpleDateFormat("yyyy-MM-dd").format(rendimento.getDataPagamento()));
                Double somaDespesas = 0.;
                if(!despesas.isEmpty()) {
                    for (DespesaVO despesa : despesas) {
                        somaDespesas = somaDespesas + new Double(despesa.getValor());
                    }
                }
                balanco = (new Double(rendimento.getValor()) - somaDespesas) + balanco;
                BalancoVO balancoVO = new BalancoVO();
                balancoVO.setValor(balanco.toString());
                balancoVO.setData(rendimento.getDataPagamento());
                balancos.add(balancoVO);

                dataPagamentoAnterior = new SimpleDateFormat("yyyy-MM-dd").format(rendimento.getDataPagamento());
            }
        }
        return balancos;
    }

    public ArrayList<BalancoVO> getBalancos(){
        ArrayList<RendimentoVO> rendimentos = getRendimentos();
        ArrayList<DespesaVO> despesas = getDespesas();
        ArrayList<BalancoVO> balancos = new ArrayList<>();
        BalancoVO balanco = new BalancoVO();

        if((despesas.isEmpty() && !rendimentos.isEmpty()) || (!rendimentos.isEmpty() && rendimentos.get(0).getDataPagamento().before(despesas.get(0).getDataDespesa()))){
            balanco.setValor(rendimentos.get(0).getValor());
            balanco.setData(rendimentos.get(0).getDataPagamento());
            balancos.add(balanco);
        }if(despesas.get(0)==null && rendimentos.get(0)==null){
            return balancos;
        }
        ArrayList<Date> datasPagamentoPosterior = new ArrayList<>();
        for (RendimentoVO rendimento : rendimentos) {
            datasPagamentoPosterior.add(rendimento.getDataPagamento());
        }
        datasPagamentoPosterior.remove(0);
        Double saldo = 0.;

        for (DespesaVO despesa : despesas) {
            if(rendimentos.isEmpty()){
                saldo = saldo-new Double(despesa.getValor());
                balanco.setValor(saldo.toString());
                balanco.setData(despesa.getDataDespesa());
                balancos.add(balanco);
            }else{
                outerloop:
                    for (RendimentoVO rendimento : rendimentos) {
                        for (Date dataPagamentoPosterior : datasPagamentoPosterior) {
                            if((despesa.getDataDespesa().equals(rendimento.getDataPagamento()) || despesa.getDataDespesa().after(rendimento.getDataPagamento())) && despesa.getDataDespesa().before(dataPagamentoPosterior)){
                                saldo = saldo + (new Double(rendimento.getValor())-new Double(despesa.getValor()));
                                balanco.setValor(saldo.toString());
                                balanco.setData(despesa.getDataDespesa());
                                balancos.add(balanco);
                                break outerloop;
                            }else{
                                saldo = saldo + (new Double(rendimento.getValor())-new Double(despesa.getValor()));
                                balanco.setValor(saldo.toString());
                                balanco.setData(despesa.getDataDespesa());
                                balancos.add(balanco);
                                break outerloop;
                            }
                        }
                    }
            }
        }
        return balancos;
    }*/

    /** public ArrayList<DespesaVO> getDespesasEntreDatas(String dataInicio, String dataFim){
        String query = "SELECT * FROM ".concat(Constantes.TABELA_DESPESA).
                concat(Constantes.WHERE).concat(Constantes.USUARIO_INCLUSAO).concat(Constantes.IGUAL).concat(Constantes.INTERROGACAO).
                concat(Constantes.AND).concat(Constantes.DATA_DESPESA).concat(Constantes.MAIOR).concat("date(").concat(Constantes.INTERROGACAO).concat(")").
                concat(Constantes.AND).concat(Constantes.DATA_DESPESA).concat(Constantes.MENOR_IGUAL).concat("date(").concat(Constantes.INTERROGACAO).concat(")");

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
    public ArrayList<RendimentoVO> getRendimentosEntreDatas(String dataInicio, String dataFim){
        String query = "SELECT * FROM ".concat(Constantes.TABELA_RENDIMENTO).
                concat(Constantes.WHERE).concat(Constantes.USUARIO_INCLUSAO).concat(Constantes.IGUAL).concat(Constantes.INTERROGACAO).
                concat(Constantes.AND).concat(Constantes.DATA_PAGAMENTO).concat(Constantes.MAIOR).concat(Constantes.INTERROGACAO).
                concat(Constantes.AND).concat(Constantes.DATA_PAGAMENTO).concat(Constantes.MENOR_IGUAL).concat(Constantes.INTERROGACAO);

        ArrayList<RendimentoVO> rendimentoVOs = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(query, new String[]{Constantes.LOGIN, dataInicio, dataFim});

        if(MetodosComuns.isNotNull(cursor)){
            while (cursor.moveToNext()){
                RendimentoVO rendimentoVO = new RendimentoVO();
                rendimentoVO .setDescricao(cursor.getString(cursor.getColumnIndex(Constantes.DESCRICAO)));
                rendimentoVO .setValor(cursor.getString(cursor.getColumnIndex(Constantes.VALOR)));
                rendimentoVO .setDataPagamento(MetodosComuns.convertToDate(cursor.getString(cursor.getColumnIndex(Constantes.DATA_PAGAMENTO))));
                rendimentoVOs.add(rendimentoVO );
            }
        }
        return rendimentoVOs;
    } */
}
