package com.sebastian.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sebastian.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
