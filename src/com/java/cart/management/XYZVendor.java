package com.java.cart.management;

class XYZVendor extends Vendor {
    XYZVendor() {
        this.addProduct(new Product("XYZShirt", 900, this, 4));
        this.addProduct(new Product("XYZShorts", 600, this, 8));
        this.addProduct(new Product("XYZTracks", 400, this, 10));
    }
}
