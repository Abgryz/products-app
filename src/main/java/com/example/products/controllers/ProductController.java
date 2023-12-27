package com.example.products.controllers;

import com.example.products.model.ProductsRequest;
import com.example.products.services.ProductService;
import com.example.products.tables.product.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/all")
    public List<ProductDto> getAllProducts(){
        return productService.getAll();
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProducts(@RequestBody ProductsRequest productsRequest, Principal principal){
        productService.saveProductsOnTable(productsRequest, principal.getName());
        return ResponseEntity.ok().build();
    }
}
