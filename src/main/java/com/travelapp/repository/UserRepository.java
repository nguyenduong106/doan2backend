package com.travelapp.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.travelapp.model.Tour;
import com.travelapp.model.User;


@Repository
public interface UserRepository extends JpaRepository<User , Long> {
	Optional<User> findByEmail(String email);

	Optional<User> findByUsernameOrEmail(String username, String email);

	List<User> findByIdIn(List<Long> userIds);

	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
	
	@Query(value = "select distinct users from User users",
	        countQuery = "select count(distinct users) from User users")
	Page<User> findAllWithEagerRelationships(Pageable pageable);
	@Query("select distinct user from User user where user.username like CONCAT('%',:query,'%') or user.address like CONCAT('%',:query,'%') or user.gender like CONCAT('%',:query,'%') or user.name like CONCAT('%',:query,'%') or user.email like CONCAT('%',:query,'%')  or user.phone like CONCAT('%',:query,'%')")
	Collection<User> search(String query);

}
