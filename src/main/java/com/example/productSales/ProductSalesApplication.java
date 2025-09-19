package com.example.productSales;

import com.example.productSales.controller.ProductController;
import com.example.productSales.entity.Product;
import com.example.productSales.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
@SpringBootApplication
public class ProductSalesApplication {
	private static final Logger log = LoggerFactory.getLogger(ProductController.class);

	public static void main(String[] args) {
		SpringApplication.run(ProductSalesApplication.class, args);
	}

	@Bean
	CommandLineRunner seed(ProductService productService) {
		log.info("Initialising the DB with sample data.");
		return args -> {

			Product p1 = new Product.Builder().name("Apple MacBook Pro 14").description("Apple M2 Pro, 16GB RAM, 512GB SSD").price(199900.0).quantity(8).build();
			Product p2 = new Product.Builder().name("Samsung Galaxy S24 Ultra").description("6.8-inch AMOLED, 12GB RAM, 256GB").price(129999.0).quantity(15).build();
			Product p3 = new Product.Builder().name("Sony WH-1000XM5").description("Wireless noise-cancelling headphones").price(29990.0).quantity(25).build();
			Product p4 = new Product.Builder().name("Apple Watch Series 9").description("GPS, 45mm, Midnight Aluminum Case").price(45900.0).quantity(20).build();
			Product p5 = new Product.Builder().name("iPad Pro 12.9").description("Apple M2, 128GB, Wi-Fi").price(99900.0).quantity(10).build();

			p1 = productService.addProduct(p1);
			p2 = productService.addProduct(p2);
			p3 = productService.addProduct(p3);
			p4 = productService.addProduct(p4);
			p5 = productService.addProduct(p5);


			productService.addSale(p1.getId(), 2);
			productService.addSale(p2.getId(), 5);
			productService.addSale(p3.getId(), 3);
			productService.addSale(p4.getId(), 6);
			productService.addSale(p5.getId(), 7);
		};
	}

}
