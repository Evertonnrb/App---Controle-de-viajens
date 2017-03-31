package br.com.nrbsistemas.appaps;

import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.attr.id;

/**
 * Created by Everton on 28/03/2017.
 */

public class ViajemListActivity extends ListActivity implements AdapterView.OnItemClickListener, DialogInterface.OnClickListener {

    private AlertDialog alertDialog;
    private AlertDialog dialogoConfirmacao;
    private int viagemSelecionada;
    private DataBaseHelper helper;
    private SimpleDateFormat dateFormat;
    private Double valorLimite;


    private List<Map<String, Object>> viagens;

    private List<Map<String, Object>> listarViajens() {

        helper = new DataBaseHelper(this);
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SharedPreferences preferencias = PreferenceManager.getDefaultSharedPreferences(this);

        String valor = preferencias.getString("valor limte", "-1");
        valorLimite = Double.valueOf(valor);

        //buscando do banco
        SQLiteDatabase db = helper.getReadableDatabase();
        //cursor le as info
        Cursor cursor = db.rawQuery("SELECT _id,tipo_vigem,destino," +
                "data_chegada,data_saida,orcamento from viagem", null);
        //vai ao primeiro regitro da tabela

        cursor.moveToFirst();

        viagens = new ArrayList<Map<String, Object>>();

        for (int i = 0; i < cursor.getCount(); i++) {
            Map<String, Object> item = new HashMap<String, Object>();
            String id = cursor.getString(0);
            int tipoViagem = cursor.getInt(1);
            String destino = cursor.getString(2);
            long dataChegada = cursor.getLong(3);
            long dataSaida = cursor.getLong(4);
            double orcamento = cursor.getDouble(5);

            item.put("id", id);

            if (tipoViagem == Constantes.VIAGEM_LAZER) {
                item.put("imagem", R.drawable.ic_sentiment_satisfied_black_24dp);

            } else {
                item.put("imagem", R.drawable.ic_sentiment_very_dissatisfied_black_24dp);

                Date dataChegadaDate = new Date(dataChegada);
                Date dataSaidaDate = new Date(dataSaida);
                String periodo = dateFormat.format(dataChegadaDate) + " a";
                item.put("data",periodo);
                double totalGasto = cacularTotalGasto(db,id);
                item.put("total","Gasto total R$ " +totalGasto);
                double alerta = orcamento * valorLimite /100;
                Double [] valores = new Double [] {orcamento,alerta,totalGasto};
                item.put("barraProgresso",valores);
                viagens.add(item);

                cursor.moveToNext();

            }
            cursor.close();

            return viagens;
        }

        private double calcularTotalGasto(SQLiteDatabase  db,String id){
        Cursor cursor = db.rawQuery(
                "SELECT SUM (valor) FROM gasto WHERE viagem_id = ?";
            new String[] { id });
    }
        /*
        *     //passando os obj para o hash na mao
        viagens = new ArrayList<Map<String,Object>>();
        Map<String,Object> item = new HashMap<String, Object>();
        item.put("imagem",R.drawable.ic_sentiment_very_dissatisfied_black_24dp);
        item.put("destino","São paulo");
        item.put("data","02/02/2012 a 04/02/2012");
        item.put("valor","Gasto total de 1033,90 R$");
        viagens.add(item);

        item = new HashMap<String,Object>();
        item.put("imagem",R.drawable.ic_sentiment_satisfied_black_24dp);
        item.put("destino","Maceio");
        item.put("data","14/05/2012 a 21/05/2012");
        item.put("valor","Gasto toatal de 3023.99 R$");
        item.put("barraProgresso",new Double[] {500.0,450.0,314.98});
        viagens.add(item);

        return viagens;
    */
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.alertDialog = criAlertDialog();
        this.dialogoConfirmacao = criaDialogoConfirmacao();

//        setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listarViajens()));

        //Instanciando um listview e pegando por herança atravez do getListView()
        //    ListView listView = getListView();
        //      listView.setOnItemClickListener(this);

        //Passando os valores que serao exiibidos no adapter
        String[] de = {"imagem", "destino", "data", "valor"};
        int[] para = {R.id.img_tipo_viagem, R.id.txt_destino_viajem, R.id.txt_data_viajem, R.id.txt_valor_viajem};

        SimpleAdapter adapter = new SimpleAdapter(this, listarViajens(), R.layout.lista_viajens, de, para);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);

        //alert
        this.alertDialog = criAlertDialog();
    }

    // private List<String> listarViajens() {return Arrays.asList("São Paulo", "Maceio", "Salvador");}

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //TextView textView = (TextView) view;
        //String msg = "Viajem selecionada " + textView.getText();

        this.viagemSelecionada = position;
        alertDialog.show();

        Map<String, Object> map = viagens.get(position);
        String destino = (String) map.get("destino");

        Toast.makeText(getApplicationContext(), "Viagem selecionada " + destino, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, GastoListActivity.class));

    }


    //Implementar os Dialogs
    @Override
    public void onClick(DialogInterface dialog, int item) {
        switch (item) {
            case 0:
                startActivity(new Intent(this, CadastroNovaViajem.class));
                break;
            case 1:
                startActivity(new Intent(this, GastoActivity.class));
                break;
            case 2:
                startActivity(new Intent(this, GastoListActivity.class));
                break;
            case 3:
                dialogoConfirmacao.show();
                //viagens.remove(this.viagemSelecionada);
                //getListView().invalidateViews();
                break;
            case DialogInterface.BUTTON_POSITIVE:

                viagens.remove(viagemSelecionada);
                getListView().invalidateViews();
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                dialogoConfirmacao.dismiss();
                break;
        }
    }

    public boolean setViewValuew(View view, Object data, String text) {
        if (view.getId() == R.id.barra_de_progresso) {
            Double valores[] = (Double[]) data;
            ProgressBar progressBar = (ProgressBar) view;
            progressBar.setMax(valores[0].intValue());
            progressBar.setSecondaryProgress(valores[1].intValue());
            progressBar.setProgress(valores[2].intValue());
            return true;
        }
        return false;
    }


    private AlertDialog criaDialogoConfirmacao() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.str_confir_exclusao_viagem);
        builder.setPositiveButton(getString(R.string.str_sim), this);
        builder.setNegativeButton(getString(R.string.str_nao), this);

        return builder.create();
    }

    private AlertDialog criAlertDialog() {
        final CharSequence[] itens = {
                getString(R.string.str_editar),
                getString(R.string.str_opcoes),
                getString(R.string.str_gastos_realizados),
                getString(R.string.str_remover)
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.str_opcoes);
        builder.setItems(itens, this);
        return builder.create();
    }
}
