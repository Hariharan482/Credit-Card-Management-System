package com.management.cart.service;

import com.management.cart.model.Product;
import java.util.ArrayList;

public interface VendorService {
    void changePassword(int inputPassword);
    ArrayList<Product> getAvailableProducts();
}
