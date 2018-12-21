package com.travelapp.repository;

import com.travelapp.model.RateType;
import com.travelapp.model.Tour;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RateType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RateTypeRepository extends JpaRepository<RateType, Long> {
	@Query(value = "select distinct rateType from RateType rateType",
	        countQuery = "select count(distinct rateType) from RateType rateType")
	Page<RateType> findAllWithEagerRelationships(Pageable pageable);
	@Query("select distinct rateType from RateType rateType where rateType.rate like CONCAT('%',:query,'%')")
	Collection<RateType> search(String query);

}
