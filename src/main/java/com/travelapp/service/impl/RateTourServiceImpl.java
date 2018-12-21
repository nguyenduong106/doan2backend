package com.travelapp.service.impl;

import com.travelapp.model.RateType;
import com.travelapp.repository.RateTypeRepository;
import com.travelapp.service.RateTourService;
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
 * Service Implementation for managing RateType.
 */
@Service
@Transactional
public class RateTourServiceImpl implements RateTourService {

    private final Logger log = LoggerFactory.getLogger(RateTourServiceImpl.class);

    private final RateTypeRepository rateTypeRepository;

    @Autowired
    public RateTourServiceImpl(RateTypeRepository rateTypeRepository) {
        this.rateTypeRepository = rateTypeRepository;
    }

    /**
     * Save a rateTour.
     *
     * @param rateTypeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RateType save(RateType rateTypeDTO) {
        log.debug("Request to save RateType : {}", rateTypeDTO);

        rateTypeDTO = rateTypeRepository.save(rateTypeDTO);
        return rateTypeDTO;
    }

    /**
     * Get all the rateTours.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<RateType> findAll() {
        log.debug("Request to get all RateTours");
        return rateTypeRepository.findAll().stream()

            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one rateTour by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RateType> findOne(Long id) {
        log.debug("Request to get RateType : {}", id);
        return rateTypeRepository.findById(id)
            ;
    }

    /**
     * Delete the rateTour by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RateType : {}", id);
        rateTypeRepository.deleteById(id);
    }

	@Override
	public Page<RateType> findAlls(Pageable pageable) {
		log.debug("Request to get all RateTypes");
        return rateTypeRepository.findAll(pageable);
	}

	@Override
	public Page<RateType> findAllWithEagerRelationships(Pageable pageable) {
		return rateTypeRepository.findAllWithEagerRelationships(pageable);
	}

	@Override
	public Collection<RateType> search(String query) {
		return rateTypeRepository.search(query);
	}
}
