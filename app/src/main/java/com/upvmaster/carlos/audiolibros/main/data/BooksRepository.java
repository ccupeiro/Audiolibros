package com.upvmaster.carlos.audiolibros.main.data;

import com.upvmaster.carlos.audiolibros.main.data.datasources.LibroStorage;

/**
 * Created by Carlos on 01/02/2017.
 */

public class BooksRepository {
    private final LibroStorage librosStorage;

    public BooksRepository(LibroStorage librosStorage) {
        this.librosStorage = librosStorage;
    }

    public boolean hasLastBook(){
        return librosStorage.hasLastBook();
    }
    public int getLastBook(){
        return librosStorage.getLastBook();
    }
    public void saveLastBook(int id){
        librosStorage.saveLastBook(id);
    }
}
