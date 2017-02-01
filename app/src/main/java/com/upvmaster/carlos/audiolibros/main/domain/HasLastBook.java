package com.upvmaster.carlos.audiolibros.main.domain;

import com.upvmaster.carlos.audiolibros.main.data.BooksRepository;

/**
 * Created by Carlos on 01/02/2017.
 */

public class HasLastBook {
    private final BooksRepository booksRepository;

    public HasLastBook(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public boolean execute() {
        return booksRepository.hasLastBook();
    }
}
