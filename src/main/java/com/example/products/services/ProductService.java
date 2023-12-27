package com.example.products.services;

import com.example.products.mappers.ProductMapper;
import com.example.products.model.ProductsRequest;
import com.example.products.tables.product.ProductDto;
import com.example.products.tables.product.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductDto getById(Long id){
        return productMapper.toDto(productRepository.findById(id).orElseThrow());
    }

    public List<ProductDto> getAll(){
        return productRepository.findAll().stream()
                .map(productMapper::toDto)
                .toList();
    }

    @Transactional
    public void saveProductsOnTable(ProductsRequest productsRequest, String username){
        if (Objects.equals(productsRequest.getTable(), "products")){
            productsRequest.getRecords()
                    .forEach(product -> {
                        product.setUser(username);
                        productRepository.save(productMapper.toEntity(product));
                    });
        }
    }
}
