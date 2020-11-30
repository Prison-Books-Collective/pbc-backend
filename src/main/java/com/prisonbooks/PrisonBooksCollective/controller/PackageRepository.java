package com.prisonbooks.PrisonBooksCollective.controller;

import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface PackageRepository extends CrudRepository<Package,Long> {

    List<Package> findByDate(Date date);
}
