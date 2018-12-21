package com.travelapp.service;


import com.travelapp.model.Location;
import com.travelapp.model.Tour;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LocationService {
	
	/**
     * Get all the tours.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Location> findAlls(Pageable pageable);

    /**
     * Get all the Tour with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<Location> findAllWithEagerRelationships(Pageable pageable);


    Location save(Location locationDTO);

    /**
     * Get all the locations.
     *
     * @return the list of entities
     */
    List<Location> findAll();


    /**
     * Get the "id" location.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Location> findOne(Long id);

    /**
     * Delete the "id" location.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

	Collection<Location> search(String query);
}
