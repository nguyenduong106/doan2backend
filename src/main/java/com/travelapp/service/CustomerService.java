package com.travelapp.service;


import com.travelapp.model.Tour;
import com.travelapp.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Customer.
 */
public interface CustomerService {
	
	/**
     * Get all the tours.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<User> findAlls(Pageable pageable);

    /**
     * Get all the Tour with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<User> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Save a customer.
     *
     * @param customerDTO the entity to save
     * @return the persisted entity
     */
    User save(User customerDTO);

    /**
     * Get all the customers.
     *
     * @return the list of entities
     */
    List<User> findAll();


    /**
     * Get the "id" customer.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<User> findOne(Long id);

    /**
     * Delete the "id" customer.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

	Collection<User> search(String query);

}
