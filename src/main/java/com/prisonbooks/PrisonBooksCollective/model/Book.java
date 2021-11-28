package com.prisonbooks.PrisonBooksCollective.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Book {


    public Book(){

    }

    @Id
    @GeneratedValue
    private long id;

    @Column(unique = true)
    private String isbn10;

    @Column(unique = true)
    private String isbn13;

    private String title;

    @ElementCollection
    private List<String> authors;

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn10() {
        return isbn10;
    }

    public void setIsbn10(String isbn) {
        this.isbn10 = isbn;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
