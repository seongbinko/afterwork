package com.hanghae99.afterwork.dto;

import lombok.Data;
import lombok.Setter;

@Setter
@Data
public class ProductByCategoryDto {
    private Long id;
    private String title;
    private int price;
    private String author;
    private String imgUrl;
    private boolean isOnline;
    private String location;
    private int popularity;
    private String status;
    private String siteName;
    private String sitUrl;

    public ProductByCategoryDto(Long id, String title, String author, int price, String priceInfo, String imgUrl, boolean online, String location, int popularity, String status, String siteName, String siteUrl) {

    }
}
