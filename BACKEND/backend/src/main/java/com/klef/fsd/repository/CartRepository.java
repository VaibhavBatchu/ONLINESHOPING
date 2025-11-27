package com.klef.fsd.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.klef.fsd.model.Buyer;
import com.klef.fsd.model.Cart;

@Repository
public interface CartRepository extends MongoRepository<Cart, String> {

    List<Cart> findByBuyer(Buyer buyer);

    @Query("{ 'buyer.$id': ?0, 'product.$id': ?1 }")
    Cart findByBuyerIdAndProductId(String buyerId, String productId);

    void deleteByBuyerId(String buyerId);

    long countByBuyerId(String buyerId);
}