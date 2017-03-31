package br.com.nrbsistemas.appaps;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Calendar;

public class CadastroNovaViajem extends AppCompatActivity {

    //atribuindo  o bd
    private DataBaseHelper helper;
    private EditText destino, quantidadePessoas, orcamento;
    private RadioGroup radioGroup;
    private int ano,mes,dia;
    private Button dataChegada,dataSaida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_nova_viajem);

        Calendar calendar = Calendar.getInstance();
        ano = calendar.get(Calendar.YEAR);
        mes = calendar.get(Calendar.MONTH);
        dia = calendar.get(Calendar.DAY_OF_MONTH);

        dataChegada = (Button)findViewById(R.id.btn_data_chegada);
        dataSaida = (Button)findViewById(R.id.btn_data_saida);
        destino = (EditText) findViewById(R.id.edt_destino);
        quantidadePessoas = (EditText)findViewById(R.id.edt_qtd_pessoa);
        orcamento = (EditText)findViewById(R.id.edt_orcamento);

        //instancia de BD
        helper = new DataBaseHelper(this);
    }

    @Override
    protected void onDestroy() {
        helper.close();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.viajem_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_novo_gasto:
                startActivity(new Intent(this, GastoActivity.class));
                break;
            case R.id.menu_remover:
                //TODO remover do banco
                Toast.makeText(getApplicationContext(), "Remover", Toast.LENGTH_SHORT).show();
                break;
            default:

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void salvarViajem(View view) {
    }

    public void salvarViagem(View view){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("destino",destino.getText().toString());
        values.put("data_chegada",dataChegada.getTime());
        values.put("data_saida",dataSaida.getTime());
        values.put("orcamento",orcamento.getText().toString());
        values.put("quantidade_pessoas",quantidadePessoas.getText().toString());
        int tipo = radioGroup.getCheckedRadioButtonId();
        if (tipo == R.id.rb_lazer)
            values.put("tipo_viagem", Constantes.VIAGEM_LAZER);
        else
            values.put("tipo_viagem", Constantes.VIAGEM_NEGOCIOS);

        long resultado = db.insert("viagem",null,values);
        if(resultado != -1)
            Toast.makeText(getApplicationContext(), R.string.str_registro_salvo,Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getApplicationContext(), R.string.str_erro_ao_salvar,Toast.LENGTH_SHORT).show();
    }
}
