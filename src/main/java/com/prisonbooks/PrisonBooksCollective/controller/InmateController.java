package com.prisonbooks.PrisonBooksCollective.controller;

import com.prisonbooks.PrisonBooksCollective.model.Book;
import com.prisonbooks.PrisonBooksCollective.model.Inmate;
import com.prisonbooks.PrisonBooksCollective.model.Package;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
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
        Optional<Inmate> inmate = inmateRepository.findById(id);
        if (inmate.isPresent()){
            return ResponseEntity.ok(inmate.get());
        }
        return new ResponseEntity(null, HttpStatus.NO_CONTENT);
    }

    @GetMapping(path="/getPackages")
    public ResponseEntity<List<Package>> getPackagesForInmate(@RequestParam String id){
        ResponseEntity<Inmate> inmateEntity = getInmate(id);
        if(inmateEntity.hasBody()){
            Inmate inmate = inmateEntity.getBody();
            return ResponseEntity.ok(inmate.getPackages());
        }
        return new ResponseEntity(null, HttpStatus.NO_CONTENT);
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
    @PostMapping(path="/addPackage")
    public ResponseEntity<Package> addPackageForInmate(@RequestParam String id, @RequestBody Package packageForInmate){
        ResponseEntity<Inmate> inmateEntity = getInmate(id);
        if(inmateEntity.hasBody()){
            Inmate inmate = inmateEntity.getBody();
            List<Package> packages = inmate.getPackages();
            packages.add(packageForInmate);
            inmate.setPackages(packages);
            inmateRepository.save(inmate);
            return ResponseEntity.ok(packageForInmate);
        }
        return new ResponseEntity(null, HttpStatus.NO_CONTENT);
    }
}
