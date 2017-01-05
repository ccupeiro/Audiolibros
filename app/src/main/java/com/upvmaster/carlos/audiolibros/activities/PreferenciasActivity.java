package com.upvmaster.carlos.audiolibros.activities;

import android.app.Activity;
import android.os.Bundle;

import com.upvmaster.carlos.audiolibros.fragments.PreferenciasFragment;

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
