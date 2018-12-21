package com.travelapp.repository;

import com.travelapp.model.Rate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface RateRepository extends JpaRepository<Rate,Long> {
    @Query(value = "select distinct rate from Rate rate left join fetch rate.rateType")
    List<Rate> findAllWithEagerRelationships();
    @Query(value = "select distinct rate from Rate rate",
            countQuery = "select count(distinct rate) from Rate rate")
    Page<Rate> findAllWithEagerRelationships(Pageable pageable);
    @Query("select distinct rate from Rate rate where rate.tour.name like CONCAT('%',:query,'%') or rate.user.name like CONCAT('%',:query,'%') or rate.rateType.rate like CONCAT('%',:query,'%')")
	List<Rate> search(String query);
}
