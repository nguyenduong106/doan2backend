package com.travelapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.travelapp.model.Role;
import com.travelapp.model.RoleName;



@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
	Optional<Role> findByName(RoleName roleName);
}
