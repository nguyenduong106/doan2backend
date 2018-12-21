package com.travelapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "category")
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @OneToMany(mappedBy = "category",fetch = FetchType.LAZY)
    private Set<Tour> tours=new HashSet<Tour>();

    public Set<Tour> getTours() {
        return tours;
    }

    public void setTours(Set<Tour> tours) {
        this.tours = tours;
    }

    public Category addTour(Tour tour) {
        this.tours.add(tour);
        tour.setCategory(this);
        return this;
    }

    public Category removeTour(Tour tour) {
        this.tours.remove(tour);
        tour.setCategory(null);
        return this;
    }
}
