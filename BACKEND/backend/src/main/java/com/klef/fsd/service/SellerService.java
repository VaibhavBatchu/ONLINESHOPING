package com.klef.fsd.service;

import com.klef.fsd.model.Seller;

import java.util.List;
import java.util.Map;

public interface SellerService {
    // Existing methods
    String sellerRegistration(Seller seller);
    Seller checkSellerLogin(String username, String password);
    List<Seller> viewPendingSellers();
    String approveSeller(String sellerId);
    String rejectSeller(String id);
    String deleteSeller(String id);
    Seller getSellerById(String sid);
    String updateSellerProfile(Seller seller);
    List<Seller> viewAllSellers();
    String generateResetToken(String email);
    String resetPassword(String token, String newPassword);

    // New methods for dashboard
    long getTotalProductsBySeller(String sellerId);
    long getTotalOrdersBySeller(String sellerId);
    double getTotalRevenueBySeller(String sellerId);
    List<Map<String, Object>> getSalesDataBySeller(String sellerId, String period);
}