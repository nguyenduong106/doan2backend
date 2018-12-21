package com.travelapp.service;


import com.travelapp.model.Comment;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Comment.
 */
public interface CommentService {
	
	/**
     * Get all the tours.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Comment> findAlls(Pageable pageable);

    /**
     * Get all the Tour with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<Comment> findAllWithEagerRelationships(Pageable pageable);
    
    Comment save(Comment commentDTO);

    /**
     * Get all the comments.
     *
     * @return the list of entities
     */
    List<Comment> findAll();


    /**
     * Get the "id" comment.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Comment> findOne(Long id);

    /**
     * Delete the "id" comment.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

	Collection<Comment> search(String query);
}
