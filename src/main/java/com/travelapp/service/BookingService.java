package com.travelapp.service;

import com.travelapp.model.Booking;
import com.travelapp.model.Tour;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface BookingService {
    Booking save(Booking bookingDTO);
    List<Booking> findAll();
    Page<Booking> findAlls(Pageable pageable);
    Page<Booking> findAllWithEagerRelationships(Pageable pageable);
    Optional<Booking> findOne(Long id);
    void delete(Long id);
    List<Booking> search(String query);
    List<Booking> searchSTT(String query,boolean status);
}
