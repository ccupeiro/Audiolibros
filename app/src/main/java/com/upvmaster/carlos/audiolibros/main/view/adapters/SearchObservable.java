package com.upvmaster.carlos.audiolibros.main.view.adapters;

import java.util.Observable;

import android.support.v7.widget.SearchView;

/**
 * Created by Carlos on 21/01/2017.
 */

public class SearchObservable extends Observable implements SearchView.OnQueryTextListener {
    @Override
    public boolean onQueryTextChange(String query) {
        setChanged();
        notifyObservers(query);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }
}
