package com.travelapp.service;

import com.travelapp.model.Rate;
import com.travelapp.model.Tour;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RateService {
	
	/**
     * Get all the tours.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Rate> findAlls(Pageable pageable);

    /**
     * Get all the Tour with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<Rate> findAllWithEagerRelationships(Pageable pageable);
    
    Rate save(Rate rate);

    List<Rate> findAll();


    Optional<Rate> findOne(Long id);

    List<Rate> findRateByTour(Long tourId);

    void delete(Long id);

	Collection<Rate> search(String query);
}
