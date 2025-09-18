package com.example.productSales.repository;

import com.example.productSales.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesRepository extends JpaRepository<Sale,Long> {
}
