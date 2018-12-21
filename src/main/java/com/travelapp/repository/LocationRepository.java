package com.travelapp.repository;

import com.travelapp.model.Location;
import com.travelapp.model.Tour;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Location entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
	@Query(value = "select distinct location from Location location",
	        countQuery = "select count(distinct location) from Location location")
	    Page<Location> findAllWithEagerRelationships(Pageable pageable);
	@Query("select distinct location from Location location where location.name like CONCAT('%',:query,'%') or location.provider.name like CONCAT('%',:query,'%')")
	Collection<Location> search(String query);
}
