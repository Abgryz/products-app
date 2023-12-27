package com.example.products.mappers;

import java.util.List;
import java.util.stream.Collectors;

public interface Mapper<DTO, ENTITY> {
    DTO toDto(ENTITY entity);
    ENTITY toEntity(DTO dto);
    default List<DTO> toDto(List<ENTITY> entities){
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    default List<ENTITY> toEntity(List<DTO> dtos){
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
