package com.sebastian.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sebastian.model.IngredientCategory;

public interface IngredientCategoryRepository extends JpaRepository<IngredientCategory, Long>{

    List<IngredientCategory> findByRestaurantId(Long id);
}
