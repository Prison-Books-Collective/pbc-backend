package com.prisonbooks.PrisonBooksCollective.controller;

import com.prisonbooks.PrisonBooksCollective.model.Inmate;
import com.prisonbooks.PrisonBooksCollective.model.Package;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin
@RestController
public class InmateController {

    @Autowired
    private InmateRepository inmateRepository;

    public InmateController(@Autowired InmateRepository inmateRepository){
        this.inmateRepository = inmateRepository;
    }

    @PostMapping(path="/addInmate")
    public
    ResponseEntity<Inmate> addNewInmate(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String id){
        Inmate inmate = new Inmate();
        inmate.setId(id);
        inmate.setFirstName(firstName);
        inmate.setLastName(lastName);
        inmateRepository.save(inmate);

        return ResponseEntity.ok(inmate);
    }

    @GetMapping(path="/getInmate")
    public ResponseEntity<Inmate> getInmate(@RequestParam String id){
        Optional<Inmate> inmate = inmateRepository.findById(id);
        if (inmate.isPresent()){
            return ResponseEntity.ok(inmate.get());
        }
        return new ResponseEntity(null, HttpStatus.NO_CONTENT);
    }

    @GetMapping(path="/searchInmatesByName")
    public ResponseEntity<List<Inmate>> getInmateByName(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName
    ){
        if(Strings.isBlank(firstName) && Strings.isBlank(lastName)) {
            return ResponseEntity.badRequest().build();
        }

        if(Strings.isNotBlank(firstName) && Strings.isNotBlank(lastName)) {
            List<Inmate> inmates = inmateRepository.findByPartialFullName(firstName, lastName);
            return inmates.isEmpty()
                    ? ResponseEntity.noContent().build()
                    : ResponseEntity.ok(inmates);
        }

        List<Inmate> inmates = Strings.isNotBlank(firstName)
                ? inmateRepository.findByPartialFirstName(firstName)
                : inmateRepository.findByPartialLastName(lastName);

        return inmates.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(inmates);
    }

    @PutMapping(path = "/updateInmate")
    public ResponseEntity<Inmate> updateInmate(@RequestParam String originalId, @RequestParam String firstName, @RequestParam String lastName, @RequestParam String id){
        Optional<Inmate> originalInmate = inmateRepository.findById(originalId);
        if (originalInmate.isPresent()){
            Inmate inmate = originalInmate.get();
            inmate.setFirstName(firstName);
            inmate.setLastName(lastName);
            if (!id.equals(originalId)){
                Inmate toClear = originalInmate.get();
                List<Package> packages = List.copyOf(toClear.getPackages());
                toClear.setPackages(new LinkedList<>());
                inmateRepository.save(toClear);
                inmateRepository.deleteById(originalId);
                inmate.setId(id);
                inmate.setPackages(packages);
                Inmate save = inmateRepository.save(inmate);
                return ResponseEntity.ok(save);
            }

            Inmate save = inmateRepository.save(inmate);
            return ResponseEntity.ok(save);
        }
        return new ResponseEntity(null, HttpStatus.NO_CONTENT);
    }

}
