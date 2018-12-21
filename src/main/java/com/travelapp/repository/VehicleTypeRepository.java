package com.travelapp.repository;

import com.travelapp.model.Tour;
import com.travelapp.model.VehicleType;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
@Repository
public interface VehicleTypeRepository extends JpaRepository<VehicleType, Long> {
	@Query(value = "select distinct vehicleType from VehicleType vehicleType",
	        countQuery = "select count(distinct vehicleType) from VehicleType vehicleType")
	Page<VehicleType> findAllWithEagerRelationships(Pageable pageable);
	@Query("select distinct vehicleType from VehicleType vehicleType where vehicleType.name like CONCAT('%',:query,'%') or vehicleType.price like CONCAT('%',:query,'%')")
	Collection<VehicleType> search(String query);
}
