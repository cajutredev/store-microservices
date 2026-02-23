package com.store.product_service.service;


import com.store.product_service.dto.ProductDto;
import com.store.product_service.entity.Product;
import com.store.product_service.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<ProductDto> findAll() {
        return productRepository.findByIsActiveTrue()
                .stream()
                .map(p -> modelMapper.map(p, ProductDto.class))
                .collect(Collectors.toList());
    }

    public ProductDto create(ProductDto productDto) {
        Product product = productRepository.save(modelMapper.map(productDto, Product.class));
        return modelMapper.map(product, ProductDto.class);
    }

    public Boolean update(ProductDto productDto) {
        return productRepository.findById(productDto.getId())
                .map(product1 -> {
                    modelMapper.map(productDto, product1);
                    productRepository.save(product1);
                    return true;
                }).orElse(false);
    }

    public Boolean delete(Long id) {
        if (productRepository.existsById(id)) {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            product.setIsActive(false);
            productRepository.save(product);
            return true;
        }
        return false;
    }

    public List<ProductDto> searchProductByStock(String keyword) {
        return productRepository.searchProductByStock(keyword)
                .stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .collect(Collectors.toList());
    }
}
