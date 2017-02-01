package com.upvmaster.carlos.audiolibros.common.view;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by carlos.cupeiro on 05/01/2017.
 */

public class PreferenciasActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new PreferenciasFragment()).commit();
    }
}
