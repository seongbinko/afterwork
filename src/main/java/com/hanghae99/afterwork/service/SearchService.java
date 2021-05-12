package com.hanghae99.afterwork.service;

import com.hanghae99.afterwork.dto.ProductResponseDto;
import com.hanghae99.afterwork.model.Product;
import com.hanghae99.afterwork.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Transactional
public class SearchService {

    private final ProductRepository productRepository;

    public Page<ProductResponseDto> getProductByKeyword(String keyword, int page, int size, String strSort, String strDirection, String strFilter){

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

        return productList.map(
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
    }
}
