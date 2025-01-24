package com.java.cart.management;

import java.util.ArrayList;

class Vendor {
    private String vendorName;
    private int vendorID;
    private final ArrayList<Product> products;
    private int vendorPassword = 999;

    Vendor() {
        this.products = new ArrayList<>();
    }

    protected void addProduct(Product product) {
        this.products.add(product);
    }

    protected ArrayList<Product> getProducts() {
        return this.products;
    }

    protected ArrayList<Product> getAvailableProducts() {
        ArrayList<Product> products = new ArrayList<>();
        for (Product product : this.products) {
            if (product.getProductStocks()) {
                products.add(product);
            }
        }
        return products;
    }

    protected int getVendorID() {
        return vendorID;
    }

    protected void setVendorID(int vendorID) {
        this.vendorID = vendorID;
    }

    protected String getVendorName() {
        return vendorName;
    }

    protected void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    protected boolean validatePassword(int inputPassword) {
        return this.vendorPassword == inputPassword;
    }

    protected void changePassword(int inputPassword) {
        if (this.validatePassword(inputPassword)) {
            this.vendorPassword = inputPassword;
            System.out.println("Password Successfully changed!!");
            return;
        }
        System.out.println("Unauthorised operation!!");
    }

}
