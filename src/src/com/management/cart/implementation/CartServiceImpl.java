package com.management.cart.implementation;

import com.management.cart.model.Cart;
import com.management.cart.model.Product;
import com.management.cart.service.CartService;
import com.management.creditcard.CreditManager;

import java.util.ArrayList;
import java.util.Iterator;

public record CartServiceImpl(Cart cart) implements CartService {

    private void updateCart() {
        Iterator<Product> iterator = this.cart.getCartItems().iterator();
        while (iterator.hasNext()){
            Product product=iterator.next();
            product.decreaseProductStocks();
            iterator.remove();
        }
    }

    private void purchaseProducts(int appUserID, long cartAmount) {
        if (CreditManager.buyProducts(appUserID, cartAmount)) {
            System.out.println("Products Successfully purchased");
            this.updateCart();
            return;
        }
        System.out.println("Transaction failed!!");
    }

    @Override
    public void addToCart(Product product) {
        if (cart.addProduct(product)) {
            System.out.println("Product successfully added to the cart");
            return;
        }
        System.out.println("There was a problem in adding products from cart.. Please try again later!");

    }

    @Override
    public void removeFromCart(Product product) {
        if (cart.removeFromCart(product)) {
            System.out.println("Product successfully removed from the cart");
            return;
        }
        System.out.println("There was a problem in removing products from cart.. Please try again later!");

    }

    @Override
    public void emptyCart() {
        cart.emptyCartItems();
    }

    @Override
    public void orderItems(int appUserID) {
        ArrayList<Product> cartItems = this.cart.getCartItems();
        if (!cartItems.isEmpty()) {
            long totalProductCost = 0;
            for (Product product : cartItems) {
                totalProductCost += product.getProductPrice();
            }
            this.purchaseProducts(appUserID, totalProductCost);
            return;
        }
        System.out.println("Cart is Empty");
    }
}
