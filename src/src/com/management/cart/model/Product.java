package com.management.cart.model;

public class Product {
    private String productName;
    private long productPrice;
    private final Vendor vendor;
    private int productCount;

    public Product(String productName, long productPrice, Vendor vendor, int productCount) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.vendor = vendor;
        this.productCount = productCount;
    }

    public String getProductName() {
        return this.productName;
    }

    public String getProductVendorName() {
        return this.vendor.getVendorName();
    }

    protected void changeProductName(String productName) {
        this.productName = productName;
    }

    protected void changePrice(long productPrice) {
        this.productPrice = productPrice;
    }

    protected void increaseProductStocks() {
        this.productCount++;
    }

    public void decreaseProductStocks() {
        this.productCount--;
    }

    public boolean getProductStocks() {
        return this.productCount > 0;
    }

    public long getProductPrice() {
        return this.productPrice;
    }
}
