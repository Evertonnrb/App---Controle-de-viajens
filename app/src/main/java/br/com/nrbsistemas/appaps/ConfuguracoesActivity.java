package br.com.nrbsistemas.appaps;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.annotation.Nullable;

import br.com.nrbsistemas.appaps.R;

/**
 * Created by Everton on 31/03/2017.
 */

public class ConfuguracoesActivity extends PreferenceActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.layout.preferencias);
    }
}
