package com.cafeteria.campus.controller;

import com.cafeteria.campus.model.Product;
import com.cafeteria.campus.repository.ProductRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    @PostMapping
    public Product create(@RequestBody Product p) {
        return productRepository.save(p);
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable String id, @RequestBody Product p) {
        Product original = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        original.setName(p.getName());
        original.setDescription(p.getDescription());
        original.setPrice(p.getPrice());
        original.setStock(p.getStock()); // 🔥 Corrección

        return productRepository.save(original);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        productRepository.deleteById(id);
    }
}

