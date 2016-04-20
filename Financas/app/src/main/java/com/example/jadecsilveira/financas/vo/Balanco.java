package com.example.jadecsilveira.financas.vo;

import java.util.Date;

/**
 * Created by jadecsilveira on 20/04/2016.
 */
public class Balanco {
    private Date data;
    private String valor;

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
}
