package com.travelapp.repository;

import com.travelapp.model.Tour;
import com.travelapp.model.Vehicle;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Vehicle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
	@Query(value = "select distinct vehicle from Vehicle vehicle",
	        countQuery = "select count(distinct vehicle) from Vehicle vehicle")
	Page<Vehicle> findAllWithEagerRelationships(Pageable pageable);
	@Query("select distinct vehicle from Vehicle vehicle where vehicle.container like CONCAT('%',:query,'%') or vehicle.name like CONCAT('%',:query,'%') or vehicle.vehicleType.name like CONCAT('%',:query,'%')")
	Collection<Vehicle> search(String query);

}
