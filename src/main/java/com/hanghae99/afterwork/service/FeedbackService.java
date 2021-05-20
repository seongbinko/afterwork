package com.hanghae99.afterwork.service;

import com.hanghae99.afterwork.dto.FeedbackRequestDto;
import com.hanghae99.afterwork.entity.Feedback;
import com.hanghae99.afterwork.repository.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;

    public void createFeedback(FeedbackRequestDto feedbackRequestDto){

        String content = feedbackRequestDto.getContent();

        Feedback feedback = Feedback.builder()
                .content(content)
                .build();

        feedbackRepository.save(feedback);
    }

}
