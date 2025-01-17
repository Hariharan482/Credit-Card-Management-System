package com.java.cart.management;

public class Product {
    private String productName;
    private long productPrice;
    private final Vendor vendor;
    private int productCount;

    Product(String productName, long productPrice, Vendor vendor, int productCount) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.vendor = vendor;
        this.productCount = productCount;
    }

    protected String getProductName() {
        return this.productName;
    }

    protected String getProductVendorName() {
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

    protected void decreaseProductStocks() {
        this.productCount--;
    }

    public boolean getProductStocks() {
        return this.productCount > 0;
    }

    public long getProductPrice() {
        return this.productPrice;
    }
}
