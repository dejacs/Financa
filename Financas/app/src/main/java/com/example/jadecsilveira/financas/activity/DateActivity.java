package com.example.jadecsilveira.financas.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.jadecsilveira.financas.R;

import java.lang.reflect.Array;
import java.util.Arrays;

public class DateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.date);

        final Intent[] intent = {getIntent()};
        final Bundle params = intent[0].getExtras();

        Button ok = (Button) findViewById(R.id.btn_ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String caller = intent[0].getStringExtra("caller");

                if(null!=caller && caller.equals("InclusaoRendimentoActivity")){
                    intent[0] = new Intent(DateActivity.this, InclusaoRendimentoActivity.class);
                }else if(null!=caller && caller.equals("InclusaoDespesaActivity")){
                    intent[0] = new Intent(DateActivity.this, InclusaoDespesaActivity.class);
                }else{
                    Toast.makeText(DateActivity.this, getString(R.string.erro_salvar), Toast.LENGTH_SHORT).show();
                }

                DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);

                if(null!=params){
                    String funcaoBotao = params.getString("funcao_botao");
                    String valor = params.getString("valor");
                    String descricao = params.getString("descricao");
                    String id = params.getString("id");

                    if(null!=id && !id.equals("")){
                        params.putString("id", params.getString("id"));
                    }
                    if(null!=funcaoBotao && funcaoBotao.equals("incluir")){
                        params.putString("funcao_botao", "incluir");
                    }else if(null!=funcaoBotao && funcaoBotao.equals("alterar")){
                        params.putString("funcao_botao", "alterar");
                    }
                    if(null!=valor && !valor.equals("")){
                        params.putString("valor", valor);
                    }
                    if(null!=descricao && !descricao.equals("")){
                        params.putString("descricao", descricao);
                    }
                    params.putString("data", formatarData(datePicker));
                    intent[0].putExtras(params);
                }else{
                    Toast.makeText(DateActivity.this, getString(R.string.erro_salvar), Toast.LENGTH_SHORT).show();
                }

                startActivity(intent[0]);
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
