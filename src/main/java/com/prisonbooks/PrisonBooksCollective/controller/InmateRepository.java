package com.prisonbooks.PrisonBooksCollective.controller;


import com.prisonbooks.PrisonBooksCollective.model.Inmate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InmateRepository extends CrudRepository<Inmate, String> {

    List<Inmate> findByFirstNameAndLastName(String firstName, String lastName);

    @Query("SELECT i FROM Inmate i WHERE i.firstName LIKE CONCAT('%', :firstName,  '%') AND i.lastName LIKE CONCAT('%', :lastName,  '%') ")
    List<Inmate> findByPartialFullName(String firstName, String lastName);

    @Query("SELECT i FROM Inmate i WHERE i.firstName LIKE CONCAT('%', :firstName,  '%') ")
    List<Inmate> findByPartialFirstName(String firstName);

    @Query("SELECT i FROM Inmate i WHERE i.lastName LIKE CONCAT('%', :lastName,  '%') ")
    List<Inmate> findByPartialLastName(String lastName);
}
