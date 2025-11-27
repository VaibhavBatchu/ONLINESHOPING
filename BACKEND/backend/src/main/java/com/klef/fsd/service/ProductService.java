package com.klef.fsd.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.klef.fsd.model.Product;

public interface ProductService {

	public String addProduct(Product product, MultipartFile imageFile) throws IOException;
	
	public String updateProduct(Product product, MultipartFile imageFile) throws IOException;
	
	public String deleteProduct(String pid) throws IOException;

	public List<Product> viewallProducts();

	public List<Product> viewProductsBySeller(String sid);

	public List<Product> viewProductsByCategory(String category);
	
	public Product viewProductById(String sid);
	
	public Product getProductById(String pid);

}
