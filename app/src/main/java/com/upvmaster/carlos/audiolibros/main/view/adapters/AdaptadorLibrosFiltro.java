package com.upvmaster.carlos.audiolibros.main.view.adapters;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.upvmaster.carlos.audiolibros.main.data.datasources.Libro;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by carlos.cupeiro on 22/12/2016.
 */

public class AdaptadorLibrosFiltro extends AdaptadorLibros implements Observer {
    private List<Integer> indiceFiltro; // Índice en listaSinFiltros de Cada elemento de vectorLibros
    private int librosUltimoFiltro; //Número libros del padre en último filtro
    private String busqueda = ""; // Búsqueda sobre autor o título
    private String genero = ""; // Género seleccionado
    private boolean novedad = false; // Si queremos ver solo novedades
    private boolean leido = false; // Si queremos ver solo leidos

    public AdaptadorLibrosFiltro(Context contexto, DatabaseReference reference) {
        super(contexto, reference);
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
        indiceFiltro = new ArrayList<>();
        librosUltimoFiltro = super.getItemCount();
        for (int i = 0; i < librosUltimoFiltro; i++) {
            Libro libro = super.getItem(i);
            if ((libro.getTitulo().toLowerCase().contains(busqueda) || libro.getAutor().toLowerCase().contains(busqueda))
                    && (libro.getGenero().startsWith(genero))
                    && (!novedad || (novedad && libro.getNovedad()))
                    && (!leido || (leido /*&& libro.getLeido()*/))) {
                indiceFiltro.add(i);
            }
        }
    }

    public Libro getItem(int posicion) {
        if (librosUltimoFiltro != super.getItemCount()) {
            recalculaFiltro();
        }
        return super.getItem(indiceFiltro.get(posicion));
    }

    public long getItemId(int posicion) {
        if (librosUltimoFiltro != super.getItemCount()) {
            recalculaFiltro();
        }
        return indiceFiltro.size();
    }

    public Libro getItemById(int id) {
        return super.getItem(id);
    }

    public void borrar(int posicion) {
        DatabaseReference referencia = getRef(indiceFiltro.get(posicion));
        referencia.removeValue();
        recalculaFiltro();
    }

    public void insertar(Libro libro) {
        booksReference.push().setValue(libro);
        recalculaFiltro();
    }

    @Override
    public void update(Observable observable, Object data) {
        setBusqueda((String) data);
        notifyDataSetChanged();
    }
}
