package com.hanghae99.afterwork.controller;

import com.hanghae99.afterwork.dto.ProductResponseDto;
import com.hanghae99.afterwork.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/api/categorys")
    public ResponseEntity getCategorys() {

        return ResponseEntity.ok().body(categoryService.getCategorys());
    }

    @GetMapping("/api/categorys/{id}")
    public Page<ProductResponseDto> getProductByCategory(@PathVariable("id") Long categoryId, @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("sort") String strSort, @RequestParam(value = "direction", required = false, defaultValue = "desc") String strDirection, @RequestParam(value = "filter", required = false, defaultValue = "total") String strFilter) {

        return categoryService.getProductByCategory(categoryId, page, size, strSort, strDirection, strFilter);
    }

}
