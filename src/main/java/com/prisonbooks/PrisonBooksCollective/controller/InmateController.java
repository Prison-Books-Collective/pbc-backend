package com.prisonbooks.PrisonBooksCollective.controller;

import com.prisonbooks.PrisonBooksCollective.model.Inmate;
import com.prisonbooks.PrisonBooksCollective.model.Package;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(path="/add")
    public
    ResponseEntity<Inmate> addNewInmate(@RequestParam String firstName, @RequestParam String lastName, @RequestParam long id){
        Inmate inmate = new Inmate();
        inmate.setId(id);
        inmate.setFirstName(firstName);
        inmate.setLastName(lastName);
        inmateRepository.save(inmate);

        return ResponseEntity.ok(inmate);
    }

    @GetMapping(path="/get")
    public ResponseEntity<Inmate> getInmate(@RequestParam long id){
        Optional<Inmate> inmate = inmateRepository.findById(id);
        if (inmate.isPresent()){
            return ResponseEntity.ok(inmate.get());
        }
        return new ResponseEntity(null, HttpStatus.NO_CONTENT);
    }

    @GetMapping(path="/getPackages")
    public ResponseEntity<List<Package>> getPackagesForInmate(@RequestParam long id){
        ResponseEntity<Inmate> inmateEntity = getInmate(id);
        if(inmateEntity.hasBody()){
            Inmate inmate = inmateEntity.getBody();
            return ResponseEntity.ok(inmate.getPackages());
        }
        return new ResponseEntity(null, HttpStatus.NO_CONTENT);
    }

    @PostMapping(path="/addPackage")
    public ResponseEntity<List<Package>> addPackageForInmate(@RequestParam long id, @RequestBody Package packageForInmate){
        ResponseEntity<Inmate> inmateEntity = getInmate(id);
        if(inmateEntity.hasBody()){
            Inmate inmate = inmateEntity.getBody();
            List<Package> packages = inmate.getPackages();
            packages.add(packageForInmate);
            inmate.setPackages(packages);
            inmateRepository.save(inmate);
            return ResponseEntity.ok(packages);
        }
        return new ResponseEntity(null, HttpStatus.NO_CONTENT);
    }
}
