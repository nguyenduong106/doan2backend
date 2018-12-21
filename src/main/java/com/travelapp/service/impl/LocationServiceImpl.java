package com.travelapp.service.impl;

import com.travelapp.model.Location;
import com.travelapp.repository.LocationRepository;
import com.travelapp.service.LocationService;
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
 * Service Implementation for managing Location.
 */
@Service
@Transactional
public class LocationServiceImpl implements LocationService {

    private final Logger log = LoggerFactory.getLogger(LocationServiceImpl.class);

    private final LocationRepository locationRepository;

    @Autowired
    public LocationServiceImpl(LocationRepository locationRepository ) {
        this.locationRepository = locationRepository;
    }

    /**
     * Save a location.
     *
     * @param locationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public Location save(Location locationDTO) {
        log.debug("Request to save Location : {}", locationDTO);

        locationDTO = locationRepository.save(locationDTO);
        return locationDTO;
    }

    /**
     * Get all the locations.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Location> findAll() {
        log.debug("Request to get all Locations");
        return locationRepository.findAll().stream()

            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one location by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Location> findOne(Long id) {
        log.debug("Request to get Location : {}", id);
        return locationRepository.findById(id);
    }

    /**
     * Delete the location by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Location : {}", id);
        locationRepository.deleteById(id);
    }

	@Override
	public Page<Location> findAlls(Pageable pageable) {
		log.debug("Request to get all Locations");
        return locationRepository.findAll(pageable);
	}

	@Override
	public Page<Location> findAllWithEagerRelationships(Pageable pageable) {
		return locationRepository.findAllWithEagerRelationships(pageable);
	}

	@Override
	public Collection<Location> search(String query) {
		return locationRepository.search(query);
	}
}
