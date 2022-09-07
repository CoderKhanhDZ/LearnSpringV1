package com.learnspring.java11.LearnSpringV1.controller;

import com.learnspring.java11.LearnSpringV1.model.Product;
import com.learnspring.java11.LearnSpringV1.model.ResponseObject;
import com.learnspring.java11.LearnSpringV1.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    //DI dependency injection
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("")
    ResponseEntity<List<Product>> findAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(productRepository.findAll());
    }

    // response data
    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> findProductById(@PathVariable("id") long id) {

        Optional<Product> foundProduct = productRepository.findById(id);

        if (foundProduct.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK.toString(), "success", foundProduct));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject(HttpStatus.NOT_FOUND.toString(), "cannot not find id " + id, ""));
        }

    }

    @PostMapping("/{id}")
    ResponseEntity<ResponseObject> deleteProduct(@PathVariable("id") long id) {

        Boolean exists = productRepository.existsById(id);

        if (exists) {
            productRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK.toString(), "delete success", ""));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject(HttpStatus.NOT_FOUND.toString(), "delete fail by id: " + id, ""));
        }

    }

    // postman: raw, json
    @PostMapping("/add")
    ResponseEntity<ResponseObject> insertProduct(@RequestBody Product newProduct) {
        // check name not allow similar
        List<Product> products = productRepository.findProductByName(newProduct.getName());
        if (products.size() > 0) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                    .body(new ResponseObject(HttpStatus.NOT_IMPLEMENTED.toString(), "insert-product fail", ""));
        }
        productRepository.save(newProduct);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK.toString(), "insert-product success", ""));

    }

    // postman: raw, json
    //update product by id
    @PostMapping("/update")
    ResponseEntity<ResponseObject> updateProduct(@RequestBody Product newProduct) {
        // tim product by id
        Optional<Product> foundProduct = productRepository.findById(newProduct.getId());

        if (foundProduct.isPresent()) {
            //neu ton tai product by id thi cap nhat
            foundProduct.map(product -> {
                product.setName(newProduct.getName());
                product.setDescription(newProduct.getDescription());
                product.setUrl(newProduct.getUrl());
                return productRepository.save(product);
            });
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK.toString(), "update-product success", ""));
        }
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                .body(new ResponseObject(HttpStatus.NOT_IMPLEMENTED.toString(), "update-product fail", ""));
    }


}




