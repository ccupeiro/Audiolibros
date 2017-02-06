package com.upvmaster.carlos.audiolibros.main.data.datasources;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.upvmaster.carlos.audiolibros.common.data.datasources.FirebaseDatabaseSingleton;
import com.upvmaster.carlos.audiolibros.main.view.adapters.AdaptadorLibrosFiltro;

import java.util.List;

/**
 * Created by carlos.cupeiro on 25/01/2017.
 */
public class LibrosSingleton {
    private static LibrosSingleton instance;
    private AdaptadorLibrosFiltro adaptadorLibros;

    public static LibrosSingleton getInstance(Context context) {
        if(instance == null){
            instance = new LibrosSingleton(context);
        }
        return instance;
    }

    private LibrosSingleton(Context context) {
        adaptadorLibros = new AdaptadorLibrosFiltro(context, FirebaseDatabaseSingleton.getInstance().getBooksReference());
    }

    public AdaptadorLibrosFiltro getAdaptadorLibros() {
        return adaptadorLibros;
    }

}
