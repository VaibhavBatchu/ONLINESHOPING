package com.klef.fsd.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.klef.fsd.model.Address;

@Repository
public interface AddressRepository extends MongoRepository<Address, String> {
	public List<Address> findByBuyerId(String buyerId);
	public Optional<Address> findByIdAndBuyerId(String addressId, String buyerId);
}
