package com.klef.fsd.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.klef.fsd.model.Buyer;

@Repository
public interface BuyerRepository extends MongoRepository<Buyer, String> {
	public Buyer findByEmailAndPassword(String email, String password);

	public Optional<Buyer> findByEmail(String email);

	public Buyer findByResetToken(String resetToken);

	public Optional<Buyer> findById(String buyerId);

}
