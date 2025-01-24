package com.java.cart.management;

import com.java.credit.card.management.CreditManager;

import java.util.ArrayList;
import java.util.Iterator;

public class Cart {
    private final ArrayList<Product> cartItems;

    public Cart() {
        this.cartItems = new ArrayList<>();
    }

    protected void addToCart(Product product) {
        boolean isSuccess = this.cartItems.add(product);
        if (isSuccess) {
            System.out.println("Product successfully added to the cart");
            return;
        }
        System.out.println("There was a problem in adding products from cart.. Please try again later!");
    }

    protected void removeFromCart(Product product) {
        boolean isSuccess = this.cartItems.remove(product);
        if (isSuccess) {
            System.out.println("Product successfully removed from the cart");
            return;
        }
        System.out.println("There was a problem in removing products from cart.. Please try again later!");
    }

    protected void emptyCart() {
        this.cartItems.clear();
    }

    protected ArrayList<Product> getCartItems() {
        return this.cartItems;
    }

    protected void orderItems(int appUserID) {
        ArrayList<Product> cartItems = this.getCartItems();
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

    private void updateCart() {
        Iterator<Product> iterator = this.cartItems.iterator();
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
}
