package com.example.productSales.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;

    private Double  price;

    private Integer quantity;


    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private List<Sale> sales;

    public static class Builder {
        private Long id;
        private String name;
        private String description;
        private Double price;
        private Integer quantity;
        private List<Sale> sales;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder price(Double price) {
            this.price = price;
            return this;
        }

        public Builder quantity(Integer quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder sales(List<Sale> sales) {
            this.sales = sales;
            return this;
        }

        public Product build() {
            Product product = new Product();
            product.setId(this.id);
            product.setName(this.name);
            product.setDescription(this.description);
            product.setPrice(this.price);
            product.setQuantity(this.quantity);
            product.setSales(this.sales);
            return product;
        }
    }


    public void addSale(Sale sale) {
        if (this.sales == null) {
            this.sales = new ArrayList<>();
        }

        sales.add(sale);
        sale.setProduct(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public List<Sale> getSales() {
        return sales;
    }

    public void setSales(List<Sale> sales) {
        this.sales = sales;
    }
}
