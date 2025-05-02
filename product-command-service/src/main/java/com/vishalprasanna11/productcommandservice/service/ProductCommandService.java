package com.vishalprasanna11.productcommandservice.service;

import com.vishalprasanna11.productcommandservice.dto.ProductEvent;
import com.vishalprasanna11.productcommandservice.entity.Product;
import com.vishalprasanna11.productcommandservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProductCommandService {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private ProductRepository repository;

    public Product createProduct(ProductEvent productEvent) {
        Product productDO = repository.save(productEvent.getProduct());
        ProductEvent event = new ProductEvent("CreateProduct",productDO);
        kafkaTemplate.send("product-topic",event);
        return productDO;
    }

    public Product updateProduct(long id, ProductEvent productEvent) {
        Product existingProduct = repository.findById(id).get();
        Product newProduct = productEvent.getProduct();
        if (newProduct.getName() != null) {
            existingProduct.setName(newProduct.getName());
        }
        if (newProduct.getDescription() != null) {
            existingProduct.setDescription(newProduct.getDescription());
        }
        existingProduct.setPrice(newProduct.getPrice());

        Product productDU = repository.save(existingProduct);
        ProductEvent event = new ProductEvent("UpdateProduct",productDU);
        kafkaTemplate.send("product-topic",event);
        return productDU;
    }

    public void deleteProduct(long id) {
        // First retrieve the product to include in the event
        Product product = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        // Delete the product from the repository
        repository.deleteById(id);

        // Create and publish a delete event
        ProductEvent event = new ProductEvent("DeleteProduct", product);
        kafkaTemplate.send("product-topic", event);
    }
}
