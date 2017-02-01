package com.upvmaster.carlos.audiolibros.common.view;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.upvmaster.carlos.audiolibros.R;
import com.upvmaster.carlos.audiolibros.main.view.MainActivity;

/**
 * Created by carlos.cupeiro on 05/01/2017.
 */

public class PreferenciasFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
    @Override
    public void onResume() {
        PreferenciasFragment preferenciasFragment = (PreferenciasFragment) getFragmentManager().findFragmentById(R.id.detalle_fragment);
        if (preferenciasFragment == null) {
            ((MainActivity) getActivity()).mostrarElementos(false);
        }
        super.onResume();
    }
}
