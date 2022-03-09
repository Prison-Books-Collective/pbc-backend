package com.prisonbooks.PrisonBooksCollective.controller;


import com.prisonbooks.PrisonBooksCollective.model.Inmate;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InmateRepository extends CrudRepository<Inmate, String> {

    List<Inmate> findByFirstNameAndLastName(String firstName, String lastName);
}
