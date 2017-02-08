package com.upvmaster.carlos.audiolibros.common.data.datasources;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Carlos on 06/02/2017.
 */
public class FirebaseDatabaseSingleton {
    private static FirebaseDatabaseSingleton instance;
    private final static String BOOKS_CHILD = "libros";
    private final static String USERS_CHILD = "usuarios";
    private FirebaseDatabase database;
    private DatabaseReference usersReference;
    private DatabaseReference booksReference;

    public static FirebaseDatabaseSingleton getInstance() {
        if (instance == null) {
            instance = new FirebaseDatabaseSingleton();
        }
        return instance;
    }

    private FirebaseDatabaseSingleton() {
        database = FirebaseDatabase.getInstance();
        database.setPersistenceEnabled(true);
        booksReference = database.getReference().child(BOOKS_CHILD);
        usersReference = database.getReference().child(USERS_CHILD);
    }

    public FirebaseDatabase getDatabase() {
        return database;
    }

    public DatabaseReference getUsersReference() {
        return usersReference;
    }

    public DatabaseReference getBooksReference() {
        return booksReference;
    }
}
