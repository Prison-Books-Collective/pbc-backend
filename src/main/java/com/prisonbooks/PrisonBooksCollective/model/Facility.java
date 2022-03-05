package com.prisonbooks.PrisonBooksCollective.model;

import javax.persistence.*;

@Entity
public class Facility {

    @Id
    @GeneratedValue
    private long id;

    private String facility_name;

    @Enumerated(EnumType.STRING)
    private State state;

    @Enumerated(EnumType.STRING)
    private Type facility_type;

    public State getState() {
        return state;
    }

    public String getFacility_name() {
        return facility_name;
    }

    public long getId() {
        return id;
    }

    public Type getFacility_type() {
        return facility_type;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setFacility_name(String facility_name) {
        this.facility_name = facility_name;
    }

    public void setFacility_type(Type facility_type) {
        this.facility_type = facility_type;
    }

    public void setState(State state) {
        this.state = state;
    }
}
