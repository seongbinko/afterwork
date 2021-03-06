package com.hanghae99.afterwork.service;

import com.hanghae99.afterwork.dto.CategoryResponseDto;
import com.hanghae99.afterwork.dto.ProductByCategoryRequestDto;
import com.hanghae99.afterwork.dto.ProductResponseDto;
import com.hanghae99.afterwork.entity.Category;
import com.hanghae99.afterwork.entity.Product;
import com.hanghae99.afterwork.repository.CategoryRepository;
import com.hanghae99.afterwork.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ProductService productService;

    public List<CategoryResponseDto> getCategorys(){

        List<Category> categoryList = categoryRepository.findAll();

        return categoryList.stream().map(
                category -> new CategoryResponseDto(
                        category.getCategoryId(),
                        category.getName(),
                        category.getImgUrl()
                )).collect(Collectors.toList());
    }

    public Page<ProductResponseDto> getProductByCategory(Long categoryId, ProductByCategoryRequestDto productByCategoryRequestDto){

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

        String strDirection = productByCategoryRequestDto.getDirection();
        String strFilter = productByCategoryRequestDto.getFilter();
        String strSiteName = productByCategoryRequestDto.getSitename();
        String strSort = productByCategoryRequestDto.getSort();
        int page = productByCategoryRequestDto.getPage();
        int size = productByCategoryRequestDto.getSize();
        String strLocation = productByCategoryRequestDto.getLocation();

        if (strLocation.split(",")[1].equals("??????")){

            strLocation = "%" + strLocation.split(",")[0] + "%";
        }
        else{
            strLocation = "%" + strLocation.split(",")[1] + "%";
        }

        //???????????? ????????????
        if (strDirection.toLowerCase(Locale.ROOT).equals("asc")) {
            direction = Sort.Direction.ASC;
        }

        //????????? ????????????
        if (strFilter.equals("offline")){
            isOnline = false;
        }
        else if(strFilter.equals("online")){
            isOffline = false;
        }

        //????????? ??? ?????? ?????? ??????
        if (strSiteName.contains("??????")){
            isTaling = true;
        }
        if (strSiteName.contains("?????????101")){
            isClass101 = true;
        }
        if (strSiteName.contains("??????????????????")){
            isHobyInTheBox = true;
        }
        if (strSiteName.contains("???????????????")){
            isIdus = true;
        }
        if (strSiteName.contains("???????????????")){
            isMybiskit = true;
        }
        if (strSiteName.contains("???????????????")){
            isMochaClass = true;
        }
        if (strSiteName.contains("?????????")){
            isHobbyful = true;
        }
        
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(direction, strSort).and(Sort.by(Sort.Direction.ASC,"title")));

        Category category = categoryRepository.findById(categoryId).orElse(null);
        Page<Product> productList = productRepository.findAllByCategoryAndOnline(category, isOnline, isOffline, isTaling,
                isClass101, isHobyInTheBox, isIdus, isMybiskit, isMochaClass, isHobbyful, strLocation, pageRequest);

        return productService.createProductResponseDtoList(productList);

    }

}
