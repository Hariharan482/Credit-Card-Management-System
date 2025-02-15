package com.management.cart.service;

import com.management.cart.model.Product;

public interface CartService {
    void addToCart(Product product);
    void removeFromCart(Product product);
    void emptyCart();
    void orderItems(int appUserID);
}
