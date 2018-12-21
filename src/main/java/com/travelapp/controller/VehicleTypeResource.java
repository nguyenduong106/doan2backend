package com.travelapp.controller;

import com.travelapp.controller.util.HeaderUtil;
import com.travelapp.controller.util.PaginationUtil;
import com.travelapp.exception.BadRequestException;
import com.travelapp.exception.ResourceNotFoundException;
import com.travelapp.model.Tour;
import com.travelapp.model.Vehicle;
import com.travelapp.model.VehicleType;
import com.travelapp.payload.TourDetailRequest;
import com.travelapp.payload.VehicleTypeDetailRequest;
import com.travelapp.service.VehicleTypeService;

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
 * REST controller for managing VehicleType.
 */
@RestController
@RequestMapping("/api")
public class VehicleTypeResource {
	@Autowired
    private ModelMapper modelMapper;
    private final Logger log = LoggerFactory.getLogger(VehicleTypeResource.class);

    private static final String ENTITY_NAME = "vehicleType";

    private final VehicleTypeService vehicleTypeService;
    @Autowired
    public VehicleTypeResource(VehicleTypeService vehicleTypeService) {
        this.vehicleTypeService = vehicleTypeService;
    }

    @PostMapping("/vehicle-types")
    public ResponseEntity<VehicleType> createVehicleType(@Valid @RequestBody VehicleType vehicleTypeDTO) throws URISyntaxException {
        log.debug("REST request to save VehicleType : {}", vehicleTypeDTO);
        if (vehicleTypeDTO.getId() != null) {
            throw new BadRequestException("A new vehicleType cannot already have an ID");
        }
        VehicleType result = vehicleTypeService.save(vehicleTypeDTO);
        return ResponseEntity.created(new URI("/api/vehicle-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }


    @PutMapping("/vehicle-types")
    public ResponseEntity<VehicleType> updateVehicleType(@Valid @RequestBody VehicleType vehicleTypeDTO) throws URISyntaxException {
        log.debug("REST request to update VehicleType : {}", vehicleTypeDTO);
        if (vehicleTypeDTO.getId() == null) {
            throw new BadRequestException("Invalid id");
        }
        VehicleType result = vehicleTypeService.save(vehicleTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, vehicleTypeDTO.getId().toString()))
            .body(result);
    }


    @GetMapping("/vehicle-types")
    public List<VehicleType> getAllVehicleTypes() {
        log.debug("REST request to get all VehicleTypes");
        return vehicleTypeService.findAll();
    }
    
    @GetMapping(value = "/vehicle-type")
    public ResponseEntity<List<VehicleTypeDetailRequest>> getAllTour(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of VehicleTypes");
        Page<VehicleType> page;
        if (eagerload) {

            page = vehicleTypeService.findAllWithEagerRelationships(pageable);


        } else {
            page = vehicleTypeService.findAlls(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/vehicleTypes?eagerload=%b", eagerload));
        return new ResponseEntity<List<VehicleTypeDetailRequest>>(page.getContent().stream().map(vehicleType->converToVehicleTypeDetail(vehicleType)).collect(Collectors.toList()), headers, HttpStatus.OK);
    }

    @GetMapping("/vehicle-types/{id}")
    public ResponseEntity<VehicleType> getVehicleType(@PathVariable Long id) {
        log.debug("REST request to get VehicleType : {}", id);
        Optional<VehicleType> vehicleTypeDTO = vehicleTypeService.findOne(id);
        if (vehicleTypeDTO==null){
            throw new ResourceNotFoundException(ENTITY_NAME,"id",id);
        }
        return ResponseEntity.ok(vehicleTypeDTO.get());
    }

    @DeleteMapping("/vehicle-types/{id}")
    public ResponseEntity<Void> deleteVehicleType(@PathVariable Long id) {
        log.debug("REST request to delete VehicleType : {}", id);
        vehicleTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    
    @GetMapping("/admin/vehicletype/search")
    public ResponseEntity<List<VehicleTypeDetailRequest>> searchComment(@RequestParam(name="query",required = true)String query){
  
        return  new ResponseEntity<List<VehicleTypeDetailRequest>>(vehicleTypeService.search(query).stream().map(vehicleType->converToVehicleTypeDetail(vehicleType)).collect(Collectors.toList()), null, HttpStatus.OK);
    }
    
    private VehicleTypeDetailRequest converToVehicleTypeDetail(VehicleType vehicleType){
        VehicleTypeDetailRequest vehicleTypeDetailRequest=modelMapper.map(vehicleType,VehicleTypeDetailRequest.class);
        return vehicleTypeDetailRequest;
    }
}
