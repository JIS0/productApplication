package com.example.productSales.controller;

import com.example.productSales.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private ProductService productService;


    @GetMapping
    public ResponseEntity<List> getAllProducts(){
        return (ResponseEntity<List>) productService.getAllProducts();
    }

    @
}
