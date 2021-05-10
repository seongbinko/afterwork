package com.hanghae99.afterwork.controller;

import com.hanghae99.afterwork.dto.ProductResponseDto;
import com.hanghae99.afterwork.model.Product;
import com.hanghae99.afterwork.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequiredArgsConstructor
public class SearchController {

    private final ProductRepository productRepository;

    @GetMapping("/api/search")
    public Page<ProductResponseDto> getProductByCategory(@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword, @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("sort") String strSort, @RequestParam(value = "direction", required = false, defaultValue = "desc") String strDirection, @RequestParam(value = "filter", required = false, defaultValue = "total") String strFilter) {

        Sort.Direction direction = Sort.Direction.DESC;

        if (strDirection.toLowerCase(Locale.ROOT).equals("asc"))
        {
            direction = Sort.Direction.ASC;
        }

        boolean isOnline = true;
        boolean isOffline = true;

        if (strFilter.equals("offline")){
            isOnline = false;
        }
        else if(strFilter.equals("online")){
            isOffline = false;
        }

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(direction, strSort));

        keyword = "%" + keyword + "%";

        Page<Product> productList = productRepository.findAllByTitleLikeAndOnlineAndLocation(keyword, isOnline, isOffline, pageRequest);
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