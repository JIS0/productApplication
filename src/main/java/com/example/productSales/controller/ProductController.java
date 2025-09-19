package com.example.productSales.controller;

import com.example.productSales.entity.Product;
import com.example.productSales.entity.Sale;
import com.example.productSales.service.PdfService;
import com.example.productSales.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@SecurityRequirement(name = "basicAuth")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private PdfService pdfService;

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);


    @GetMapping
    @Operation(summary = "List products (paginated)")
    public Page<Product> getAllProducts(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size){

        return productService.getAllProducts(PageRequest.of(page,size));
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

    @GetMapping("/export/pdf")
    @Operation(summary = "Download product table as PDF")
    public ResponseEntity<InputStreamResource> downloadPdf() {
        byte[] pdfBytes = pdfService.generateProductsPdf();
        ByteArrayInputStream bis = new ByteArrayInputStream(pdfBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=products.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
}
