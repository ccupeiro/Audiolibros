package com.upvmaster.carlos.audiolibros.entities;

import android.app.Application;

import com.upvmaster.carlos.audiolibros.adapters.AdaptadorLibros;
import com.upvmaster.carlos.audiolibros.adapters.AdaptadorLibrosFiltro;

import java.util.List;

/**
 * Created by Carlos on 21/12/2016.
 */

public class Aplicacion extends Application {
    private List<Libro> listaLibros;
    private AdaptadorLibrosFiltro adaptador;
    @Override
    public void onCreate() {
        listaLibros = Libro.ejemploLibros();
        adaptador = new AdaptadorLibrosFiltro (this, listaLibros);
    }
    public AdaptadorLibrosFiltro getAdaptador() {
        return adaptador;
    }
    public List<Libro> getListLibros() {
        return listaLibros;
    }
}
