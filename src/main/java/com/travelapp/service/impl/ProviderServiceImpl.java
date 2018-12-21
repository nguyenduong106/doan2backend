package com.travelapp.service.impl;


import com.travelapp.model.Provider;
import com.travelapp.repository.ProviderRepository;
import com.travelapp.service.ProviderService;
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
 * Service Implementation for managing Provider.
 */
@Service
@Transactional
public class ProviderServiceImpl implements ProviderService {

    private final Logger log = LoggerFactory.getLogger(ProviderServiceImpl.class);

    private final ProviderRepository providerRepository;

    @Autowired
    public ProviderServiceImpl(ProviderRepository providerRepository ) {
        this.providerRepository = providerRepository;

    }

    /**
     * Save a provider.
     *
     * @param providerDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public Provider save(Provider providerDTO) {
        log.debug("Request to save Provider : {}", providerDTO);

        providerDTO = providerRepository.save(providerDTO);
        return providerDTO;
    }

    /**
     * Get all the providers.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Provider> findAll() {
        log.debug("Request to get all Providers");
        return providerRepository.findAll().stream()

            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one provider by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Provider> findOne(Long id) {
        log.debug("Request to get Provider : {}", id);
        return providerRepository.findById(id)
       ;
    }

    /**
     * Delete the provider by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Provider : {}", id);
        providerRepository.deleteById(id);
    }

	@Override
	public Page<Provider> findAlls(Pageable pageable) {
		log.debug("Request to get all Providers");
        return providerRepository.findAll(pageable);
	}

	@Override
	public Page<Provider> findAllWithEagerRelationships(Pageable pageable) {
		return providerRepository.findAllWithEagerRelationships(pageable);
	}

	@Override
	public Collection<Provider> search(String query) {
		return providerRepository.search(query);
	}
}
