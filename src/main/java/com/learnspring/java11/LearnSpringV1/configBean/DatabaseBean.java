package com.learnspring.java11.LearnSpringV1.configBean;

import com.learnspring.java11.LearnSpringV1.model.Product;
import com.learnspring.java11.LearnSpringV1.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseBean {

    //logger
    private static final Logger logger = LoggerFactory.getLogger(DatabaseBean.class);
    @Bean
    CommandLineRunner initDataBase(ProductRepository productRepository){
        return CommandLindRunner -> {
            Product productA = new Product("iphone","this is iphone");
            Product productB = new Product("ipad", "this is ipad");

            logger.info("insert data: " + productRepository.save(productA));
            logger.info("insert data: " + productRepository.save(productB));
        };
    }
}
