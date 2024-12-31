package com.sebastian.service;

import java.sql.Date; 
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sebastian.model.Category;
import com.sebastian.model.Food;
import com.sebastian.model.Restaurant;
import com.sebastian.repository.FoodRepository;
import com.sebastian.request.CreateFoodRequest;

@Service
public class FoodServiceImpl implements FoodService{

    @Autowired
    private FoodRepository foodRepository;


    @Override
    public Food createFood(CreateFoodRequest req, Category category, Restaurant restaurant) {
       
        Food food = new Food();

        food.setCategory(category);
        food.setRestaurant(restaurant);
        food.setDescription(req.getDescription());
        food.setImages(req.getImages());
        food.setName(req.getName());
        food.setPrice(req.getPrice());
        food.setIngredients(req.getIngredients());
        food.setSeasonal(req.isSesasional());
        food.setVegetarian(req.isVegetarian());
        food.setCreationDate(new Date(System.currentTimeMillis()));

        Food savedFood = foodRepository.save(food);
        restaurant.getFoods().add(savedFood);

        return savedFood;
    }

    @Override
    public void deleteFood(Long foodId) throws Exception {
        
        Food food = findFoodById(foodId);
        food.setRestaurant(null);
        foodRepository.save(food);

    }

    @Override
    public List<Food> getRestaurantsFood(Long restaurantId, boolean isVegetarian, boolean isNonveg, boolean isSeasonal,
    String foodCategory) {
        
        List<Food> foods = foodRepository.findByRestaurantId(restaurantId);

        if(isVegetarian){
            foods = filtreByVegetarian(foods, isVegetarian);
        }
        if(isNonveg) { 
            foods = filterByNonveg(foods, isNonveg);
        }
        if(isSeasonal) { 
            foods = filterBySeasonal(foods, isSeasonal);
        }
        if(foodCategory != null && !foodCategory.equals("")){
            foods=filterbyCategory(foods, foodCategory);
            }
                                            
                                                
        return foods;
        }
                                                
    private List<Food> filterbyCategory(List<Food> foods, String foodCategory) {
        return foods.stream().filter(food -> {
            if(food.getCategory()!=null){
                return food.getCategory().getName().equals(foodCategory);
            }
            return false;
        }).collect(Collectors.toList());
    }
            
    private List<Food> filterBySeasonal(List<Food> foods, boolean isSeasonal) {
        return foods.stream().filter(food -> food.isSeasonal() == isSeasonal).collect(Collectors.toList());
    }
            
    private List<Food> filterByNonveg(List<Food> foods, boolean isNonveg) {   
        return foods.stream().filter(food -> food.isVegetarian() == false).collect(Collectors.toList());
    }
            
    private List<Food> filtreByVegetarian(List<Food> foods, boolean isVegetarian) {
        return foods.stream().filter(food -> food.isVegetarian() == isVegetarian).collect(Collectors.toList());
    }
            
    @Override
    public List<Food> searchFood(String keyword) {
        return foodRepository.searchFood(keyword);
    }

    @Override
    public Food findFoodById(Long foodId) throws Exception {
        Optional<Food> optionalFood = foodRepository.findById(foodId);

        if(optionalFood.isEmpty()) {
            throw new Exception("food not exist");
        }

        return optionalFood.get();
    }

    @Override
    public Food updateAvailabilityStatus(Long foodId) throws Exception {
       Food food = findFoodById(foodId);
        food.setAvailable(!food.isAvailable());
        return foodRepository.save(food);
    }

}
