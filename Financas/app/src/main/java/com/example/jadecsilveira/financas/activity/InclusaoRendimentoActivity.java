package com.example.jadecsilveira.financas.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jadecsilveira.financas.R;
import com.example.jadecsilveira.financas.control.ControleLancamento;
import com.example.jadecsilveira.financas.dao.DatabaseHelper;
import com.example.jadecsilveira.financas.util.Constantes;
import com.example.jadecsilveira.financas.util.MetodosComuns;
import com.example.jadecsilveira.financas.vo.AgendamentoVO;

public class InclusaoRendimentoActivity extends AppCompatActivity {

    private DatabaseHelper helper = new DatabaseHelper(this);
    AgendamentoVO agendamento = new AgendamentoVO();
    ControleLancamento controle;
    Bundle params;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controle = new ControleLancamento();
        controle.setCampos(this, R.layout.activity_inclusao_rendimento);
        helper = new DatabaseHelper(this);
        String id;

        EditText descricao = (EditText) findViewById(R.id.descricao);
        EditText valor = (EditText) findViewById(R.id.valor);
        EditText data = (EditText) findViewById(R.id.data);

        intent = getIntent();
        params = intent.getExtras();

        AgendamentoVO agendamento = new AgendamentoVO();

        if(null!=params){
            String valor_param = params.getString("valor");
            String descricao_param = params.getString("descricao");
            id = params.getString("id");

            if(null!=valor_param && !valor_param.equals("")){
                valor.setText(valor_param);
            }
            if(null!=descricao_param && !descricao_param.equals("")){
                descricao.setText(descricao_param);
            }else if(null!=id && !id.equals("")){
                agendamento = helper.getAgendamento(id);

                descricao.setText(agendamento.getLancamento().getDescricao());
                valor.setText(MetodosComuns.convertToDoubleView(agendamento.getLancamento().getValor()));
                data.setText(MetodosComuns.convertDateToStringView(agendamento.getData()));
            }
        }

        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(new Intent(InclusaoRendimentoActivity.this, DateActivity.class));
                intent.putExtra("caller", "InclusaoRendimentoActivity");
                String funcaoBotao = params.getString("funcao_botao");
                EditText valor = (EditText) findViewById(R.id.valor);
                EditText descricao = (EditText) findViewById(R.id.descricao);

                if(null!=params && null!=params.getString("id") && !params.getString("id").equals("")){
                    params.putString("id", params.getString("id"));
                }
                if(null!=funcaoBotao && funcaoBotao.equals("incluir")){
                    params.putString("funcao_botao", "incluir");
                }else if(null!=funcaoBotao && funcaoBotao.equals("alterar")){
                    params.putString("funcao_botao", "alterar");
                }
                if(null!=valor && null!=valor.getText().toString() && !valor.getText().toString().equals("")){
                    params.putString("valor", valor.getText().toString());
                }
                if(null!=descricao && null!=descricao.getText().toString() && !descricao.getText().toString().equals("")){
                    params.putString("descricao", descricao.getText().toString());
                }
                intent.putExtras(params);
                startActivity(intent);
            }
        });
        if(null!=params && null!=params.getString("data") && !params.getString("data").equals("")){
            data.setText(params.getString("data"));
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.inclusao, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        int id = item.getItemId();

        if(id == R.id.action_enviar && null!=params.getString("funcao_botao") && params.getString("funcao_botao").equals("incluir")){
            agendamento = controle.setObjeto(Constantes.RENDIMENTO, -1);
            if(helper.incluirLancamento(agendamento)){
                Toast.makeText(this, getString(R.string.registro_salvo), Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, getString(R.string.erro_salvar),Toast.LENGTH_SHORT).show();
            }
        }else if(id == R.id.action_enviar && null!=params.getString("funcao_botao") && params.getString("funcao_botao").equals("alterar")){
            agendamento = controle.setObjeto(Constantes.RENDIMENTO, Long.valueOf(params.getString("id")));
            if(helper.alterarLancamento(agendamento)){
                Toast.makeText(this, getString(R.string.registro_salvo), Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, getString(R.string.erro_salvar),Toast.LENGTH_SHORT).show();
            }
        }
        intent = new Intent(InclusaoRendimentoActivity.this, RendimentoActivity.class);
        startActivity(intent);
        this.finish();
        return true;
    }
}
