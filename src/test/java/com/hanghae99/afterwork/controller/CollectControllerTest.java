package com.hanghae99.afterwork.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanghae99.afterwork.dto.CollectRequestDto;
import com.hanghae99.afterwork.dto.ProductResponseDto;
import com.hanghae99.afterwork.entity.*;
import com.hanghae99.afterwork.exception.ResourceNotFoundException;
import com.hanghae99.afterwork.repository.CategoryRepository;
import com.hanghae99.afterwork.repository.CollectRepository;
import com.hanghae99.afterwork.repository.ProductRepository;
import com.hanghae99.afterwork.repository.UserRepository;
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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CollectControllerTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CollectRepository collectRepository;

    @Autowired
    MockMvc mockMvc;

    Long userId;

    String[] arr;

    @BeforeEach
    void setup() {
        //User 등록
        String email = "wnrhd1082@gmail.com";
        String name = "seongbin";
        User user = User.builder()
                .email(email)
                .name(name)
                .provider(AuthProvider.google)
                .build();
        User saveUser = userRepository.save(user);
        userId = saveUser.getUserId();

        //카테고리 등록
        arr = new String[]{"운동/건강", "요리", "아트", "교육", "공예", "음악"};
        List<Category> list = new ArrayList<>();
        for(String categoryName : arr) {
            list.add(Category.builder()
                    .name(categoryName)
                    .build());
        }
        categoryRepository.saveAll(list);

        Category category = categoryRepository.findByName(arr[0]).orElse(null);
        int intSize = 60;
        for (int i = 0; i < intSize; i++) {
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
                    .build();
            productRepository.save(product);
        }
    }

    @AfterEach
    void tearDown() {
        collectRepository.deleteAll();
        productRepository.deleteAll();
        categoryRepository.deleteAll();
        userRepository.deleteAll();
    }

    @WithUserDetails(value = "wnrhd1082@gmail.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @DisplayName("찜 상품 등록 - 정상")
    @Test
    void collectPost() throws Exception{

        User user1 = userRepository.findByUserId(userId);

        CollectRequestDto collectRequestDto = new CollectRequestDto();
        List<Product> productList = productRepository.findAll();
        collectRequestDto.setProductId(productList.get(0).getProductId());

        ObjectMapper objectMapper = new ObjectMapper();
        String collectInfo = objectMapper.writeValueAsString(collectRequestDto);

        mockMvc.perform(post("/api/collects")
                .content(collectInfo)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(authenticated());

        Product product = productRepository.findByProductId(productList.get(0).getProductId());
        Collect collect = collectRepository.findByProductAndUser(product, user1);

        assertEquals(collect.getProduct().getProductId(), productList.get(0).getProductId());
    }

    @WithUserDetails(value = "wnrhd1082@gmail.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @DisplayName("찜 상품 - 전체 조회")
    @Test
    void collectGetAll() throws Exception {

        User user1 = userRepository.findByUserId(userId);

        List<Product> productList = productRepository.findAll();
        int collectSize = 3;
        for(int i = 0; i < collectSize; i++){
            Product product = productRepository.findByProductId(productList.get(i).getProductId());
            Collect collect = Collect.builder()
                    .product(product)
                    .user(user1)
                    .build();
            collectRepository.save(collect);
        }

        MvcResult mvcResult = mockMvc.perform(get("/api/collects")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(authenticated())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        List<ProductResponseDto> productResponseDtoList = objectMapper.readValue(contentAsString, new TypeReference<List<ProductResponseDto>>() {});

        assertEquals(collectSize,productResponseDtoList.size());

        List<Collect> found = collectRepository.findAllByUser(user1);

        for(int i = 0; i < productResponseDtoList.size(); i++){
            assertEquals(productResponseDtoList.get(i).getCollectId(), found.get(i).getCollectId());
        }
    }

    @WithUserDetails(value = "wnrhd1082@gmail.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @DisplayName("찜 상품 - 개별 삭제")
    @Test
    void collectIndividualDelete() throws Exception {

        User user1 = userRepository.findByUserId(userId);

        List<Product> productList = productRepository.findAll();
        int collectSize = 3;
        for(int i = 0; i < collectSize; i++){
            Product product = productRepository.findByProductId(productList.get(i).getProductId());
            Collect collect = Collect.builder()
                    .product(product)
                    .user(user1)
                    .build();
            collectRepository.save(collect);
        }

        List<Collect> foundList = collectRepository.findAllByUser(user1);

        mockMvc.perform(delete("/api/collects/{collectId}", foundList.get(0).getCollectId()))
                .andExpect(status().isOk())
                .andExpect(authenticated())
                .andReturn();

        Optional<Collect> notCollect = collectRepository.findById(foundList.get(0).getCollectId());
        assertFalse(notCollect.isPresent());
    }

    @WithUserDetails(value = "wnrhd1082@gmail.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @DisplayName("찜 상품 - 전체 삭제")
    @Test
    void collectDeleteAll() throws Exception {

        User user1 = userRepository.findByUserId(userId);

        Long productId = 1L;
        for(int i = 0; i < 3; i++){
            Product product = productRepository.findByProductId(productId);
            Collect collect = Collect.builder()
                    .product(product)
                    .user(user1)
                    .build();
            collectRepository.save(collect);
            productId++;
        }

        mockMvc.perform(delete("/api/collects"))
                .andExpect(status().isOk())
                .andExpect(authenticated());

        List<Collect> found = collectRepository.findAllByUser(user1);
        assertEquals(0, found.size());
    }

    @WithUserDetails(value = "wnrhd1082@gmail.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @DisplayName("찜 상품 - 초과 시")
    @Test
    void collectExceed() throws Exception{


        User user = userRepository.findByUserId(userId);

        CollectRequestDto collectRequestDto = new CollectRequestDto();
        List<Product> productList = productRepository.findAll();

        int collectedSize = 50;
        for(int i = 0; i < collectedSize; i++){
            Collect collect = Collect.builder()
                    .product(productList.get(i))
                    .user(user)
                    .build();
            collectRepository.save(collect);
        }

        collectRequestDto.setProductId(productList.get(50).getProductId());
        ObjectMapper objectMapper = new ObjectMapper();
        String collectInfo = objectMapper.writeValueAsString(collectRequestDto);

        mockMvc.perform(post("/api/collects")
            .content(collectInfo)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(authenticated());
        }
}