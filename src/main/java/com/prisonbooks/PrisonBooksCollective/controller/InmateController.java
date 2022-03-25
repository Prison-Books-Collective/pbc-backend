package com.prisonbooks.PrisonBooksCollective.controller;

import com.prisonbooks.PrisonBooksCollective.model.Inmate;
import com.prisonbooks.PrisonBooksCollective.model.Package;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

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
        return inmateRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @GetMapping(path="/searchInmatesByName")
    public ResponseEntity<List<Inmate>> getInmateByName(@RequestParam String firstName, @RequestParam String lastName){
        List<Inmate> inmates = inmateRepository.findByFirstNameAndLastName(firstName, lastName);
        return ResponseEntity.ok(inmates);
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
        return ResponseEntity.noContent().build();
    }

}
