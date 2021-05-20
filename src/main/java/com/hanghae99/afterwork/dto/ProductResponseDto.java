package com.hanghae99.afterwork.dto;

import com.hanghae99.afterwork.entity.Category;
import com.hanghae99.afterwork.entity.Collect;
import com.hanghae99.afterwork.entity.Product;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProductResponseDto {

    private Long productId;
    private Long collectId;
    private String title;
    private int price;
    private String priceInfo;
    private String author;
    private String imgUrl;
    private boolean isOnline;
    private boolean isOffline;
    private String location;
    private int popularity;
    private String status;
    private String siteName;
    private String siteUrl;
    private Category category;

    List<Collect> collects = new ArrayList<>();

    public ProductResponseDto(){

    }

    public ProductResponseDto(Long productId, String title, int price, String priceInfo, String author, String imgUrl,
                              boolean isOnline, boolean isOffline,String location, int popularity, String status, String siteName,
                              String siteUrl)
    {
        this.productId = productId;
        this.title = title;
        this.price = price;
        this.priceInfo = priceInfo;
        this.author = author;
        this.imgUrl = imgUrl;
        this.isOnline = isOnline;
        this.isOffline = isOffline;
        this.location = location;
        this.popularity = popularity;
        this.status = status;
        this.siteName = siteName;
        this.siteUrl = siteUrl;
    }

    public ProductResponseDto(Product product, Long collectId)
    {
        this.productId = product.getProductId();
        this.collectId = collectId;
        this.title = product.getTitle();
        this.price = product.getPrice();
        this.priceInfo = product.getPriceInfo();
        this.author = product.getAuthor();
        this.imgUrl = product.getImgUrl();
        this.isOnline = product.isOnline();
        this.location = product.getLocation();
        this.popularity = product.getPopularity();
        this.status = product.getStatus();
        this.siteName = product.getSiteName();
        this.siteUrl = product.getSiteUrl();
    }

}
