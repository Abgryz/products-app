package com.example.products.tables.product;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Long id;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate entryDate;

    private Long itemCode;
    private String itemName;
    private int itemQuantity;
    private String status;
    private String user;
}
