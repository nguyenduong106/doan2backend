package com.travelapp.service;

import com.travelapp.model.Tour;
import com.travelapp.model.VehicleType;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface VehicleTypeService {
	/**
     * Get all the tours.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<VehicleType> findAlls(Pageable pageable);

    /**
     * Get all the Tour with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<VehicleType> findAllWithEagerRelationships(Pageable pageable);

    VehicleType save(VehicleType vehicleTypeDTO);

    List<VehicleType> findAll();


    Optional<VehicleType> findOne(Long id);


    void delete(Long id);

	Collection<VehicleType> search(String query);
}
