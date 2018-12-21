package com.travelapp.repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.travelapp.model.Tour;
import com.travelapp.payload.TourDetailRequest;

/**
 * Spring Data  repository for the Tour entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TourRepository extends JpaRepository<Tour, Long> {

    @Query(value = "select distinct tour from Tour tour left join fetch tour.rates left join fetch tour.locations left join  fetch tour.comments",
        countQuery = "select count(distinct tour) from Tour tour")
    Page<Tour> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct tour from Tour tour left join fetch tour.rates left join fetch tour.locations left join  fetch tour.comments" )
    List<Tour> findAllWithEagerRelationships();

    @Query("select tour from Tour tour left join fetch tour.rates left join fetch tour.locations left join  fetch tour.comments where tour.id =:id")
    Optional<Tour> findOneWithEagerRelationships(@Param("id") Long id);
    List<Tour> findAllByCategoryId(long categoryId);
    List<Tour> findByNameContainingIgnoreCase(String name);
    @Query("select distinct tour from Tour tour left join fetch tour.rates where tour.category.name like CONCAT('%',:category,'%') and tour.name like CONCAT('%',:name,'%') and tour.fromDate >= :fromDate and tour.toDate <= :todate")
    List<Tour> findAllWithSearch(@Param("category") String categoryName,@Param("name") String name, @Param("fromDate")Date fromDate,@Param("todate") Date toDate);
    @Query("select distinct tour from Tour tour left join fetch tour.rates where tour.category.name like CONCAT('%',:category,'%') and tour.name like CONCAT('%',:name,'%')")
    List<Tour> findByNameAndCategory(@Param("category") String category,@Param("name") String name);	
    @Query("select distinct tour from Tour tour left join fetch tour.rates where tour.category.name like CONCAT('%',:query,'%') or tour.name like CONCAT('%',:query,'%') or tour.vehicle.name like CONCAT('%',:query,'%') or tour.price like CONCAT('%',:query,'%')")
    List<Tour> search(@Param("query")String query);
}
