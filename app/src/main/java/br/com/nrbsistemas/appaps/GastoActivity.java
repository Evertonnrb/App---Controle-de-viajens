package br.com.nrbsistemas.appaps;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

import java.util.Calendar;

public class GastoActivity extends AppCompatActivity {

    private Spinner categoriaGastos;
    private int dia,mes,ano;
    private Button btnDataGasto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gasto);
        //incializando o calendario
        Calendar calendar = Calendar.getInstance();
        ano = calendar.get(Calendar.YEAR);
        mes = calendar.get(Calendar.MONTH);
        dia = calendar.get(Calendar.DAY_OF_MONTH);

        btnDataGasto = (Button)findViewById(R.id.btn_data);
        btnDataGasto.setText(dia+"/"+(mes+1)+"/"+ano);

        //Passado array de string para o spiner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,R.array.categoria_gasto,
                android.R.layout.simple_spinner_item
        );

        categoriaGastos = (Spinner) findViewById(R.id.spn_categoria);
        categoriaGastos.setAdapter(adapter);
    }

    public void selecionarData(View view) {
        showDialog(view.getId());
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if(R.id.btn_data == id){
            return new DatePickerDialog(this,listener,ano,mes,dia);
        }

        return null;
    }

    DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            ano = year;
            mes = monthOfYear;
            dia = dayOfMonth;
            btnDataGasto.setText(dia+"/"+(mes+1)+"/"+ano);
        }
    };

    public void registrarGasto(View view) {
        //TODO cadastrar gasto
    }
}
