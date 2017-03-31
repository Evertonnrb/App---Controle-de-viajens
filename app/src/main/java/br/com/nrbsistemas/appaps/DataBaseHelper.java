package br.com.nrbsistemas.appaps;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;

/**
 * Created by Everton on 31/03/2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String BANCO_DE_DADOS = "BoaViagem";
    private static int VERSAO = 1;

    public DataBaseHelper(Context context) {
        super(context, BANCO_DE_DADOS, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //TABELA VIAGEM
        db.execSQL("CREATE TABLE viagem (_id INTEGER PRIMARY KEY," +
                "destino TEXT, tipo_viagem INTEGER, data_chegada DATE," +
                "data_saida DATE, orcamento DOUBLE, quantidade_pessoas INTEGER);");

        //TABELA GASTOS
        db.execSQL("CREATE TABLE gasto (_id INTEGER PRIMARY KEY, categoria TEXT," +
                "data DATE, valor DOUBLE, descrisao TEXT, local TEXT, viagem_id INTEGER," +
                "FOREIGN KEY (viagem_id) REFERENCES viagem(_id))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
