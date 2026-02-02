package com.poc.gemf.mapper;


import com.poc.gemf.requestdto.ProductDto;
import com.poc.gemf.entity.Product;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {

    // Convert Entity -> DTO
    public ProductDto toDto(Product product) {
        if (product == null) return null;

        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        return dto;
    }

    // Convert DTO -> Entity (for saving new items)
    public Product toEntity(ProductDto dto) {
        Product product = new Product();
        // ID is not set here because DB generates it
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        return product;
    }

    // Merge logic: Takes existing entity and updates it with DTO data
    public void updateEntityFromDto(ProductDto dto, Product entity) {
        entity.setName(dto.getName());
        entity.setPrice(dto.getPrice());
    }

    public List<ProductDto> toDtoList(List<Product> products) {
        if (products == null) {
            return Collections.emptyList();
        }
        return products.stream()
                .map(this::toDto) // Calls the method above
                .collect(Collectors.toList());
    }
}