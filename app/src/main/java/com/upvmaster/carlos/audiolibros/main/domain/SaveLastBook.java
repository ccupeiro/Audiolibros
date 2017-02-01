package com.upvmaster.carlos.audiolibros.main.domain;

import com.upvmaster.carlos.audiolibros.main.data.BooksRepository;

/**
 * Created by Carlos on 01/02/2017.
 */

public class SaveLastBook {
    private final BooksRepository booksRepository;

    public SaveLastBook(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public void execute(int id) {
        booksRepository.saveLastBook(id);
    }
}
