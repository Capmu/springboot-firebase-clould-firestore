package com.capmu.firestore.controller;

import com.capmu.firestore.entity.Product;
import com.capmu.firestore.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/product")
    public String saveProduct(@RequestBody Product product) throws ExecutionException, InterruptedException {

        return productService.saveProduct(product);
    }

    @GetMapping("/product/{name}")
    public Product getProduct(@PathVariable String name) throws ExecutionException, InterruptedException {

        return productService.getProductByName(name);
    }

    @GetMapping("/products")
    public List<Product> getAllProducts() throws ExecutionException, InterruptedException {

        return productService.getAllProducts();
    }

    @PutMapping("/product")
    public String updateProduct(@RequestBody Product product) throws ExecutionException, InterruptedException {

        return productService.updateProduct(product);
    }

    @DeleteMapping("/product/{name}")
    public String deleteProduct(@PathVariable String name) throws ExecutionException, InterruptedException {

        return productService.deleteProductByName(name);
    }
}
