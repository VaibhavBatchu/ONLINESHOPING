package com.klef.fsd.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.klef.fsd.model.Admin;

import java.util.Optional;

@Repository
public interface AdminRepository extends MongoRepository<Admin, String> {
	public Admin findByUsernameAndPassword(String username, String password);
	public Optional<Admin> findByUsername(String username);
}
