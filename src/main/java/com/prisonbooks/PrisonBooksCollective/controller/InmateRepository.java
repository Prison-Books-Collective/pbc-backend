package com.prisonbooks.PrisonBooksCollective.controller;


import com.prisonbooks.PrisonBooksCollective.model.Inmate;
import org.springframework.data.repository.CrudRepository;

public interface InmateRepository extends CrudRepository<Inmate, String> {


}
