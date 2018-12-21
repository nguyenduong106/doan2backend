package com.travelapp.controller;

import com.travelapp.controller.util.HeaderUtil;
import com.travelapp.controller.util.PaginationUtil;
import com.travelapp.exception.BadRequestException;
import com.travelapp.exception.ResourceNotFoundException;
import com.travelapp.model.Tour;
import com.travelapp.model.User;
import com.travelapp.payload.TourDetailRequest;
import com.travelapp.payload.TourDetailRequests;
import com.travelapp.payload.UserDetailRequest;
import com.travelapp.service.CustomerService;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Customer.
 */
@RestController
@RequestMapping("/api")
public class CustomerResource {

	@Autowired
    private ModelMapper modelMapper;
    private final Logger log = LoggerFactory.getLogger(CustomerResource.class);

    private static final String ENTITY_NAME = "customer";

    private final CustomerService customerService;
    @Autowired
    public CustomerResource(CustomerService customerService) {
        this.customerService = customerService;
    }


    @PostMapping("/customers")
    public ResponseEntity<User> createCustomer(@Valid @RequestBody User customerDTO) throws URISyntaxException {
        log.debug("REST request to save Customer : {}", customerDTO);
        if (customerDTO.getId() != null) {
            throw new BadRequestException("A new customer cannot already have an ID");
        }
        User result = customerService.save(customerDTO);
        return ResponseEntity.created(new URI("/api/customers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }


    @PutMapping("/customers")
    public ResponseEntity<User> updateCustomer(@Valid @RequestBody User customerDTO) throws URISyntaxException {
        log.debug("REST request to update Customer : {}", customerDTO);
        if (customerDTO.getId() == null) {
            throw new BadRequestException("Invalid id");
        }
        User result = customerService.save(customerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, customerDTO.getId().toString()))
            .body(result);
    }

    @GetMapping("/customers")
    public List<User> getAllCustomers() {
        log.debug("REST request to get all Customers");
        return customerService.findAll();
    }


    @GetMapping("/customers/{id}")
    public ResponseEntity<User> getCustomer(@PathVariable Long id) {
        log.debug("REST request to get Customer : {}", id);
        Optional<User> customerDTO = customerService.findOne(id);
        if (customerDTO==null)
            throw new ResourceNotFoundException(ENTITY_NAME,"id",id);
        return ResponseEntity.ok(customerDTO.get());
    }

    @GetMapping(value = "/customer")
    public ResponseEntity<List<UserDetailRequest>> getAllUser(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Users");
        Page<User> page;
        if (eagerload) {

            page = customerService.findAllWithEagerRelationships(pageable);


        } else {
            page = customerService.findAlls(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/tours?eagerload=%b", eagerload));
        return new ResponseEntity<List<UserDetailRequest>>(page.getContent().stream().map(user->converToUserDetail(user)).collect(Collectors.toList()), headers, HttpStatus.OK);
    }
    
    @DeleteMapping("/customers/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        log.debug("REST request to delete Customer : {}", id);
        customerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    @GetMapping("/admin/customer/search")
    public ResponseEntity<List<UserDetailRequest>> searchCustomner(@RequestParam(name="query",required = true)String query){
  
        return  new ResponseEntity<List<UserDetailRequest>>(customerService.search(query).stream().map(user->converToUserDetail(user)).collect(Collectors.toList()), null, HttpStatus.OK);
    }
    private UserDetailRequest converToUserDetail(User user){
        UserDetailRequest userDetailRequest=modelMapper.map(user,UserDetailRequest.class);
        return userDetailRequest;
    }
}
