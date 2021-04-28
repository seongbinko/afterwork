package com.hanghae99.afterwork.dto;

import com.hanghae99.afterwork.model.Category;
import com.hanghae99.afterwork.model.Collect;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProductResponseDto {

    private Long id;
    private String title;
    private int price;
    private String priceInfo;
    private String author;
    private String imgUrl;
    private boolean isOnline;
    private String location;
    private int popularity;
    private String status;
    private String siteName;
    private String siteUrl;
    private Category category;

    List<Collect> collects = new ArrayList<>();

    public ProductResponseDto(Long id, String title, int price, String priceInfo, String author, String imgUrl,
                              boolean isOnline, String location, int popularity, String status, String siteName,
                              String siteUrl)
    {
        this.id = id;
        this.title = title;
        this.price = price;
        this.priceInfo = priceInfo;
        this.author = author;
        this.imgUrl = imgUrl;
        this.isOnline = isOnline;
        this.location = location;
        this.popularity = popularity;
        this.status = status;
        this.siteName = siteName;
        this.siteUrl = siteUrl;

    }

}
