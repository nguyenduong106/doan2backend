package com.travelapp.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.travelapp.model.Tour;
import com.travelapp.payload.TourDetailRequest;

public interface TourService {


    Tour save(Tour tourDTO);

    /**
     * Get all the tours.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Tour> findAll(Pageable pageable);

    /**
     * Get all the Tour with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<Tour> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" tour.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Tour> findOne(Long id);

    /**
     * Delete the "id" tour.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
    List<Tour> getTourByCategoryId(long categoryId);
    List<Tour> findTourByName(String name);
    List<Tour> findTourByCategoryAndTimeStartAndTimeEnd(String category,String name, Date starttime, Date endTime);
    List<Tour> findTourByCategoryAndName(String category,String name);

	Collection<Tour> search(String query);
}
