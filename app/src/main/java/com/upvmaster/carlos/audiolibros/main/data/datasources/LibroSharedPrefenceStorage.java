package com.upvmaster.carlos.audiolibros.main.data.datasources;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Carlos on 21/01/2017.
 */

public class LibroSharedPrefenceStorage implements LibroStorage {

    public static final String PREF_AUDIOLIBROS = "com.example.audiolibros_internal";
    public static final String KEY_ULTIMO_LIBRO = "ultimo";
    private final Context context;

    private static LibroSharedPrefenceStorage instance;

    public static LibroStorage getInstance(Context context) {
        if (instance == null) {
            instance = new LibroSharedPrefenceStorage(context);
        }
        return instance;
    }

    private LibroSharedPrefenceStorage(Context context) {
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

    public void saveLastBook(int id) {
        getPreference().edit().putInt(KEY_ULTIMO_LIBRO,id).commit();
    }

}
