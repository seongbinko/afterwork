package com.hanghae99.afterwork.controller;

import com.hanghae99.afterwork.model.Category;
import com.hanghae99.afterwork.repository.CategoryRepository;
import com.hanghae99.afterwork.service.CategoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerTest {

    @Autowired
    CategoryService categoryService;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("카테고리 목록 조회 테스트")
    void getCategorys() throws Exception {

        String[] arr = {"운동/건강", "요리", "아트", "교육", "공예", "음악"};
        List<Category> list = new ArrayList<>();
        for(String categoryName : arr) {
            list.add(Category.builder()
                    .name(categoryName)
                    .build());
        }
        categoryRepository.saveAll(list);

        mockMvc.perform(get("/api/categorys")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(unauthenticated());

        Category category = categoryRepository.findByName(arr[0]).orElse(null);
        List<Category> categories = categoryRepository.findAll();
        assertEquals(arr[0], category.getName());
        assertEquals(arr.length, categories.size());
    }
}