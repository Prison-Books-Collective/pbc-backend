package com.prisonbooks.PrisonBooksCollective.controller;

import com.prisonbooks.PrisonBooksCollective.model.Book;
import com.prisonbooks.PrisonBooksCollective.model.Inmate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
public class BookController {

    @Autowired
    BookRepository bookRepository;

    public BookController(@Autowired BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    @PostMapping(path="/addBook")
    public ResponseEntity<Book> addNewBook(@RequestBody Book book){
        if (!getBookIsbn10(book.getIsbn10()).hasBody() && !getBookIsbn13(book.getIsbn13()).hasBody()){
            Book bookSaved = bookRepository.save(book);
            return ResponseEntity.ok(bookSaved);
        }
        return ResponseEntity.status(HttpStatus.FOUND).build();
    }

    @GetMapping(path="/getIsbn13StartsWith")
    public ResponseEntity<List<Book>> getBookIsbn13StartsWith(@RequestParam String startsWith){
        List<Book> books = bookRepository.findByIsbn13StartsWith(startsWith);
        return ResponseEntity.ok(books);
    }

    @GetMapping(path="/getIsbn10StartsWith")
    public ResponseEntity<List<Book>> getBookIsbn10StartsWith(@RequestParam String startsWith){
        List<Book> books = bookRepository.findByIsbn10StartsWith(startsWith);
        return ResponseEntity.ok(books);
    }

    @GetMapping(path="/getIsbn10")
    public ResponseEntity<Book> getBookIsbn10(@RequestParam String isbn10){
        return bookRepository.findByIsbn10(isbn10)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @GetMapping(path="/getIsbn13")
    public ResponseEntity<Book> getBookIsbn13(@RequestParam String isbn13){
        return bookRepository.findByIsbn13(isbn13)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @DeleteMapping(path = "/deleteBook")
    public  HttpStatus deleteBook(@RequestParam long id){
        if (bookRepository.existsById(id)){
            bookRepository.deleteById(id);
            return HttpStatus.OK;
        } else {
            return HttpStatus.NO_CONTENT;
        }
    }

    @PutMapping(path = "/updateIsbn13")
    public ResponseEntity<Book> updateIsbn13(@RequestParam long id, @RequestParam String isbn13){
        Optional<Book> bookOptional = bookRepository.findById(id);
        if (bookOptional.isPresent()){
            Book book = bookOptional.get();
            book.setIsbn13(isbn13);
            Book savedBook = bookRepository.save(book);
            return  ResponseEntity.ok(savedBook);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping(path = "/updateIsbn10")
    public ResponseEntity<Book> updateIsbn10(@RequestParam long id, @RequestParam String isbn10){
        Optional<Book> bookOptional = bookRepository.findById(id);
        if (bookOptional.isPresent()){
            Book book = bookOptional.get();
            book.setIsbn10(isbn10);
            Book savedBook = bookRepository.save(book);
            return  ResponseEntity.ok(savedBook);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping(path = "/updateBook")
    public ResponseEntity<Book> updateBook(@RequestBody Book book){
        Book savedBook;
        Book originalBook;
        if (book.getIsbn10().substring(0,2).equals("NO")){
            Optional<Book> byIsbn13 = bookRepository.findByIsbn13(book.getIsbn13());
            originalBook = byIsbn13.get();
        }else{
            Optional<Book> byIsbn10 = bookRepository.findByIsbn10(book.getIsbn10());
            originalBook = byIsbn10.get();
        }
        originalBook.setAuthors(book.getAuthors());
        originalBook.setTitle(book.getTitle());
        savedBook = bookRepository.save(originalBook);
        return  ResponseEntity.ok(savedBook);
    }


    @GetMapping(path = "/getBooksTitleContaining")
    public ResponseEntity<List<Book>> getBooksWithTitleContaining(@RequestParam String targetString){
        List<Book> byTitleContaining = bookRepository.findByTitleContaining(targetString);
        return ResponseEntity.ok(byTitleContaining);
    }

    @GetMapping(path = "/getAuthorsContaining")
    public ResponseEntity<List<Book>> getBooksWithAuthorsContaining(@RequestParam String targetString){
        List<Book> byAuthorsContaining = bookRepository.findByAuthorContaining(targetString);
        return ResponseEntity.ok(byAuthorsContaining);
    }

    @GetMapping(path = "/getBooksIsbn10Containing")
    public ResponseEntity<List<Book>> getBooksWithIsbn10Containing(@RequestParam String targetString){
        List<Book> byIsbn10Containing = bookRepository.findByIsbn10Containing(targetString);
        return ResponseEntity.ok(byIsbn10Containing);
    }

    @GetMapping(path = "/getBooksIsbn13Containing")
    public ResponseEntity<List<Book>> getBooksWithIsbn13Containing(@RequestParam String targetString){
        List<Book> byIsbn13Containing = bookRepository.findByIsbn13Containing(targetString);
        return ResponseEntity.ok(byIsbn13Containing);
    }

    @GetMapping(path = "/getBooksTitle")
    public ResponseEntity<List<Book>> getBooksWithTitle(@RequestParam String targetString){
        List<Book> byTitle = bookRepository.findByTitle(targetString);
        return ResponseEntity.ok(byTitle);
    }
}
