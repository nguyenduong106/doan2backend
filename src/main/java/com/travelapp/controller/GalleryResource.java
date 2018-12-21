package com.travelapp.controller;

import com.travelapp.controller.util.HeaderUtil;
import com.travelapp.controller.util.PaginationUtil;
import com.travelapp.exception.BadRequestException;
import com.travelapp.exception.ResourceNotFoundException;
import com.travelapp.model.Gallery;
import com.travelapp.model.Location;
import com.travelapp.model.Tour;
import com.travelapp.payload.GalleryDetailRequest;
import com.travelapp.payload.HomeTourPayload;
import com.travelapp.payload.TourDetailRequest;
import com.travelapp.service.GalleryService;
import com.travelapp.service.LocationService;

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
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Gallery.
 */
@RestController
@RequestMapping("/api")
public class GalleryResource {
	@Autowired
    private ModelMapper modelMapper;
    private final Logger log = LoggerFactory.getLogger(GalleryResource.class);

    private static final String ENTITY_NAME = "gallery";

    private final GalleryService galleryService;
    private  final LocationService locationService;
    @Autowired
    public GalleryResource(GalleryService galleryService, LocationService locationService) {
        this.galleryService = galleryService;
        this.locationService=locationService;
    }

    @PostMapping("/galleries/{locationId}")
    public ResponseEntity<Gallery> createGallery(@RequestParam(value="file")MultipartFile picture,@PathVariable Long locationId) throws URISyntaxException, IOException {
    	Gallery galleryDTO=new Gallery();
       Location location=locationService.findOne(locationId).get();
       galleryDTO.setLocation(location);
       galleryDTO.setPicture(picture.getBytes());
        Gallery result = galleryService.save(galleryDTO);
        return ResponseEntity.created(new URI("/api/galleries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(null);
    }

    @PutMapping("/galleries")
    public ResponseEntity<Gallery> updateGallery(@Valid @RequestBody Gallery galleryDTO) throws URISyntaxException {
        log.debug("REST request to update Gallery : {}", galleryDTO);
        if (galleryDTO.getId() == null) {
            throw new BadRequestException("Invalid id");
        }
        Gallery result = galleryService.save(galleryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, galleryDTO.getId().toString()))
            .body(result);
    }
    
    @PutMapping("/galleries/{locationId}/{galleryId}")
    public ResponseEntity<Gallery> updateGallerys(@RequestParam(value = "file")MultipartFile file,@PathVariable Long locationId,@PathVariable Long galleryId) throws URISyntaxException,IOException {
        Location location=locationService.findOne(locationId).get();
        Gallery galleryDTO;
        galleryDTO=location.getGalleries().stream().filter(gallery -> gallery.getId()==galleryId).findFirst().get();
        galleryDTO.setPicture(file.getBytes());
        Gallery result = galleryService.save(galleryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, galleryDTO.getId().toString()))
            .body(result);
    }


    @GetMapping("/galleries")
    public List<Gallery> getAllGalleries() {
        log.debug("REST request to get all Galleries");
        return galleryService.findAll();
    }
    
    @GetMapping(value = "/gallerie")
    public ResponseEntity<List<GalleryDetailRequest>> getAllGallerie(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Tours");
        Page<Gallery> page;
        if (eagerload) {

            page = galleryService.findAllWithEagerRelationships(pageable);


        } else {
            page = galleryService.findAlls(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/tours?eagerload=%b", eagerload));
        return new ResponseEntity<List<GalleryDetailRequest>>(page.getContent().stream().map(gallery->converToGalleryDetail(gallery)).collect(Collectors.toList()), headers, HttpStatus.OK);
    }
    
    @GetMapping("/galleriess")
    public ResponseEntity<List<GalleryDetailRequest>> getAllGalleriess(){
  
        return  new ResponseEntity<List<GalleryDetailRequest>>(galleryService.findAll().stream().map(gallery -> converToGalleryDetail(gallery)).collect(Collectors.toList()), null, HttpStatus.OK);
    }

    @GetMapping("/galleries/{id}")
    public ResponseEntity<Gallery> getGallery(@PathVariable Long id) {
        log.debug("REST request to get Gallery : {}", id);
        Optional<Gallery> galleryDTO = galleryService.findOne(id);
        if (galleryDTO==null){
            throw new ResourceNotFoundException(ENTITY_NAME,"id",id);
        }
        return ResponseEntity.ok(galleryDTO.get());
    }

    @DeleteMapping("/galleries/{id}")
    public ResponseEntity<Void> deleteGallery(@PathVariable Long id) {
        log.debug("REST request to delete Gallery : {}", id);
        galleryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    private GalleryDetailRequest converToGalleryDetail(Gallery gallery){
        GalleryDetailRequest galleryDetailRequest=modelMapper.map(gallery,GalleryDetailRequest.class);
        return galleryDetailRequest;
    }
}
