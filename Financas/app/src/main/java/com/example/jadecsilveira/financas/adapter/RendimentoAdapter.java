package com.example.jadecsilveira.financas.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jadecsilveira.financas.R;
import com.example.jadecsilveira.financas.control.ControleLancamento;
import com.example.jadecsilveira.financas.util.MetodosComuns;
import com.example.jadecsilveira.financas.vo.AgendamentoVO;
import com.example.jadecsilveira.financas.vo.RendimentoVO;

import java.util.ArrayList;

/**
 * Created by jadecsilveira on 18/04/2016.
 */
public class RendimentoAdapter extends BaseAdapter {


    Context context;
    ArrayList<AgendamentoVO> agendamentos;
    private static LayoutInflater inflater = null;

    public RendimentoAdapter(Context context, ArrayList<AgendamentoVO> agendamentos){
        this.context = context;
        this.agendamentos = agendamentos;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return agendamentos.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ControleLancamento controle = new ControleLancamento();
        return controle.setAdapter(agendamentos, position, convertView, R.layout.grid_rendimentos, inflater);
    }
}
