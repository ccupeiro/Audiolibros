package com.upvmaster.carlos.audiolibros.login.data.datasources;

import android.net.Uri;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Carlos on 05/02/2017.
 */
public class FirebaseAuthSingleton {
    private static FirebaseAuthSingleton instance;
    private FirebaseAuth auth;

    public static FirebaseAuthSingleton getInstance() {
        if(instance == null){
            instance = new FirebaseAuthSingleton();
        }
        return instance;
    }

    private FirebaseAuthSingleton() {
        auth = FirebaseAuth.getInstance();
    }

    public FirebaseAuth getAuth() {
        return auth;
    }
    public FirebaseUser getCurrentUser(){
        return auth.getCurrentUser();
    }
    public Uri getPhotoCurrentUser(){
        return getCurrentUser().getPhotoUrl();
    }
}
