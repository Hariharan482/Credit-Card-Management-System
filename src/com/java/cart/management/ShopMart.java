package com.java.cart.management;

public class ShopMart {
    public void getMenu(Cart cart, int appUserID) {
        new ProductManager().listProducts(cart, appUserID);
    }
}
