package com.travelapp.repository;

import com.travelapp.model.Provider;
import com.travelapp.model.Tour;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;


/**
 * Spring Data  repository for the Provider entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProviderRepository extends JpaRepository<Provider, Long> {
    Provider getProviderByName(String providerName);
    @Query(value = "select distinct provider from Provider provider",
            countQuery = "select count(distinct provider) from Provider provider")
    Page<Provider> findAllWithEagerRelationships(Pageable pageable);
	@Query("select distinct provider from Provider provider where provider.name like CONCAT('%',:query,'%')")
	Collection<Provider> search(String query);
}
