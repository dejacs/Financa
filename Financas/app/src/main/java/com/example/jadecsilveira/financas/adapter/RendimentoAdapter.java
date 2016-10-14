package com.example.jadecsilveira.financas.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.example.jadecsilveira.financas.R;
import com.example.jadecsilveira.financas.activity.InclusaoRendimentoActivity;
import com.example.jadecsilveira.financas.activity.RendimentoActivity;
import com.example.jadecsilveira.financas.control.ControleLancamento;
import com.example.jadecsilveira.financas.dao.DatabaseHelper;
import com.example.jadecsilveira.financas.vo.AgendamentoVO;

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
            row = inflater.inflate(R.layout.grid_rendimentos, parent, false);
        }
        Button excluir = (Button)row.findViewById(R.id.btn_deletar);
        excluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper db = new DatabaseHelper(context);
                db.deletarAgendamento(agendamentos.get(position));
                agendamentos.remove(position);
                notifyDataSetChanged();
            }
        });
        Button alterar = (Button)row.findViewById(R.id.btn_alterar);
        alterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle params = new Bundle();
                Intent intent = new Intent(context, InclusaoRendimentoActivity.class);
                params.putString("id", agendamentos.get(position).getLancamento().getId().toString());
                intent.putExtras(params);
                context.startActivity(intent);
            }
        });

        return controle.setAdapter(agendamentos, position, convertView, R.layout.grid_rendimentos, inflater);
    }
}
