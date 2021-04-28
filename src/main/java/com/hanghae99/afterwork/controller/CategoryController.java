package com.hanghae99.afterwork.controller;

import com.hanghae99.afterwork.dto.CategoryResponseDto;
import com.hanghae99.afterwork.model.Category;
import com.hanghae99.afterwork.repository.CategoryRepository;
import com.hanghae99.afterwork.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;

    @GetMapping("/api/categorys")
    public ResponseEntity getCurrentUser() {

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

}
