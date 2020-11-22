package com.prisonbooks.PrisonBooksCollective.controller;

import com.prisonbooks.PrisonBooksCollective.model.Inmate;
import com.prisonbooks.PrisonBooksCollective.model.Resource;
import org.springframework.data.repository.CrudRepository;

public interface ResourceRepository extends CrudRepository<Resource, Long> {
}
