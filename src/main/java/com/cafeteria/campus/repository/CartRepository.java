package com.cafeteria.campus.repository;

import com.cafeteria.campus.model.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CartRepository extends MongoRepository<Cart, String> {
    Optional<Cart> findByUserIdAndStatus(String userId, String status);
}
