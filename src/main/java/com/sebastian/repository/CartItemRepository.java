package com.sebastian.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sebastian.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

}
