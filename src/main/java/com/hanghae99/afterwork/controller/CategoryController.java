package com.hanghae99.afterwork.controller;

import com.hanghae99.afterwork.dto.CategoryResponseDto;
import com.hanghae99.afterwork.dto.ProductByCategoryDto;
import com.hanghae99.afterwork.model.Category;
import com.hanghae99.afterwork.model.Product;
import com.hanghae99.afterwork.repository.CategoryRepository;
import com.hanghae99.afterwork.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class CategoryController {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public CategoryController(ProductRepository productRepository, CategoryRepository categoryRepository){
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/api/categorys")
    public ResponseEntity getCategorys() {

        List<Category> categoryList = categoryRepository.findAll();
        List<CategoryResponseDto> categoryResponseDtoList =
                categoryList.stream().map(
                        category -> new CategoryResponseDto(
                                category.getId(),
                                category.getName(),
                                category.getImgUrl()
                        )).collect(Collectors.toList());

        return ResponseEntity.ok().body(categoryResponseDtoList);
    }

    @GetMapping("api/categorys/{id}")
    public ResponseEntity productsByCategory(@PathVariable Long id){
        List<Product> productsByCategory = productRepository.findAllByCategoryId(id);
        List<ProductByCategoryDto> productByCategoryResponseDtoList =
                productsByCategory.stream().map(
                        product -> new ProductByCategoryDto(
                                product.getId(),
                                product.getTitle(),
                                product.getAuthor(),
                                product.getPrice(),
                                product.getPriceInfo(),
                                product.getImgUrl(),
                                product.isOnline(),
                                product.getLocation(),
                                product.getPopularity(),
                                product.getStatus(),
                                product.getSiteName(),
                                product.getSiteUrl()
                        )
                ).collect(Collectors.toList());
        return ResponseEntity.ok().body(productsByCategory);
    }

}
