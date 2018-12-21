package com.travelapp.service;


import com.travelapp.model.Tour;
import com.travelapp.model.Vehicle;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VehicleService {

	/**
     * Get all the tours.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Vehicle> findAlls(Pageable pageable);

    /**
     * Get all the Tour with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<Vehicle> findAllWithEagerRelationships(Pageable pageable);

    Vehicle save(Vehicle vehicleDTO);

    List<Vehicle> findAll();


    Optional<Vehicle> findOne(Long id);


    void delete(Long id);

	Collection<Vehicle> search(String query);
}
