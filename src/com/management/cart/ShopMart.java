package com.management.cart;

import com.management.cart.model.Cart;

public class ShopMart {
    public void getMenu(Cart cart, int appUserID) {
        new ProductManager().listProducts(cart, appUserID);
    }
}
