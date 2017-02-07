package com.upvmaster.carlos.audiolibros.main.domain;

import com.upvmaster.carlos.audiolibros.main.data.BooksRepository;

import static android.R.attr.id;

/**
 * Created by Carlos on 01/02/2017.
 */

public class SaveLastBook {
    private final BooksRepository booksRepository;

    public SaveLastBook(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public void execute(String key) {
        booksRepository.saveLastBook(key);
    }
}
