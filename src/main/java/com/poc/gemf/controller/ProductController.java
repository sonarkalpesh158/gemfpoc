package com.poc.gemf.controller;

import com.poc.gemf.requestdto.ProductDto;
import com.poc.gemf.responsedto.ApiResponseDTO;
import com.poc.gemf.service.ProductService;
import com.poc.gemf.utils.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
// 1. Tagging for UI Grouping
@Tag(name = "Product Management", description = "APIs for creating, retrieving, updating, and deleting inventory products.")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Create a new product", description = "Validates and saves a new product to the database and cache.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data (e.g. missing name, negative price)", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @PostMapping
    public ResponseEntity<ApiResponseDTO<ProductDto>> create(
            @Parameter(description = "Product details to be created", required = true)
            @Valid @RequestBody ProductDto dto) {

        ProductDto createdProduct = productService.createProduct(dto);
        return new ResponseEntity<>(
                new ApiResponseDTO<>(Constants.SUCCESS, "Product created successfully", createdProduct),
                HttpStatus.CREATED
        );
    }

    // 1. Get Product By ID
    @Operation(summary = "Get product by ID", description = "Fetches a product by its unique identifier from Cache or DB.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(responseCode = "404", description = "Product not found with the given ID", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<ProductDto>> getById(
            @Parameter(description = "ID of the product to be retrieved", example = "1")
            @PathVariable Long id) {
        ProductDto product = productService.getProductById(id);

        return new ResponseEntity<>(
                new ApiResponseDTO<>(Constants.SUCCESS, "Product fetched successfully", product),
                HttpStatus.OK
        );
    }

    // 2. Search Products By Name (Returns a List)
    @Operation(summary = "Search products by name", description = "Returns a list of products that contain the given name (case-insensitive).")
    @ApiResponse(responseCode = "200", description = "List retrieved successfully (can be empty)")
    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<ProductDto>>> getProductsByName(
            @Parameter(description = "Name keyword to search for", example = "Laptop")
            @RequestParam String name) {
        List<ProductDto> products = productService.getProductsByName(name);

        return new ResponseEntity<>(
                new ApiResponseDTO<>(Constants.SUCCESS, "Products fetched successfully", products),
                HttpStatus.OK
        );
    }

    // 3. Update Product
    @Operation(summary = "Update an existing product", description = "Updates product details and refreshes the cache.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found to update", content = @Content),
            @ApiResponse(responseCode = "400", description = "Validation failed", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<ProductDto>> update(
            @Parameter(description = "ID of the product to update", example = "1")
            @PathVariable Long id, @Valid @RequestBody ProductDto dto) {
        ProductDto updatedProduct = productService.updateProduct(id, dto);

        return new ResponseEntity<>(
                new ApiResponseDTO<>(Constants.SUCCESS, "Product updated successfully", updatedProduct),
                HttpStatus.OK
        );
    }

    // 4. Delete Product
    @Operation(summary = "Delete a product", description = "Removes the product from the database and evicts it from the cache.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> delete(
            @Parameter(description = "ID of the product to delete", example = "1")
            @PathVariable Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(
                new ApiResponseDTO<>(Constants.SUCCESS, "Product deleted successfully", null),
                HttpStatus.OK
        );
    }
}