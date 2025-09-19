package com.example.productSales.service;

import com.example.productSales.controller.ProductController;
import com.example.productSales.entity.Product;
import com.example.productSales.entity.Sale;
import com.example.productSales.exception.EntityNotFound;
import com.example.productSales.repository.ProductRepository;
import com.example.productSales.repository.SalesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SalesRepository salesRepository;


    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    public Page<Product> getAllProducts(Pageable pageable){
        log.info("Fetching all products, page: {}", pageable);
        return productRepository.findAll(pageable);
    }

    public Product getProductById(long id){
        log.info("Fetching product with id {}", id);
        return productRepository.findById(id).orElseThrow(()->new EntityNotFound("Product",id));
    }

    public Product addProduct(Product product){
        log.info("Adding product {}", product.getName());
       return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product product) {
        log.info("Updating product id {}", id);
        Product existing = getProductById(id);
        existing.setName(product.getName());
        existing.setDescription(product.getDescription());
        existing.setPrice(product.getPrice());
        existing.setQuantity(product.getQuantity());
        return productRepository.save(existing);
    }


    public void deleteProduct(Long id) {
        log.info("Deleting product id {}", id);
        Product p = getProductById(id);
        productRepository.delete(p);
    }

    public Sale addSale(Long productId, Integer quantity) {
        log.info("Adding sale for product {}: qty {}", productId, quantity);
        Product product = getProductById(productId);

        if (product.getQuantity() < quantity) {
            throw new IllegalArgumentException("Insufficient quantity");
        }
        product.setQuantity(product.getQuantity() - quantity);
        Sale s = new Sale.Builder().quantity(quantity).saleDate(LocalDateTime.now()).product(product).build();
        product.addSale(s);
        salesRepository.save(s);
        productRepository.save(product);
        return s;
    }

    public void deleteSale(Long saleId) {
        log.info("Deleting sale id {}", saleId);
        Sale s = salesRepository.findById(saleId).orElseThrow(() -> new RuntimeException("Sale not found"));
        Product p = s.getProduct();
        p.setQuantity(p.getQuantity() + s.getQuantity());
        p.getSales().remove(s);
        salesRepository.delete(s);
        productRepository.save(p);
    }

    public Double getTotalRevenue() {
        double totalRevenue = 0.0;

        List<Product> products = productRepository.findAll();

        for (Product product : products) {
            List<Sale> sales = product.getSales();
            if (sales != null) {
                for (Sale sale : sales) {
                    totalRevenue += sale.getQuantity() * product.getPrice();
                }
            }
        }
        return totalRevenue;
    }

    public Double getRevenueByProduct(Long productId) {
        double revenue = 0.0;

        Product product = productRepository.findById(productId).orElse(null);

        if (product != null && product.getSales() != null) {
            for (Sale sale : product.getSales()) {
                revenue += sale.getQuantity() * product.getPrice();
            }
        }

        return revenue;
    }


}
