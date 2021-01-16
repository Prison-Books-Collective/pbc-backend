package com.prisonbooks.PrisonBooksCollective.controller;

import com.prisonbooks.PrisonBooksCollective.model.InmateNoID;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InmateNoIDRepository extends CrudRepository<InmateNoID, Long> {

    List<InmateNoID> findByFirstNameAndLastName(String firstName, String lastName);
}
