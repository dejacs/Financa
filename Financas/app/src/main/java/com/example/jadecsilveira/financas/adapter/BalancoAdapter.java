package com.example.jadecsilveira.financas.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jadecsilveira.financas.R;
import com.example.jadecsilveira.financas.util.MetodosComuns;
import com.example.jadecsilveira.financas.vo.BalancoVO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by jadecsilveira on 20/04/2016.
 */
public class BalancoAdapter extends BaseAdapter {
    Context context;
    ArrayList<BalancoVO> balancos;
    private static LayoutInflater inflater = null;

    public BalancoAdapter(Context context, ArrayList<BalancoVO> balancos){
        this.context = context;
        this.balancos = balancos;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return balancos.size();
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
        if (convertView == null)
            convertView = inflater.inflate(R.layout.grid_balancos, null);

        TextView tvDataPagamento = (TextView) convertView.findViewById(R.id.tvDataPagamento);
        TextView valorTextView = (TextView) convertView.findViewById(R.id.tvValor);

        BalancoVO balanco = new BalancoVO();
        balanco = balancos.get(position);
        tvDataPagamento.setText(new SimpleDateFormat("dd/MM/yyyy").format(balanco.getData()));
        valorTextView.setText("R$ " + MetodosComuns.convertToDouble(balanco.getValor()));
        return convertView;
    }
}
