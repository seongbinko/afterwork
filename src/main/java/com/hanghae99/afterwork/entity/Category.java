package com.hanghae99.afterwork.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categorys")
@Getter @Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Column(nullable = false)
    private String name;

    private String imgUrl;

    @OneToMany(mappedBy = "category")
    @Builder.Default
    private List<Interest> interests = new ArrayList<>();

    @OneToMany(mappedBy = "category")
    @Builder.Default
    private List<Product> products = new ArrayList<>();
}
