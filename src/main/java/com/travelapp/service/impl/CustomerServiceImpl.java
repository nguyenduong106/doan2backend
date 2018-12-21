package com.travelapp.service.impl;

import com.travelapp.model.User;
import com.travelapp.repository.UserRepository;
import com.travelapp.service.CustomerService;
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
 * Service Implementation for managing Customer.
 */
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private final UserRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(UserRepository customerRepository ) {
        this.customerRepository = customerRepository;
    }

    /**
     * Save a customer.
     *
     * @param customerDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public User save(User customerDTO) {
        log.debug("Request to save Customer : {}", customerDTO);

        customerDTO = customerRepository.save(customerDTO);
        return customerDTO;
    }

    /**
     * Get all the customers.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        log.debug("Request to get all Customers");
        return customerRepository.findAll().stream()

            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one customer by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<User> findOne(Long id) {
        log.debug("Request to get Customer : {}", id);
        return customerRepository.findById(id)
            ;
    }

    /**
     * Delete the customer by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Customer : {}", id);
        customerRepository.deleteById(id);
    }

	@Override
	public Page<User> findAlls(Pageable pageable) {
		log.debug("Request to get all Users");
        return customerRepository.findAll(pageable)
         ;
	}

	@Override
	public Page<User> findAllWithEagerRelationships(Pageable pageable) {
		return customerRepository.findAllWithEagerRelationships(pageable);
	}

	@Override
	public Collection<User> search(String query) {
		return customerRepository.search(query);
	}
}
