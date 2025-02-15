package com.management.cart.vendor;

import com.management.cart.model.Product;
import com.management.cart.model.Vendor;

public class ABCVendor extends Vendor {
    public ABCVendor() {
        this.setVendorName("ABC Vendor");
        this.addProduct(new Product("ABCShirt", 1000, this, 5));
        this.addProduct(new Product("ABCShorts", 500, this, 7));
        this.addProduct(new Product("ABCTracks", 800, this, 3));
    }
}
