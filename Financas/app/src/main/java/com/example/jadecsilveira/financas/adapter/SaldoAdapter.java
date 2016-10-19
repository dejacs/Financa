package com.example.jadecsilveira.financas.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.jadecsilveira.financas.R;
import com.example.jadecsilveira.financas.control.ControleLancamento;
import com.example.jadecsilveira.financas.util.MetodosComuns;
import com.example.jadecsilveira.financas.vo.AgendamentoVO;
import com.example.jadecsilveira.financas.vo.SaldoVO;

import java.util.ArrayList;

/**
 * Created by jadecsilveira on 11/05/2016.
 */
public class SaldoAdapter extends BaseAdapter {
    Context context;
    ArrayList<SaldoVO> saldos;
    private static LayoutInflater inflater = null;

    public SaldoAdapter(Context context, ArrayList<SaldoVO> saldos){
        this.context = context;
        this.saldos = saldos;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return saldos.size();
    }

    @Override
    public Object getItem(int position) {
        return saldos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null)
            convertView = inflater.inflate(R.layout.grid_saldos, null);

        TextView dataTextView = (TextView) convertView.findViewById(R.id.tvData);
        TextView descTextView = (TextView) convertView.findViewById(R.id.tvDescricao);
        TextView rendimentoTextView = (TextView) convertView.findViewById(R.id.tvRendimento);
        TextView despesaTextView = (TextView) convertView.findViewById(R.id.tvDespesa);
        TextView valorTextView = (TextView) convertView.findViewById(R.id.tvValor);

        SaldoVO saldo = new SaldoVO();
        saldo = saldos.get(position);

        dataTextView.setText(MetodosComuns.convertDateToStringView(saldo.getData()));
        descTextView.setText(saldo.getLancamento().getDescricao());
        if(saldo.getLancamento().getTipo().equals("rendimento")){
            rendimentoTextView.setText("+R$ " + MetodosComuns.convertToDoubleView(saldo.getLancamento().getValor()));
            despesaTextView.setText("");
        }else{
            rendimentoTextView.setText("");
            despesaTextView.setText("-R$ " + MetodosComuns.convertToDoubleView(saldo.getLancamento().getValor()));
        }

        valorTextView.setText("R$ " + MetodosComuns.convertToDoubleView(saldo.getValor()));

        return convertView;
    }
}
