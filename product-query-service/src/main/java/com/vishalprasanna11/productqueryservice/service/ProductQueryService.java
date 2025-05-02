package com.vishalprasanna11.productqueryservice.service;

import com.vishalprasanna11.productqueryservice.dto.ProductEvent;
import com.vishalprasanna11.productqueryservice.entity.Product;
import com.vishalprasanna11.productqueryservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductQueryService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getproducts(){
        return productRepository.findAll();
    }

    @KafkaListener(topics = "product-topic",groupId = "product-event-group")
    public void processProductEvents(ProductEvent productEvent){
        Product product = productEvent.getProduct();
        if(productEvent.getEventType().equals("CreateProduct")){
            productRepository.save(product);
        }
        else if(productEvent.getEventType().equals("UpdateProduct")){
            Product existingProduct = productRepository.findById(product.getId()).get();
            existingProduct.setName(product.getName());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setPrice(product.getPrice());
            productRepository.save(existingProduct);
        }
        else if(productEvent.getEventType().equals("DeleteProduct")){
            productRepository.deleteById(product.getId());
        }

    }
}
