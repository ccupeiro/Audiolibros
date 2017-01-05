package com.upvmaster.carlos.audiolibros.adapters;

import android.content.Context;

import com.upvmaster.carlos.audiolibros.entities.Libro;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by carlos.cupeiro on 22/12/2016.
 */

public class AdaptadorLibrosFiltro extends AdaptadorLibros {
    private List<Libro> listaSinFiltros;// Vector con todos los libros
    private List<Integer> indiceFiltro; // Índice en listaSinFiltros de Cada elemento de vectorLibros
    private String busqueda = ""; // Búsqueda sobre autor o título
    private String genero = ""; // Género seleccionado
    private boolean novedad = false; // Si queremos ver solo novedades
    private boolean leido = false; // Si queremos ver solo leidos

    public AdaptadorLibrosFiltro(Context contexto, List<Libro> vectorLibros) {
        super(contexto, vectorLibros);
        listaSinFiltros = vectorLibros;
        recalculaFiltro();
    }

    public void setBusqueda(String busqueda) {
        this.busqueda = busqueda.toLowerCase();
        recalculaFiltro();
    }

    public void setGenero(String genero) {
        this.genero = genero;
        recalculaFiltro();
    }

    public void setNovedad(boolean novedad) {
        this.novedad = novedad;
        recalculaFiltro();
    }

    public void setLeido(boolean leido) {
        this.leido = leido;
        recalculaFiltro();
    }

    public void recalculaFiltro() {
        listaLibros = new ArrayList<Libro>();
        indiceFiltro = new ArrayList<>();
        for (int i = 0; i < listaSinFiltros.size(); i++) {
            Libro libro = listaSinFiltros.get(i);
            if ((libro.titulo.toLowerCase().contains(busqueda) || libro.autor.toLowerCase().contains(busqueda))
                    && (libro.genero.startsWith(genero))
                    && (!novedad || (novedad && libro.novedad))
                    && (!leido || (leido && libro.leido))) {
                listaLibros.add(libro);
                indiceFiltro.add(i);
            }
        }
    }

    public Libro getItem(int posicion) {
        return listaSinFiltros.get(indiceFiltro.get(posicion));
    }

    public long getItemId(int posicion) {
        return indiceFiltro.get(posicion);
    }

    public void borrar(int posicion) {
        listaSinFiltros.remove((int) getItemId(posicion));
        recalculaFiltro();
    }

    public void insertar(Libro libro) {
        listaSinFiltros.add(0,libro);
        recalculaFiltro();
    }
}
