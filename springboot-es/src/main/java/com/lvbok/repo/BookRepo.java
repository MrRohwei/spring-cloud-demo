package com.lvbok.repo;

import com.lvbok.entity.Book;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookRepo extends CrudRepository<Book, Integer> {

    List<Book> findByTitle(String title);


    List<Book> findByTitleOrAuthor(String title, String author);
}
