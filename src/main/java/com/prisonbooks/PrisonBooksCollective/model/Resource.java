package com.prisonbooks.PrisonBooksCollective.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Resource {

    @GeneratedValue
    @Id
    public long id;

    public String title;

    public String details;

    
}
