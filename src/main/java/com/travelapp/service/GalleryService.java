package com.travelapp.service;


import com.travelapp.model.Gallery;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Gallery.
 */
public interface GalleryService {


    Gallery save(Gallery galleryDTO);

    /**
     * Get all the galleries.
     *
     * @return the list of entities
     */
    List<Gallery> findAll();


    /**
     * Get the "id" gallery.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Gallery> findOne(Long id);

    /**
     * Delete the "id" gallery.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

	Page<Gallery> findAllWithEagerRelationships(Pageable pageable);

	Page<Gallery> findAlls(Pageable pageable);
}
