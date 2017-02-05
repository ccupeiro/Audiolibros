package com.upvmaster.carlos.audiolibros.login.data.datasources;

import android.content.Context;
import android.content.SharedPreferences;

import com.upvmaster.carlos.audiolibros.main.data.datasources.LibroSharedPrefenceStorage;
import com.upvmaster.carlos.audiolibros.main.data.datasources.LibroStorage;

/**
 * Created by Carlos on 05/02/2017.
 */

public class FirebaseStorageSharedPreferences implements FirebaseStorage {
    public static final String PREF_AUDIOLIBROS = "com.example.audiolibros_internal";
    public static final String KEY_PROVIDER = "provider";
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    private final Context context;
    private static FirebaseStorageSharedPreferences instance;

    public static FirebaseStorage getInstance(Context context) {
        if (instance == null) {
            instance = new FirebaseStorageSharedPreferences(context);
        }
        return instance;
    }

    private FirebaseStorageSharedPreferences(Context context) {
        this.context = context;
    }

    @Override
    public void saveUser(String name, String email, String provider) {
        SharedPreferences pref = getPreference();
        pref.edit().putString(KEY_PROVIDER, provider).commit();
        pref.edit().putString(KEY_NAME, name).commit();
        if (email != null) {
            pref.edit().putString(KEY_EMAIL, email).commit();
        }
    }

    @Override
    public void removeUser() {
        SharedPreferences pref = getPreference();
        pref.edit().remove(KEY_PROVIDER).commit();
        pref.edit().remove(KEY_NAME).commit();
        pref.edit().remove(KEY_EMAIL).commit();
    }

    private SharedPreferences getPreference() {
        return context.getSharedPreferences(PREF_AUDIOLIBROS, Context.MODE_PRIVATE);
    }

    @Override
    public String getNameUser() {
        return getPreference().getString(KEY_NAME,"NO TIENE");
    }
}
