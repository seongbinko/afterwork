package com.hanghae99.afterwork.dto;

import com.hanghae99.afterwork.model.Interest;
import com.hanghae99.afterwork.model.Product;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CategoryResponseDto {

    private Long id;

    private String name;

    private String imgUrl;

    private List<Interest> interests = new ArrayList<>();

    private List<Product> products = new ArrayList<>();

    public CategoryResponseDto(){

    }

    public CategoryResponseDto(Long id, String name, String imgUrl)
    {
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
    }

}
