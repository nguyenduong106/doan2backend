package com.travelapp.service.impl;
import com.travelapp.model.Booking;
import com.travelapp.repository.BookingRepository;
import com.travelapp.service.BookingService;
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
 * Service Implementation for managing Booking.
 */
@Service
@Transactional
public class BookingServiceImpl implements BookingService {

    private final Logger log = LoggerFactory.getLogger(BookingServiceImpl.class);

    private final BookingRepository bookingRepository;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository ) {
        this.bookingRepository = bookingRepository;
    }


    @Override
    public Booking save(Booking booking) {
        log.debug("Request to save Booking : {}", booking);

        booking = bookingRepository.save(booking);
        return booking;
    }


    @Override
    @Transactional(readOnly = true)
    public List<Booking> findAll() {
        log.debug("Request to get all Bookings");
        return bookingRepository.findAllWithEagerRelationships().stream()
            .collect(Collectors.toCollection(LinkedList::new));
    }


    public Page<Booking> findAllWithEagerRelationships(Pageable pageable) {
        return bookingRepository.findAllWithEagerRelationships(pageable);
    }
    


    @Override
    @Transactional(readOnly = true)
    public Optional<Booking> findOne(Long id) {
        log.debug("Request to get Booking : {}", id);
        return bookingRepository.findOneWithEagerRelationships(id);
    }


    @Override
    public void delete(Long id) {
        log.debug("Request to delete Booking : {}", id);
        bookingRepository.deleteById(id);
    }


	@Override
	public Page<Booking> findAlls(Pageable pageable) {
		return bookingRepository.findAllWithEagerRelationships(pageable);
	}


	@Override
	public List<Booking> search(String query) {
		return bookingRepository.search(query);
	}


	@Override
	public List<Booking> searchSTT(String query, boolean status) {
		System.out.println(status);
		return bookingRepository.searchSTT(query,status);
	}
}
