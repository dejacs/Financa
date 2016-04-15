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

public class InclusaoRendimento extends AppCompatActivity {

    private DatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inclusao_rendimento);

        RendimentoVO rendimento = new RendimentoVO();
        rendimento.setDescricao(((EditText) findViewById(R.id.descRend)).toString());
        rendimento.setValor(((EditText) findViewById(R.id.valorRend)).toString());

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
        switch (item.getItemId()) {
            case R.id.action_enviar:
                SQLiteDatabase db = helper.getWritableDatabase();
                ContentValues values = new ContentValues();

                RendimentoVO rendimento = new RendimentoVO();

                values.put("descricao", rendimento.getDescricao());
                values.put("valor", rendimento.getValor());

                long resultado = db.insert("rendimento", null, values);
                if(resultado != -1 ){
                    Toast.makeText(this, getString(R.string.registro_salvo),Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, getString(R.string.erro_salvar),Toast.LENGTH_SHORT).show();
                }
                onDestroy();

                intent = new Intent(InclusaoRendimento.this, RendimentosActivity.class);
                this.finish();
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    protected void onDestroy() {
        helper.close();
        super.onDestroy();
    }
}
