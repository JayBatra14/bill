package com.jay.api.service;


import com.jay.api.entity.Product;
import com.jay.api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

        public Product save(Product product) {
            return productRepository.save(product);
        }

        public List<Product> findAll() {
            return productRepository.findAll();
        }

    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product is not available"));
    }

    public Product update(Long id, Product product) {
        Optional<Product> findProduct = productRepository.findById(id);
        if(!findProduct.isPresent()){
            throw new RuntimeException("Product is not available");
        }
        Product foundProduct = findProduct.get();
        foundProduct.setHsnCode(product.getHsnCode());
        foundProduct.setName(product.getName());
        foundProduct.setGstPercentage(product.getGstPercentage());
        return productRepository.save(foundProduct);
    }

    public void deleteById(Long id) {
        Optional<Product> findProduct = productRepository.findById(id);
        if(!findProduct.isPresent()){
            throw new RuntimeException("Product is not available");
        }
        productRepository.deleteById(id);
    }
}


