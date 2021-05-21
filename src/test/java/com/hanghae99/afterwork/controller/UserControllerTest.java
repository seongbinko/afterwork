package com.hanghae99.afterwork.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanghae99.afterwork.dto.UserRequestDto;
import com.hanghae99.afterwork.dto.UserResponseDto;
import com.hanghae99.afterwork.entity.AuthProvider;
import com.hanghae99.afterwork.entity.User;
import com.hanghae99.afterwork.exception.ResourceNotFoundException;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MockMvc mockMvc;

    Long userId;

    @BeforeEach
    void setup() {
        String email = "wnrhd1082@gmail.com";
        String name = "seongbin";
        User user = User.builder()
                .email(email)
                .name(name)
                .provider(AuthProvider.google)
                .build();
        User saveUser = userRepository.save(user);
        userId = saveUser.getUserId();
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @WithUserDetails(value = "wnrhd1082@gmail.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @DisplayName("회원 정보 불러오기 - 입력값 정상")
    @Test
    void getCurrentUser() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/user/me"))
                .andExpect(status().isOk())
                .andExpect(authenticated())
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        UserResponseDto userResponseDto = objectMapper.readValue(contentAsString, UserResponseDto.class);
        assertEquals(userId, userResponseDto.getUserId());
    }

    @WithUserDetails(value = "wnrhd1082@gmail.com", setupBefore = TestExecutionEvent.TEST_EXECUTION) //BeforEach 실행 이후에 실행시킨다
    @DisplayName("프로필 수정하기 - 입력값 정상")
    @Test
    void modifyUser() throws Exception {
        String offTime = "18:00:28";
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setOffTime(offTime);
        
        ObjectMapper objectMapper = new ObjectMapper();
        String userInfo = objectMapper.writeValueAsString(userRequestDto);

        mockMvc.perform(post("/api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userInfo))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(userId)))
                .andExpect(authenticated());

        Optional<User> user = userRepository.findById(userId);
        if(!user.isPresent()) {
            throw new ResourceNotFoundException("User", "id", userId);
        }
        assertEquals(offTime, user.get().getOffTime());
    }

    @WithUserDetails(value = "wnrhd1082@gmail.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @DisplayName("프로필 삭제 - 입력값 정상")
    @Test
    void deleteUser() throws Exception {
        mockMvc.perform(delete("/api/user"))
                .andExpect(status().isOk())
                .andExpect(authenticated());
        Optional<User> notUser = userRepository.findById(userId);
        assertFalse(notUser.isPresent());
    }
}