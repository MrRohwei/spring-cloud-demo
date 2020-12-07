package com.lvbok.service;

import com.lvbok.entity.Book;

import java.util.List;

public interface BookService {

    List<Book> findByTitle(String title);


    List<Book> findByTitleOrAuthor(String title, String author);
}
