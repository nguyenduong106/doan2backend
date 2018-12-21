package com.travelapp.service;


import com.travelapp.model.Provider;
import com.travelapp.model.Tour;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Provider.
 */
public interface ProviderService {


    Provider save(Provider providerDTO);
    
    /**
     * Get all the tours.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Provider> findAlls(Pageable pageable);

    /**
     * Get all the Tour with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<Provider> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get all the providers.
     *
     * @return the list of entities
     */
    List<Provider> findAll();


    /**
     * Get the "id" provider.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Provider> findOne(Long id);

    /**
     * Delete the "id" provider.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

	Collection<Provider> search(String query);
}
