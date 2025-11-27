package com.klef.fsd.service;

import java.util.List;

import com.klef.fsd.dto.CartDTO;
import com.klef.fsd.model.Cart;

public interface CartService {

    Cart addToCart(Cart cart);

    List<CartDTO> getCartItemsByBuyerId(String buyerId);

    void removeCartItem(String cartId);

    void clearCartByBuyerId(String buyerId);

    Cart updateCartQuantity(String buyerId, String productId, int quantity);

    long getCartCountByBuyerId(String buyerId);
    
}
