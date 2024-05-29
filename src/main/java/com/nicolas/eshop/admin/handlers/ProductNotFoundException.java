package com.nicolas.eshop.admin.handlers;

public class ProductNotFoundException extends RuntimeException{

    public ProductNotFoundException(Integer productId) {
        super("Product with id " + productId + "doesn't exist");
    }
}
