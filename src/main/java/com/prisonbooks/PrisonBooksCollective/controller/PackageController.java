package com.prisonbooks.PrisonBooksCollective.controller;

import com.prisonbooks.PrisonBooksCollective.model.Book;
import com.prisonbooks.PrisonBooksCollective.model.NoISBNBook;
import com.prisonbooks.PrisonBooksCollective.model.Package;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

import static com.prisonbooks.PrisonBooksCollective.model.Package.filterPackagesWithoutInmate;

@CrossOrigin
@RestController
public class PackageController {

    @Autowired
    PackageRepository packageRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    NoISBNBookRepository noISBNBookRepository;

    public PackageController(
            @Autowired PackageRepository packageRepository,
            @Autowired BookRepository bookRepository,
            @Autowired NoISBNBookRepository noISBNBookRepository
    ){
        this.packageRepository = packageRepository;
        this.bookRepository = bookRepository;
        this.noISBNBookRepository = noISBNBookRepository;
    }

    @GetMapping(path="/getPackageById")
    public ResponseEntity<Package> getPackageById(@RequestParam long id) {
        return packageRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity(null, HttpStatus.NO_CONTENT));
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

    @GetMapping(path = "/getPackagesByISBN")
    public ResponseEntity<List<Package>> getPackagesByISBN(@RequestParam String isbn) {
        try {
            if (isbn.length() != 10 && isbn.length() != 13) throw new NumberFormatException();
            Long.parseLong(isbn);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Book> book = isbn.length() == 10
                ? bookRepository.findByIsbn10(isbn)
                : bookRepository.findByIsbn13(isbn);
        if (book.isEmpty()) return ResponseEntity.noContent().build();

        List<Package> packages = filterPackagesWithoutInmate(packageRepository.findAllByBooks(book.get()));
        return packages.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(packages);
    }

    @GetMapping(path = "/getPackagesByAuthorAndTitle")
    public ResponseEntity<List<Package>> getPackagesByAuthorAndTitle(
            @RequestParam String author,
            @RequestParam String title
    ) {
        Set<Package> allMatches = new HashSet<>();
        List<Package> bookMatches = packageRepository.findAllByAuthorAndTitleContains(author, title);
        List<Package> noISBNMatches = packageRepository.findAllByNoISBNAuthorAndTitleContains(author, title);

        allMatches.addAll(bookMatches);
        allMatches.addAll(noISBNMatches);

        List<Package> matches = filterPackagesWithoutInmate(allMatches);

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
