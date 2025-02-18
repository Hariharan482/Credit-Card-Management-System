package com.management.cart.model;

import java.util.ArrayList;
public class Vendor {
    private String vendorName;
    private int vendorID;
    private final ArrayList<Product> products;
    private int vendorPassword = 999;

    public Vendor() {
        this.products = new ArrayList<>();
    }

    protected void addProduct(Product product) {
        this.products.add(product);
    }

    public ArrayList<Product> getProducts() {
        return this.products;
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

    public boolean validatePassword(int inputPassword) {
        return this.vendorPassword == inputPassword;
    }

    public void setVendorPassword(int inputPassword){
        this.vendorPassword=inputPassword;
    }
}
