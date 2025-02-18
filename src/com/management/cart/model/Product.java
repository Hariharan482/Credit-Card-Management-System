package com.management.cart.model;

/**
 * a product model with details such as product name, price, vendor, and available stock.
 */
public class Product {
    private String productName;
    private long productPrice;
    private final Vendor vendor;
    private int productCount;

    /**
     * Constructs a new Product with the specified details.
     * @param productName  The name of the product.
     * @param productPrice The price of the product.
     * @param vendor       The vendor of the product.
     * @param productCount The initial stock count of the product.
     */
    public Product(String productName, long productPrice, Vendor vendor, int productCount) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.vendor = vendor;
        this.productCount = productCount;
    }

    /**
     * returns the name of the product.
     * @return The product's name.
     */
    public String getProductName() {
        return this.productName;
    }

    /**
     * returns the name of the vendor associated with the product.
     * @return The name of the product's vendor.
     */
    public String getProductVendorName() {
        return this.vendor.getVendorName();
    }

    /**
     * Changes the name of the product.
     * @param productName The new name for the product.
     */
    protected void changeProductName(String productName) {
        this.productName = productName;
    }

    /**
     * Changes the price of the product.
     * @param productPrice The new price for the product.
     */
    protected void changePrice(long productPrice) {
        this.productPrice = productPrice;
    }

    /**
     * Increases the stock count of the product by one.
     */
    protected void increaseProductStocks() {
        this.productCount++;
    }

    /**
     * Decreases the stock count of the product by one.
     */
    public void decreaseProductStocks() {
        this.productCount--;
    }

    /**
     * Checks whether the product is in stock.
     * @return {@code true} if the product is in stock
     *         {@code false} if the product is out of stock.
     */
    public boolean getProductStocks() {
        return this.productCount > 0;
    }

    /**
     * Gets the price of the product.
     * @return The product's price.
     */
    public long getProductPrice() {
        return this.productPrice;
    }
}
