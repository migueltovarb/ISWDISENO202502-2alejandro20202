package com.cafeteria.campus.repository;

import com.cafeteria.campus.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
