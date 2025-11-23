package com.cafeteria.campus.repository;

import com.cafeteria.campus.model.Promotion;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PromotionRepository extends MongoRepository<Promotion, String> {
}
