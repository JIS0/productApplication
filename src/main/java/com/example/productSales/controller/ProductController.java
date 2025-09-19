package com.example.productSales.controller;

import com.example.productSales.entity.Product;
import com.example.productSales.entity.Sale;
import com.example.productSales.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@SecurityRequirement(name = "basicAuth")
public class ProductController {

    @Autowired
    private ProductService productService;


    @GetMapping
    @Operation(summary = "List products (paginated)")
    public List<Product> getAllProducts(){

        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by id")
    public ResponseEntity<Product> getProduct(@PathVariable Long id){
        Product p= productService.getProductById(id);
        if(p==null){
           return  ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(p);
    }

    @PostMapping
    @Operation(summary = "Create product (USER or ADMIN)")
    public ResponseEntity<Product> addProduct(@RequestBody Product product){
        Product p=productService.addProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(p);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete product (ADMIN only)")
    public ResponseEntity deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update product (ADMIN only)")
    public Product updateProduct(@PathVariable Long id,@RequestBody Product product){
        return productService.updateProduct(id,product);
    }

    // Sales endpoints
    @PostMapping("/{id}/sales")
    @Operation(summary = "Create sale for product")
    public Sale addSale(@PathVariable Long id, @RequestParam Integer quantity) {
        return productService.addSale(id, quantity);
    }

    @DeleteMapping("/sales/{saleId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete sale (ADMIN only)")
    public ResponseEntity<Void> deleteSale(@PathVariable Long saleId) {
        productService.deleteSale(saleId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/revenue")
    @Operation(summary = "Revenue for product")
    public Double getRevenueByProduct(@PathVariable Long id) {
        return productService.getRevenueByProduct(id);
    }

    @GetMapping("/revenue/total")
    @Operation(summary = "Total revenue")
    public Double getTotalRevenue() {
        return productService.getTotalRevenue();
    }
}
