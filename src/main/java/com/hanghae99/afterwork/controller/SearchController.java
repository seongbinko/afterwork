package com.hanghae99.afterwork.controller;

import com.hanghae99.afterwork.dto.ProductResponseDto;
import com.hanghae99.afterwork.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/api/search")
    public Page<ProductResponseDto> getProductByKeyword(@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword, @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("sort") String strSort, @RequestParam(value = "direction", required = false, defaultValue = "desc") String strDirection, @RequestParam(value = "filter", required = false, defaultValue = "total") String strFilter) {
        return searchService.getProductByKeyword(keyword, page, size, strSort, strDirection, strFilter);
    }

}