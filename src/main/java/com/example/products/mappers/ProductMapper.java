package com.example.products.mappers;

import com.example.products.tables.product.Product;
import com.example.products.tables.product.ProductDto;
import com.example.products.tables.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductMapper implements Mapper<ProductDto, Product>{
    private final UserRepository userRepository;

    @Override
    public ProductDto toDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .status(product.getStatus())
                .entryDate(product.getEntryDate())
                .itemQuantity(product.getItemQuantity())
                .itemCode(product.getItemCode())
                .itemName(product.getItemName())
                .user(product.getUser().getUsername())
                .build();
    }

    @Override
    public Product toEntity(ProductDto productDTO) {
        return Product.builder()
                .id(productDTO.getId())
                .status(productDTO.getStatus())
                .entryDate(productDTO.getEntryDate())
                .itemQuantity(productDTO.getItemQuantity())
                .itemCode(productDTO.getItemCode())
                .itemName(productDTO.getItemName())
                .user(userRepository.findByUsername(productDTO.getUser()).orElseThrow())
                .build();
    }
}
