package com.example.jadecsilveira.financas.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jadecsilveira.financas.R;
import com.example.jadecsilveira.financas.util.MetodosComuns;
import com.example.jadecsilveira.financas.vo.DespesaVO;

import java.util.ArrayList;

/**
 * Created by jadecsilveira on 19/04/2016.
 */
public class DespesaAdapter extends BaseAdapter {

    Context context;
    ArrayList<DespesaVO> despesas;
    private static LayoutInflater inflater = null;

    public DespesaAdapter(Context context, ArrayList<DespesaVO> despesas){
        this.context = context;
        this.despesas = despesas;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return despesas.size();
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
            convertView = inflater.inflate(R.layout.grid_despesas, null);

        TextView descTextView = (TextView) convertView.findViewById(R.id.tvDescricao);
        TextView valorTextView = (TextView) convertView.findViewById(R.id.tvValor);

        DespesaVO despesa = new DespesaVO();
        despesa = despesas.get(position);
        descTextView.setText(despesa.getDescricao());
        valorTextView.setText("R$ " + MetodosComuns.convertToDouble(despesa.getValor()));
        return convertView;
    }
}
