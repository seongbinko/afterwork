package com.hanghae99.afterwork.controller;

import com.hanghae99.afterwork.dto.ProductByKeywordRequestDto;
import com.hanghae99.afterwork.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/api/search")
    public ResponseEntity<Object> getProductByKeyword(@Valid @ModelAttribute ProductByKeywordRequestDto productByKeywordRequestDto, Errors errors) {

        if(errors.hasErrors()){
            return ResponseEntity.badRequest().body(errors.getAllErrors());
        }

        return ResponseEntity.ok(searchService.getProductByKeyword(productByKeywordRequestDto));
    }

}