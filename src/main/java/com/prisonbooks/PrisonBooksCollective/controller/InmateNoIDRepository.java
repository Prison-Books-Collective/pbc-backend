package com.prisonbooks.PrisonBooksCollective.controller;

import com.prisonbooks.PrisonBooksCollective.model.InmateNoID;
import org.springframework.data.repository.CrudRepository;

public interface InmateNoIDRepository extends CrudRepository<InmateNoID, Long> {
}
