package com.travelapp.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Provider.
 */
@Entity
@Table(name = "provider")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Provider implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 2)
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "provider")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Location> locations = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Provider name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Location> getLocations() {
        return locations;
    }

    public Provider locations(Set<Location> locations) {
        this.locations = locations;
        return this;
    }

    public Provider addLocation(Location location) {
        this.locations.add(location);
        location.setProvider(this);
        return this;
    }

    public Provider removeLocation(Location location) {
        this.locations.remove(location);
        location.setProvider(null);
        return this;
    }

    public void setLocations(Set<Location> locations) {
        this.locations = locations;
    }





    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Provider provider = (Provider) o;
        if (provider.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), provider.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }


}
