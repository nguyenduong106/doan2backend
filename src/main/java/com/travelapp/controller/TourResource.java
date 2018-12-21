package com.travelapp.controller;

import com.travelapp.controller.util.HeaderUtil;
import com.travelapp.controller.util.PaginationUtil;
import com.travelapp.exception.BadRequestException;
import com.travelapp.exception.ResourceNotFoundException;
import com.travelapp.model.Location;
import com.travelapp.model.Tour;
import com.travelapp.payload.HomeTourPayload;
import com.travelapp.payload.TourDetailRequest;
import com.travelapp.payload.TourDetailRequests;
import com.travelapp.service.TourService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class TourResource {
    @Autowired
    private ModelMapper modelMapper;
    private final Logger log = LoggerFactory.getLogger(TourResource.class);

    private static final String ENTITY_NAME = "tour";

    private final TourService tourService;
    @Autowired
    public TourResource(TourService tourService) {
        this.tourService = tourService;
    }


    @PostMapping("/tours")
    public ResponseEntity<Tour> createTour(@Valid @RequestBody Tour tourDTO) throws URISyntaxException {
        log.debug("REST request to save Tour : {}", tourDTO);
        if (tourDTO.getId() != null) {
            throw new BadRequestException("A new tour cannot already have an ID");
        }
        Tour result = tourService.save(tourDTO);
        return ResponseEntity.created(new URI("/api/tours/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }


    @PutMapping("/tours")
    public ResponseEntity<Tour> updateTour(@Valid @RequestBody Tour tourDTO) throws URISyntaxException {
        log.debug("REST request to update Tour : {}", tourDTO);
        if (tourDTO.getId() == null) {
            throw new BadRequestException("Invalid id");
        }
        Tour result = tourService.save(tourDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tourDTO.getId().toString()))
            .body(result);
    }


    @GetMapping(value = "/tours")
    public ResponseEntity<List<HomeTourPayload>> getAllTours(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Tours");
        Page<Tour> page;
        if (eagerload) {

            page = tourService.findAllWithEagerRelationships(pageable);


        } else {
            page = tourService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/tours?eagerload=%b", eagerload));
        return new ResponseEntity<List<HomeTourPayload>>(page.getContent().stream().map(tour->convertToDto(tour)).collect(Collectors.toList()), headers, HttpStatus.OK);
    }
    
    @GetMapping(value = "/tour")
    public ResponseEntity<List<TourDetailRequests>> getAllTour(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Tours");
        Page<Tour> page;
        if (eagerload) {

            page = tourService.findAllWithEagerRelationships(pageable);


        } else {
            page = tourService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/tours?eagerload=%b", eagerload));
        return new ResponseEntity<List<TourDetailRequests>>(page.getContent().stream().map(tour->converToTourDetail1(tour)).collect(Collectors.toList()), headers, HttpStatus.OK);
    }

    @GetMapping("/tours/{id}")
    public ResponseEntity<TourDetailRequest> getTour(@PathVariable Long id) {
        log.debug("REST request to get Tour : {}", id);
        Optional<Tour> tourDTO = tourService.findOne(id);
        if (tourDTO==null)
            throw new ResourceNotFoundException(ENTITY_NAME,"id",id);
        return ResponseEntity.ok(converToTourDetail(tourDTO.get()));
    }

    @GetMapping("/tour/{id}")
    public ResponseEntity<TourDetailRequests> getTours(@PathVariable Long id) {
        log.debug("REST request to get Tour : {}", id);
        Optional<Tour> tourDTO = tourService.findOne(id);
        if (tourDTO==null)
            throw new ResourceNotFoundException(ENTITY_NAME,"id",id);
        return ResponseEntity.ok(converToTourDetail1(tourDTO.get()));
    }

    @DeleteMapping("/tours/{id}")
    public ResponseEntity<Void> deleteTour(@PathVariable Long id) {
        log.debug("REST request to delete Tour : {}", id);
        tourService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    @GetMapping("/tours/category/{categoryId}")
    public ResponseEntity<List<Tour>> getTourByCategory(@PathVariable long categoryId){
        log.debug("REST request to get Tour by cageoryId : {}", categoryId);
        return new ResponseEntity<>(tourService.getTourByCategoryId(categoryId),null,HttpStatus.OK);
    }
    private HomeTourPayload convertToDto(Tour post) {
        HomeTourPayload postDto = modelMapper.map(post, HomeTourPayload.class);
        postDto.setPicture(post.getLocations().iterator().next().getGalleries().iterator().next().getPicture());
        postDto.setCategory(post.getCategory().getName());
        return postDto;
    }
    private TourDetailRequests converToTourDetail1(Tour tour){
        TourDetailRequests tourDetailRequest=modelMapper.map(tour,TourDetailRequests.class);
        return tourDetailRequest;
    }
    private TourDetailRequest converToTourDetail(Tour tour){
        TourDetailRequest tourDetailRequest=modelMapper.map(tour,TourDetailRequest.class);
        return tourDetailRequest;
    }
    @GetMapping("/tours/search/{category}")
        public ResponseEntity<List<HomeTourPayload>> getTourByProviderAndName(@PathVariable String category,@RequestParam(name="name",required = false)String name, @RequestParam(name = "fromDate",required = false) @DateTimeFormat(pattern="yyyy-MM-dd")Date fromdate, @RequestParam(name = "toDate",required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date toDate){
        if(fromdate==null||toDate==null){
            return new ResponseEntity<List<HomeTourPayload>>(tourService.findTourByCategoryAndName(category,name).stream().map(tour->convertToDto(tour)).collect(Collectors.toList()), null, HttpStatus.OK);
        }
        return  new ResponseEntity<List<HomeTourPayload>>(tourService.findTourByCategoryAndTimeStartAndTimeEnd(category,name,fromdate,toDate).stream().map(tour->convertToDto(tour)).collect(Collectors.toList()), null, HttpStatus.OK);
    }
    @GetMapping("/admin/tour/search")
    public ResponseEntity<List<TourDetailRequests>> searchTour(@RequestParam(name="query",required = true)String query){
  
        return  new ResponseEntity<List<TourDetailRequests>>(tourService.search(query).stream().map(tour->converToTourDetail1(tour)).collect(Collectors.toList()), null, HttpStatus.OK);
    }
    private String getLocations(Set<Location> locations) {
    	String name="";
    	int count=0;
    	for (Location location : locations) {
			if(count==0) {
				name=location.getName();
				count++;
			}else {
				name=name+","+location.getName();
			}
		}
    	return name;
    }
}
