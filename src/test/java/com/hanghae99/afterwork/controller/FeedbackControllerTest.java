package com.hanghae99.afterwork.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanghae99.afterwork.dto.FeedbackRequestDto;
import com.hanghae99.afterwork.entity.Feedback;
import com.hanghae99.afterwork.repository.FeedbackRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FeedbackControllerTest {

    @Autowired
    FeedbackRepository feedbackRepository;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("피드백 등록 - 입력값 정상")
    void createFeedback_correct_input() throws Exception {

        String content = "퇴근하고 뭐하지? 서비스가 아주 훌륭합니다.";
        String feedbackContent = getFeedbackJsonBody(content);

        mockMvc.perform(post("/api/feedback")
                .content(feedbackContent)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(unauthenticated());

        Feedback feedback = feedbackRepository.findByContent(content);
        assertNotNull(feedback);
        assertEquals(content, feedback.getContent());
    }
    @Test
    @DisplayName("피드백 등록 - 입력값 오류")
    void createFeedback_wrong_input() throws Exception {
        String content = "";
        String feedbackContent = getFeedbackJsonBody(content);

        mockMvc.perform(post("/api/feedback")
                .content(feedbackContent)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(unauthenticated());

        Feedback feedback = feedbackRepository.findByContent(content);
        assertNull(feedback);
    }

    private String getFeedbackJsonBody(String content) throws JsonProcessingException {
        FeedbackRequestDto feedbackRequestDto = new FeedbackRequestDto();
        feedbackRequestDto.setContent(content);
        return objectMapper.writeValueAsString(feedbackRequestDto);
    }

}