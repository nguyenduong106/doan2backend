package com.travelapp.service.impl;

import com.travelapp.model.Gallery;
import com.travelapp.repository.GalleryRepository;
import com.travelapp.service.GalleryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Gallery.
 */
@Service
@Transactional
public class GalleryServiceImpl implements GalleryService {

    private final Logger log = LoggerFactory.getLogger(GalleryServiceImpl.class);

    private final GalleryRepository galleryRepository;

    @Autowired
    public GalleryServiceImpl(GalleryRepository galleryRepository ) {
        this.galleryRepository = galleryRepository;
    }

    /**
     * Save a gallery.
     *
     * @param galleryDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public Gallery save(Gallery galleryDTO) {
        log.debug("Request to save Gallery : {}", galleryDTO);


        galleryDTO = galleryRepository.save(galleryDTO);
        return galleryDTO;
    }

    /**
     * Get all the galleries.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Gallery> findAll() {
        log.debug("Request to get all Galleries");
        return galleryRepository.findAll().stream()

            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one gallery by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Gallery> findOne(Long id) {
        log.debug("Request to get Gallery : {}", id);
        return galleryRepository.findById(id)
           ;
    }

    /**
     * Delete the gallery by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Gallery : {}", id);
        galleryRepository.deleteById(id);
    }

	@Override
	public Page<Gallery> findAllWithEagerRelationships(Pageable pageable) {
        return galleryRepository.findAllWithEagerRelationships(pageable);
	}

	@Override
	public Page<Gallery> findAlls(Pageable pageable) {
		log.debug("Request to get all Tours");
        return galleryRepository.findAll(pageable)
         ;
	}
}
