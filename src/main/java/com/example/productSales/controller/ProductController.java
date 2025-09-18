package com.example.productSales.controller;

import com.example.productSales.entity.Product;
import com.example.productSales.entity.Sale;
import com.example.productSales.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;


    @GetMapping
    public List<Product> getAllProducts(){

        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id){
        Product p= productService.getProductById(id);
        if(p==null){
           return  ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(p);
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product){
        Product p=productService.addProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(p);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id,@RequestBody Product product){
        return productService.updateProduct(id,product);
    }

    // Sales endpoints
    @PostMapping("/{id}/sales")
    public Sale addSale(@PathVariable Long id, @RequestParam Integer quantity) {
        return productService.addSale(id, quantity);
    }

    @DeleteMapping("/sales/{saleId}")
    public ResponseEntity<Void> deleteSale(@PathVariable Long saleId) {
        productService.deleteSale(saleId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/revenue")
    public Double getRevenueByProduct(@PathVariable Long id) {
        return productService.getRevenueByProduct(id);
    }

    @GetMapping("/revenue/total")
    public Double getTotalRevenue() {
        return productService.getTotalRevenue();
    }
}
