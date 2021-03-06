package com.hanghae99.afterwork.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanghae99.afterwork.dto.ProductResponseDto;
import com.hanghae99.afterwork.entity.*;
import com.hanghae99.afterwork.exception.ResourceNotFoundException;
import com.hanghae99.afterwork.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RecommendControllerTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    InterestRepository interestRepository;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    MockMvc mockMvc;

    Long userId;

    String[] arr;

    @BeforeEach
    void setup() {
        //User ??????
        String email = "wnrhd1082@gmail.com";
        String name = "seongbin";
        User user = User.builder()
                .email(email)
                .name(name)
                .provider(AuthProvider.google)
                .build();
        User saveUser = userRepository.save(user);
        userId = saveUser.getUserId();

        //???????????? ??????
        arr = new String[]{"??????/??????", "??????", "??????", "??????", "??????", "??????"};
        List<Category> list = new ArrayList<>();
        for(String categoryName : arr) {
            list.add(Category.builder()
                    .name(categoryName)
                    .build());
        }
        categoryRepository.saveAll(list);
    }

    @AfterEach
    void tearDown() {
        interestRepository.deleteAll();
        productRepository.deleteAll();
        categoryRepository.deleteAll();
        locationRepository.deleteAll();
        userRepository.deleteAll();
    }

    @WithUserDetails(value = "wnrhd1082@gmail.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @DisplayName("?????? ?????? ?????? - ??????")
    @Test
    void recommendProduct() throws Exception{

        int intSize = 12;
        String locationName = "??????";

        Optional<User> user = userRepository.findById(userId);
        if(!user.isPresent()) {
            throw new ResourceNotFoundException("User", "id", userId);
        }
        User user1 = userRepository.findByUserId(userId);

        Location location = Location.builder()
                .name(locationName)
                .user(user1)
                .build();
        locationRepository.save(location);

        Category category = categoryRepository.findByName(arr[0]).orElse(null);

        for (int i = 0; i < intSize; i++) {
            Product product = Product.builder()
                    .title("title" + i)
                    .isOnline(true)
                    .popularity(1000)
                    .price(50000)
                    .priceInfo("50,000")
                    .siteName("Test")
                    .siteUrl(null)
                    .location(locationName)
                    .status("Y")
                    .category(category)
                    .build();
            productRepository.save(product);
        }

        MvcResult mvcResult = mockMvc.perform(get("/api/recommend")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(authenticated())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        List<ProductResponseDto> productResponseDtoList = objectMapper.readValue(contentAsString, new TypeReference<List<ProductResponseDto>>() {});

        assertEquals(intSize,productResponseDtoList.size());

        for (ProductResponseDto productResponseDto : productResponseDtoList) {
            assertEquals("Y", productResponseDto.getStatus());
        }
    }

    @WithUserDetails(value = "wnrhd1082@gmail.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @DisplayName("?????? ?????? ???????????? - ??????")
    @Test
    void recommendCategoryProduct() throws Exception {

        int intSize = 12;

        Optional<User> user = userRepository.findById(userId);
        if(!user.isPresent()) {
            throw new ResourceNotFoundException("User", "id", userId);
        }

        Category category = categoryRepository.findByName(arr[0]).orElse(null);

        Interest interest = Interest.builder()
                .user(user.get())
                .category(category)
                .build();

        interestRepository.save(interest);

        for (int i = 0; i < 24; i++) {
            String strStatus = "Y";
            if ( i > 11) {
                strStatus = "N";
            }
            Product product = Product.builder()
                    .title("title" + i)
                    .isOnline(true)
                    .popularity(1000)
                    .price(50000)
                    .priceInfo("50,000")
                    .siteName("Test")
                    .siteUrl(null)
                    .status(strStatus)
                    .category(category)
                    .build();

            productRepository.save(product);
        }

        MvcResult mvcResult = mockMvc.perform(get("/api/recommend/category")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(authenticated())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        List<ProductResponseDto> productResponseDtoList = objectMapper.readValue(contentAsString, new TypeReference<List<ProductResponseDto>>() {});

        assertEquals(intSize,productResponseDtoList.size());

        for (ProductResponseDto productResponseDto : productResponseDtoList) {
            assertEquals("Y", productResponseDto.getStatus());
        }
    }

    @DisplayName("????????? ?????? ?????? - ??????")
    @Test
    void recommendOnlineProduct() throws Exception {

        int intSize = 12;

        Category category = categoryRepository.findByName(arr[0]).orElse(null);

        for (int i = 0; i < 24; i++) {
            boolean isRecommendOnline = true;
            if ( i > 11) {
                isRecommendOnline = false;
            }
            Product product = Product.builder()
                    .title("title" + i)
                    .isOnline(true)
                    .popularity(1000)
                    .price(50000)
                    .priceInfo("50,000")
                    .siteName("Test")
                    .siteUrl(null)
                    .status("Y")
                    .category(category)
                    .isRecommendOnline(isRecommendOnline)
                    .build();

            productRepository.save(product);
        }

        MvcResult mvcResult = mockMvc.perform(get("/api/recommend/online")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(unauthenticated())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        List<ProductResponseDto> productResponseDtoList = objectMapper.readValue(contentAsString, new TypeReference<List<ProductResponseDto>>() {});

        assertEquals(intSize,productResponseDtoList.size());

        for (ProductResponseDto productResponseDto : productResponseDtoList) {
            assertEquals(true, productResponseDto.isOnline());
        }
    }

    @DisplayName("???????????? ?????? ?????? - ??????")
    @Test
    void recommendOfflineProduct() throws Exception {

        int intSize = 12;

        Category category = categoryRepository.findByName(arr[0]).orElse(null);

        for (int i = 0; i < 24; i++) {
            boolean isRecommendOffline = true;
            if ( i > 11) {
                isRecommendOffline = false;
            }
            Product product = Product.builder()
                    .title("title" + i)
                    .isOffline(true)
                    .popularity(1000)
                    .price(50000)
                    .priceInfo("50,000")
                    .siteName("Test")
                    .siteUrl(null)
                    .status("Y")
                    .category(category)
                    .isRecommendOffline(isRecommendOffline)
                    .build();

            productRepository.save(product);
        }

        MvcResult mvcResult = mockMvc.perform(get("/api/recommend/offline")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(unauthenticated())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        List<ProductResponseDto> productResponseDtoList = objectMapper.readValue(contentAsString, new TypeReference<List<ProductResponseDto>>() {});

        assertEquals(intSize,productResponseDtoList.size());

        for (ProductResponseDto productResponseDto : productResponseDtoList) {
            assertEquals(true, productResponseDto.isOffline());
        }
    }
}