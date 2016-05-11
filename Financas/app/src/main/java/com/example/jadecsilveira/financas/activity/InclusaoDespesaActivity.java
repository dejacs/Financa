package com.example.jadecsilveira.financas.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.jadecsilveira.financas.R;
import com.example.jadecsilveira.financas.control.ControleLancamento;
import com.example.jadecsilveira.financas.dao.DatabaseHelper;
import com.example.jadecsilveira.financas.util.Constantes;
import com.example.jadecsilveira.financas.vo.AgendamentoVO;

public class InclusaoDespesaActivity extends AppCompatActivity {

    private DatabaseHelper helper = new DatabaseHelper(this);
    AgendamentoVO agendamento = new AgendamentoVO();
    ControleLancamento controle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controle = new ControleLancamento();
        controle.setCampos(this, R.layout.activity_inclusao_despesa);
        helper = new DatabaseHelper(this);
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
            agendamento = controle.setObjeto(Constantes.DESPESA);
            if(helper.incluirLancamento(agendamento)){
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
