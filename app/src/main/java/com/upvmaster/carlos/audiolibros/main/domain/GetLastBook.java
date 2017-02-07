package com.upvmaster.carlos.audiolibros.main.domain;

import com.upvmaster.carlos.audiolibros.main.data.BooksRepository;

/**
 * Created by Carlos on 01/02/2017.
 */

public class GetLastBook {
    private final BooksRepository booksRepository;

    public GetLastBook(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public String execute() {
        return booksRepository.getLastBook();
    }
}
