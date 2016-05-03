package com.example.jadecsilveira.financas.vo;

import java.util.Date;

/**
 * Created by jadecsilveira on 02/05/2016.
 */
public class AgendamentoVO {
    private Long id;
    private LancamentoVO lancamento;
    private Date data;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LancamentoVO getLancamento() {
        return lancamento;
    }

    public void setLancamento(LancamentoVO lancamento) {
        this.lancamento = lancamento;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
}
