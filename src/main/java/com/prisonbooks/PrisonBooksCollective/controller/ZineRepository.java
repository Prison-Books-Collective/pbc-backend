package com.prisonbooks.PrisonBooksCollective.controller;

import com.prisonbooks.PrisonBooksCollective.model.Zine;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ZineRepository extends CrudRepository<Zine, Long> {
    Optional<Zine> findByThreeLetterCode(String threeLetterCode);
}
