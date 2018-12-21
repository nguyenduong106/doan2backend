package com.travelapp.controller;

import com.travelapp.controller.util.HeaderUtil;
import com.travelapp.exception.BadRequestException;
import com.travelapp.exception.ResourceNotFoundException;
import com.travelapp.model.Category;
import com.travelapp.model.Provider;
import com.travelapp.payload.CategoryPayload;
import com.travelapp.payload.TourDetailRequests;
import com.travelapp.service.CategoryService;
import com.travelapp.service.ProviderService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CategoryResource {
    private final Logger log = LoggerFactory.getLogger(ProviderResource.class);

    private static final String ENTITY_NAME = "category";
    @Autowired
    private ModelMapper modelMapper;

    private final CategoryService categoryService;
    @Autowired
    public CategoryResource(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @PostMapping("/category")
    public ResponseEntity<Category> createCategory(@Valid @RequestBody Category category) throws URISyntaxException {
        log.debug("REST request to save Category : {}", category);
        if (category.getId() != null) {
            throw new BadRequestException("A new provider cannot already have an ID");
        }
        Category result = categoryService.save(category);
        return ResponseEntity.created(new URI("/api/category/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    @PutMapping("/category")
    public ResponseEntity<CategoryPayload> updateCategory(@Valid @RequestBody Category category) throws URISyntaxException {
        log.debug("REST request to update Category : {}", category);
        if (category.getId() == null) {
            throw new BadRequestException("Invalid id");
        }
        Category result = categoryService.save(category);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, category.getId().toString()))
                .body(convertToDto(result));
    }


    @GetMapping("/category")
    public List<CategoryPayload> getAllCategory() {
        log.debug("REST request to get all Category");
        return categoryService.findAll().stream().map(category -> convertToDto(category)).collect(Collectors.toList());
    }

    @GetMapping("/category/{id}")

    public ResponseEntity<Category> getCategory(@PathVariable Long id) {
        log.debug("REST request to get Category : {}", id);
        Optional<Category> category = categoryService.findOne(id);
        if (category==null)
            throw new ResourceNotFoundException(ENTITY_NAME,"id",id);
        return ResponseEntity.ok(category.get());
    }


    @DeleteMapping("/category/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        log.debug("REST request to delete category : {}", id);
        categoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    
    @GetMapping("/admin/category/search")
    public ResponseEntity<List<CategoryPayload>> searchCategory(@RequestParam(name="query",required = true)String query){
  
        return  new ResponseEntity<List<CategoryPayload>>(categoryService.search(query).stream().map(category -> convertToDto(category)).collect(Collectors.toList()), null, HttpStatus.OK);
    }
    
    private CategoryPayload convertToDto(Category post) {
        CategoryPayload postDto = modelMapper.map(post, CategoryPayload.class);
        return postDto;
    }
    

}
