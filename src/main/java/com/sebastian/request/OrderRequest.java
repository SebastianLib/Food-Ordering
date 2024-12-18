package com.sebastian.request;

import com.sebastian.model.Address;

import lombok.Data;

@Data
public class OrderRequest {

    private Long restaurantId;

    private Address deliveryAddress;

}
