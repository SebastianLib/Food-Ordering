package com.sebastian.request;

import java.util.List;

import com.sebastian.model.Category;
import com.sebastian.model.IngredientsItem;

import lombok.Data;

@Data
public class CreateFoodRequest {

    private String name;

    private String description;

    private Long price;

    private Category category;

    private List<String> images;

    private Long restaurantId;

    private boolean vegetarian;

    private boolean sesasional;

    private List<IngredientsItem> ingredients;
}
