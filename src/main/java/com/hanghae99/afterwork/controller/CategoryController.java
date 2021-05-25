package com.hanghae99.afterwork.controller;

import com.hanghae99.afterwork.dto.ProductByCategoryRequestDto;
import com.hanghae99.afterwork.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/api/categorys")
    public ResponseEntity getCategorys() {

        return ResponseEntity.ok().body(categoryService.getCategorys());
    }

    @GetMapping("/api/categorys/{id}")
    public ResponseEntity getProductByCategory(@PathVariable("id") Long categoryId, @Valid @ModelAttribute ProductByCategoryRequestDto productByCategoryRequestDto, Errors errors) {

        if(errors.hasErrors()){
            return ResponseEntity.badRequest().body(errors.getAllErrors());
        }

        return ResponseEntity.ok(categoryService.getProductByCategory(categoryId, productByCategoryRequestDto));
    }

}
