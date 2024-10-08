package com.prisonbooks.PrisonBooksCollective.controller;

import com.prisonbooks.PrisonBooksCollective.model.Inmate;
import com.prisonbooks.PrisonBooksCollective.model.InmateNoID;
import com.prisonbooks.PrisonBooksCollective.model.Package;
import org.apache.logging.log4j.util.Strings;
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
    public ResponseEntity<List<InmateNoID>> getInmateNoIDByName(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName
    ){
        if(Strings.isBlank(firstName) && Strings.isBlank(lastName)) {
            return ResponseEntity.badRequest().build();
        }

        if(Strings.isNotBlank(firstName) && Strings.isNotBlank(lastName)) {
            List<InmateNoID> inmates = inmateNoIDRepository.findByPartialFullName(firstName, lastName);
            return inmates.isEmpty()
                    ? ResponseEntity.noContent().build()
                    : ResponseEntity.ok(inmates);
        }

        List<InmateNoID> inmates = Strings.isNotBlank(firstName)
                ? inmateNoIDRepository.findByPartialFirstName(firstName)
                : inmateNoIDRepository.findByPartialLastName(lastName);

        return inmates.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(inmates);
    }


    @GetMapping(path="/getInmateNoIDByDatabaseID")
    public ResponseEntity<InmateNoID> getInmateNoIDByDatabaseID(@RequestParam long id){
        Optional<InmateNoID> optional = inmateNoIDRepository.findById(id);
        if(optional.isPresent()){
            InmateNoID inmateNoID = optional.get();
            return ResponseEntity.ok(inmateNoID);
        }
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @PostMapping(path="/addPackageInmateNoID")
    public ResponseEntity<Package> addPackageForInmateNoID(@RequestParam long id, @RequestBody Package packageForInmate){
        Optional<InmateNoID> optional = inmateNoIDRepository.findById(id);
        if (optional.isPresent()){
            InmateNoID inmateNoID = optional.get();
            List<Package> packages = inmateNoID.getPackages();
            packages.add(packageForInmate);
            inmateNoID.setPackages(packages);
            inmateNoIDRepository.save(inmateNoID);
            return ResponseEntity.ok(packageForInmate);
        }
        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "/updateInmateNoID")
    public ResponseEntity<InmateNoID> updateInmateNoID(@RequestParam long originalId, @RequestParam String firstName, @RequestParam String lastName, @RequestParam String location, @RequestParam long id){
        Optional<InmateNoID> originalInmate = inmateNoIDRepository.findById(originalId);
        if (originalInmate.isPresent()){
            InmateNoID inmate = originalInmate.get();
            inmate.setFirstName(firstName);
            inmate.setLastName(lastName);
            inmate.setLocation(location);
            if (id != originalId){
                InmateNoID toClear = originalInmate.get();
                List<Package> packages = List.copyOf(toClear.getPackages());
                toClear.setPackages(new LinkedList<>());
                inmateNoIDRepository.save(toClear);
                inmateNoIDRepository.deleteById(originalId);
                inmate.setId(id);
                inmate.setPackages(packages);
                InmateNoID save = inmateNoIDRepository.save(inmate);
                return ResponseEntity.ok(save);
            }

            InmateNoID save = inmateNoIDRepository.save(inmate);
            return ResponseEntity.ok(save);
        }
        return ResponseEntity.noContent().build();
    }

}
