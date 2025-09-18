package com.example.productSales.exception;

public class EntityNotFound extends RuntimeException {
    public EntityNotFound(String name , Long id) {
        super(name+" not found with id: " + id);
    }
}