package com.sebastian.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sebastian.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

}
