package com.prisonbooks.PrisonBooksCollective.controller;

import com.prisonbooks.PrisonBooksCollective.model.NoISBNBook;
import org.springframework.data.repository.CrudRepository;

public interface NoISBNBookRepository extends CrudRepository<NoISBNBook, Long> {
}
