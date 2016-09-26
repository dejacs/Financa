package com.example.jadecsilveira.financas.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.example.jadecsilveira.financas.R;
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
        Button button = (Button)row.findViewById(R.id.btn_deletar);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper db = new DatabaseHelper(context);
                db.deletarAgendamento(agendamentos.get(position));
                context.startActivity(new Intent(context, RendimentoActivity.class));
            }
        });

        return controle.setAdapter(agendamentos, position, convertView, R.layout.grid_rendimentos, inflater);
    }
}
