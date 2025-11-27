package com.klef.fsd.repository;

import com.klef.fsd.model.Order;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {
    List<Order> findByBuyerId(String buyerId);
    List<Order> findBySellerId(String sellerId);
    Optional<Order> findByRazorpayPaymentId(String razorpayPaymentId);
    
    // MongoDB queries for sales data
    @Query("{ 'seller.$id': ?0, 'orderDate': { $gte: ?1 } }")
    List<Order> findBySellerIdAndOrderDateAfter(String sellerId, LocalDateTime startDate);
    
    @Query("{ 'orderDate': { $gte: ?0 } }")
    List<Order> findByOrderDateAfter(LocalDateTime startDate);
    
    // Aggregation for admin daily sales data
    @Aggregation(pipeline = {
        "{ $match: { orderDate: { $gte: ?0 } } }",
        "{ $group: { _id: { $dateToString: { format: '%Y-%m-%d', date: '$orderDate' } }, orderCount: { $sum: 1 }, revenue: { $sum: '$amount' } } }",
        "{ $project: { _id: 0, date: '$_id', orderCount: 1, revenue: 1 } }",
        "{ $sort: { date: 1 } }"
    })
    List<Object[]> getAdminDailySalesData(LocalDateTime startDate);
    
    // Aggregation for admin monthly sales data
    @Aggregation(pipeline = {
        "{ $match: { orderDate: { $gte: ?0 } } }",
        "{ $group: { _id: { $dateToString: { format: '%Y-%m', date: '$orderDate' } }, orderCount: { $sum: 1 }, revenue: { $sum: '$amount' } } }",
        "{ $project: { _id: 0, month: '$_id', orderCount: 1, revenue: 1 } }",
        "{ $sort: { month: 1 } }"
    })
    List<Object[]> getAdminMonthlySalesData(LocalDateTime startDate);
}