package com.upvmaster.carlos.audiolibros.entities;

import android.content.Context;

import com.upvmaster.carlos.audiolibros.adapters.AdaptadorLibros;
import com.upvmaster.carlos.audiolibros.adapters.AdaptadorLibrosFiltro;

import java.util.List;

/**
 * Created by carlos.cupeiro on 25/01/2017.
 */
public class LibrosSingleton {
    private static LibrosSingleton instance;
    private List<Libro> listaLibros;
    private AdaptadorLibrosFiltro adaptadorLibros;

    public static LibrosSingleton getInstance(Context context) {
        if(instance == null){
            instance = new LibrosSingleton(context);
        }
        return instance;
    }

    private LibrosSingleton(Context context) {
        listaLibros = Libro.ejemploLibros();
        adaptadorLibros = new AdaptadorLibrosFiltro(context, listaLibros);
    }

    public List<Libro> getListaLibros() {
        return listaLibros;
    }

    public AdaptadorLibrosFiltro getAdaptadorLibros() {
        return adaptadorLibros;
    }
}
