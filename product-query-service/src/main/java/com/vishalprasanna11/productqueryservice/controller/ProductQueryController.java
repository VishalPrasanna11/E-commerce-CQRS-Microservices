package com.vishalprasanna11.productqueryservice.controller;

import com.vishalprasanna11.productqueryservice.entity.Product;
import com.vishalprasanna11.productqueryservice.service.ProductQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/products")
@RestController
public class ProductQueryController {

    @Autowired
    private ProductQueryService productQueryService;

    @GetMapping
    public List<Product> fetchAllProducts() {
        return productQueryService.getproducts();
    }
}
