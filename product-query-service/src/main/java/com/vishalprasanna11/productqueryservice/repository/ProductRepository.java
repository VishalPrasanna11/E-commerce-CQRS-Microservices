package com.vishalprasanna11.productqueryservice.repository;

import com.vishalprasanna11.productqueryservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Long> {
}
