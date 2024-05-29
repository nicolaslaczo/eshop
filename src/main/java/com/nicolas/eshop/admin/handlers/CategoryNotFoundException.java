package com.nicolas.eshop.admin.handlers;

public class CategoryNotFoundException extends RuntimeException{
    public CategoryNotFoundException(Integer id) {
        super("Category with id " + id + " not found");
    }
}
