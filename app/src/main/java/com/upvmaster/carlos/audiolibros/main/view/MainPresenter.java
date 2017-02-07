package com.upvmaster.carlos.audiolibros.main.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.android.volley.toolbox.ImageLoader;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.upvmaster.carlos.audiolibros.login.data.datasources.FirebaseAuthSingleton;
import com.upvmaster.carlos.audiolibros.login.data.datasources.FirebaseStorageSharedPreferences;
import com.upvmaster.carlos.audiolibros.login.view.CustomLoginActivity;
import com.upvmaster.carlos.audiolibros.login.view.LoginActivity;
import com.upvmaster.carlos.audiolibros.main.data.datasources.VolleySingleton;
import com.upvmaster.carlos.audiolibros.main.domain.GetLastBook;
import com.upvmaster.carlos.audiolibros.main.domain.HasLastBook;
import com.upvmaster.carlos.audiolibros.main.domain.SaveLastBook;

import static android.R.attr.id;

/**
 * Created by Carlos on 25/01/2017.
 */

public class MainPresenter {
    private final SaveLastBook saveLastBook;
    private final GetLastBook getLastBook;
    private final HasLastBook hasLastBook;
    private final View view;

    public MainPresenter(SaveLastBook saveLastBook, GetLastBook getLastBook, HasLastBook hasLastBook, View view) {
        this.saveLastBook = saveLastBook;
        this.getLastBook = getLastBook;
        this.hasLastBook = hasLastBook;
        this.view = view;
    }

    public void clickFavoriteButton() {
        if (hasLastBook.execute()) {
            view.mostrarFragmentDetalle(getLastBook.execute());
        } else {
            view.mostrarNoUltimaVisita();
        }
    }

    public String getNameUser(Context context){
        return FirebaseStorageSharedPreferences.getInstance(context).getNameUser();
    }

    public void logout(final Activity activity){
        AuthUI.getInstance().signOut(activity).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                FirebaseStorageSharedPreferences.getInstance(activity).removeUser();
                Intent i = new Intent(activity, CustomLoginActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                activity.startActivity(i);
                activity.finish();
            }
        });

    }

    public void openDetalle(String key) {
        saveLastBook.execute(key);
        view.mostrarFragmentDetalle(key);
    }

    public void colocarImagen(Context context){
        Uri url = FirebaseAuthSingleton.getInstance().getPhotoCurrentUser();
        view.mostrarImagenUser(url, VolleySingleton.getInstance(context).getLectorImagenes());

    }

    public interface View {
        void mostrarFragmentDetalle(String key);
        void mostrarNoUltimaVisita();
        void mostrarImagenUser(Uri url, ImageLoader loader);
    }
}
