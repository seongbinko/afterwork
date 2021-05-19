package com.hanghae99.afterwork.service;

import com.hanghae99.afterwork.dto.CategoryResponseDto;
import com.hanghae99.afterwork.dto.ProductResponseDto;
import com.hanghae99.afterwork.model.Category;
import com.hanghae99.afterwork.model.Product;
import com.hanghae99.afterwork.repository.CategoryRepository;
import com.hanghae99.afterwork.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public List<CategoryResponseDto> getCategorys(){

        List<Category> categoryList = categoryRepository.findAll();

        return categoryList.stream().map(
                category -> new CategoryResponseDto(
                        category.getCategoryId(),
                        category.getName(),
                        category.getImgUrl()
                )).collect(Collectors.toList());
    }


    public Page<ProductResponseDto> getProductByCategory(Long categoryId, int page, int size, String strSort, String strDirection, String strFilter, String strSiteName){

        Sort.Direction direction = Sort.Direction.DESC;
        boolean isOnline = true;
        boolean isOffline = true;
        boolean isTaling = false;
        boolean isClass101 = false;
        boolean isHobyInTheBox = false;
        boolean isIdus = false;
        boolean isMybiskit = false;
        boolean isMochaClass = false;
        boolean isHobbyful = false;


        //오름차순 내림차순
        if (strDirection.toLowerCase(Locale.ROOT).equals("asc")) {
            direction = Sort.Direction.ASC;
        }

        //온라인 오프라인
        if (strFilter.equals("offline")){
            isOnline = false;
        }
        else if(strFilter.equals("online")){
            isOffline = false;
        }

        //사이트 별 검색 필터 조건
        if (strSiteName.contains("전체")){
            isTaling = true;
            isClass101 = true;
            isHobyInTheBox = true;
            isIdus = true;
            isMybiskit = true;
            isMochaClass = true;
            isHobbyful = true;
        }
        else{
            if (strSiteName.contains("탈잉")){
                isTaling = true;
            }
            if (strSiteName.contains("클래스101")){
                isClass101 = true;
            }
            if (strSiteName.contains("하비인더박스")){
                isHobyInTheBox = true;
            }
            if (strSiteName.contains("아이디어스")){
                isIdus = true;
            }
            if (strSiteName.contains("마이비스킷")){
                isMybiskit = true;
            }
            if (strSiteName.contains("모카클래스")){
                isMochaClass = true;
            }
            if (strSiteName.contains("하비풀")){
                isHobbyful = true;
            }
        }
        
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(direction, strSort).and(Sort.by(Sort.Direction.ASC,"title")));

        Category category = categoryRepository.findById(categoryId).orElse(null);
        Page<Product> productList = productRepository.findAllByCategoryAndOnline(category, isOnline, isOffline, isTaling,
                isClass101, isHobyInTheBox, isIdus, isMybiskit, isMochaClass, isHobbyful, pageRequest);

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
