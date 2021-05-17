package com.hanghae99.afterwork.controller;

import com.hanghae99.afterwork.dto.ProductResponseDto;
import com.hanghae99.afterwork.security.CurrentUser;
import com.hanghae99.afterwork.security.UserPrincipal;
import com.hanghae99.afterwork.service.RecommendService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RecommendController {

    private final RecommendService recommendService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/api/recommend")
    public List<ProductResponseDto> recommendProduct(@CurrentUser UserPrincipal userPrincipal){
        return recommendService.recommendProduct(userPrincipal);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/api/recommend/category")
    public List<ProductResponseDto> recommendCategoryProduct(@CurrentUser UserPrincipal userPrincipal){
        return recommendService.recommendCategoryProduct(userPrincipal);
    }

    @GetMapping("/api/recommend/online")
    public List<ProductResponseDto> recommendOnlineProduct(){
        return recommendService.recommendOnlineProduct();
    }

    @GetMapping("/api/recommend/offline")
    public List<ProductResponseDto> recommendOfflineProduct(){
        return recommendService.recommendOfflineProduct();
    }
}
