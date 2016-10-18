package com.example.jadecsilveira.financas.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jadecsilveira.financas.R;
import com.example.jadecsilveira.financas.adapter.DespesaAdapter;
import com.example.jadecsilveira.financas.control.ControleLancamento;
import com.example.jadecsilveira.financas.dao.DatabaseHelper;
import com.example.jadecsilveira.financas.util.Constantes;
import com.example.jadecsilveira.financas.util.MetodosComuns;
import com.example.jadecsilveira.financas.vo.AgendamentoVO;

import java.util.ArrayList;

public class DespesaActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ListView gridDespesas;
    DespesaAdapter adapter;
    ArrayList<AgendamentoVO> agendamentos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despesas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle params = new Bundle();
                Intent intent = new Intent(new Intent(DespesaActivity.this, InclusaoDespesaActivity.class));
                params.putString("funcao_botao", "incluir");
                intent.putExtras(params);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        gridDespesas = (ListView) findViewById(R.id.gridDespesas);
        DatabaseHelper db = new DatabaseHelper(this);
        agendamentos = db.getAgendamentos(Constantes.DESPESA);

        TextView total = (TextView) findViewById(R.id.tvTotal);

        if(!agendamentos.isEmpty()) {
            View header = (View) getLayoutInflater().inflate(R.layout.header_lancamentos, null);
            gridDespesas.addHeaderView(header);
            ControleLancamento controle = new ControleLancamento();
            total.setText("R$ " + MetodosComuns.convertToDouble(controle.getTotal(agendamentos)));
        }
        adapter = new DespesaAdapter(this, agendamentos);
        gridDespesas.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.despesas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sair) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent = null;

        if (id == R.id.nav_pagina_inicial) {
            intent = new Intent(DespesaActivity.this, PaginaInicialActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_rendimentos) {
            intent = new Intent(DespesaActivity.this, RendimentoActivity.class);
            startActivity(intent);
            finish();
        }
        DespesaActivity.this.overridePendingTransition(0, 0);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        this.finish();
        return true;
    }
}
