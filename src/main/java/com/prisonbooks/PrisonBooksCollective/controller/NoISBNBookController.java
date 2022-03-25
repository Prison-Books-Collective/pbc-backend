package com.prisonbooks.PrisonBooksCollective.controller;

import com.prisonbooks.PrisonBooksCollective.model.NoISBNBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin
@RestController
public class NoISBNBookController {

    @Autowired
    NoISBNBookRepository noISBNBookRepository;

    public NoISBNBookController(@Autowired NoISBNBookRepository noISBNBookRepository){
        this.noISBNBookRepository = noISBNBookRepository;
    }

    @PostMapping(path="/addNoISBNBook")
    public ResponseEntity<NoISBNBook> addNoISBNBook(@RequestBody NoISBNBook noISBNBook){
        NoISBNBook save = noISBNBookRepository.save(noISBNBook);
        return ResponseEntity.ok(save);
    }

    @PutMapping(path="/updateNoISBNBook")
    public ResponseEntity<NoISBNBook> updateNoISBNBook(@RequestBody NoISBNBook noISBNBook) {
        Optional<NoISBNBook> optional = noISBNBookRepository.findById(noISBNBook.getId());
        if (optional.isPresent()) {
            NoISBNBook actual = optional.get();
            actual.setTitle(noISBNBook.getTitle());
            actual.setAuthors(noISBNBook.getAuthors());
            NoISBNBook save = noISBNBookRepository.save(actual);
            return ResponseEntity.ok(save);
        }
        return ResponseEntity.noContent().build();
    }
}
