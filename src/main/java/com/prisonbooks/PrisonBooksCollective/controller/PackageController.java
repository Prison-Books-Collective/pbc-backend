package com.prisonbooks.PrisonBooksCollective.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
public class PackageController {

    @Autowired
    PackageRepository packageRepository;

    public PackageController(@Autowired PackageRepository packageRepository){
        this.packageRepository = packageRepository;
    }

    @GetMapping(path="/getPackagesFromDate")
    public ResponseEntity<List<Package>> getPackagesFromDate(@RequestParam Date date){
        List<Package> byDate = packageRepository.findByDate(date);
        return ResponseEntity.ok(byDate);
    }
}
