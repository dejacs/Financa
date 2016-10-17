package com.example.jadecsilveira.financas.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.example.jadecsilveira.financas.R;

import java.lang.reflect.Array;
import java.util.Arrays;

public class DateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.date);

        Button ok = (Button) findViewById(R.id.btn_ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                String caller = intent.getStringExtra("caller");
                String funcaoBotao = intent.getStringExtra("funcao_botao");
                String id = intent.getStringExtra("id");

                if(caller.equals("InclusaoRendimentoActivity")){
                    intent = new Intent(DateActivity.this, InclusaoRendimentoActivity.class);
                }else{
                    intent = new Intent(DateActivity.this, InclusaoDespesaActivity.class);
                }

                DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);

                Bundle params = new Bundle();
                params.putString("data", formatarData(datePicker));

                if(null!=funcaoBotao && funcaoBotao.equals("incluir")){
                    params.putString("funcao_botao", "incluir");
                }else if(null!=funcaoBotao && funcaoBotao.equals("alterar")){
                    params.putString("funcao_botao", "alterar");
                }
                intent.putExtras(params);

                startActivity(intent);
                finish();
            }
        });
    }

    private String formatarData(DatePicker data){
        String dia = String.valueOf(data.getDayOfMonth()), mes = String.valueOf(data.getMonth()+1), ano = String.valueOf(data.getYear()), zero = "0", barra= "/";
        Integer tamDia = 2, tamMes = 2, tamAno = 4;


        if(dia.length()!=tamDia) dia = zero + dia;
        if(mes.length()!=tamMes) mes = zero + mes;
        if(ano.length()!=tamAno){
            for(int i=0; i<tamAno-String.valueOf(data.getYear()).length()-1; i++){
                ano = zero + ano;
            }
        }
        return dia + barra + mes + barra + ano;
    }
}
