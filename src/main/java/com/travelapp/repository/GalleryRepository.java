package com.travelapp.repository;

import com.travelapp.model.Gallery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Gallery entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GalleryRepository extends JpaRepository<Gallery, Long> {
	 @Query(value = "select distinct gallery from Gallery gallery",
		        countQuery = "select count(distinct gallery) from Gallery gallery")
	Page<Gallery> findAllWithEagerRelationships(Pageable pageable);

}
