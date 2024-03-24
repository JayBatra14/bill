package com.jay.api.controller;

import com.jay.api.entity.Product;
import com.jay.api.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/")
    public Product addProduct(@RequestBody Product product){
        return productService.save(product);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/")
    public List<Product> findAll(){
        return productService.findAll();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/{id}")
    public Product findProductById(@PathVariable Long id){
        return productService.findById(id);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product){
        return productService.update(id, product);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id){
        productService.deleteById(id);
        return "Product deleted successfully";
    }


}
