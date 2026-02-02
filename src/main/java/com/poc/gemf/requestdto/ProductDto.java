package com.poc.gemf.requestdto;


import lombok.Data;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class ProductDto implements Serializable {
    private Long id;
    @NotBlank(message = "Product name cannot be empty")
    private String name;
    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price must be positive")
    private Double price;
}