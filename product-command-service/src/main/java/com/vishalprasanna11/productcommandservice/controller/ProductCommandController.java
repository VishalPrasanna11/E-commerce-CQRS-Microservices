package com.vishalprasanna11.productcommandservice.controller;


import com.vishalprasanna11.productcommandservice.dto.ProductEvent;
import com.vishalprasanna11.productcommandservice.entity.Product;
import com.vishalprasanna11.productcommandservice.service.ProductCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
@RestController
@RequestMapping("/products")
public class ProductCommandController {
    @Autowired
    private ProductCommandService productCommandService;

    @PostMapping
    public Product createProduct(@RequestBody ProductEvent product) {
        return productCommandService.createProduct(product);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable long id,@RequestBody  ProductEvent product) {
        return productCommandService.updateProduct(id,product);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable long id) {
        productCommandService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
