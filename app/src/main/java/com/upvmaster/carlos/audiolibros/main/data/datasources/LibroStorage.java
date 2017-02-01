package com.upvmaster.carlos.audiolibros.main.data.datasources;

/**
 * Created by Carlos on 21/01/2017.
 */

public interface LibroStorage {
    boolean hasLastBook();
    int getLastBook();
    void saveLastBook(int id);
}
