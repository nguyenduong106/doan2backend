package com.travelapp.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Location.
 */
@Entity
@Table(name = "location")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Location implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 2)
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Size(min = 2)
    @Column(name = "description", nullable = false)
    private String description;
    @OneToMany(mappedBy = "location",fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Gallery> galleries = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("locations")
    private Provider provider;

    @ManyToMany(mappedBy = "locations")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Tour> tours = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Location name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Location description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Gallery> getGalleries() {
        return galleries;
    }

    public Location galleries(Set<Gallery> galleries) {
        this.galleries = galleries;
        return this;
    }

    public Location addGallery(Gallery gallery) {
        this.galleries.add(gallery);
        gallery.setLocation(this);
        return this;
    }

    public Location removeGallery(Gallery gallery) {
        this.galleries.remove(gallery);
        gallery.setLocation(null);
        return this;
    }

    public void setGalleries(Set<Gallery> galleries) {
        this.galleries = galleries;
    }

    public Provider getProvider() {
        return provider;
    }

    public Location provider(Provider provider) {
        this.provider = provider;
        return this;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public Set<Tour> getTours() {
        return tours;
    }

    public Location tours(Set<Tour> tours) {
        this.tours = tours;
        return this;
    }

    public Location addTour(Tour tour) {
        this.tours.add(tour);
        tour.getLocations().add(this);
        return this;
    }

    public Location removeTour(Tour tour) {
        this.tours.remove(tour);
        tour.getLocations().remove(this);
        return this;
    }

    public void setTours(Set<Tour> tours) {
        this.tours = tours;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Location location = (Location) o;
        if (location.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), location.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }


}
