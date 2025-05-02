package com.vishalprasanna11.productcommandservice.repository;

import com.vishalprasanna11.productcommandservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Long> {
}
