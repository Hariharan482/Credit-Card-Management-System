package com.management.cart.model;

import java.util.ArrayList;

public class Cart {
    private final ArrayList<Product> cartItems;

    public Cart() {
        this.cartItems = new ArrayList<>();
    }

    public ArrayList<Product> getCartItems() {
        return cartItems;
    }

    public boolean addProduct(Product product){
        boolean isSuccess = this.cartItems.add(product);
        return isSuccess;
    }

    public boolean removeFromCart(Product product) {
        boolean isSuccess = this.cartItems.remove(product);
        return isSuccess;
    }

    public void emptyCartItems(){
        this.cartItems.clear();
    }
}
