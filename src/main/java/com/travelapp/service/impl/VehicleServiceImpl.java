package com.travelapp.service.impl;

import com.travelapp.model.Vehicle;
import com.travelapp.repository.VehicleRepository;
import com.travelapp.service.VehicleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Vehicle.
 */
@Service
@Transactional
public class VehicleServiceImpl implements VehicleService {

    private final Logger log = LoggerFactory.getLogger(VehicleServiceImpl.class);

    private final VehicleRepository vehicleRepository;

    @Autowired
    public VehicleServiceImpl(VehicleRepository vehicleRepository ) {
        this.vehicleRepository = vehicleRepository;
    }


    @Override
    public Vehicle save(Vehicle vehicleDTO) {
        log.debug("Request to save Vehicle : {}", vehicleDTO);


        vehicleDTO = vehicleRepository.save(vehicleDTO);
        return vehicleDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Vehicle> findAll() {
        log.debug("Request to get all Vehicles");
        return vehicleRepository.findAll().stream()

            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one vehicle by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Vehicle> findOne(Long id) {
        log.debug("Request to get Vehicle : {}", id);
        return vehicleRepository.findById(id)
           ;
    }

    /**
     * Delete the vehicle by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Vehicle : {}", id);
        vehicleRepository.deleteById(id);
    }


	@Override
	public Page<Vehicle> findAlls(Pageable pageable) {
		log.debug("Request to get all Tours");
        return vehicleRepository.findAll(pageable);
	}


	@Override
	public Page<Vehicle> findAllWithEagerRelationships(Pageable pageable) {
		return vehicleRepository.findAllWithEagerRelationships(pageable);
	}


	@Override
	public Collection<Vehicle> search(String query) {
		return vehicleRepository.search(query);
	}
}
