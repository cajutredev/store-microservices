package com.store.product_service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ProductDto {

    private Long id;
    private String productName;
    private String productDescription;
    private String productPrice;
    private Integer stock;
    private String category;
    private String productImageUrl;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
