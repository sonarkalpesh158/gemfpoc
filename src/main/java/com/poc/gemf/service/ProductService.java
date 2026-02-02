package com.poc.gemf.service;


import com.poc.gemf.entity.Product;
import com.poc.gemf.exception.ResourceNotFoundException;
import com.poc.gemf.mapper.ProductMapper;
import com.poc.gemf.repository.ProductRepository;
import com.poc.gemf.requestdto.ProductDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Transactional
    public ProductDto createProduct(ProductDto dto) {
        // 1. Convert
        Product product = productMapper.toEntity(dto);

        // 2. Save to DB
        Product savedProduct = productRepository.save(product);
        log.info("Saved new product to MySQL with ID: {}", savedProduct.getId());

        // 3. Convert back
        return productMapper.toDto(savedProduct);
    }

    // Look-Aside Cache Pattern
    @Cacheable(value = "Products", key = "#id")
    public ProductDto getProductById(Long id) {
        log.info("Cache MISS: Fetching ID {} from MySQL ---", id);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        return productMapper.toDto(product);
    }

    // Update Pattern: Updates DB -> Updates Cache
    @Transactional
    @CachePut(value = "Products", key = "#id")
    public ProductDto updateProduct(Long id, ProductDto dto) {
        // 1. Fetch Existing
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        // 2. Update fields using Mapper
        productMapper.updateEntityFromDto(dto,existingProduct);

        // 3. Save to DB
        Product savedProduct = productRepository.save(existingProduct);

        // 4. Return updated DTO (This value goes into the Cache)
        return productMapper.toDto(savedProduct);
    }

    // Delete Pattern: Deletes DB -> Evicts Cache
    @Transactional
    @CacheEvict(value = "Products", key = "#id")
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cannot delete. Product not found.");
        }
        productRepository.deleteById(id);
    }

    // get list products by name
    @Cacheable(value = "Products", key = "#name")
    public List<ProductDto> getProductsByName(String name) {
        List<Product> product = productRepository.findByNameContainingIgnoreCase(name);
        return productMapper.toDtoList(product);
    }
}