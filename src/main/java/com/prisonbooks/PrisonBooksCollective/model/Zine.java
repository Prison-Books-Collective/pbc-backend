package com.prisonbooks.PrisonBooksCollective.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Zine {

    @Id
    @GeneratedValue
    private long id;

    private String title;

    @Column(unique = true)
    private String threeLetterCode;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getThreeLetterCode() {
        return threeLetterCode;
    }

    public void setThreeLetterCode(String threeLetterCode) {
        this.threeLetterCode = threeLetterCode;
    }
}
