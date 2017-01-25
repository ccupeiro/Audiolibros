package com.upvmaster.carlos.audiolibros.events;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.upvmaster.carlos.audiolibros.R;
import com.upvmaster.carlos.audiolibros.activities.MainActivity;
import com.upvmaster.carlos.audiolibros.entities.Libro;
import com.upvmaster.carlos.audiolibros.entities.LibrosSingleton;
import com.upvmaster.carlos.audiolibros.fragments.SelectorFragment;

/**
 * Created by Carlos on 21/01/2017.
 */

public class OpenMenuActionsClickAction implements ClickAction{
    private final MainActivity mainActivity;
    private final View view;

    public OpenMenuActionsClickAction(MainActivity mainActivity, View view) {
        this.mainActivity = mainActivity;
        this.view = view;
    }

    @Override
    public void execute(final int position) {
        AlertDialog.Builder menu = new AlertDialog.Builder(mainActivity);
        CharSequence[] opciones = {"Compartir", "Borrar ", "Insertar"};
        menu.setItems(opciones, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int opcion) {
                switch (opcion) {
                    case 0: //Compartir
                        Animator anim = AnimatorInflater
                                .loadAnimator(mainActivity, R.animator.compartir);
                        anim.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animator) {
                                Libro libro =  LibrosSingleton.getInstance(view.getContext()).getListaLibros().get(position);
                                Intent i = new Intent(Intent.ACTION_SEND);
                                i.setType("text/plain");
                                i.putExtra(Intent.EXTRA_SUBJECT, libro.titulo);
                                i.putExtra(Intent.EXTRA_TEXT, libro.urlAudio);
                                mainActivity.startActivity(Intent.createChooser(i, "Compartir"));
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
                        anim.setTarget(view);
                        anim.start();
                        break;
                    case 1: //Borrar
                        Snackbar.make(view, "¿Estás seguro?", Snackbar.LENGTH_LONG).setAction("SI", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                LibrosSingleton.getInstance(view.getContext()).getAdaptadorLibros().borrar(position);
                                LibrosSingleton.getInstance(view.getContext()).getAdaptadorLibros().notifyDataSetChanged();
                            }
                        }).show();
                        break;
                    case 2: //Insertar
                        LibrosSingleton.getInstance(view.getContext()).getAdaptadorLibros().insertar(
                                LibrosSingleton.getInstance(view.getContext()).getAdaptadorLibros().getItem(position));
                        //adaptador.notifyDataSetChanged();
                        LibrosSingleton.getInstance(view.getContext()).getAdaptadorLibros().notifyItemInserted(0);
                        Snackbar.make(view, "Libro insertado", Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                            }
                        }).show();
                        break;
                }
            }
        });
        menu.create().show();
    }

}
