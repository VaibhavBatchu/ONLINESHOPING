package com.klef.fsd.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.klef.fsd.model.Seller;

@Repository
public interface SellerRepository extends MongoRepository<Seller, String> {
    Seller findByUsernameAndPassword(String username, String password);
    List<Seller> findByStatus(String status);
    
    Optional<Seller> findByEmail(String email);
    Seller findByResetToken(String resetToken);

}

