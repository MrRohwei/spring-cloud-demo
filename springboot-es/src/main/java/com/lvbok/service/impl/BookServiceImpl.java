package com.lvbok.service.impl;

import com.lvbok.entity.Book;
import com.lvbok.repo.BookRepo;
import com.lvbok.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookRepo bookRepo;

    @Override
    public List<Book> findByTitle(String title) {
        return bookRepo.findByTitle(title);
    }

    @Override
    public List<Book> findByTitleOrAuthor(String title, String author) {
        return bookRepo.findByTitleOrAuthor(title, author);
    }
}
