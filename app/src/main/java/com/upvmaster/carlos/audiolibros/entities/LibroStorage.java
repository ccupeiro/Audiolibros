package com.upvmaster.carlos.audiolibros.entities;

/**
 * Created by Carlos on 21/01/2017.
 */

public interface LibroStorage {
    boolean hasLastBook();
    int getLastBook();
    void saveLastBook(int id);
}
