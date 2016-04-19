package com.example.jadecsilveira.financas.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jadecsilveira.financas.R;
import com.example.jadecsilveira.financas.dao.DatabaseHelper;
import com.example.jadecsilveira.financas.util.Constantes;

public class InclusaoDespesaActivity extends AppCompatActivity {

    private DatabaseHelper helper = new DatabaseHelper(this);
    private EditText descricao, valor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inclusao_despesa);

        descricao = (EditText) findViewById(R.id.descDespesa);
        valor = (EditText) findViewById(R.id.valorDespesa);
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
            SQLiteDatabase db = helper.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(Constantes.DESCRICAO, descricao.getText().toString());
            values.put(Constantes.VALOR, valor.getText().toString());

            long resultado = db.insert(Constantes.TABELA_DESPESA, null, values);
            if(resultado != -1 ){
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
