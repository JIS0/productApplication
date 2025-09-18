package com.example.productSales.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "sale")
public class Sale {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @JsonBackReference
    private Product product;

    private Integer quantity;

    private LocalDateTime saleDate;

    public  Sale(){

    }

    public static class Builder {
        private Long id;
        private Integer quantity;
        private LocalDateTime saleDate;
        private Product product;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }
        public Builder quantity(Integer quantity) {
            this.quantity = quantity;
            return this;
        }
        public Builder saleDate(LocalDateTime saleDate) {
            this.saleDate = saleDate;
            return this;
        }
        public Builder product(Product product) {
            this.product = product;
            return this;
        }

        public Sale build() {
            Sale s= new Sale();
            s.setProduct(product);
            s.setQuantity(quantity);
            s.setSaleDate(saleDate);
            return s;
        }
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDateTime saleDate) {
        this.saleDate = saleDate;
    }
}
