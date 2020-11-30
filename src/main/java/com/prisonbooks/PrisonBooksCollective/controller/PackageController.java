package com.prisonbooks.PrisonBooksCollective.controller;

import com.prisonbooks.PrisonBooksCollective.model.Package;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
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
    public ResponseEntity<List<Package>> getPackagesFromDate(@RequestParam String date){
        LocalDate dateObj = LocalDate.parse(date);
        List<Package> byDate = packageRepository.findAllByDate(dateObj);
        return ResponseEntity.ok(byDate);
    }
}
