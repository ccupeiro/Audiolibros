package com.upvmaster.carlos.audiolibros.main.data;

import com.upvmaster.carlos.audiolibros.main.data.datasources.LibroStorage;

import static android.R.attr.id;

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
    public String getLastBook(){
        return librosStorage.getLastBook();
    }
    public void saveLastBook(String key){
        librosStorage.saveLastBook(key);
    }
}
