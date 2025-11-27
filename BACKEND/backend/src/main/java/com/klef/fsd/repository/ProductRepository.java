package com.klef.fsd.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.klef.fsd.model.Product;
import com.klef.fsd.model.Seller;

@Repository
public interface ProductRepository extends MongoRepository<Product, String>{

	 public List<Product> findByCategory(String category);
	 
	 public List<Product> findBySeller(Seller seller);
	 
	
}
