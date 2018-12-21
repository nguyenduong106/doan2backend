package com.travelapp.controller;

import com.travelapp.controller.util.HeaderUtil;
import com.travelapp.controller.util.PaginationUtil;
import com.travelapp.exception.BadRequestException;
import com.travelapp.exception.ResourceNotFoundException;
import com.travelapp.model.Booking;
import com.travelapp.model.Tour;
import com.travelapp.payload.BookingDetailRequest;
import com.travelapp.payload.BookingPayload;
import com.travelapp.payload.TourDetailRequest;
import com.travelapp.payload.TourDetailRequests;
import com.travelapp.service.BookingService;
import com.travelapp.service.CustomerService;
import com.travelapp.service.TourService;

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

import java.awt.print.Book;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Booking.
 */
@RestController
@RequestMapping("/api")
public class BookingResource {
	@Autowired
    private ModelMapper modelMapper;
    private final Logger log = LoggerFactory.getLogger(BookingResource.class);

    private static final String ENTITY_NAME = "booking";

    private final BookingService bookingService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private TourService tourService;
    @Autowired
    public BookingResource(BookingService bookingService) {
        this.bookingService = bookingService;
    }


    @PostMapping("/bookings")
    public ResponseEntity<Booking> createBooking(@RequestBody Booking bookingDTO) throws URISyntaxException {
        log.debug("REST request to save Booking : {}", bookingDTO);
        if (bookingDTO.getId() != null) {
            throw new BadRequestException("A new booking cannot already have an ID");
        }
        Booking result = bookingService.save(bookingDTO);
        return ResponseEntity.created(new URI("/api/bookings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/bookings")
    public ResponseEntity<BookingDetailRequest> updateBooking(@RequestBody Booking bookingDTO) throws URISyntaxException {
        log.debug("REST request to update Booking : {}", bookingDTO);
        if (bookingDTO.getId() == null) {
            throw new BadRequestException("Invalid id");
        }
        Booking result = bookingService.save(bookingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bookingDTO.getId().toString()))
            .body(converToBookingDetail(result));
    }

    @GetMapping("/bookings")
    public List<Booking> getAllBookings(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Bookings");
        return bookingService.findAll();
    }
    
    @GetMapping(value = "/booking")
    public ResponseEntity<List<BookingDetailRequest>> getAllBooking(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Tours");
        Page<Booking> page;
        if (eagerload) {

            page = bookingService.findAllWithEagerRelationships(pageable);


        } else {
            page =  bookingService.findAlls(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/booking?eagerload=%b", eagerload));
        return new ResponseEntity<List<BookingDetailRequest>>(page.getContent().stream().map(booking->converToBookingDetail(booking)).collect(Collectors.toList()), headers, HttpStatus.OK);
    }
    /**
     * GET  /bookings/:id : get the "id" booking.
     *
     * @param id the id of the bookingDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bookingDTO, or with status 404 (Not Found)
     */
    @GetMapping("/bookings/{id}")
    public ResponseEntity<Booking> getBooking(@PathVariable Long id) {
        log.debug("REST request to get Booking : {}", id);
        Optional<Booking> bookingDTO = bookingService.findOne(id);
        if (bookingDTO==null){
            throw new ResourceNotFoundException(ENTITY_NAME,"id",id);
        }
        return ResponseEntity.ok(bookingDTO.get());
    }

    /**
     * DELETE  /bookings/:id : delete the "id" booking.
     *
     * @param id the id of the bookingDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bookings/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        log.debug("REST request to delete Booking : {}", id);
        bookingService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    @PostMapping("/bookings/payment")
    public ResponseEntity<Booking> createBookingAndpayment(@RequestBody BookingPayload bookingDTO) throws URISyntaxException {
        log.debug("REST request to save Booking : {}", bookingDTO);
        Booking booking =new Booking();
        booking.setStatus(false);
        booking.setTotalPeople(bookingDTO.getTotalPeople());
        booking.setCardName(bookingDTO.getCardNumber());
        booking.setCardNumber(bookingDTO.getCardNumber());
        booking.setExpirationDate(bookingDTO.getExpirationDate());
        booking.setSecurityCode(bookingDTO.getSecurityCode());
        booking.setUser(customerService.findOne(bookingDTO.getUserId()).get());
        Tour tour=tourService.findOne(bookingDTO.getTourId()).get();
        tour.setFreeSpace(tour.getFreeSpace()-bookingDTO.getTotalPeople());

        booking.setTour(tour);
        Booking result = bookingService.save(booking);
        tourService.save(tour);
        return ResponseEntity.created(new URI("/api/bookings/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }
    @GetMapping("/admin/booking/search")
    public ResponseEntity<List<BookingDetailRequest>> searchBooking(@RequestParam(name="query",required = true)String query,@RequestParam(name="status",required=false)Boolean status){
    	if(status==null) {
            return  new ResponseEntity<List<BookingDetailRequest>>(bookingService.search(query).stream().map(booking->converToBookingDetail(booking)).collect(Collectors.toList()), null, HttpStatus.OK);

    	}
    	System.out.println("Value of status "+status);
        return  new ResponseEntity<List<BookingDetailRequest>>(bookingService.searchSTT(query,status).stream().map(booking->converToBookingDetail(booking)).collect(Collectors.toList()), null, HttpStatus.OK);

    }
    private BookingDetailRequest converToBookingDetail(Booking booking){
        BookingDetailRequest bookingDetailRequest=modelMapper.map(booking,BookingDetailRequest.class);
        return bookingDetailRequest;
    }
}
