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

        Intent intent = getIntent();
        Bundle params = intent.getExtras();

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
                Intent intent1 = new Intent(InclusaoRendimentoActivity.this, DateActivity.class);
                intent1.putExtra("caller", "InclusaoRendimentoActivity");
                startActivity(intent1);
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
        Intent intent = null;
        int id = item.getItemId();
        if(id == R.id.action_enviar){
            agendamento = controle.setObjeto(Constantes.RENDIMENTO);
            if(helper.incluirLancamento(agendamento)){
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
