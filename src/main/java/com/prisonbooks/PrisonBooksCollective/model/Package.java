package com.prisonbooks.PrisonBooksCollective.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Package {

    @Id
    @GeneratedValue
    private long id;

    @ManyToMany
    private List<Book> books;

    @ManyToMany
    private List<Zine> zines;

    @ManyToMany
    private List<NoISBNBook> noISBNBooks;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @JsonManagedReference
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="inmate_id")
    private Inmate inmate;

    @JsonManagedReference
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="inmate_no_id_id")
    private InmateNoID inmateNoId;

    public Inmate getInmate() {
        return inmate;
    }

    public InmateNoID getInmateNoId() {
        return inmateNoId;
    }

    public void setInmate(Inmate inmate) {
        this.inmate = inmate;
    }

    public void setInmateNoId(InmateNoID inmateNoId) {
        this.inmateNoId = inmateNoId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Zine> getZines() {
        return zines;
    }

    public void setZines(List<Zine> zines) {
        this.zines = zines;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<NoISBNBook> getNoISBNBooks() {
        return noISBNBooks;
    }

    public void setNoISBNBooks(List<NoISBNBook> noISBNBooks) {
        this.noISBNBooks = noISBNBooks;
    }
}
