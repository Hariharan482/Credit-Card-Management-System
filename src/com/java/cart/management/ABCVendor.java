package com.java.cart.management;

import java.util.ArrayList;

class ABCVendor extends Vendor {
    ABCVendor() {
        this.addProduct(new Product("ABCShirt", 1000, this, 5));
        this.addProduct(new Product("ABCShorts", 500, this, 7));
        this.addProduct(new Product("ABCTracks", 800, this, 3));
    }

}
