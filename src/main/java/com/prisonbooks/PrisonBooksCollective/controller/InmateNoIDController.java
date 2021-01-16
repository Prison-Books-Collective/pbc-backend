package com.prisonbooks.PrisonBooksCollective.controller;

import com.prisonbooks.PrisonBooksCollective.model.Inmate;
import com.prisonbooks.PrisonBooksCollective.model.InmateNoID;
import com.prisonbooks.PrisonBooksCollective.model.Package;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin
@RestController
public class InmateNoIDController {

    @Autowired
    private InmateNoIDRepository inmateNoIDRepository;

    public InmateNoIDController(@Autowired InmateNoIDRepository inmateNoIDRepository){
        this.inmateNoIDRepository = inmateNoIDRepository;
    }

    @PostMapping(path="/addInmateNoID")
    public
    ResponseEntity<InmateNoID> addNewInmateNoID(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String location){
        InmateNoID inmate = new InmateNoID();
        inmate.setLocation(location);
        inmate.setFirstName(firstName);
        inmate.setLastName(lastName);
        inmateNoIDRepository.save(inmate);

        return ResponseEntity.ok(inmate);
    }

    @GetMapping(path="/getInmateNoID")
    public ResponseEntity<List<InmateNoID>> getInmate(@RequestParam String firstName, @RequestParam String lastName){
        List<InmateNoID> inmates = inmateNoIDRepository.findByFirstNameAndLastName(firstName, lastName);
        return ResponseEntity.ok(inmates);
    }


}
