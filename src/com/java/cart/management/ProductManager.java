package com.java.cart.management;

import com.java.utils.UserInputValidation;

import java.util.ArrayList;

class ProductManager {
    private static ABCVendor abcVendor = new ABCVendor();
    private static XYZVendor xyzVendor = new XYZVendor();

    private void listProductDetails(ArrayList<Product> availableProducts) {
        System.out.println();
        int count = 1;
        String[] headers = {"Cart No", "Product name", "Product price", "Vendor Name"};
        System.out.printf("%-15s %-15s %-15s %-15s%n", headers[0], headers[1], headers[2], headers[3]);
        for (Product product : availableProducts) {
            System.out.printf("%15d %-15s %-15d %-15s%n", count++, product.getProductName(), product.getProductPrice(), product.getProductVendorName());
        }
    }

    private void listAllProducts(Cart cart) {
        ArrayList<Product> availableProducts = new ArrayList<>();
        availableProducts.addAll(abcVendor.getAvailableProducts());
        availableProducts.addAll(xyzVendor.getAvailableProducts());
        System.out.println();
        if(availableProducts.isEmpty()){
            System.out.println("No products available!");
            return;
        }
        this.listProductDetails(availableProducts);
        System.out.println("Select the product to add to cart");
        int selectedOption= UserInputValidation.getValidInteger();
        if (selectedOption > 0 && selectedOption <= availableProducts.size()) {
            cart.addToCart(availableProducts.get(selectedOption - 1));
            System.out.println("Product added to cart!");
            return;
        }
        System.out.println("Invalid selection!..Returning to ShopMart menu!");
    }

    private void listProductsOfVendor(Vendor vendor, Cart cart) {
        ArrayList<Product> vendorProducts = vendor.getAvailableProducts();
        if(vendorProducts.isEmpty()){
            System.out.println("No products available!");
            return;
        }
        this.listProductDetails(vendorProducts);
        System.out.println("Select the product to add to cart");
        int selectedOption=UserInputValidation.getValidInteger();
        if (selectedOption > 0 && selectedOption <= vendorProducts.size()) {
            cart.addToCart(vendorProducts.get(selectedOption - 1));
            System.out.println("Product added to cart!");
            return;
        }
        System.out.println("Invalid selection!..Returning to ShopMart menu!");
    }

    private void listCartItems(Cart cart, int appUserID) {
        ArrayList<Product> selectedProducts = cart.getCartItems();
        if (selectedProducts.isEmpty()) {
            System.out.println("Nothing in Cart, Please add products!!");
            return;
        }
        this.getCartMenu(cart, appUserID);
    }

    private void removeProductFromCart(Cart cart) {
        this.listProductDetails(cart.getCartItems());
        if (!cart.getCartItems().isEmpty()) {
            System.out.println("Select the product to remove from cart");
            int selectedOption=UserInputValidation.getValidInteger();
            if (selectedOption > 0 && selectedOption <= cart.getCartItems().size()) {
                cart.removeFromCart(cart.getCartItems().get(selectedOption - 1));
                System.out.println("Product removed from cart!");
                return;
            }
            System.out.println("Invalid selection!..Returning to ShopMart menu!");
        } else {
            System.out.println("Cart is empty");
        }
    }

    private void getCartMenu(Cart cart, int appUserID) {
        boolean isExit = false;
        while (!isExit) {
            System.out.println("Select the operation to perform");
            System.out.println("1-> List all cart products");
            System.out.println("2-> Remove a product from cart");
            System.out.println("3-> Empty Cart");
            System.out.println("4-> Buy all products");
            System.out.println("0-> Returning back to main menu");
            int operation=UserInputValidation.getValidInteger();
            switch (operation) {
                case 1:
                    ArrayList<Product> selectedProducts = cart.getCartItems();
                    this.listProductDetails(selectedProducts);
                    break;
                case 2:
                    this.removeProductFromCart(cart);
                    break;
                case 3:
                    cart.emptyCart();
                    System.out.println("Cart is Empty");
                    break;
                case 4:
                    cart.orderItems(appUserID);
                    break;
                case 0:
                    isExit = true;
                    break;
                default:
                    System.out.println("Enter a valid operation need to be done");
                    break;
            }
        }
    }

    protected void listProducts(Cart cart, int appUserID) {
        System.out.println("Welcome to ShopMart!!");
        boolean isExit = false;
        while (!isExit) {
            System.out.println("Select the operation to perform");
            System.out.println("1-> List all available products");
            System.out.println("2-> List vendor specific products");
            System.out.println("3-> Get Cart Items");
            System.out.println("0-> Exit");
            int operation=UserInputValidation.getValidInteger();
            switch (operation) {
                case 1:
                    this.listAllProducts(cart);
                    break;
                case 2:
                    System.out.println("Select the operation to perform");
                    System.out.println("1-> ABC Vendor");
                    System.out.println("2-> XYZ Vendor");
                    System.out.println("Press any key to return back to ShopMart main menu");
                    int userPreference=UserInputValidation.getValidInteger();
                    if (userPreference == 1) {
                        this.listProductsOfVendor(abcVendor, cart);
                    } else if (userPreference == 2) {
                        this.listProductsOfVendor(xyzVendor, cart);
                    } else {
                        System.out.println("Returning to ShopMart main menu");
                    }
                    break;
                case 3:
                    this.listCartItems(cart, appUserID);
                    break;
                case 0:
                    isExit = true;
                    System.out.println("Thanks for using ShopMart! Have a great day!!");
                    break;
                default:
                    System.out.println("Enter a valid operation need to be done");
                    break;
            }
        }
    }

}
