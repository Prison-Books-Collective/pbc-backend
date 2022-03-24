package com.prisonbooks.PrisonBooksCollective.controller;

import com.prisonbooks.PrisonBooksCollective.model.InmateNoID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InmateNoIDRepository extends CrudRepository<InmateNoID, Long> {

    List<InmateNoID> findByFirstNameAndLastName(String firstName, String lastName);

    @Query("SELECT i FROM InmateNoID i WHERE i.firstName LIKE CONCAT('%', :firstName,  '%') AND i.lastName LIKE CONCAT('%', :lastName,  '%') ")
    List<InmateNoID> findByPartialFullName(String firstName, String lastName);

    @Query("SELECT i FROM InmateNoID i WHERE i.firstName LIKE CONCAT('%', :firstName,  '%') ")
    List<InmateNoID> findByPartialFirstName(String firstName);

    @Query("SELECT i FROM InmateNoID i WHERE i.lastName LIKE CONCAT('%', :lastName,  '%') ")
    List<InmateNoID> findByPartialLastName(String lastName);
}
