package com.example.productSales.service;

import com.example.productSales.entity.Product;
import com.example.productSales.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

public class ProductService {

    private ProductRepository productRepository;

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(long id){
        return productRepository.findById(id);
    }

    public void addProduct(Product product){
        productRepository.save(product);
    }

    public Product updateProduct(Long id, Product product) {

        Product existing = getProductById(id).get();
        existing.setName(product.getName());
        existing.setDescription(product.getDescription());
        existing.setPrice(product.getPrice());
        existing.setQuantity(product.getQuantity());
        return productRepository.save(existing);
    }


    public void deleteProduct(Long id) {
        Product p = getProductById(id).get();
        productRepository.delete(p);
    }

    public Double getTotalRevenue() {
        return null;
    }

    public Double getRevenueByProduct(Long productId) {
        return null;
    }

}
