package com.upvmaster.carlos.audiolibros.main.data.datasources;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.upvmaster.carlos.audiolibros.main.view.adapters.AdaptadorLibrosFiltro;

import java.util.List;

/**
 * Created by carlos.cupeiro on 25/01/2017.
 */
public class LibrosSingleton {
    private static LibrosSingleton instance;
    private List<Libro> listaLibros;
    private AdaptadorLibrosFiltro adaptadorLibros;
    private FirebaseAuth auth;

    public static LibrosSingleton getInstance(Context context) {
        if(instance == null){
            instance = new LibrosSingleton(context);
        }
        return instance;
    }

    private LibrosSingleton(Context context) {
        listaLibros = Libro.ejemploLibros();
        adaptadorLibros = new AdaptadorLibrosFiltro(context, listaLibros);
        auth = FirebaseAuth.getInstance();
    }

    public List<Libro> getListaLibros() {
        return listaLibros;
    }

    public AdaptadorLibrosFiltro getAdaptadorLibros() {
        return adaptadorLibros;
    }

    public FirebaseAuth getAuth() {
        return auth;
    }
}
