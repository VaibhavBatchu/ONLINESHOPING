package com.klef.fsd.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.klef.fsd.model.Product;
import com.klef.fsd.model.Seller;
import com.klef.fsd.repository.ProductRepository;
import com.klef.fsd.repository.SellerRepository;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private SellerRepository sellerRepository;

	@Autowired
	private CloudinaryService cloudinaryService;

	@Override
	public String addProduct(Product product, MultipartFile imageFile) throws IOException {
		// Upload image to Cloudinary
		if (imageFile != null && !imageFile.isEmpty()) {
			Map<String, Object> uploadResult = cloudinaryService.uploadImage(imageFile, "llcart/products");
			String imageUrl = (String) uploadResult.get("secure_url");
			product.setImageUrl(imageUrl);
		}
		productRepository.save(product);
		return "Product Added Successfully";
	}

	@Override
	public List<Product> viewallProducts() {
		return productRepository.findAll();
	}

	@Override
	public List<Product> viewProductsByCategory(String category) {
		return productRepository.findByCategory(category);
	}

	@Override
	public List<Product> viewProductsBySeller(String sid) {
		Seller seller = sellerRepository.findById(sid).orElse(null);
		return productRepository.findBySeller(seller);
	}

	@Override
	public String deleteProduct(String pid) throws IOException {
		Optional<Product> productOpt = productRepository.findById(pid);
		if (productOpt.isPresent()) {
			Product product = productOpt.get();
			productRepository.delete(product);
			return "Product Deleted Successfully";
		}
		return "Product Not found";
	}

	@Override
	public Product viewProductById(String sid) {
		return productRepository.findById(sid).orElse(null);
	}

	@Override
	public String updateProduct(Product product, MultipartFile imageFile) throws IOException {
		Optional<Product> existingOpt = productRepository.findById(product.getId());
		if (existingOpt.isPresent()) {
			Product existing = existingOpt.get();
			
			// Update image if new file provided
			if (imageFile != null && !imageFile.isEmpty()) {
				Map<String, Object> uploadResult = cloudinaryService.uploadImage(imageFile, "llcart/products");
				String imageUrl = (String) uploadResult.get("secure_url");
				product.setImageUrl(imageUrl);
			} else {
				// Keep existing image
				product.setImageUrl(existing.getImageUrl());
			}
		}
		
		productRepository.save(product);
		return "Product Updated Successfully";
	}

	@Override
	public Product getProductById(String id) {
	    return productRepository.findById(id).orElse(null);
	}

}
