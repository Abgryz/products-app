package com.example.products.model;

import com.example.products.tables.product.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductsRequest {
    private String table;
    private List<ProductDto> records;
}
