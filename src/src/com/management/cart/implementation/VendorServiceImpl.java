package com.management.cart.implementation;

import com.management.cart.model.Product;
import com.management.cart.model.Vendor;
import com.management.cart.service.VendorService;
import java.util.ArrayList;

public record VendorServiceImpl(Vendor vendor) implements VendorService {

    @Override
    public void changePassword(int inputPassword) {
        if (this.vendor.validatePassword(inputPassword)) {
            this.vendor.setVendorPassword(inputPassword);
            System.out.println("Password Successfully changed!!");
            return;
        }
        System.out.println("Unauthorised operation!!");
    }

    @Override
    public ArrayList<Product> getAvailableProducts() {
        ArrayList<Product> products = new ArrayList<>();
        for (Product product : this.vendor.getProducts()) {
            if (product.getProductStocks()) {
                products.add(product);
            }
        }
        return products;
    }
}
