package com.prisonbooks.PrisonBooksCollective.controller;

import com.prisonbooks.PrisonBooksCollective.model.Package;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static com.prisonbooks.PrisonBooksCollective.model.Package.filterPackagesWithoutInmate;

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
                .orElseGet(() -> new ResponseEntity(null, HttpStatus.NO_CONTENT));
    }

    /**
     * Date is expected to be in format 'yyyy-mm-dd'
     */
    @GetMapping(path = "/getPackagesFromDate")
    public ResponseEntity<List<Package>> getPackagesFromDate(@RequestParam String date) {
        try {
            LocalDate dateObj = LocalDate.parse(date);
            List<Package> packages = filterPackagesWithoutInmate(packageRepository.findAllByDate(dateObj));
            return packages.isEmpty()
                    ? ResponseEntity.noContent().build()
                    : ResponseEntity.ok(packages);
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Dates are expected to be in format 'yyyy-mm-dd'
     */
    @GetMapping(path = "/getPackagesBetweenDates")
    public ResponseEntity<List<Package>> getPackagesBetweenDates(@RequestParam String startDate, @RequestParam String endDate) {
        try {
            LocalDate startDateObj = LocalDate.parse(startDate), endDateObj = LocalDate.parse(endDate);
            List<Package> packages = filterPackagesWithoutInmate(packageRepository.findAllBetweenDates(startDateObj, endDateObj));
            return packages.isEmpty()
                    ? ResponseEntity.noContent().build()
                    : ResponseEntity.ok(packages);
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().build();
        }
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

    @GetMapping(path = "/getPackagesByISBN")
    public ResponseEntity<List<Package>> getPackagesByISBN(@RequestParam String isbn) {
        try {
            if (isbn.length() != 10 && isbn.length() != 13) throw new NumberFormatException();
            Long.parseLong(isbn);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }

        List<Package> packages = filterPackagesWithoutInmate(packageRepository.findAllByISBN(isbn));
        return packages.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(packages);
    }

    @GetMapping(path = "/getPackagesByAuthorAndTitle")
    public ResponseEntity<List<Package>> getPackagesByAuthorAndTitle(
            @RequestParam String author,
            @RequestParam String title
    ) {
        List<Package> bookMatches = packageRepository.findAllByAuthorAndTitleContains(author, title);
        List<Package> noISBNMatches = packageRepository.findAllByNoISBNAuthorAndTitleContains(author, title);
        List<Package> matches = new ArrayList<>();

        matches.addAll(bookMatches);
        matches.addAll(noISBNMatches);
        matches = filterPackagesWithoutInmate(matches);

        return matches.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(matches);
    }

    @PutMapping(path = "/updatePackage")
    public ResponseEntity<Package> updatePackage(@RequestBody Package updatedPackage){
        Optional<Package> optionalPackage = packageRepository.findById(updatedPackage.getId());
        if (optionalPackage.isPresent()){
            Package originalPackage = updatedPackage;

            Package save = packageRepository.save(originalPackage);
            return ResponseEntity.ok(save);
        }
        return new ResponseEntity(null, HttpStatus.NO_CONTENT);
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
