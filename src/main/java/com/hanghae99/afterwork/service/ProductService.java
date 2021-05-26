package com.hanghae99.afterwork.service;

import com.hanghae99.afterwork.dto.ProductResponseDto;
import com.hanghae99.afterwork.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    public List<ProductResponseDto> createProductResponseDtoList (List<Product> productList){

        return productList.stream().map(
                product -> new ProductResponseDto(
                        product.getProductId(),
                        product.getTitle(),
                        product.getPrice(),
                        product.getPriceInfo(),
                        product.getAuthor(),
                        product.getImgUrl(),
                        product.isOnline(),
                        product.isOffline(),
                        product.getLocation(),
                        product.getPopularity(),
                        product.getStatus(),
                        product.getSiteName(),
                        product.getSiteUrl()
                )).collect(Collectors.toList());
    }

    public Page<ProductResponseDto> createProductResponseDtoList (Page<Product> productList){

        return productList.map(
                product -> new ProductResponseDto(
                        product.getProductId(),
                        product.getTitle(),
                        product.getPrice(),
                        product.getPriceInfo(),
                        product.getAuthor(),
                        product.getImgUrl(),
                        product.isOnline(),
                        product.isOffline(),
                        product.getLocation(),
                        product.getPopularity(),
                        product.getStatus(),
                        product.getSiteName(),
                        product.getSiteUrl()
                ));
    }
}
