package com.example.jadecsilveira.financas.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jadecsilveira.financas.R;
import com.example.jadecsilveira.financas.util.MetodosComuns;
import com.example.jadecsilveira.financas.vo.RendimentoVO;

import java.util.ArrayList;

/**
 * Created by jadecsilveira on 18/04/2016.
 */
public class RendimentoAdapter extends BaseAdapter {


    Context context;
    ArrayList<RendimentoVO> rendimentos;
    private static LayoutInflater inflater = null;

    public RendimentoAdapter(Context context, ArrayList<RendimentoVO> rendimentos){
        this.context = context;
        this.rendimentos = rendimentos;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return rendimentos.size();
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

        RendimentoVO rendimento = new RendimentoVO();
        rendimento = rendimentos.get(position);
        descTextView.setText(rendimento.getDescricao());
        valorTextView.setText("R$ " + MetodosComuns.convertToDouble(rendimento.getValor()));
        return convertView;
    }
}
