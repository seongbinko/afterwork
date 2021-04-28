package com.hanghae99.afterwork.controller;

import com.hanghae99.afterwork.repository.ProductRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ProductController {

    private final ProductRepository productRepository;

}
