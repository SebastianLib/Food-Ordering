package com.sebastian.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sebastian.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
