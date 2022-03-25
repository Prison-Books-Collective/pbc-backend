package com.prisonbooks.PrisonBooksCollective.controller;

import com.prisonbooks.PrisonBooksCollective.model.Package;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
public class PackageController {

    @Autowired
    PackageRepository packageRepository;

    public PackageController(@Autowired PackageRepository packageRepository){
        this.packageRepository = packageRepository;
    }

    @GetMapping(path="/getPackageById")
    public ResponseEntity<Package> getPackageById(@RequestParam long id) {
        return packageRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @GetMapping(path="/getPackagesFromDate")
    public ResponseEntity<List<Package>> getPackagesFromDate(@RequestParam String date) {
        LocalDate dateObj = LocalDate.parse(date);
        List<Package> byDate = packageRepository.findAllByDate(dateObj);
        return ResponseEntity.ok(byDate);
    }

    @GetMapping(path="/getPackageCountFromDate")
    public ResponseEntity<Long> getPackageCountFromDate(@RequestParam String date) {
        LocalDate dateObj = LocalDate.parse(date);
        Long count = packageRepository.countByDate(dateObj);
        return ResponseEntity.ok(count);
    }

    @GetMapping(path="/getAllPackages")
    public ResponseEntity<List<Package>> getAllPackages() {
        Iterable<Package> all = packageRepository.findAll();
        List<Package> packages = new LinkedList<>();
        all.forEach(packages::add);

        return ResponseEntity.ok(packages);
    }

    @GetMapping(path="/getPackagesForInmate")
    public ResponseEntity<List<Package>> getPackagesForInmate(@RequestParam String inmateId) {

        List<Package> allByInmateId = packageRepository.findAllByInmateId(inmateId);

        return ResponseEntity.ok(allByInmateId);
    }

    @GetMapping(path="/getPackagesForInmateNoId")
    public ResponseEntity<List<Package>> getPackagesForInmate(@RequestParam long inmateId) {

        List<Package> allByInmateNoId = packageRepository.findAllByInmateNoId(inmateId);

        return ResponseEntity.ok(allByInmateNoId);
    }

    @PutMapping(path = "/updatePackage")
    public ResponseEntity<Package> updatePackage(@RequestBody Package updatedPackage){
        Optional<Package> optionalPackage = packageRepository.findById(updatedPackage.getId());
        if (optionalPackage.isPresent()){
            Package save = packageRepository.save(updatedPackage);
            return ResponseEntity.ok(save);
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping(path="/addPackage")
    public ResponseEntity<Package> addPackage(@RequestBody Package packageForInmate){
        Package savedPackage = packageRepository.save(packageForInmate);
        return ResponseEntity.ok(savedPackage);
    }

    @DeleteMapping(path = "/deletePackage")
    public HttpStatus deletePackage(@RequestParam long packageId){
        packageRepository.deleteById(packageId);
        if (packageRepository.findById(packageId).isPresent()){
            return HttpStatus.EXPECTATION_FAILED;
        }else {
            return HttpStatus.OK;
        }
    }
}
