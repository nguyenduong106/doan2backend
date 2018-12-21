package com.travelapp.controller;

import com.travelapp.controller.util.HeaderUtil;
import com.travelapp.controller.util.PaginationUtil;
import com.travelapp.exception.BadRequestException;
import com.travelapp.exception.ResourceNotFoundException;
import com.travelapp.model.Rate;
import com.travelapp.model.Tour;
import com.travelapp.payload.CommentDetailRequest;
import com.travelapp.payload.RateDetailRequest;
import com.travelapp.payload.TourDetailRequest;
import com.travelapp.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class RateController {
	@Autowired
    private ModelMapper modelMapper;
    private final Logger log = LoggerFactory.getLogger(BookingResource.class);

    private static final String ENTITY_NAME = "rating";
    @Autowired
    private  RateService rateService;
    @PostMapping("/rate")
    public ResponseEntity<Rate> createRating(@RequestBody Rate rateDTO) throws URISyntaxException {
        log.debug("REST request to save Rate : {}", rateDTO);
        if (rateDTO.getId() != null) {
            throw new BadRequestException("A new booking cannot already have an ID");
        }
        Rate result = rateService.save(rateDTO);
        return ResponseEntity.created(new URI("/api/rates/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    @PutMapping("/rate")
    public ResponseEntity<Rate> updateRating(@RequestBody Rate rateDTO) throws URISyntaxException {
        log.debug("REST request to update Rate : {}", rateDTO);
        if (rateDTO.getId() == null) {
            throw new BadRequestException("Invalid id");
        }
        Rate result = rateService.save(rateDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rateDTO.getId().toString()))
                .body(result);
    }

    @GetMapping("/rate")
    public List<Rate> getAllRates(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Bookings");
        return rateService.findAll();
    }
    
    @GetMapping(value = "/rates")
    public ResponseEntity<List<RateDetailRequest>> getAllRate(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Bookings");
        Page<Rate> page;
        if (eagerload) {

            page = rateService.findAllWithEagerRelationships(pageable);


        } else {
            page = rateService.findAlls(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/rates?eagerload=%b", eagerload));
        return new ResponseEntity<List<RateDetailRequest>>(page.getContent().stream().map(rate->converToRateDetail(rate)).collect(Collectors.toList()), headers, HttpStatus.OK);
    }

    @GetMapping("/rate/{id}")
    public ResponseEntity<Rate> getRating(@PathVariable Long id) {
        log.debug("REST request to get Rate : {}", id);
        Optional<Rate> rateDTO = rateService.findOne(id);
        if (rateDTO==null){
            throw new ResourceNotFoundException(ENTITY_NAME,"id",id);
        }
        return ResponseEntity.ok(rateDTO.get());
    }

    @DeleteMapping("/rate/{id}")
    public ResponseEntity<Void> deleteRate(@PathVariable Long id) {
        log.debug("REST request to delete Rate : {}", id);
        rateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    @GetMapping("/rate/tour/{tourId}")
    public ResponseEntity<?> getRateByTourId(@PathVariable Long tourId){
        log.debug("REST request to get List rate of tour using tourID");
        List<Rate> rates=rateService.findRateByTour(tourId);
        return new ResponseEntity<>(rates, HttpStatus.OK);
    }

    private RateDetailRequest converToRateDetail(Rate rate){
        RateDetailRequest rateDetailRequest=modelMapper.map(rate,RateDetailRequest.class);
        rateDetailRequest.setRateType(rate.getRateType().getRate());
        rateDetailRequest.setTour(rate.getTour().getName());
        rateDetailRequest.setUser(rate.getUser().getName());
        return rateDetailRequest;
    }
    @GetMapping("/admin/rate/search")
    public ResponseEntity<List<RateDetailRequest>> searchRate(@RequestParam(name="query",required = true)String query){
  
        return  new ResponseEntity<List<RateDetailRequest>>(rateService.search(query).stream().map(rate->converToRateDetail(rate)).collect(Collectors.toList()), null, HttpStatus.OK);
    }
}

