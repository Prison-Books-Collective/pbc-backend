package com.prisonbooks.PrisonBooksCollective.controller;

import com.prisonbooks.PrisonBooksCollective.model.Facility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@CrossOrigin
@RestController
public class FacilityController {

    @Autowired
    final FacilityRepository facilityRepository;

    public FacilityController(@Autowired FacilityRepository facilityRepository){
        this.facilityRepository = facilityRepository;
    }

    @PostMapping(path="/addFacility")
    public ResponseEntity<Facility> addFacility(@RequestBody Facility facility){
        Facility savedFacility = facilityRepository.save(facility);
        return ResponseEntity.ok(savedFacility);
    }

    @GetMapping(path="/getAllFacilities")
    public ResponseEntity<List<Facility>> getAllFacilities() {
        Iterable<Facility> all = facilityRepository.findAll();
        List<Facility> facilities = new LinkedList<>();
        all.forEach(facilities::add);

        return ResponseEntity.ok(facilities);
    }

    @DeleteMapping(path="/deleteFacility")
    public HttpStatus deleteFacility(@RequestParam long facilityId){
        facilityRepository.deleteById(facilityId);
        if (facilityRepository.findById(facilityId).isPresent()){
            return HttpStatus.EXPECTATION_FAILED;
        }else {
            return HttpStatus.OK;
        }
    }
}
