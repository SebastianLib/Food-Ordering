package com.sebastian.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sebastian.dto.RestaurantDto;
import com.sebastian.model.Address;
import com.sebastian.model.Restaurant;
import com.sebastian.model.User;
import com.sebastian.repository.AddressRepository;
import com.sebastian.repository.RestaurantRepository;
import com.sebastian.request.CreateRestaurantRequest;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserService userService;


    @Override
    public Restaurant createRestaurant(CreateRestaurantRequest req, User user) {

        Address address = addressRepository.save(req.getAddress());

        Restaurant restaurant = new Restaurant();
        restaurant.setAddress(address);
        restaurant.setContactInformation(req.getContactInformation());
        restaurant.setCuisineType(req.getCuisineType());
        restaurant.setDescription(req.getDescription());
        restaurant.setImages(req.getImages());
        restaurant.setName(req.getName());
        restaurant.setOpeningHours(req.getOpeningHours());
        restaurant.setRegistrationDate(LocalDateTime.now());
        restaurant.setOwner(user);

        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updatedRestaurant) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);

        if(restaurant.getCuisineType()!=null){
            restaurant.setCuisineType(updatedRestaurant.getCuisineType());
        }

        if(restaurant.getDescription()!=null){
            restaurant.setDescription(updatedRestaurant.getDescription());
        }

        if(restaurant.getName()!=null){
            restaurant.setName(updatedRestaurant.getName());
        }

        return restaurantRepository.save(restaurant);
    }

    @Override
    public void deleteRestaurant(Long restaurantId) throws Exception {
        // TODO Auto-generated method stub
        
    }

    @Override
    public RestaurantDto addToFavorites(Long restaurantId, User user) throws Exception {
        
        return null;
    }

    @Override
    public Restaurant findRestaurantById(Long id) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Restaurant getRestaurantByUserId(Long userId) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Restaurant> searchRestaurant() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Restaurant updateRestaurantStatus(Long id) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

}
