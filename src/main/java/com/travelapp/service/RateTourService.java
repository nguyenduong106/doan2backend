package com.travelapp.service;


import com.travelapp.model.RateType;
import com.travelapp.model.Tour;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing RateType.
 */
public interface RateTourService {

    RateType save(RateType rateTypeDTO);
    
    /**
     * Get all the tours.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<RateType> findAlls(Pageable pageable);

    /**
     * Get all the Tour with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<RateType> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get all the rateTours.
     *
     * @return the list of entities
     */
    List<RateType> findAll();


    /**
     * Get the "id" rateTour.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<RateType> findOne(Long id);

    /**
     * Delete the "id" rateTour.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

	Collection<RateType> search(String query);
}
