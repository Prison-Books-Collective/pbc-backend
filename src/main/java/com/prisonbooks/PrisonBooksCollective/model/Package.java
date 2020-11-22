package com.prisonbooks.PrisonBooksCollective.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Package {

    @Id
    @GeneratedValue
    private long id;

    @ManyToMany(cascade=CascadeType.ALL)
    private List<Book> books;

    @ManyToMany(cascade=CascadeType.ALL)
    private List<Zine> zines;

    @ManyToMany(cascade=CascadeType.ALL)
    private List<Resource> resources;

    @Temporal(TemporalType.DATE)
    @CreationTimestamp
    @Column(name = "date")
    private Date date;


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }
}
