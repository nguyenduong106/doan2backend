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
 * A VehicleType.
 */
@Entity
@Table(name = "vehicle_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class VehicleType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "price", nullable = false)
    private Integer price;

    @OneToMany(mappedBy = "vehicleType")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Vehicle> vehicles = new HashSet<>();
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

    public VehicleType name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public VehicleType price(Integer price) {
        this.price = price;
        return this;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Set<Vehicle> getVehicles() {
        return vehicles;
    }

    public VehicleType vehicles(Set<Vehicle> vehicles) {
        this.vehicles = vehicles;
        return this;
    }

    public VehicleType addVehicle(Vehicle vehicle) {
        this.vehicles.add(vehicle);
        vehicle.setVehicleType(this);
        return this;
    }

    public VehicleType removeVehicle(Vehicle vehicle) {
        this.vehicles.remove(vehicle);
        vehicle.setVehicleType(null);
        return this;
    }

    public void setVehicles(Set<Vehicle> vehicles) {
        this.vehicles = vehicles;
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
        VehicleType vehicleType = (VehicleType) o;
        if (vehicleType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vehicleType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }


}
