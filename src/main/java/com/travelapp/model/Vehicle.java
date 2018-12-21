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
 * A Vehicle.
 */
@Entity
@Table(name = "vehicle")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Vehicle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "container")
    private Integer container;

    @OneToMany(mappedBy = "vehicle")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Tour> tours = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("vehicles")
    private VehicleType vehicleType;

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

    public Vehicle name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isStatus() {
        return status;
    }

    public Vehicle status(Boolean status) {
        this.status = status;
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getContainer() {
        return container;
    }

    public Vehicle container(Integer container) {
        this.container = container;
        return this;
    }

    public void setContainer(Integer container) {
        this.container = container;
    }

    public Set<Tour> getTours() {
        return tours;
    }

    public Vehicle tours(Set<Tour> tours) {
        this.tours = tours;
        return this;
    }

    public Vehicle addTour(Tour tour) {
        this.tours.add(tour);
        tour.setVehicle(this);
        return this;
    }

    public Vehicle removeTour(Tour tour) {
        this.tours.remove(tour);
        tour.setVehicle(null);
        return this;
    }

    public void setTours(Set<Tour> tours) {
        this.tours = tours;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public Vehicle vehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
        return this;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
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
        Vehicle vehicle = (Vehicle) o;
        if (vehicle.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vehicle.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }


}
