package com.learnspring.java11.LearnSpringV1.repository;

import com.learnspring.java11.LearnSpringV1.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// su dung module jpa co san cua spring framework
public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findProductByName(String name);
}
