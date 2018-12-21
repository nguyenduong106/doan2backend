package com.travelapp.repository;

import com.travelapp.model.Category;
import com.travelapp.model.Tour;
import com.travelapp.payload.CategoryPayload;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("select distinct category from Category category where category.name like CONCAT('%',:query,'%')")
	List<Category> search(String query);
}
