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

public class InclusaoDespesaActivity extends AppCompatActivity {

    private DatabaseHelper helper = new DatabaseHelper(this);
    AgendamentoVO agendamento = new AgendamentoVO();
    ControleLancamento controle;
    Bundle params;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controle = new ControleLancamento();
        controle.setCampos(this, R.layout.activity_inclusao_despesa);
        helper = new DatabaseHelper(this);
        String id;

        EditText descricao = (EditText) findViewById(R.id.descricao);
        EditText valor = (EditText) findViewById(R.id.valor);
        EditText data = (EditText) findViewById(R.id.data);

        intent = getIntent();
        params = intent.getExtras();

        AgendamentoVO agendamento = new AgendamentoVO();

        if(null!=params && null!=params.getString("id") && !params.getString("id").equals("")){
            id = params.getString("id");
            agendamento = helper.getAgendamento(id);

            descricao.setText(agendamento.getLancamento().getDescricao());
            valor.setText(MetodosComuns.convertToDouble(agendamento.getLancamento().getValor()));
            data.setText(MetodosComuns.convertDateToStringView(agendamento.getData()));
        }

        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(new Intent(InclusaoDespesaActivity.this, DateActivity.class));
                intent.putExtra("caller", "InclusaoDespesaActivity");
                String funcaoBotao = params.getString("funcao_botao");

                if(null!=params && null!=params.getString("id") && !params.getString("id").equals("")){
                    params.putString("id", params.getString("id"));
                }
                if(null!=funcaoBotao && funcaoBotao.equals("incluir")){
                    params.putString("funcao_botao", "incluir");
                }else if(null!=funcaoBotao && funcaoBotao.equals("alterar")){
                    params.putString("funcao_botao", "alterar");
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
            agendamento = controle.setObjeto(Constantes.DESPESA, Long.valueOf(params.getString("id")));
            if(helper.incluirLancamento(agendamento)){
                Toast.makeText(this, getString(R.string.registro_salvo), Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, getString(R.string.erro_salvar),Toast.LENGTH_SHORT).show();
            }
        }else if(id == R.id.action_enviar && null!=params.getString("funcao_botao") && params.getString("funcao_botao").equals("alterar")){
            agendamento = controle.setObjeto(Constantes.DESPESA, Long.valueOf(params.getString("id")));
            if(helper.alterarLancamento(agendamento)){
                Toast.makeText(this, getString(R.string.registro_salvo), Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, getString(R.string.erro_salvar),Toast.LENGTH_SHORT).show();
            }
        }
        intent = new Intent(InclusaoDespesaActivity.this, DespesaActivity.class);
        startActivity(intent);
        this.finish();
        return true;
    }
}
