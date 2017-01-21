package com.upvmaster.carlos.audiolibros.entities;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Carlos on 21/01/2017.
 */

public class LibroStoragePreferencesStorage implements LibroStorage{
    public static final String PREF_AUDIOLIBROS = "com.example.audiolibros_internal";
    public static final String KEY_ULTIMO_LIBRO = "ultimo";
    private final Context context;

    public LibroStoragePreferencesStorage(Context context) {
        this.context = context;
    }

    public boolean hasLastBook() {
        return getPreference().contains(KEY_ULTIMO_LIBRO);
    }

    private SharedPreferences getPreference() {
        return context.getSharedPreferences(PREF_AUDIOLIBROS, Context.MODE_PRIVATE);
    }

    public int getLastBook() {
        return getPreference().getInt(KEY_ULTIMO_LIBRO, -1);
    }
}
