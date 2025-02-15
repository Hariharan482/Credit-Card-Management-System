package com.management.cart;

import com.management.cart.implementation.CartServiceImpl;
import com.management.cart.implementation.VendorServiceImpl;
import com.management.cart.model.Cart;
import com.management.cart.model.Product;
import com.management.cart.vendor.ABCVendor;
import com.management.cart.vendor.XYZVendor;
import com.management.utils.UserInputValidation;

import java.util.ArrayList;

class ProductManager {
    private static final ABCVendor abcVendor = new ABCVendor();
    private static final XYZVendor xyzVendor = new XYZVendor();
    private final VendorServiceImpl abcVendorService =new VendorServiceImpl(abcVendor);
    private final VendorServiceImpl xyzVendorService =new VendorServiceImpl(xyzVendor);


    private void listProductDetails(ArrayList<Product> availableProducts) {
        System.out.println();
        int count = 1;
        String[] headers = {"Cart No", "Product name", "Product price", "Vendor Name"};
        System.out.printf("%-15s %-15s %-15s %-15s%n", headers[0], headers[1], headers[2], headers[3]);
        for (Product product : availableProducts) {
            System.out.printf("%15d %-15s %-15d %-15s%-15s%n", count++, product.getProductName(), product.getProductPrice(), product.getProductVendorName(),product.getProductStocks());
        }
    }

    private void listAllProducts(CartServiceImpl cartService) {
        ArrayList<Product> availableProducts = new ArrayList<>();
        availableProducts.addAll(abcVendorService.getAvailableProducts());
        availableProducts.addAll(xyzVendorService.getAvailableProducts());
        System.out.println();
        if(availableProducts.isEmpty()){
            System.out.println("No products available!");
            return;
        }
        this.listProductDetails(availableProducts);
        System.out.println("Select the product to add to cart");
        int selectedOption= UserInputValidation.getValidInteger();
        if (selectedOption > 0 && selectedOption <= availableProducts.size()) {
            cartService.addToCart(availableProducts.get(selectedOption - 1));
            System.out.println("Product added to cart!");
            return;
        }
        System.out.println("Invalid selection!..Returning to ShopMart menu!");
    }

    private void listProductsOfVendor(VendorServiceImpl vendorService, CartServiceImpl cartService) {
        ArrayList<Product> vendorProducts = vendorService.getAvailableProducts();
        if(vendorProducts.isEmpty()){
            System.out.println("No products available!");
            return;
        }
        this.listProductDetails(vendorProducts);
        System.out.println("Select the product to add to cart");
        int selectedOption=UserInputValidation.getValidInteger();
        if (selectedOption > 0 && selectedOption <= vendorProducts.size()) {
            cartService.addToCart(vendorProducts.get(selectedOption - 1));
            System.out.println("Product added to cart!");
            return;
        }
        System.out.println("Invalid selection!..Returning to ShopMart menu!");
    }

    private void removeProductFromCart(Cart cart) {
        this.listProductDetails(cart.getCartItems());
        if (!cart.getCartItems().isEmpty()) {
            System.out.println("Select the product to remove from cart");
            int selectedOption=UserInputValidation.getValidInteger();
            if (selectedOption > 0 && selectedOption <= cart.getCartItems().size()) {
                new CartServiceImpl(cart).removeFromCart(cart.getCartItems().get(selectedOption - 1));
                System.out.println("Product removed from cart!");
                return;
            }
            System.out.println("Invalid selection!..Returning to ShopMart menu!");
        } else {
            System.out.println("Cart is empty");
        }
    }

    private void listCartItems(Cart cart, int appUserID) {
        ArrayList<Product> selectedProducts = cart.getCartItems();
        if (selectedProducts.isEmpty()) {
            System.out.println("Nothing in Cart, Please add products!!");
            return;
        }
        this.getCartMenu(cart, appUserID);
    }

    private void getCartMenu(Cart cart, int appUserID) {
        boolean isExit = false;
        CartServiceImpl cartService=new CartServiceImpl(cart);
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
                    if(selectedProducts.isEmpty()){
                        System.out.println("Cart is empty!");
                        return;
                    }
                    this.listProductDetails(selectedProducts);
                    break;
                case 2:
                    this.removeProductFromCart(cart);
                    break;
                case 3:
                    cartService.emptyCart();
                    System.out.println("Cart is Empty");
                    break;
                case 4:
                    cartService.orderItems(appUserID);
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
        CartServiceImpl cartService=new CartServiceImpl(cart);
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
                    this.listAllProducts(cartService);
                    break;
                case 2:
                    System.out.println("Select the operation to perform");
                    System.out.println("1-> ABC Vendor");
                    System.out.println("2-> XYZ Vendor");
                    System.out.println("Press any key to return back to ShopMart main menu");
                    int userPreference=UserInputValidation.getValidInteger();
                    if (userPreference == 1) {
                        this.listProductsOfVendor(abcVendorService, cartService);
                    } else if (userPreference == 2) {
                        this.listProductsOfVendor(xyzVendorService, cartService);
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
