package com.example.jadecsilveira.financas.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.example.jadecsilveira.financas.R;
import com.example.jadecsilveira.financas.activity.DespesaActivity;
import com.example.jadecsilveira.financas.control.ControleLancamento;
import com.example.jadecsilveira.financas.dao.DatabaseHelper;
import com.example.jadecsilveira.financas.vo.AgendamentoVO;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by jadecsilveira on 19/04/2016.
 */
public class DespesaAdapter extends BaseAdapter {

    Context context;
    ArrayList<AgendamentoVO> agendamentos;
    private static LayoutInflater inflater = null;

    public DespesaAdapter(Context context, ArrayList<AgendamentoVO> agendamentos){
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
        return agendamentos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ControleLancamento controle = new ControleLancamento();

        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            row = inflater.inflate(R.layout.grid_despesas, parent, false);
        }
        Button button = (Button)row.findViewById(R.id.btn_deletar);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper db = new DatabaseHelper(context);
                db.deletarAgendamento(agendamentos.get(position));
                agendamentos.remove(position);
                notifyDataSetChanged();
            }
        });

        return controle.setAdapter(agendamentos, position, convertView, R.layout.grid_despesas, inflater);
    }
}
