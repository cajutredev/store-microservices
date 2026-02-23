package com.store.product_service.controller;

import com.store.product_service.dto.ProductDto;
import com.store.product_service.service.ProductService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@NoArgsConstructor
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDto>> findAll() {
        return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        return ResponseEntity.ok(productService.create(productDto));
    }

    @PutMapping
    public ResponseEntity<Boolean> updateProduct(@RequestBody ProductDto productDto) {
        return productService.update(productDto)
                ? ResponseEntity.ok(true)
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteProduct(@RequestBody ProductDto productDto) {
        return productService.delete(productDto.getId())
                ? ResponseEntity.ok(true)
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductDto>> searchProductByStock(@RequestParam String keyword) {
        return new ResponseEntity<>(productService.searchProductByStock(keyword), HttpStatus.OK);
    }
}
