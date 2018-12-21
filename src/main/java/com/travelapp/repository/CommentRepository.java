package com.travelapp.repository;

import com.travelapp.model.Comment;
import com.travelapp.model.Tour;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Comment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
	@Query(value = "select distinct comment from Comment comment",
	        countQuery = "select count(distinct comment) from Comment comment")
	    Page<Comment> findAllWithEagerRelationships(Pageable pageable);
	@Query("select distinct comment from Comment comment where comment.tour.name like CONCAT('%',:query,'%') or comment.user.name like CONCAT('%',:query,'%')")
	Collection<Comment> search(String query);
}
