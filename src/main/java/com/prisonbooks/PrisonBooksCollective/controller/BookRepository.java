package com.prisonbooks.PrisonBooksCollective.controller;

import com.prisonbooks.PrisonBooksCollective.model.Book;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends CrudRepository<Book, Long> {

     Optional<Book> findByIsbn10(String isbn10);

     Optional<Book> findByIsbn13(String isbn13);

     List<Book> findByIsbn13StartsWith(String beginning);

     List<Book> findByIsbn10StartsWith(String beginning);
}
