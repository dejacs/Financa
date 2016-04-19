package com.example.jadecsilveira.financas.vo;

/**
 * Created by jadecsilveira on 19/04/2016.
 */
public class DespesaVO {

    private String id;
    private String descricao;
    private String valor;

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
