package com.example.productSales.service;

import com.example.productSales.entity.Product;
import com.example.productSales.entity.Sale;
import com.example.productSales.exception.EntityNotFound;
import com.example.productSales.repository.ProductRepository;
import com.example.productSales.repository.SalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Product getProductById(long id){
        return productRepository.findById(id).orElseThrow(()->new EntityNotFound("Product",id));
    }

    public Product addProduct(Product product){
       return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product product) {

        Product existing = getProductById(id);
        existing.setName(product.getName());
        existing.setDescription(product.getDescription());
        existing.setPrice(product.getPrice());
        existing.setQuantity(product.getQuantity());
        return productRepository.save(existing);
    }


    public void deleteProduct(Long id) {
        Product p = getProductById(id);
        productRepository.delete(p);
    }

    public Sale addSale(Long productId, Integer quantity) {
        //log.info("Adding sale for product {}: qty {}", productId, quantity);
        Product product = getProductById(productId);
        // reduce quantity
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
        //log.info("Deleting sale id {}", saleId);
        Sale s = salesRepository.findById(saleId).orElseThrow(() -> new RuntimeException("Sale not found"));
        Product p = s.getProduct();
        p.setQuantity(p.getQuantity() + s.getQuantity()); // restore stock
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

    // ✅ Get revenue by product ID (no streams)
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
