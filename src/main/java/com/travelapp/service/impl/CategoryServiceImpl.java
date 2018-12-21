package com.travelapp.service.impl;

import com.travelapp.model.Category;
import com.travelapp.payload.CategoryPayload;
import com.travelapp.repository.CategoryRepository;
import com.travelapp.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private final Logger log = LoggerFactory.getLogger(CommentServiceImpl.class);

    private final CategoryRepository categoryRepository;
    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category save(Category category) {
        log.debug("Request to save category : {}", category);
        category = categoryRepository.save(category);
        return category;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> findAll() {
        log.debug("Request to get all Comments");
        return categoryRepository.findAll().stream()
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Category> findOne(Long id) {
        log.debug("Request to get Comment : {}", id);
        return categoryRepository.findById(id)
                ;
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete category : {}", id);
        categoryRepository.deleteById(id);
    }

	@Override
	public List<Category> search(String query) {
		 return categoryRepository.search(query);
	}

}
