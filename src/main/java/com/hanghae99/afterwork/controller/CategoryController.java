package com.hanghae99.afterwork.controller;

import com.hanghae99.afterwork.dto.CategoryResponseDto;
import com.hanghae99.afterwork.dto.ProductResponseDto;
import com.hanghae99.afterwork.model.Category;
import com.hanghae99.afterwork.model.Product;
import com.hanghae99.afterwork.repository.CategoryRepository;
import com.hanghae99.afterwork.repository.ProductRepository;
import com.hanghae99.afterwork.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @GetMapping("/api/categorys")
    public ResponseEntity getCategorys() {

        List<Category> categoryList = categoryRepository.findAll();
        List<CategoryResponseDto> categoryResponseDtoList =
                categoryList.stream().map(
                        category -> new CategoryResponseDto(
                                category.getCategoryId(),
                                category.getName(),
                                category.getImgUrl()
                        )).collect(Collectors.toList());

        return ResponseEntity.ok().body(categoryResponseDtoList);
    }

    @GetMapping("/api/categorys/{id}")
    public Page<ProductResponseDto> getProductByCategory(@PathVariable("id") Long categoryId, @RequestParam("page") int page, @RequestParam("size") int size) {

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        Category category = categoryRepository.findById(categoryId).orElse(null);
        Page<Product> productList = productRepository.findAllByCategory(category, pageRequest);
        Page<ProductResponseDto> productResponseDtoList =
                productList.map(
                        product -> new ProductResponseDto(
                                product.getProductId(),
                                product.getTitle(),
                                product.getPrice(),
                                product.getPriceInfo(),
                                product.getAuthor(),
                                product.getImgUrl(),
                                product.isOnline(),
                                product.getLocation(),
                                product.getPopularity(),
                                product.getStatus(),
                                product.getSiteName(),
                                product.getSiteUrl()
                        ));

        return productResponseDtoList;
    }

}
