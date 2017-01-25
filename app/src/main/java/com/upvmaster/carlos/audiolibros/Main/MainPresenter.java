package com.upvmaster.carlos.audiolibros.Main;

import com.upvmaster.carlos.audiolibros.entities.LibroStorage;

/**
 * Created by Carlos on 25/01/2017.
 */

public class MainPresenter {
    private final LibroStorage libroStorage;
    private final View view;

    public MainPresenter(LibroStorage libroStorage, MainPresenter.View view) {
        this.libroStorage = libroStorage;
        this.view = view;
    }

    public void clickFavoriteButton() {
        if (libroStorage.hasLastBook()) {
            view.mostrarFragmentDetalle(libroStorage.getLastBook());
        } else {
            view.mostrarNoUltimaVisita();
        }
    }

    public void openDetalle(int id) {
        libroStorage.saveLastBook(id);
        view.mostrarFragmentDetalle(id);
    }

    public interface View {
        void mostrarFragmentDetalle(int lastBook);
        void mostrarNoUltimaVisita();
    }
}
