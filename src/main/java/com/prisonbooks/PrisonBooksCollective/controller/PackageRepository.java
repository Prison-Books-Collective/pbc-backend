package com.prisonbooks.PrisonBooksCollective.controller;

import com.prisonbooks.PrisonBooksCollective.model.Package;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface PackageRepository extends CrudRepository<Package,Long> {

    List<Package> findAllByDate(LocalDate date);

    Long countByDate(LocalDate date);

    @Query("SELECT p FROM Package p WHERE p.inmate.id = ?1")
    List<Package> findAllByInmateId(String inmateId);


    @Query("SELECT p FROM Package p WHERE p.inmateNoId.id = ?1")
    List<Package> findAllByInmateNoId(long inmateId);

    @Query("SELECT p FROM Package p JOIN p.books b WHERE b.isbn10 = :isbn OR b.isbn13 = :isbn")
    List<Package> findAllByISBN(String isbn);

    @Query("SELECT p FROM Package p JOIN p.books b JOIN b.authors a WHERE b.title LIKE CONCAT('%', :title,  '%') AND a LIKE CONCAT('%', :author, '%')")
    List<Package> findAllByAuthorAndTitleContains(String author, String title);

    @Query("SELECT p FROM Package p JOIN p.noISBNBooks b JOIN b.authors a WHERE b.title LIKE CONCAT('%', :title,  '%') AND a LIKE CONCAT('%', :author, '%')")
    List<Package> findAllByNoISBNAuthorAndTitleContains(String author, String title);
}
