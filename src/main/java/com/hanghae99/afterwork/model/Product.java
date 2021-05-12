package com.hanghae99.afterwork.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Getter @Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private int price;

    private String priceInfo;

    private String author;

    @Column(length = 1000)
    private String imgUrl;

    private boolean isOnline;

    private boolean isOffline;

    private String location;

    private int popularity;

    private String status;

    @Column(nullable = false)
    private String siteName;

    @Column(length = 1000)
    private String siteUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product")
    @Builder.Default
    List<Collect> collects = new ArrayList<>();
}
