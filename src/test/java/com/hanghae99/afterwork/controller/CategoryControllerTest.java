package com.hanghae99.afterwork.controller;

import com.hanghae99.afterwork.entity.Category;
import com.hanghae99.afterwork.entity.Product;
import com.hanghae99.afterwork.repository.CategoryRepository;
import com.hanghae99.afterwork.repository.ProductRepository;
import com.hanghae99.afterwork.service.CategoryService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerTest {

    @Autowired
    CategoryService categoryService;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    MockMvc mockMvc;

    String[] arr;

    @BeforeEach
    void setup() {
        arr = new String[]{"운동/건강", "요리", "아트", "교육", "공예", "음악"};
        List<Category> list = new ArrayList<>();
        for(String categoryName : arr) {
            list.add(Category.builder()
                    .name(categoryName)
                    .build());
        }
        categoryRepository.saveAll(list);
    }

    @AfterEach
    void tearDown(){
        productRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @Test
    @DisplayName("카테고리 목록 조회 테스트")
    void getCategorys() throws Exception {

        mockMvc.perform(get("/api/categorys")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(unauthenticated());

        Category category = categoryRepository.findByName(arr[0]).orElse(null);
        List<Category> categories = categoryRepository.findAll();
        assertEquals(arr[0], category.getName());
        assertEquals(arr.length, categories.size());
    }

    @Test
    @DisplayName("카테고리별 리스트 - 정상")
    void getProductByCategoryTestSuccess() throws Exception{

        String strPage = "0";
        String strSize = "12";
        String strSort = "price";
        String strDirection = "asc";
        String strFilter = "total";
        String strSitename = "탈잉,마이비스킷,클래스101,하비인더박스,아이디어스,하비풀,모카클래스";
        String strLocation = "전체,전체";

        List<Category> categoryList = categoryRepository.findAll();

        for (int i = 0; i < 24; i++) {
            Product product = Product.builder()
                    .title("title" + i)
                    .isOnline(true)
                    .popularity(1000)
                    .price(50000)
                    .priceInfo("50,000")
                    .siteName("탈잉")
                    .siteUrl(null)
                    .status("Y")
                    .category(categoryList.get(categoryList.size() - 1))
                    .build();

            productRepository.save(product);
        }

        ResultActions resultActions = mockMvc.perform(get("/api/categorys/" + categoryList.get(categoryList.size() - 1).getCategoryId())
                .param("page",strPage)
                .param("size",strSize)
                .param("sort",strSort)
                .param("direction",strDirection)
                .param("filter",strFilter)
                .param("sitename",strSitename)
                .param("location",strLocation)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(unauthenticated());

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("content").exists())
                .andExpect(jsonPath("pageable").exists())
                .andExpect(jsonPath("numberOfElements").value(strSize));
    }

    @Test
    @DisplayName("카테고리별 리스트 - 실패")
    void getProductByCategoryTestFail() throws Exception{

        String strPage = "0";
        String strSize = "12";
        String strSort = "price";
        String strDirection = "asc";
        String strFilter = "total";
        String strSitename = "탈잉,마이비스킷,클래스101,하비인더박스,아이디어스,하비풀,모카클래스";
        String strLocation = "전체,전체";

        List<Category> categoryList = categoryRepository.findAll();

        for (int i = 0; i < 24; i++) {
            Product product = Product.builder()
                    .title("title" + i)
                    .isOnline(true)
                    .popularity(1000)
                    .price(50000)
                    .priceInfo("50,000")
                    .siteName("탈잉")
                    .siteUrl(null)
                    .status("Y")
                    .category(categoryList.get(categoryList.size() - 1))
                    .build();

            productRepository.save(product);
        }

        mockMvc.perform(get("/api/categorys/" + categoryList.get(categoryList.size() - 1).getCategoryId())
                .param("page",strPage)
                .param("size",strSize)
                .param("sort",strSort)
                .param("direction",strDirection)
                .param("sitename",strSitename)
                .param("location",strLocation)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(unauthenticated());
    }
}