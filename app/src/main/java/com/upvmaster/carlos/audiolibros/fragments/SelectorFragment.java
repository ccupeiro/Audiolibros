package com.upvmaster.carlos.audiolibros.fragments;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.Activity;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.upvmaster.carlos.audiolibros.R;
import com.upvmaster.carlos.audiolibros.activities.MainActivity;
import com.upvmaster.carlos.audiolibros.adapters.AdaptadorLibrosFiltro;
import com.upvmaster.carlos.audiolibros.entities.Aplicacion;
import com.upvmaster.carlos.audiolibros.entities.Libro;

import java.util.List;

/**
 * Created by carlos.cupeiro on 22/12/2016.
 */

public class SelectorFragment extends Fragment implements Animation.AnimationListener {
    private Activity actividad;
    private RecyclerView recyclerView;
    private AdaptadorLibrosFiltro adaptador;
    private List<Libro> listaLibros;

    @Override
    public void onAttach(Activity actividad) {
        super.onAttach(actividad);
        this.actividad = actividad;
        Aplicacion app = (Aplicacion) actividad.getApplication();
        adaptador = app.getAdaptador();
        listaLibros = app.getListLibros();

    }

    @Override
    public void onResume() {
        ((MainActivity) getActivity()).mostrarElementos(true);
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflador, ViewGroup contenedor, Bundle savedInstanceState) {
        View vista = inflador.inflate(R.layout.fragment_selector, contenedor, false);
        setHasOptionsMenu(true);
        recyclerView = (RecyclerView) vista.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(actividad, 2));
        recyclerView.setAdapter(adaptador);
        adaptador.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) actividad).mostrarDetalle(
                        (int) adaptador.getItemId(recyclerView.getChildAdapterPosition(v)));

            }
        });
        adaptador.setOnItemLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(final View v) {
                final int id = recyclerView.getChildAdapterPosition(v);
                AlertDialog.Builder menu = new AlertDialog.Builder(actividad);
                CharSequence[] opciones = {"Compartir", "Borrar ", "Insertar"};
                menu.setItems(opciones, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int opcion) {
                        switch (opcion) {
                            case 0: //Compartir
                                Animator anim = AnimatorInflater
                                        .loadAnimator(actividad, R.animator.compartir);
                                anim.addListener(new Animator.AnimatorListener() {
                                    @Override
                                    public void onAnimationStart(Animator animator) {
                                        Libro libro = listaLibros.get(id);
                                        Intent i = new Intent(Intent.ACTION_SEND);
                                        i.setType("text/plain");
                                        i.putExtra(Intent.EXTRA_SUBJECT, libro.titulo);
                                        i.putExtra(Intent.EXTRA_TEXT, libro.urlAudio);
                                        startActivity(Intent.createChooser(i, "Compartir"));
                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animator) {

                                    }

                                    @Override
                                    public void onAnimationCancel(Animator animator) {

                                    }

                                    @Override
                                    public void onAnimationRepeat(Animator animator) {

                                    }
                                });
                                anim.setTarget(v);
                                anim.start();
                                break;
                            case 1: //Borrar
                                Snackbar.make(v, "¿Estás seguro?", Snackbar.LENGTH_LONG).setAction("SI", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Animation anim = AnimationUtils.loadAnimation(actividad, R.anim.menguar);
                                        anim.setAnimationListener(SelectorFragment.this);
                                        v.startAnimation(anim);
                                        adaptador.borrar(id);
                                    }
                                }).show();
                                break;
                            case 2: //Insertar
                                int posicion = recyclerView.getChildLayoutPosition(v);
                                adaptador.insertar(adaptador.getItem(posicion));
                                //adaptador.notifyDataSetChanged();
                                adaptador.notifyItemInserted(0);
                                Snackbar.make(v, "Libro insertado", Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                    }
                                }).show();
                                break;
                        }
                    }
                });
                menu.create().show();
                return true;
            }
        });
        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setAddDuration(500);
        animator.setMoveDuration(500);
        recyclerView.setItemAnimator(animator);
        return vista;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_selector, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_buscar);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String query) {
                adaptador.setBusqueda(query);
                adaptador.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
        });
        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                adaptador.setBusqueda("");
                adaptador.notifyDataSetChanged();
                return true; // Para permitir cierre
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true; // Para permitir expansión
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_ultimo) {
            ((MainActivity) actividad).irUltimoVisitado();
            return true;
        } else if (id == R.id.menu_buscar) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Animation
    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        adaptador.notifyDataSetChanged();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
