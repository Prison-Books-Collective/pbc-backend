package com.prisonbooks.PrisonBooksCollective.controller;

import com.prisonbooks.PrisonBooksCollective.model.NoISBNBook;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface NoISBNBookRepository extends CrudRepository<NoISBNBook, Long> {

    @Query("SELECT b FROM NoISBNBook b join b.authors author WHERE authors LIKE CONCAT('%', :author, '%') AND b.title LIKE CONCAT('%', :title, '%')")
    List<NoISBNBook> findByAuthorAndTitleContains(String author, String title);

}
