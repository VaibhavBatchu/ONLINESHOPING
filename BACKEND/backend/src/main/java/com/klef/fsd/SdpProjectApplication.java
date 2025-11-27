package com.klef.fsd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.klef.fsd.repository")
public class SdpProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SdpProjectApplication.class, args);
		System.out.println("✅ LL-CART Backend is Running Successfully with MongoDB!");
	}

}
