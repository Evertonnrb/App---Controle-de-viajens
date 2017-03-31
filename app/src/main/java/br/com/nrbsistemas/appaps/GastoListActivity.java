package br.com.nrbsistemas.appaps;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Everton on 28/03/2017.
 */

public class GastoListActivity extends ListActivity implements AdapterView.OnItemClickListener {

    private List<Map<String,Object>> gastos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listarGastos()));
        //ListView listView = getListView();
        //listView.setOnItemClickListener(this);

        String[] de = {"data","descrisao","valor","categoria"};
        int[] para = {R.id.txt_data_lista_gastos,R.id.txt_lista_gasto_descrisao,R.id.txt_lista_gasto_valor,R.id.lay_categoria};

        SimpleAdapter adapter = new SimpleAdapter(this,listarGastos(),R.layout.lista_gastos,de,para);

        //passsando o view binder
        adapter.setViewBinder(new GastoViewBinder());

        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);

        //Menu de contexto regiistrando o menu de contexto
        registerForContextMenu(getListView());
    }

    //passando o menu para o menu de contexto
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.gasto_menu,menu);
    }

    //pegando a acao  no menu de context
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.menu_context_remover){
            AdapterView.AdapterContextMenuInfo info= (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
            gastos.remove(info.position);
            getListView().invalidateViews();
            dataAnterior="";
            //TODO remover do banco
            return true;
        }

        return super.onContextItemSelected(item);
    }

    private List<Map<String,Object>> listarGastos() {
        //return Arrays.asList("Taxi 19.90", "Sanduiche 12.99", "Fotos 300.00");
        gastos = new ArrayList<Map<String,Object>>();
        Map<String,Object> item = new HashMap<String, Object>();
        item.put("data","04/02/2012");
        item.put("descrisao","Diaria Hotel");
        item.put("valor","R$ 260");
        item.put("categoria",R.color.color_hospedagem);
        gastos.add(item);

        return gastos;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //TextView textView = (TextView) view;
        //String msg = "GAsto == " + textView.getText();
        //Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();



        Map<String,Object> map = gastos.get(position);
        String descrisao = (String)map.get("descrisao");
        String mensagem = "Gastos selecionados"+descrisao;
        Toast.makeText(getApplicationContext(),"Gasto selecionado "+mensagem,Toast.LENGTH_SHORT).show();
    }

    private String dataAnterior="";

    private class GastoViewBinder implements SimpleAdapter.ViewBinder{
        @Override
        public boolean setViewValue(View view, Object data, String textRepresentation) {

            if(view.getId() == R.id.txt_data_lista_gastos){
                if(!dataAnterior.equals(data)){
                    TextView view1 = (TextView) view;
                    view1.setText(textRepresentation);
                    dataAnterior = textRepresentation;
                    view.setVisibility(View.VISIBLE);
                }else{
                    view.setVisibility(View.GONE);

                }
                return  true;
            }
            if(view.getId() == R.id.lay_categoria){
                Integer id = (Integer) data;
                view.setBackgroundColor(getResources().getColor(id));
                return true;
            }
            return false;
        }
    }
}
