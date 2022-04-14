package com.prisonbooks.PrisonBooksCollective.controller;

import com.prisonbooks.PrisonBooksCollective.model.Book;
import com.prisonbooks.PrisonBooksCollective.model.NoISBNBook;
import com.prisonbooks.PrisonBooksCollective.model.Package;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface PackageRepository extends CrudRepository<Package, Long> {

    List<Package> findAllByDate(LocalDate date);

    Long countByDate(LocalDate date);

    @Query("SELECT p FROM Package p WHERE p.inmate.id = ?1")
    List<Package> findAllByInmateId(String inmateId);


    @Query("SELECT p FROM Package p WHERE p.inmateNoId.id = ?1")
    List<Package> findAllByInmateNoId(long inmateId);

    List<Package> findAllByBooks(Book book);

    List<Package> findAllByNoISBNBooks(NoISBNBook book);

}
