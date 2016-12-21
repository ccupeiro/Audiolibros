package com.upvmaster.carlos.audiolibros;

import android.app.Application;

import java.util.List;

/**
 * Created by Carlos on 21/12/2016.
 */

public class Aplicacion extends Application {
    private List<Libro> listaLibros;
    private AdaptadorLibros adaptador;
    @Override
    public void onCreate() {
        listaLibros = Libro.ejemploLibros();
        adaptador = new AdaptadorLibros (this, listaLibros);
    }
    public AdaptadorLibros getAdaptador() {
        return adaptador;
    }
    public List<Libro> getVectorLibros() {
        return listaLibros;
    }
}
