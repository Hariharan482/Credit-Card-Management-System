package com.management.cart.vendor;

import com.management.cart.model.Product;
import com.management.cart.model.Vendor;

public class XYZVendor extends Vendor {
    public XYZVendor() {
        this.setVendorName("XYZ Vendor");
        this.addProduct(new Product("XYZShirt", 900, this, 4));
        this.addProduct(new Product("XYZShorts", 600, this, 8));
        this.addProduct(new Product("XYZTracks", 400, this, 10));
    }
}
