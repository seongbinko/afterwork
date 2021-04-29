package com.hanghae99.afterwork.dto;

import com.hanghae99.afterwork.model.Interest;
import com.hanghae99.afterwork.model.Product;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CategoryResponseDto {

    private Long categoryId;

    private String name;

    private String imgUrl;

    private List<Interest> interests = new ArrayList<>();

    private List<Product> products = new ArrayList<>();

    public CategoryResponseDto(){

    }

    public CategoryResponseDto(Long categoryId, String name, String imgUrl)
    {
        this.categoryId = categoryId;
        this.name = name;
        this.imgUrl = imgUrl;
    }

}
