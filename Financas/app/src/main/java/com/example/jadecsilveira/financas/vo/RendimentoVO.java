package com.example.jadecsilveira.financas.vo;

import java.util.Date;

/**
 * Created by jadecsilveira on 15/04/2016.
 */
public class RendimentoVO {

    private String id;
    private String descricao;
    private String valor;
    private Date dataPagamento;

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
