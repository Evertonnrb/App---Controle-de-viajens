package br.com.nrbsistemas.appaps;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class DashBoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.dash_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menu_sair){
            finish();
        }


        return super.onOptionsItemSelected(item);
    }

    public void selecione(View view) {
        TextView textView = (TextView) view;
        String opc = "Selecionada a opção" + textView.getText().toString();
        Toast.makeText(getApplicationContext(), "::  " + opc, Toast.LENGTH_SHORT).show();

        switch (view.getId()) {
            case R.id.img_nova_viajem:
                startActivity(new Intent(this, CadastroNovaViajem.class));
                break;
            case R.id.img_novo_gasto:
                startActivity(new Intent(this,GastoActivity.class));
                break;
            case R.id.img_minhas_viajens:
                startActivity(new Intent(this,ViajemListActivity.class));
                break;
            case R.id.img_configuracoes:
                startActivity(new Intent(this,ConfuguracoesActivity.class));
                break;
        }
    }


}
