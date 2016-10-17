package com.example.jadecsilveira.financas.control;

import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jadecsilveira.financas.R;
import com.example.jadecsilveira.financas.util.MetodosComuns;
import com.example.jadecsilveira.financas.vo.AgendamentoVO;
import com.example.jadecsilveira.financas.vo.LancamentoVO;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Created by jadecsilveira on 02/05/2016.
 */
public class ControleLancamento extends AppCompatActivity {
    // Activity
    private EditText descricao, valor, data;
    LancamentoVO lancamento;
    AgendamentoVO agendamento;

    public void setCampos(AppCompatActivity activity, Integer layout){
        activity.setContentView(layout);
        descricao = (EditText) activity.findViewById(R.id.descricao);
        valor = (EditText) activity.findViewById(R.id.valor);
        data = (EditText) activity.findViewById(R.id.data);
    }
    public AgendamentoVO setObjeto(String tipo, long id){
        lancamento = new LancamentoVO();
        lancamento.setDescricao(descricao.getText().toString());
        lancamento.setValor(Double.valueOf(valor.getText().toString()));
        lancamento.setTipo(tipo);
        lancamento.setId(id);
        agendamento = new AgendamentoVO();
        agendamento.setData(Date.valueOf(MetodosComuns.convertToDateSQL(data.getText().toString())));
        agendamento.setLancamento(lancamento);
        return agendamento;
    }
    public View setAdapter(ArrayList<AgendamentoVO> agendamentos, int position, View convertView, Integer layout, LayoutInflater inflater){
        if (convertView == null)
            convertView = inflater.inflate(layout, null);

        TextView descTextView = (TextView) convertView.findViewById(R.id.tvDescricao);
        TextView valorTextView = (TextView) convertView.findViewById(R.id.tvValor);
        TextView dataTextView = (TextView) convertView.findViewById(R.id.tvData);

        AgendamentoVO agendamento = new AgendamentoVO();
        agendamento = agendamentos.get(position);
        descTextView.setText(agendamento.getLancamento().getDescricao());
        valorTextView.setText("R$ " + MetodosComuns.convertToDouble(agendamento.getLancamento().getValor()));
        dataTextView.setText(MetodosComuns.convertDateToStringView(agendamento.getData()));

        return convertView;
    }

    public Double getTotal(ArrayList<AgendamentoVO> agendamentos){
        Double total = 0.;
        for(AgendamentoVO agendamento : agendamentos){
            total = total + agendamento.getLancamento().getValor();
        }
        return total;
    }
}
