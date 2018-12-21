package com.travelapp.controller;


import com.travelapp.controller.util.HeaderUtil;
import com.travelapp.controller.util.PaginationUtil;
import com.travelapp.exception.BadRequestException;
import com.travelapp.exception.ResourceNotFoundException;
import com.travelapp.model.Provider;
import com.travelapp.model.Tour;
import com.travelapp.payload.CommentDetailRequest;
import com.travelapp.payload.ProviderDetailRequest;
import com.travelapp.payload.TourDetailRequest;
import com.travelapp.service.ProviderService;

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
 * REST controller for managing Provider.
 */
@RestController
@RequestMapping("/api")
public class ProviderResource {
	@Autowired
    private ModelMapper modelMapper;
    private final Logger log = LoggerFactory.getLogger(ProviderResource.class);

    private static final String ENTITY_NAME = "provider";

    private final ProviderService providerService;
    @Autowired
    public ProviderResource(ProviderService providerService) {
        this.providerService = providerService;
    }


    @PostMapping("/providers")
    public ResponseEntity<Provider> createProvider(@Valid @RequestBody Provider providerDTO) throws URISyntaxException {
        log.debug("REST request to save Provider : {}", providerDTO);
        if (providerDTO.getId() != null) {
            throw new BadRequestException("A new provider cannot already have an ID");
        }
        Provider result = providerService.save(providerDTO);
        return ResponseEntity.created(new URI("/api/providers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }


    @PutMapping("/providers")
    public ResponseEntity<Provider> updateProvider(@Valid @RequestBody Provider providerDTO) throws URISyntaxException {
        log.debug("REST request to update Provider : {}", providerDTO);
        if (providerDTO.getId() == null) {
            throw new BadRequestException("Invalid id");
        }
        Provider result = providerService.save(providerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, providerDTO.getId().toString()))
            .body(result);
    }


    @GetMapping("/providers")
    public List<Provider> getAllProviders() {
        log.debug("REST request to get all Providers");
        return providerService.findAll();
    }
    
    @GetMapping(value = "/provider")
    public ResponseEntity<List<ProviderDetailRequest>> getAllProvider(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Providers");
        Page<Provider> page;
        if (eagerload) {

            page = providerService.findAllWithEagerRelationships(pageable);


        } else {
            page = providerService.findAlls(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/providers?eagerload=%b", eagerload));
        return new ResponseEntity<List<ProviderDetailRequest>>(page.getContent().stream().map(provider->converToProviderDetail(provider)).collect(Collectors.toList()), headers, HttpStatus.OK);
    }

    @GetMapping("/providers/{id}")

    public ResponseEntity<Provider> getProvider(@PathVariable Long id) {
        log.debug("REST request to get Provider : {}", id);
        Optional<Provider> providerDTO = providerService.findOne(id);
        if (providerDTO==null)
            throw new ResourceNotFoundException(ENTITY_NAME,"id",id);
        return ResponseEntity.ok(providerDTO.get());
    }


    @DeleteMapping("/providers/{id}")
    public ResponseEntity<Void> deleteProvider(@PathVariable Long id) {
        log.debug("REST request to delete Provider : {}", id);
        providerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    @GetMapping("/admin/provider/search")
    public ResponseEntity<List<ProviderDetailRequest>> searchProvider(@RequestParam(name="query",required = true)String query){
  
        return  new ResponseEntity<List<ProviderDetailRequest>>(providerService.search(query).stream().map(provider->converToProviderDetail(provider)).collect(Collectors.toList()), null, HttpStatus.OK);
    }
    private ProviderDetailRequest converToProviderDetail(Provider provider){
        ProviderDetailRequest providerDetailRequest=modelMapper.map(provider,ProviderDetailRequest.class);
        return providerDetailRequest;
    }

}
